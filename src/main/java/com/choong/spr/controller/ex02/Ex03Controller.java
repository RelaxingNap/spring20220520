package com.choong.spr.controller.ex02;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.choong.spr.domain.ex02.Book;

@RequestMapping("ex03")
/*
@Controller
@ResponseBody // Data전달만 전용으로 하는 controller가 많이 있음
*/
@RestController // Data전달만 하는 controller임을 선언하는 어노테이션. 위의 2개의 어노테이션 기능을 하나로 합친것
public class Ex03Controller {

	@RequestMapping("sub01")
	public String method01() {
		return "string data";
	}
	
	@RequestMapping("sub02")
	public Book method02() {
		Book b = new Book();
		b.setTitle("soccer");
		b.setWriter("jimin");
		
		return b;
	}
	
	@RequestMapping("sub03")
	public String method03() {
		System.out.println("ex03/sub03 일함!!!");
		
		return "hello ajax";
	}
	
	@RequestMapping("sub04")
	public String method04() {
		System.out.println("ex03/sub04 일함!");
		
		return "hi";
	}
	
	@GetMapping("sub05")
	public void method05() {
		System.out.println("ex03/sub05 일함!!!!");
	}
	
	@PostMapping("sub06")
	public void method06() {
		System.out.println("ex03/sub06 일함!!!!!!");	
	}

	/*@RequestMapping(method = RequestMethod.DELETE, value = "sub07")*/
	@DeleteMapping("sub07")
	public void method07() {
		System.out.println("ex03/sub07 일함!!@!");
	}
	
	@PutMapping("sub08")
	public void method08() {
		System.out.println("ex03/sub08 일함@!#!@#");
	}
	
	@GetMapping("sub09")
	public void method09(String title, String writer) {
		System.out.println("##받은 데이터");
		System.out.println("title : " + title);
		System.out.println("writer : " + writer);
	}
	
	@PostMapping("sub10")
	public String method10(String name, String address) {
		System.out.println("##받은 데이터");
		System.out.println("name : " + name);
		System.out.println("address : " + address);
		
		return "good!~~";
	}
	
	@PostMapping("sub11")
	public void method11(Book book) {
		System.out.println(book);
		
	}
	
	@PostMapping("sub12")
	public String method12() {
		System.out.println("12번째 메소드 일함");
		
		return "hello";
	}
	
	@GetMapping("sub13")
	public Integer method13() {
		return (int)(Math.random() * 45 + 1);
	}
	
	@GetMapping("sub14")
	public Book method14() {
		Book b = new Book();
		b.setTitle("스프링");
		b.setWriter("홍길동");
		
		return b;
	}
	
	@GetMapping("sub15")
	public Map<String, String> method15(){
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "손흥민");
		map.put("age", "30");
		map.put("address", "london");
		
		return map;
	}
	
	@GetMapping("sub17")
	public ResponseEntity<String> method17() { // 이 객체를 return하면 Responsebody뿐만 아니라 헤더와 다른 것들도 조작가능
											   // 본문의 타입을 제네릭안에 작성하면 됨
		
		// 성공시는 객체리턴으로 판단이 가능하기 때문에 굳이 메소드를 사용할 필요는 없음
		return ResponseEntity.status(500).body("internal server error"); // 에러시 에러응답 리턴
	}
	
	/*@GetMapping("sub18")
	public ResponseEntity<String> method18() {
		if(success)
	}*/
	
	
}
