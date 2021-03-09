<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

    
<!DOCTYPE html>
<html>
<head>
	<link href="https://fonts.googleapis.com/css?family=Roboto:300">
	<style>
		.login-page {
		  width: 360px;
		  padding: 8% 0 0;
		  margin: auto;
		}
		.form {
		  position: relative;
		  z-index: 1;
		  background: #FFFFFF;
		  max-width: 360px;
		  margin: 0 auto 100px;
		  padding: 45px;
		  text-align: center;
		  box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 5px 5px 0 rgba(0, 0, 0, 0.24);
		}
		.form input {
		  font-family: "Roboto", sans-serif;
		  outline: 0;
		  background: #f2f2f2;
		  width: 100%;
		  border: 0;
		  margin: 0 0 15px;
		  padding: 15px;
		  box-sizing: border-box;
		  font-size: 14px;
		}
		body {
		  background: #76b852; /* fallback for old browsers */
		  background: -webkit-linear-gradient(right, #76b852, #8DC26F);
		  background: -moz-linear-gradient(right, #76b852, #8DC26F);
		  background: -o-linear-gradient(right, #76b852, #8DC26F);
		  background: linear-gradient(to left, #76b852, #8DC26F);
		  font-family: "Roboto", sans-serif;
		  -webkit-font-smoothing: antialiased;
		  -moz-osx-font-smoothing: grayscale;      
		}
		.form button {
		  font-family: "Roboto", sans-serif;
		  text-transform: uppercase;
		  outline: 0;
		  background: #4CAF50;
		  width: 100%;
		  border: 0;
		  padding: 15px;
		  color: #FFFFFF;
		  font-size: 14px;
		  -webkit-transition: all 0.3 ease;
		  transition: all 0.3 ease;
		  cursor: pointer;
		}
		.form .message {
		  margin: 15px 0 0;
		  color: #b3b3b3;
		  font-size: 12px;
		}
		.form .message a {
		  color: #4CAF50;
		  text-decoration: none;
		}
		.form button:hover,.form button:active,.form button:focus {
		  background: #43A047;
		}
		.sign {
		        color: #4CAF50;
		        font-family: 'Ubuntu', sans-serif;
		        font-weight: bold;
		        font-size: 23px;
		    }
	</style>
	
	<meta charset="UTF-8">
	<title>Insert title here</title>
</head>
<body>
	
	<h1 align="center">로그인 페이지</h1>
	
	<h2 align="center"><c:out value="${error }"/></h2>
	<h2 align="center"><c:out value="${logout }"/></h2>
	
	<p class="sign" align="center"><a href="main/all">메인화면</a></p>
	
	<form action="../travel06/login" method="POST">
		
		<div class="login-page">
			<div class="form">
				<p>아이디 : <input type="text" name="username"></p>
				<p>비밀번호 : <input type="password" name="password"></p>
				<input type="submit" value="로그인">
			</div>
		</div>
		
		
		<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }">
		
		
	</form>
	
	
</body>
</html>