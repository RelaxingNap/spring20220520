<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "my" tagdir="/WEB-INF/tags" %>
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
	$(function() {
		// 기존 이메일(같은 이메일을 등록하지 못해서 비교하기 위해 필요)
		const oldEmail = $("#emailInput1").val();
		// 기존 닉네임(같은 닉네임을 등록하지 못해서 비교하기 위해 필요)
		const oldNickName = $("#nickNameInput1").val();
		
		// 이메일 중복 확인 여부
		let emailCheck = true;
		// 닉네임 중복 확인 여부		
		let nickNameCheck = true;
		// 암호, 암호확인 일치 여부
		let passwordCheck = true;
		
		// 수정버튼
		const enableModifyButton = function() {
			if(emailCheck && nickNameCheck && passwordCheck){
				$("#modifySubmitButton1").removeAttr("disabled");
			} else {
				$("#modifySubmitButton1").attr("disabled", "");
			}
		}
		
		// 이메일 input요소에 text변경시 이메일 중복확인버튼 활성화
		$("#emailInput1").keyup(function() {
			const newEmail = $("#emailInput1").val();
			
			if(oldEmail === newEmail){
				$("#checkEmailButton1").attr("disabled", "");
				$("#emailMessage1").text("");
				emailCheck = true;
			} else {
				$("#checkEmailButton1").removeAttr("disabled");
				emailCheck = false;
			}
			
			enableModifyButton();
		});
		
		// nickName input요소에 text변경시 nickName 중복확인버튼 활성화
		$("#nickNameInput1").keyup(function() {
			const newNickName = $("#nickNameInput1").val();
			
			if(oldNickName === newNickName) {
				$("#checkNickNameButton1").attr("disabled", "");
				$("#nickNameMessage1").text("");
				nickNameCheck = true;
			} else {
				$("#checkNickNameButton1").removeAttr("disabled");
				nickNameCheck = false;
			}
				
			enableModifyButton();
		});
		
		// 이메일 중복버튼 클릭 시 ajax요청 발생
		$("#checkEmailButton1").click(function(e) {
			// 기본 이벤트 진행 중지
			e.preventDefault();
						
			const data = {
				email : $("#emailInput1").val()
			}

			emailCheck = false;
			$.ajax({
				url : "${appRoot}/member/check",
				type : "post",
				data : data,
				success : function(data) {
					
					switch (data) {
					case "":
						$("#emailMessage1").text("입력받은 email이 없습니다.");
						break;

					case "ok":
						$("#emailMessage1").text("사용 가능한 email입니다.");
						emailCheck = true;
						break;

					case "notOk":
						$("#emailMessage1").text("사용 불가능한 email입니다.");
						break;

					}
				},

				error : function() {
					$("#emailMessage1").text("중복 확인 중 문제 발생하였습니다.");
				},

				complete : function() {
					enableModifyButton();
				}

			});
		});

		
		
		// nickName 중복버튼 클릭 시 ajax요청 발생
		$("#checkNickNameButton1").click(function(e) {
			// 기본 이벤트 진행 중지
			e.preventDefault();
			
			const data = {
				nickName : $("#nickNameInput1").val()
			}

			nickNameCheck = false;
			$.ajax({
				url : "${appRoot}/member/check",
				type : "get",
				data : data,
				success : function(data) {
					
					switch (data) {
					case "":
						$("#nickNameMessage1").text("입력받은 nickName이 없습니다.");
						break;

					case "ok":
						$("#nickNameMessage1").text("사용 가능한 nickName입니다.");
						nickNameCheck = true;
						break;

					case "notOk":
						$("#nickNameMessage1").text("사용 불가능한 nickName입니다.");
						break;

					}
				},

				error : function() {
					$("#nickNameMessage1").text("중복 확인 중 문제 발생하였습니다.");
				},

				complete : function() {
					enableModifyButton();
				}

			});
		});

		// 암호, 암호확인 요소 값 변경 시
		$("#passwordInput1, #passwordInput2").keyup(function() {
			const pw1 = $("#passwordInput1").val();
			const pw2 = $("#passwordInput2").val();
			
			if(pw1 === pw2){
				$("#Message1").text("패스워드가 일치합니다.");
				passwordCheck = true;
			} else {
				$("#Message1").text("패스워드가 일치하지 않습니다.");
				passwordCheck = false;
			}
			
			enableModifyButton();
		});
		
		// 수정 submit버튼 ("modifySubmitButton2") 클릭 시
		$("#modifySubmitButton2").click(function(e) {
			e.preventDefault();
			
			const form2 = $("#form2");
			
			// input 값 옮기기
			form2.find("[name=password]").val($("#passwordInput1").val());
			form2.find("[name=email]").val($("#emailInput1").val());
			form2.find("[name=nickName]").val($("#nickNameInput1").val());
			
			// submit
			form2.submit();
		});
		
	});
