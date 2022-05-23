package com.choong.spr.controller.ex02;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.choong.spr.domain.ex02.Book;

@Controller
@RequestMapping("ex02")
public class Ex02Controller {
		
	@RequestMapping("sub01")
	public String method01() {
		
		return "hello"; // view가 아닌 DATA(String)을 전달하고 싶음
	}
	
	@RequestMapping("sub02")
	@ResponseBody // view가 아닌 DATA(String)라는 것을 알리는 어노테이션
	public String method02() {
		
		return "hello";
	}
	
	@RequestMapping("sub03")
	@ResponseBody
	public String method03() {
		
		return "{\"title\" : \"java\", \"writer\" : \"son\"}"; // JSON형식의 Data, 번거로움
	}
	
	@RequestMapping("sub04")
	@ResponseBody
	public Book method04() {
		
		Book book = new Book();
		book.setTitle("java");
		book.setWriter("son");
								
		return book; // 이렇게 객체형식으로 Data를 넘기고 싶어함
		// maven repository에서 jackson databind을 설치하여 객체형식으로 Data를 받을 수 있게 환경조성
		
	}
	
	@RequestMapping("sub05")
	public String method05() {
		
		return "/ex03/sub01";
	}
}
