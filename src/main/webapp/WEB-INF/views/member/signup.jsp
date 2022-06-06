<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.1.3/css/bootstrap.min.css" integrity="sha512-GQGU0fMMi238uA+a/bdWJfpUGKUkBdgfFdgBm72SUQ6BeyWjoY/ton0tEjH+OSH9iP4Dfh+7HM0I9f5eR0L/4w==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous"></script>

<title>Insert title here</title>

<script>
	$(function () {
		let idOk = false;
		let pwOk = false;
		let emailOk = false;
		let nickName = false;
		
		// 아이디 중복체크버튼 클릭시
		$("#checkIdButton1").click(function() {
						
			$(this).attr("disabled","");
			const data = {
				id : $("#form1").find("[name=id]").val()	
			};
			
			idOk = false;
			$.ajax({
				url : "${appRoot}/member/check",
				type : "post",
				data : data, // server로 보내는것
				success : function(data) { // server로부터 받는것
					
					switch(data){
					
					case "" :
						$("#Message1").text("입력받은 id가 없습니다.");
						break;
						
					case "ok" : 
						$("#Message1").text("사용 가능한 아이디 입니다.");
						idOk = true;
						break;
						
					case "notOk" : 
						$("#Message1").text("사용 불가능한 아이디 입니다.");
						
						break;
					}
					
				},
				error : function() {
					$("#Message1").text("중복 확인 중 문제 발생하였습니다.");
				},
				
				complete : function() {
					$("#checkIdButton1").removeAttr("disabled");	
					enableSubmit();
				}
			});
		});
		
		// 이메일 중복체크버튼 클릭시
		$("#checkEmailButton1").click(function() {
						
			$(this).attr("disabled","");
			const data = {
					email : $("#form1").find("[name=email]").val()
			}
			
			emailOk = false;
			$.ajax({
				url : "${appRoot}/member/check",
				type : "post",
				data : data,
				success : function(data) {
					
					switch(data) {
						case "" :
							$("#Message1").text("입력받은 email이 없습니다.");
							break;
						
						case "ok" :
							$("#Message1").text("사용 가능한 email입니다.");
							emailOk = true;
							break;
							
						case "notOk" :
							$("#Message1").text("사용 불가능한 email입니다.");
							break;
					}
				},
	
				error : function() {
					$("#Message1").text("중복 확인 중 문제 발생하였습니다.");
				},
				
				complete : function() {
					$("#checkEmailButton1").removeAttr("disabled");
					enableSubmit();
				}
			});
			
		});
		
		// 닉네임 중복체크버튼 클릭시
		$("#checkNickNameButton1").click(function() {
						
			$(this).attr("disabled", "");
			
			const data = {
					nickName : $("#form1").find("[name=nickName]").val()
			}
			nickName = false;
			$.ajax({
				url : "${appRoot}/member/check",
				type : "get",
				data : data,
				success : function(data) {
					
					switch(data) {
						case "" :
							$("#Message1").text("입력받은 nickName이 없습니다.");
							break;
						
						case "ok" : 
							$("#Message1").text("사용 가능한 닉네임입니다.");
							nickNameOk = true;
							break;
							
						case "notOk" : 
							$("#Message1").text("사용 불가능한 닉네임입니다.");
							break;
					}
				},
				
				error : function() {
					$("#Message1").text("중복확인 중 문제가 발생하였습니다.");
				},
				
				complete : function() {
					$("#checkNickNameButton1").removeAttr("disabled");
					enableSubmit();
				}
				
			});
		});
		// 패스워드 오타 확인
		
		$("#passwordInput1, #passwordInput2").keyup(function() {
			const pw1 = $("#passwordInput1").val();
			const pw2 = $("#passwordInput2").val();
			
			pwOk = false;
			if(pw1 === pw2) {
				$("#Message1").text("패스워드가 일치합니다.");
				pwOk = true;
			} else {
				$("#Message1").text("패스워드가 일치하지 않습니다.");
			}
			
			enableSubmit();
		});
		
		// 회원가입 submit버튼 활성화/비활성화 함수
		const enableSubmit = function() {
			if(idOk && pwOk && emailOk && nickNameOk){
				$("#submitButton1").removeAttr("disabled");

			}else {
				$("#submitButton1").attr("disabled", "");
			}
		}
		
		
	});
</script>
</head>
<body>
	<my:navBar current="signup" />

	<div class="container">
		<div class="row justify-content-center">
			<div class="col-12 col-lg-6">

				<h3 id="Message1"></h3>
				<form id="form1" action="${appRoot }/member/signup" method="post">
					  
					<label for="idInput1" class="fom-label">아이디</label>
					<div class="input-group">
						<input id="idInput1" class="form-control" type="text" name="id"/> 
						<button class="btn btn-secondary" id="checkIdButton1" type="button">아이디 중복 확인</button>
					</div>
					
					<label for="passwordInput1" class="fom-label">패스워드</label>
					<input class="form-control" id="passwordInput1" type="text" name="password" />
					
					<label for="passwordInput2" class="fom-label">패스워드 확인</label>
					<input class="form-control" id="passwordInput2" type="text" name="passwordConfirm" /> <br />
					
					<label for="emailInput1" class="fom-label">이메일</label> 
					<div class="input-group">
						<input id="emailInput1" class="form-control" type="email" name="email" /> 
						<button class="btn btn-secondary" id="checkEmailButton1" type="button">email 중복 확인</button>
					</div>
					
					<label for="nickNameInput1" class="fom-label">닉네임</label>
					<div class="input-group">
						<input id="nickNameInput1" class="form-control" type="text" name="nickName" />
						<button class="btn btn-secondary" id="checkNickNameButton1" type="button">nickName중복확인</button>
					</div>
					
					<button id="submitButton1" disabled="disabled">회원가입</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>