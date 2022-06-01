package com.choong.spr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.choong.spr.domain.BoardDto;
import com.choong.spr.domain.MemberDto;
import com.choong.spr.mapper.MemberMapper;
import com.choong.spr.mapper.ReplyMapper;

@Service
public class MemberService {

	@Autowired
	private MemberMapper mapper;
	
	@Autowired
	private ReplyMapper replyMapper;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public boolean addMember(MemberDto member) {
		
		// 평문 password를 암호화(encoding)
		String encodedPassword = passwordEncoder.encode(member.getPassword());
		
		// 암호화된 password를 다시 세팅
		member.setPassword(encodedPassword);
		
		// insert member 
		int cnt1 = mapper.insertMember(member);
		
		// insert auth
		int cnt2 = mapper.insertAuth(member.getId(),"ROLE_USER");
		
		return cnt1 == 1 && cnt2 == 1;
	}

	public boolean hasMemberId(String id) {
		
		return mapper.countMemberId(id) > 0;
	}

	public boolean hasMemberEmail(String email) {
		
		return mapper.countEmail(email) > 0;
	}

	public boolean hasMemberNickName(String nickName) {
		
		return mapper.countNickName(nickName) > 0;
	}

	public List<MemberDto> listMember() {
		
		return mapper.selectAllMember();
	}

	public MemberDto getMemberById(String id) {

		return mapper.selectMemberById(id);
	}

	@Transactional
	public boolean removeMember(MemberDto dto) {
		MemberDto member = mapper.selectMemberById(dto.getId());
		
		String rawPW = dto.getPassword();
		String encodedPW = member.getPassword();
		
		if(passwordEncoder.matches(rawPW, encodedPW)) {
			// 권한 테이블 삭제
			mapper.deleteAuth(dto.getId());
			
			// 이 멤버가 쓴 댓글 삭제
			replyMapper.deleteReplyByMemberId(dto.getId());
			
			// 이 멤버가 쓴 게시글 삭제
			List<BoardDto> boardList = boardService.listByMemberId(dto.getId());
			for(BoardDto board : boardList) {
				boardService.deleteBoard(board.getId());
			}
						
			// 멤버 테이블 삭제
			int cnt2 = mapper.deleteMemberById(dto.getId());
				
			return cnt2 == 1;
		}
		
		return false;
	}

	public boolean modifyMember(MemberDto dto, String oldPassword) {
		// db에서 member 읽어서 
		MemberDto oldMember = mapper.selectMemberById(dto.getId());
		
		String encodedPW = oldMember.getPassword();
		// 기존 password가 일치할 때만 계속 진행
		if(passwordEncoder.matches(oldPassword, encodedPW)) {
			dto.setPassword(passwordEncoder.encode(dto.getPassword()));
			return mapper.updateMember(dto) == 1;
		}
				
		return false;
	}

	public void initPassword(String id) {
		
		String pw = passwordEncoder.encode(id);
		
		mapper.updatePasswordById(id, pw);
	}

}