</script>

</head>
<body>
	<my:navBar />
	<div class="container">
		<h3 id="Message1"></h3>
	</div>
	
	<div class="container">
		아이디 : <input type="text" name="id" value="${member.id }" readonly /> <br />
		비밀번호 : <input id="passwordInput1" type="text" name="password" value="" /> <br />
		비밀번호 확인 : <input id="passwordInput2" type="text" value="" /> <br />
		이메일 : <input id="emailInput1" type="email" name="email" value="${member.email }" /> <button id="checkEmailButton1" disabled>이메일 중복 확인</button> <br />
		<p id="emailMessage1"></p>
		닉네임 : <input id="nickNameInput1" type="text" name="nickName" value="${member.nickName }" /> <button id="checkNickNameButton1" disabled>닉네임 중복확인</button> <br />
		<p id="nickNameMessage1"></p>
		가입일 : <input type="datetime-local" name="inserted" value="${member.inserted }" readonly /> <br />
	</div>
	
	<%-- 요구사항 --%>
	<%-- 1. 이메일 input에 변경 발생 시 '이메일 중복확인 버튼 활성화' -> 버튼클릭시 ajax로 요청/응답, 적절한 메시지 출력
		 2. 닉네임 input에 변경 발생 시 '닉네임 중복확인 버튼 활성화' -> 버튼클릭시 ajax로 요청/응답, 적절한 메시지 출력
		 3. 암호/암호확인일치, 이메일 중복확인 완료, 닉네임 중복확인 완료 시에만 수정버튼 활성화
		 	
	--%>
	
	<div class="container">
		<button data-bs-toggle="modal" data-bs-target="#Modal2" id="modifySubmitButton1" disabled>수정</button>
		<button data-bs-toggle="modal" data-bs-target="#Modal1">삭제</button>
	</div>
	
	<!-- 탈퇴 암호 확인 modal -->
	<div class="modal fade" id="Modal1" tabindex="-1"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">회원정보 삭제</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<form id="form1" action="${appRoot }/member/remove" method="post">
						<input type="hidden" name="id" value="${member.id }" />
						암호 : <input type="text" name="password" />
					</form>		
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Close</button>
					<button form="form1" type="submit" class="btn btn-danger">탈퇴</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 수정(modify) 기존 암호 확인 modal -->
	<div class="modal fade" id="Modal2" tabindex="-1"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel2">회원정보 수정</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<form id="form2" action="${appRoot }/member/modify" method="post">
						<input type="hidden" name="id" value="${member.id }" />
						<input type="hidden" name="password" />
						<input type="hidden" name="email" />
						<input type="hidden" name="nickName" />
						기존암호 : <input type="text" name="oldPassword" />
					</form>		
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"	data-bs-dismiss="modal">Close</button>
					<button id="modifySubmitButton2" form="form2" type="submit" class="btn btn-primary">수정</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="container">
		<p>${message }</p>
	</div>
	
</body>
</html>