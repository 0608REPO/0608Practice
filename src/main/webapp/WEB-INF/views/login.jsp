<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Login</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="/css/layout.css" rel="stylesheet" type="text/css"> 
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
</head>
<body>

<%@ include file="header.jsp" %>

<!-- action, method -->
<form action="/login" method="POST">
	<table align="center" cellpadding="5" cellspacing="1" width="600" border="1">
	    <tr>
	        <td width="1220" height="20" colspan="2" bgcolor="#336699">
	            <p align="center">
	            	<font color="white" size="3">
	            		<b>로그인</b>
	            	</font>
	            </p>
	        </td>
	    </tr>
	    <tr>
	        <td width="150" height="20">
	            <p align="center"><b><span style="font-size:12pt;">사원번호</span></b></p>
	        </td>
	        <td width="450" height="20" align="center">
	        	<b>
	        		<span style="font-size:12pt;">
	        			<input id="empno" type="text" name="empno" size="30">
	        		</span>
	        		<br/>
	        		<span id="empnoMsg" style="font-size:8pt;"></span>
	        		
	        	</b>
	        </td>
	    </tr>
	    <tr>
	        <td width="150" height="20">
	            <p align="center"><b><span style="font-size:12pt;">사  원  명</span></b></p>
	        </td>
	        <td width="450" height="20" align="center">
	        	<b>
	        		<span style="font-size:12pt;">
	        			<!-- input 박스 -->
	        			<input type="text" name="ename" size="30">
	        		</span>
	        	</b>
	        </td>
	    </tr>
	    <tr>
	        <td width="150" height="20">
	            <p><b><span style="font-size:12pt;">&nbsp;</span></b></p>
	        </td>
	        <td width="450" height="20" align="center">
	        	<b>
	        		<span style="font-size:12pt;">
						<input type="submit" value="로그인">
						&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="reset" value="다시작성">
					</span>
				</b>
			</td>
	    </tr>
	</table>
</form>
<hr>
<div align=center>
	<span style="font-size:12pt;"><input type="button" value="메인으로" onclick="location.href='/main'"></span>
</div>

<%@ include file="footer.jsp" %>
<script type="text/javascript">
	const empnoInput = document.getElementById('empno');
	const empnoMsg = document.getElementById('empnoMsg');
	empnoInput.addEventListener('blur', () => {
	    axios.get('http://localhost:8082/api/emp/' + empnoInput.value)
	    	.then(response => { // 사원이 없다면
	    		if(response.data == ''){
	    			empnoMsg.innerHTML = '존재하지 않는 사원번호입니다.';
	    			
	    		}
	    	}) 
	    	.catch(error => { // 사원이 있다면(앞에서 Exception으로 걸러진다)
	    		console.log(error.response.data);
	    		empnoMsg.innerHTML = '존재하는 사원번호입니다.';
    			
	    	}) 
	        
		
	});	
</script>
</body>
</html>