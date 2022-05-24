<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.1.3/css/bootstrap.min.css" integrity="sha512-GQGU0fMMi238uA+a/bdWJfpUGKUkBdgfFdgBm72SUQ6BeyWjoY/ton0tEjH+OSH9iP4Dfh+7HM0I9f5eR0L/4w==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

	<script>
		$(function(){
			$("#button1").click(function(){
				$.ajax({url : "/spr2/ex03/sub03"});
			});
			
			$("#button2").click(function(){
				$.ajax({url : "/spr2/ex03/sub04"});
			});
			
			$("#button3").click(function(){
				$.ajax({
					url : "/spr2/ex03/sub05",
					type : "get"
				});
			});
			
			$("#button4").click(function(){
				$.ajax({
					url : "/spr2/ex03/sub06",
					type : "post"
					/* method로도 방식설정가능(type과 같은일) */
				});
			});
			
			$("#button5").click(function(){
				$.ajax({
					url : "/spr2/ex03/sub07",
					type : "delete"
				});
			});
			
			$("#button6").click(function(){
				$.ajax({
					url : "/spr2/ex03/sub08",
					type : "put"
				});
			});
			
			$("#button7").click(function(){
				$.ajax({
					url : "/spr2/ex03/sub09",
					type : "get", // type의 기본값은 get이므로 생략가능
					data : {
						title : "epl",
						writer : "son"
					} // Data를 전달하는 property
				});
			});
			
			$("#button8").click(function(){
				$.ajax({
					url : "/spr2/ex03/sub10",
					type : "post",
					data : {
						name : "lee",
						address : "seoul"
					}
				})
			});
			
			$("#button9").click(function(){
				$.ajax({
					url : "/spr2/ex03/sub11",
					type : "post",
					data : {
						title : "득점왕 되기",
						writer : "son"
					}
				})
			});
			
			$("#button10").click(function(){
				$.ajax({
					url : "/spr2/ex03/sub10",
					type : "post",
					data : "name=donald&address=newyork"
				})
			});
			
			$("#button11").click(function(){
				$.ajax({
					url : "/spr2/ex03/sub11",
					type : "post",
					data : "title=득점왕 되기&writer=son"
				})
			});
			
			$("#button12").click(function(e){
				e.preventDefault();
				
				const dataString = $("#form1").serialize();
				
				$.ajax({
					url : "/spr2/ex03/sub10",
					type : "post",
					data : dataString
				});
			});
			
			$("#button13").click(function(e){
				e.preventDefault();
				
				const dataString = $("#form2").serialize();
				// 값을 직접 쓰지않고, 폼에 적은 데이터를 그대로 가져다 씀.
				$.ajax({
					url : "/spr2/ex03/sub11",
					type : "post",
					data : dataString
				});
			});
			
			$("#button14").click(function() {
				$.ajax({
					url : "/spr2/ex03/sub12",
					type : "post",
					success : function(data) { // 응답이 성공했을때 어떤 행동을 취할것인지 함수 안에 세팅 
						console.log("요청 성공!!!");
						console.log("받은 데이터", data); // data는 응답받은 데이터
					}
				});
			});
			
			$("#button15").click(function(){
				$.ajax({
					url : "/spr2/ex03/sub13",
					type : "get",
					success : function(data) {
						//console.log(data);
						$("#result1").text(data);
					}
				});
			});
			
			$("#button16").click(function(){
				$.ajax({
					url : "/spr2/ex03/sub14",
					type : "get",
					success : function(book) {
						console.log(book); // 객체가 그대로 찍힘. datatype은 알아서 세팅됨.
						$("#result2").text(book.title);
						$("#result3").text(book.writer);
						
					}
				});
			});
			
			$("#button17").click(function(){
				$.ajax({
					url : "/spr2/ex03/sub15",
					type : "get",
					success : function(map) {
						console.log(map); 
						// json은 객체를 문서화시킨 String형태
						// map은 객체
					}
				});
			});
			
			$("#button18").click(function(){
				$.ajax({
					url : "/spr2/ex03/sub16",
					type : "get",
					success : function(data){
						console.log(data)
					},
					error : function(){
						console.log("무언가가 잘못됨");
					}
				})
			});
			
			$("#button19").click(function(){
				$.ajax({
					url : "/spr2/ex03/sub16",
					success : function(data) {
						
					},
					error : function(){
						$("#message19").show();
						$("#message19").text("처리 중 오류 발생").fadeOut(3000);
					}
				});
			});
			
			$("#button20").click(function(){
				$.ajax({
					url : "/spr2/ex03/sub17",
					success : function(data){
						console.log("받은 데이터", data);
					},
					error : function() {
						console.log("무엇인가 잘못됨!!");
					}
				});
			});
			
			$("#button21").click(function(){
				$.ajax({
					url : "/spr2/ex03/sub18",
					
					success : function(data) {
						$("#message20").show();
						$("#message20").removeClass("error").text(data).fadeOut(3000);
					},
					
					error : function(data) {
						$("#message20").show();
						$("#meesage20").addClass("error").text("무엇인가 잘못됨").fadeOut(3000);
					}
				});
			});
			
			$("#button22").click(function(){
				$.ajax({
					url : "/spr2/ex03/sub18",
					
					success : function(data) {
						$("#message20").show();
						$("#message20").removeClass("error").text(data).fadeOut(3000);
					},
					
					error : function(data) {
						$("#message20").show();
						$("#meesage20").addClass("error").text("무엇인가 잘못됨").fadeOut(3000);
					},
					
					complete : function() {
						console.log("항상 실행 됨!!!");
					}
				});
			});
		});
	</script>
	<style>
		.error {
			background-color: red;
			color : yellow;
		}
	</style>
	
