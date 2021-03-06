package com.choong.spr.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.choong.spr.domain.MemberDto;
import com.choong.spr.service.MemberService;

@Controller
@RequestMapping("member")
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	// MemberService 작성
	// addMember method 작성
	// MemberMapper
	// insertMember method 작성
	
	@GetMapping("signup")
	public void signupForm() {
		
	}
	
	@PostMapping("signup")
	public String signupProcess(MemberDto member, RedirectAttributes rttr) {
		boolean success = service.addMember(member);
		
		if(success){
			rttr.addFlashAttribute("message", "회원가입이 완료되었습니다.");
			return "redirect:/board/list";
			
		} else {
			rttr.addFlashAttribute("message", "회원가입에 실패하였습니다.");
			rttr.addFlashAttribute("member", member);
			
			return "redirect:/member/signup";
		}
	}
	
	@PostMapping(path = "check", params = "id")
	@ResponseBody
	public String idCheck(String id) {
		boolean exist = service.hasMemberId(id);
		
		if(id.equals("")) {
			return "";
		}
				
		if(exist) {
			return "notOk";
		} else {
			return "ok";	
		}	
	}
	
	@PostMapping(path = "check", params = "email")
	@ResponseBody
	public String emailCheck(String email) {
		boolean exist = service.hasMemberEmail(email);
		
		if(email.equals("")) {
			return "";
		}
				
		if(exist) {
			return "notOk";
		} else {
			return "ok";	
		}	
	}
	
	@GetMapping(path = "check", params = "nickName")
	@ResponseBody
	public String nickNameCheck(String nickName) {
		boolean exist = service.hasMemberNickName(nickName);
		
		if(nickName.equals("")) {
			return "";
		}
		
		if(exist) {
			return "notOk";
		} else {
			return "ok";
		}
	}
	
	@GetMapping("list")
	public void list(Model model) {
		List<MemberDto> list = service.listMember();
		model.addAttribute("memberList", list);
	}
	
	@GetMapping("get")
	public String getMember(String id, 
						    Principal principal, 
						    HttpServletRequest request, 
						    Model model) {
	
		// 권한처리를 컨트롤러에서 내부메소드로 처리
		if(hasAuthOrAdmin(id, principal, request)) { 
			MemberDto member = service.getMemberById(id);
			
			model.addAttribute("member", member);
			
			return null;
		}
		
		return "redirect:/member/login";
	}
	
	private boolean hasAuthOrAdmin(String id, Principal principal, HttpServletRequest request) {
		// 권한 처리를 위한 내부 메소드 
		// HttpServletRequest에서 권한에 대한 메소드가 있어서 사용
		return request.isUserInRole("ROLE_ADMIN") || (principal != null && principal.getName().equals(id));
	}
	
	@PostMapping("remove")
	public String removeMember(MemberDto dto, 
			                   Principal principal, 
			                   HttpServletRequest request, 
			                   RedirectAttributes rttr) {
		
		// 권한처리를 컨트롤러에서 내부메소드로 처리
		if(hasAuthOrAdmin(dto.getId(), principal, request)) {
			boolean success = service.removeMember(dto);
			
			if(success) {
				rttr.addFlashAttribute("message", "회원 탈퇴 되었습니다.");
				return "redirect:/board/list";
			} else {
				rttr.addAttribute("id", dto.getId());
				return "redirect:/member/get";
			}
			
		} else {
			return "redirect:/member/login";
		}
	}
	
	@PostMapping("modify")
	public String modifyMember(MemberDto dto, 
							   Principal principal, 
							   HttpServletRequest request, 
							   String oldPassword, 
							   RedirectAttributes rttr) {
		
		// 권한처리를 컨트롤러에서 내부메소드로 처리
		if(hasAuthOrAdmin(dto.getId(), principal, request)) {
			boolean success = service.modifyMember(dto, oldPassword);
			
			if(success) {
				rttr.addFlashAttribute("message", "회원정보가 수정되었습니다.");
			} else {
				rttr.addFlashAttribute("message", "회원정보가 수정되지 않았습니다.");
			}
			
			rttr.addFlashAttribute("member", dto); // model object
			rttr.addAttribute("id", dto.getId()); // query string
			return "redirect:/member/get";	
		} else {
			return "redirect:member/login";
		}
		
	}
	
	@GetMapping("login")
	public void loginPage() {
		
	}
	
	@GetMapping("initpw") 
	public void initpwPage(){
		// password를 아이디랑 동일하게 초기화해주는 페이지
	}
	
	@PostMapping("initpw")
	public String initpwProcess(String id) {
		service.initPassword(id);
		
		return "redirect:/board/list";
	}
	
}
