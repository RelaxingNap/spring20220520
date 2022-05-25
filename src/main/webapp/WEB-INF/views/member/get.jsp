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
</head>
<body>
	<my:navBar />
	<div class="container">
		아이디 : <input type="text" name="id" value="${member.id }" readonly="readonly" />
		<br />
		비밀번호 : <input type="text" name="password" value="${member.password }"
			readonly="readonly" />
		<br />
		이메일 : <input type="email" name="email" value="${member.email }"
			readonly="readonly" />
		<br />
		닉네임 : <input type="text" name="nickName" value="${member.nickName }"
			readonly="readonly" />
		<br />
		가입일 : <input type="datetime-local" name="inserted" value="${member.inserted }"
			readonly="readonly" />
		<br />
	</div>

	<div class="container">
		<button data-bs-toggle="modal" data-bs-target="#Modal1">삭제</button>
	</div>

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
</body>
</html>