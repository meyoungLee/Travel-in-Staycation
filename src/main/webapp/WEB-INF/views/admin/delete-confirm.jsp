<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

    
<!DOCTYPE html>
<html>
	<head>
  <style>
    .deleteConfirm-page {
    width: 800px;
    padding: 8% 0 0;
    margin: auto;
    }
    .form {
      position: relative;
      z-index: 1;
      background: #FFFFFF;
      max-width: 5000px;
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
    .form button:hover,.form button:active,.form button:focus {
      background: #43A047;
    }
    .deleteConfirm {
            color: #4CAF50;
            font-family: 'Ubuntu', sans-serif;
            font-weight: bold;
            font-size: 23px;
    }
  </style>
	<meta charset="UTF-8">
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
	<title>????????? ?????? ??????</title>
	</head>
	<body>
	  <div class="deleteConfirm-page">
          <div class="form">
              <form class="deleteConfirm-form">
		
        		<h2>${custom.avo.adminID}?????? ??????</h2>
        		
        		
        		<form action="delete-confirm" method="POST">
        			<div>
        				<p> ${custom.avo.adminID }???, ?????????????????????????</p>
        				<p> ?????? ?????? : <input type="text" id="why"><br>
        				<input type="hidden" name="${_csrf.parameterName }" 
        										value="${_csrf.token }"/>
        				<a href="../main/all"><button type="button">??????</button></a>
        			</div>
        		</form>
        			<div>
        				<a href="../main/admin"><button type="button">??????</button></a>
        				<input type="hidden" name="${_csrf.parameterName }" 
        										value="${_csrf.token }"/>
        			</div>
		</form>
      </div>
    </div>
	
	</body>
</html>