<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 

<!DOCTYPE html>
<html>
	<head>
      <link href="https://fonts.googleapis.com/css?family=Roboto:300">
  
	<meta charset="UTF-8">
	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
	<title>관리자 회원가입</title>
	
	<style>
	.check_ok{
	  color: green;
	  font-family: 고딕체;
	  font-size: 20px;
	}
	
	.check_error{
		color:red;
		font-family: 고딕체;
		font-size: 20px;
	}
  
        .register-page {
    width: 800px;
    padding: 8% 0 0;
    margin: auto;
    }
    .form {
      position: relative;
      z-index: 1;
      background: #FFFFFF;
      max-width: 500px;
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
    .register {
        color: #4CAF50;
        font-family: 'Ubuntu', sans-serif;
        font-weight: bold;
        font-size: 23px;
    }
	
	</style>
	
	</head>
	<body>
		
		
		
		<form action="register" method="POST">
          <div class="register-page">
            <div class="form">
              <form class="register-form">
                 <p class="register" align="center">관리자 회원 가입</p>
                
		
        		<p>이름  <input type="text" id="adminName" name="adminName" placeholder="이름을 입력하세요." required autofocus></p>
        		<p>아이디  <input type="text" id="adminID" name="adminID" placeholder="아이디를 입력하세요." required onblur="check_adminID();">
        		<button id="check_id" type="button">아이디 중복확인</button>
        		<!-- TODO : 위의 id를 사용하여 자바스크립트로 ajax를 구현할 것 -->
        		</p> 
        		
        		<div id="id_result">
        		</div>
        		
        		
        		<p>비밀번호 <input type="password" id="adminPWD" name="adminPWD" placeholder="비밀번호를 입력하세요." required onblur="regularExpression()"></p>
        		<p>비밀번호 재확인 <input type="password" id="PWDcheck" placeholder="비밀번호를 입력하세요."></p>
        		
        		
        		<div id="passwordCheck"></div>
        		
        		
        		<p>휴대폰 전화번호  
        		
        		<input type="hidden" id="phone1">
        			<select name="phoneSelect" id="phoneSelect" onchange="phoneChange(this);">
        				<option>선택하세요</option>
        				<option value="010">010</option>
        				<option value="011">011</option>
        				<option value="016">016</option>
        				<option value="017">017</option>
        				<option value="018">018</option>
        				<option value="019">019</option>
        			</select>
        			- <input style="width:100px;" type="text" id="phone2" size="5"> - <input style="width:100px;" type="text" id="phone3" size="5">
        			
        			<br>
        		
        		<input type="hidden" id="adminPhone" name="adminPhone" placeholder="전화번호를 입력하세요." size="20" required></p>
        		
        		<div id="checkPhone"></div>
        		
        		<p>회사 전화번호  
        		<input type="hidden" id="com1">
        			<select name="companySelect" id="companySelect" onchange="companyChange(this);">
        				<option>선택하세요</option>
        				<option value="02">02</option>
        				<option value="031">031</option>
        				<option value="032">032</option>
        				<option value="033">033</option>
        				<option value="041">041</option>
        				<option value="042">042</option>
        				<option value="043">043</option>
        				<option value="051">051</option>
        				<option value="052">052</option>
        				<option value="053">053</option>
        				<option value="054">054</option>
        				<option value="055">055</option>
        				<option value="061">061</option>
        				<option value="062">062</option>
        				<option value="063">063</option>
        				<option value="064">064</option>
        				
        			</select>
        			- <input style="width:100px;" type="text" id="com2" size="5"> - <input style="width:100px;" type="text" id="com3" size="5">
        			
        			<br>
        		
        		<input type="hidden" id="adminCompany" name="adminCompany" placeholder="회사번호를 입력하세요." size="20" required></p>
        		
        		<div id="checkCompany"></div>
        		
        		
        		<p>이메일  <input type="email" id="adminEmail" name="adminEmail" placeholder="이메일을 입력하세요." size="20" required></p>
        		
        		
        		<p>생일  <input type="date" id="adminBirth" name="adminBirth" required> </p>
        		
        		
        		<input type="hidden" name="${_csrf.parameterName }" 
        												value="${_csrf.token }"/>
        		<input type="submit" value="관리자등록">
        		
        		
        		<div>
        			<a href="../main/all"><button type="button">취소</button></a>
        		</div>
              </form>
            </div>
          </div>		
		</form>
		
		<script type="text/javascript">
		
		function regularExpression(){
	         var pwd = $('#adminPWD').val();
	         var num = pwd.search(/[0-9]/g);
	         var eng = pwd.search(/[a-z]/ig);
	         var spe = pwd.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
	         var list = '';
	         
	         if(pwd.length < 8 || pwd.length > 20){
	            list += '<p class=check_error>'
	               + '8자리 ~ 20자리 이내로 입력해주세요.</p>';
	            $('#passwordRegular').html(list);
	            console.log("자리수 맞춰서 pwd : " + pwd);
	         
	         }else if(pwd.search(/\s/) != -1){
	            list += '<p class=check_error>'
	               + '비밀번호는 공백 없이 입력해주세요.</p>';
	            $('#passwordRegular').html(list);
	            console.log("공백 없이 pwd : " + pwd);
	            
	         }else if(num < 0 || eng < 0 || spe < 0 ){
	            list += '<p class=check_error>'
	               + '영문, 숫자, 특수문자를 혼합하여 입력해주세요.</p>';
	            $('#passwordRegular').html(list);
	            console.log("영대문자, 특수문자 pwd : " + pwd);
	            
	         }else {
	            list += '<p class=check_ok>'
	               + '입력에 성공 하셨습니다.</p>';
	            $('#passwordRegular').html(list);
	               console.log("통과"); 
	                return true;
	             }
	      }
		
		
		function check_adminID()
		{
			var idRule = 'admin';
			var id = $('#adminID').val();
			console.log(id);
			var list = '';
			
			
			console.log(id.search(idRule));
			if(id.search(idRule) == -1)
			{
				list += '<p class=check_error>'
					+ '관리자용 아이디로 사용할 수 없습니다. 다시 입력해주세요.</p>';
					
				$('#id_result').html(list);	
				$('#adminID').val('');
				
				
			}
			else
			{
				list += '<p class=check_ok>'
					+ '관리자용 아이디로 사용할 수 있습니다.</p>';
					
				$('#id_result').html(list);	
				
			}
			
		}
		
		
		function phoneChange(cellPhone)
		{
			var phoneText = cellPhone[cellPhone.selectedIndex].value;
			console.log(phoneText);
			$('#phone1').val(phoneText);
			
		}
		
		
		function companyChange(com)
		{
			var companyText = com[com.selectedIndex].value;
			console.log(companyText);
			$('#com1').val(companyText);
			
		}
		
		
		$(document).ready(function(){
			
			$('#check_id').click(function(){
				console.log("start id check");
				
				
				var adminID = $('#adminID').val();
				var adminName = $('#adminName').val();
				var object = {
					'adminName' : adminName,
					'adminID' : adminID
					
				};
				console.log(object);
				var list = $('#id_result').html();
		    	 console.log(list); 
				
				
				$.ajax({
					type : 'get',
					url : 'doubleCheck/all/' + adminID,
					headers:{
			      	      'Content-Type' : 'application/json',
			      	      'X-HTTP-Method-Override' : 'GET'
			      	    },
			      success : function(result, status)
			      {
			    	 
			    	 console.log(result);
			    	  console.log(status);
			    	  
			    	  
			    	  if(result == 'Exist'){
			    		  
			    		  list += '<p class=check_error>'
			    		  		+ '존재하는 아이디가 있습니다. 다른아이디를 입력해주세요. <br></p>';
			    		  
			    		  $('#id_result').html(list);
			    		  
			    	  }
			    	  else if(result == 'notExist'){
			    		  
			    		  list += '<p class=check_ok>'
			    		  		+ '사용가능한 아이디 입니다.<br></p>';
			    		  
			    		  $('#id_result').html(list);
			    		  
			    	  }  
			    	  
			    	  
			    	  
			      }	// end success
			      
					
				});	// end ajax
				
				
			});	// end check_id click
			
			
			$('#phone3').on('blur', function(){
				
				var phone1 = $('#phone1').val();
				var phone2 = $('#phone2').val();
				var phone3 = $('#phone3').val();
				
				var str = phone1 + "-" + phone2 + "-" + phone3;
				$('#adminPhone').val(str);
				var phone = $('#adminPhone').val();
				console.log(phone);
				
				
				var phoneStr = /^\d{3}-\d{3,4}-\d{4}$/;

				
				if(!phoneStr.test(phone))
				{
					$('#checkPhone').html('<p class="check_error"> 휴대폰 번호 형식이 틀렸습니다. 올바른 형식으로 입력해주세요.</p>');
					$('#phone2').val('');
					$('#phone3').val('');
				}
				else
				{
					$('#checkPhone').html('');
				}
				
				
				
			});	// end phone3 blur
			
			
			$('#com3').on('blur', function(){
				
				var com1 = $('#com1').val();
				var com2 = $('#com2').val();
				var com3 = $('#com3').val();
				
				var str = com1 + "-" + com2 + "-" + com3;
				$('#adminCompany').val(str);
				var phone = $('#adminCompany').val();
				console.log(phone);
				
				
				var phoneStr = /^\d{2,3}-\d{3,4}-\d{4}$/;
				
				if(!phoneStr.test(phone))
				{
					$('#checkCompany').html('<p class="check_error"> 휴대폰 번호 형식이 틀렸습니다. 올바른 형식으로 입력해주세요.</p>');
					$('#com2').val('');
					$('#com3').val('');
				}
				else
				{
					$('#checkCompany').html('');
				}
				
				
				
			});	// end com3 blur
			
			
			
			
			
			
		});	// end document
		
		</script>
		
		
		
		
	</body>
</html>