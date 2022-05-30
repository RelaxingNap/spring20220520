package com.choong.spr.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.choong.spr.domain.ReplyDto;
import com.choong.spr.service.ReplyService;

@RequestMapping("reply")
@RestController
public class ReplyController {

	@Autowired
	private ReplyService service;

	@PostMapping(path = "insert", produces = "text/plain;charset=UTF-8" )
	public ResponseEntity<String> insert(ReplyDto dto, Principal principal) {
				
		if(principal == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); 
			// security-context.xml에서 강제로 리다이렉트를 일어나게 하면 ajax로 구현하였기때문에 login.jsp파일을 그대로 읽어와버림
			// 그래서 security-context.xml에서 처리하지 않고, controller에서 권한없음을 나타내는 401에러를 세팅한 후 
			// 권한이 없다는 것을 화면에 띄워주면 해결
		} else {
			
			dto.setMemberId(principal.getName());
			
			boolean success = service.insertReply(dto);
	
			if (success) {
				return ResponseEntity.ok("새 댓글이 등록되었습니다.");
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
			}
		}
	}

	@PutMapping(path = "modify", produces = "text/plain;charset=UTF-8" )
	public ResponseEntity<String> modify(@RequestBody ReplyDto dto, Principal principal) {
		
		if(principal == null) {
			return ResponseEntity.status(401).build();
		} else {
			boolean success = service.updateReply(dto, principal);
			
			if (success) {
				return ResponseEntity.ok("댓글이 변경되었습니다.");
			} else {
				return ResponseEntity.status(500).body("");
			}
			
		}
	}
	
	@DeleteMapping(path = "delete/{id}", produces = "text/plain;charset=UTF-8" )
	public ResponseEntity<String> delete(@PathVariable("id") int id, Principal principal) {
		
		if(principal == null) {
			return ResponseEntity.status(401).build();
		} else {
			
			boolean success = service.deleteReply(id, principal);
			
			if (success) {
				return ResponseEntity.ok("댓글을 삭제 하였습니다.");
			} else {
				return ResponseEntity.status(500).body("");
			}
		}
	}
	
	@GetMapping("list")
	public List<ReplyDto> list(int boardId, Principal principal, Model model) {
		
		if(principal == null) {
			return service.getReplyByBoardId(boardId);
		} else {
			return service.getReplyWithOwnByBoardId(boardId, principal.getName());
		}
		
		
	}
}






