package com.choong.spr.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.choong.spr.domain.BoardDto;
import com.choong.spr.mapper.BoardMapper;
import com.choong.spr.mapper.ReplyMapper;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class BoardService {

	@Autowired
	private BoardMapper mapper;
	
	@Autowired
	private ReplyMapper replyMapper;
	
	private S3Client s3; // S3Client객체는 서비스 객체가 만들어질때 바로 구성이 되어야 한다.
	
	@Value("${aws.s3.bucketName}") // property파일에서 읽어올 수 있게 어노테이션 세팅
	private String bucketName;
	
	public List<BoardDto> listBoard(String type, String keyword) {
		// TODO Auto-generated method stub
		return mapper.selectBoardAll(type, "%" + keyword + "%");
	}
	
	@PostConstruct // 서비스 객체 생성되자마자 실행시킬 수 있는 어노테이션
	public void init() {
		
		Region region = Region.AP_NORTHEAST_2; // 지역 세팅, 서울은 AP_NORTHEAST_2
		this.s3 = S3Client.builder().region(region).build();
	}
	
	@PreDestroy // 서비스 객체 종료되기 바로 전에 실행시킬 수 있는 어노테이션
	public void destroy() {
		this.s3.close();
	}
	
	@Transactional
	public boolean insertBoard(BoardDto board, MultipartFile[] files) {
//		board.setInserted(LocalDateTime.now());
		
		// 게시글 등록
		int cnt = mapper.insertBoard(board);
		
		// 파일 등록
		addFiles(board.getId(), files);
		
		return cnt == 1;
	}

	private void addFiles(int id, MultipartFile[] files) {
		if(files != null) {
			for(MultipartFile file : files) {
				if(file.getSize() > 0) {
					mapper.insertFile(id, file.getOriginalFilename());
					// saveFile(board.getId(), file); // 파일 시스템에 저장. aws에 저장하는것으로 바꿀예정
					saveFileAwsS3(id, file); // aws S3에 업로드
				}
			}
		}
	}

	private void saveFileAwsS3(int id, MultipartFile file) {
		String key = "board/" + id + "/" + file.getOriginalFilename();
		
		PutObjectRequest putObjectRequest = PutObjectRequest.builder()
											.acl(ObjectCannedACL.PUBLIC_READ) // S3의 권한 설정
											.bucket(bucketName) // aws S3에 만들어놓은 bucket세팅 
											.key(key)	// 객체, 폴더와 파일명으로 구성됨.
											.build();
		
		// 파일의 전송과 크기를 설정 MultipartFile객체의 정보를 통해 구성
		RequestBody requestBody;
		try {
			requestBody = RequestBody.fromInputStream(file.getInputStream(), file.getSize());
			s3.putObject(putObjectRequest, requestBody);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e); // exception을 전파. 
										   // Transactional 어노테이션으로 인해서 메소드가 멈추지않고 계속 실행 되기 때문.
		}
		
		
		
	}

	// 파일 시스템에서 파일 저장 시 쓰는 내부메소드
	private void saveFile(int id, MultipartFile file) {
		// 디렉토리 만들기(파일 있을 시 만들어야 함(?)) if(file.getSize() > 0) 일때
		String pathStr = "C:/imgtmp/board/" + id + "/";
		File path = new File(pathStr);
		path.mkdirs();
		
		// 작성할 파일
		File des = new File(pathStr + file.getOriginalFilename());
		try {
			// 파일 저장
			file.transferTo(des);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	public BoardDto getBoardById(int id) {
		// TODO Auto-generated method stub
		BoardDto board = mapper.selectBoardById(id);
		List<String> flieNames = mapper.selectFileByBoardId(id);
		
		board.setFileName(flieNames);
		return board;
	}
	
	@Transactional
	public boolean updateBoard(BoardDto dto, List<String> removeFileList, MultipartFile[] addFileList) {
		if(removeFileList != null) {
			//removeFiles(dto.getId(), removeFileList);
			
			for(String fileName : removeFileList) {
				deleteFromAwsS3(dto.getId(), fileName);
				mapper.deleteFileByBoardIdAndFileName(dto.getId(), fileName);
			}
			
		}
		
		// TODO Auto-generated method stub
		// File 테이블에 추가된 파일 insert
		if(addFileList != null) {
			// s3에 upload
			addFiles(dto.getId(), addFileList);
		}
				
		// Board 테이블 update
		
		int cnt = mapper.updateBoard(dto);
		return cnt == 1;
	}

	@Transactional
	public boolean deleteBoard(int id) {
		// 파일 목록 읽기
		List<String> fileList = mapper.selectFileByBoardId(id);
		
		// 실제 파일 삭제(파일 시스템)
		/*if(fileName != null && !fileName.isEmpty()) {
			String folder = "C:/imgtmp/board/" + id + "/";
			String path= folder + fileName;
			
			File file = new File(path);
			file.delete();
			
			File dir = new File(folder);
			dir.delete();
			
		}*/
		
		// 실제 파일 삭제(aws S3)
		removeFiles(id, fileList);
		
		// 댓글테이블 삭제		
		replyMapper.deleteByBoardId(id);
		
		return mapper.deleteBoard(id) == 1;
	}

	private void removeFiles(int id, List<String> fileList) {
		for(String fileName : fileList) {
			deleteFromAwsS3(id, fileName);
		}
		
		// 파일테이블 삭제
		mapper.deleteFileByBoardId(id);
	}

	// aws에서 파일삭제를 하는 내부 메소드
	private void deleteFromAwsS3(int id, String fileName) {
		
			String key = "board/" + id + "/" + fileName;
			
			DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
					.bucket(bucketName) 		
					.key(key)
					.build();
			s3.deleteObject(deleteObjectRequest);
	}

	// 가입한 유저가 쓴 글 찾는 메소드(가입한 유저 삭제시 필요) 
	public List<BoardDto> listByMemberId(String memberId) {
		// TODO Auto-generated method stub
		return mapper.selectBoardByMemberId(memberId);
	}

}