<title>Insert title here</title>
</head>
<body>
	<%-- ajax는 page전체로딩없이 페이지 일부만 변경하여 사용하기위해 사용 --%>
	
	<button id="button1">ajax 요청보내기</button>
	<br />
	<%-- 이 버튼을 클릭하면 /spr/ex03/sub04로 ajax로 요청보내기 --%>
	<%-- controller에도 해당경로 요청에 일하는 메소드 추가 --%>
	<button id="button2">ajax 요청보내기2</button>
	<br />
	<%-- /spr2/ex03/sub05 get방식 요청보내기(기본 : get방식) --%>
	<button id="button3">get방식으로 요청보내기</button>
	<br />
	<%-- /spr2/ex03/sub06 post방식으로 요청보내기 --%>
	<button id="button4">post방식으로 요청보내기</button>
	<br />
	<%-- /spr2/ex03/sub07 delete방식으로 요청보내기 --%>
	<button id="button5">delete방식으로 요청보내기</button>
	<br />
	<%-- /spr2/ex03/sub08 put방식으로 요청보내기 --%>
	<button id="button6">put방식으로 요청보내기</button>
	<hr />
	<p>서버로 데이터 보내기</p>
	<%-- /spr2/ex03/sub09 --%>
	<button id="button7">서버로 데이터 보내기</button>
	<hr />
	<%-- /spr2/ex03/sub10 post방식으로 데이터 보네기 --%>
	<%-- 전송될 데이터는 name, address --%>
	<button id="button8">post방식으로 데이터 보내기</button>
	<br />
	<%-- /spr2/ex03/sub11 post방식으로 데이터 보내기 --%>
	<button id="button9">post방식으로 객체 보내기</button>
	<br />
	
	<%-- /spr2/ex03/sub10 post방식으로 데이터 보내기 --%>
	<%-- 전송될 데이터는 name, address --%>
	<button id="button10">post방식으로 데이터 보내기(encoded string)</button>
	<br />
	<%-- /spr2/ex03/sub11 post방식으로 데이터 보내기 --%>
	<%-- 전송될 데이터는 title, writer --%>
	<button id="button11">post방식으로 데이터 보내기(encoded string2)</button>
	<hr />
	
	<p>폼 데이터 보내기</p>
	<form id="form1" action="/spr2/ex03/sub10" method="post">
		name : <input type="text" name="name" /> <br />
		address : <input type="text" name="address" /> <br />
		<input id="button12" type="submit" value="전송" />
	</form>
	
	<%-- #button13이 클릭되면 --%>
	<%-- /spr2/ex03/sub11로 post방식으로 ajax요청전송 (title, writer전송) --%>
	
	<form id="form2" action="/spr2/ex03/sub11" method="post">
		title : <input type="text" name="title" /> <br />
		writer : <input type="text" name="writer" /> <br />
		<input id="button13" type="submit" value="전송" />
	</form>
	
	<hr />
	
	<p>응답 처리 하기</p>
	
	<%-- url : /spr2/ex03/sub12, type : post --%>
	<button id="button14">응답처리1</button>
	<br />
	
	
	<button id="button15">로또번호 받기</button>
	<p>받은번호 <span id="result1"></span></p>
	
	<button id="button16">json 데이터 받기</button>
	<p>책 제목 : <span id="result2"></span></p>
	<p>책 저자 : <span id="result3"></span></p>
	
	<button id="button17">map to json</button>
	<hr />
	
	<p>요청이 실패할 경우</p>
	<button id="button18">요청실패 1</button>
	<button id="button19">요청실패 2</button>
	<p class="error" id="message19"></p>
	<button id="button20">서버에서 에러응답</button>
	
	<button id="button21">50%확률로 ok</button>
	
	<p id="message20"></p>
	
	<button id="button22">50%확률로 ok2</button>
</body>
</html>