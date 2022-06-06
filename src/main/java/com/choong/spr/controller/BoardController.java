package com.choong.spr.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.choong.spr.domain.BoardDto;
import com.choong.spr.domain.ReplyDto;
import com.choong.spr.service.BoardService;
import com.choong.spr.service.ReplyService;

@Controller
@RequestMapping("board")
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	@Autowired
	private ReplyService replyService;

	@RequestMapping("list")
	public void list(@RequestParam(name = "keyword", defaultValue = "") String keyword,
					 @RequestParam(name = "type", defaultValue = "") String type, Model model) {
		List<BoardDto> list = service.listBoard(type, keyword);
		model.addAttribute("boardList", list);
	}
	
	@GetMapping("insert")
	public void insert() {
		
	}
	
	@PostMapping("insert")
	public String insert(BoardDto board, MultipartFile[] file, Principal principal, RedirectAttributes rttr) {
		
		//	System.out.println(file.getOriginalFilename()); // 파일명
		//	System.out.println(file.getSize()); // 파일 크기
		
//		if(file.getSize() > 0) {
//			board.setFileName(file.getOriginalFilename());
//		}
		
		if(file != null) {
			List<String> fileList = new ArrayList<>();
			
			for(MultipartFile f : file) {
				fileList.add(f.getOriginalFilename());
			}
			board.setFileName(fileList);
		}
		
		// principal객체에 로그인한 유저의 정보들이 담김
		board.setMemberId(principal.getName());// 로그인한 사람이름 세팅
		
		boolean success = service.insertBoard(board,file);
		
		if (success) {
			rttr.addFlashAttribute("message", "새 글이 등록되었습니다.");
		} else {
			rttr.addFlashAttribute("message", "새 글이 등록되지 않았습니다.");
		}
		
		return "redirect:/board/list";
	}
	
	@GetMapping("get")
	public void get(int id, Model model) {
		BoardDto dto = service.getBoardById(id);
		List<ReplyDto> replyList = replyService.getReplyByBoardId(id);
		model.addAttribute("board", dto);
		
		/* ajax로 처리하기 위해 삭제*/
		//model.addAttribute("replyList", replyList);
		
	}
	
	@PostMapping("modify")
	public String modify(BoardDto dto, 
						 @RequestParam(name = "removeFileList", required = false) List<String> removeFileList,	
						 MultipartFile[] addFileList,
						 Principal principal, 
						 RedirectAttributes rttr) {
		
		//Principal : 현재 접속되어진 사람의 정보를 담은 객체
		//DB에 등록되어진 게시물 정보
		BoardDto oldBoard = service.getBoardById(dto.getId());
		
		//DB에 등록되어진 게시판에 글쓴사람과 현재 접속한 사람이 동일한지 비교
		if(oldBoard.getMemberId().equals(principal.getName())) {
			boolean success = service.updateBoard(dto, removeFileList, addFileList);
			
			if (success) {
				rttr.addFlashAttribute("message", "글이 수정되었습니다.");
			} else {
				rttr.addFlashAttribute("message", "글이 수정되지 않았습니다.");
			}
		} else {
			rttr.addFlashAttribute("message", "권한이 없습니다.");
		}
		
		rttr.addAttribute("id", dto.getId());
		return "redirect:/board/get";
	}
	
	@PostMapping("remove")
	public String remove(BoardDto dto, Principal principal, RedirectAttributes rttr) {
		
		// 게시물 정보 얻고 
		BoardDto oldBoard = service.getBoardById(dto.getId());
		
		// 게시물 작성자(memberId)와 pricipal의 name과 비교해서 같을때만 진행.
		if(oldBoard.getMemberId().equals(principal.getName())) {
			boolean success = service.deleteBoard(dto.getId());
			
			if (success) {
				rttr.addFlashAttribute("message", "글이 삭제 되었습니다.");
				
			} else {
				rttr.addFlashAttribute("message", "글이 삭제 되지않았습니다.");
			}
			
		} else {
			rttr.addFlashAttribute("message", "권한이 없습니다.");
			rttr.addAttribute("id", dto.getId());
			return "redirect:/board/get";
		}
				
		return "redirect:/board/list";
	}
}










