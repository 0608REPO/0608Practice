<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<title>Dept List</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1"> 
<link href="/css/layout.css" rel="stylesheet" type="text/css" />
</head>
<body>

<%@ include file="header.jsp" %>

<c:if test="${not empty userId}">    


	<div align="center">
		<form id= "detailForm"  action="/submit-url" method="POST">
			<input type="text" name="searchDeptno" id="more" style="display:none" >
			
			<input type="text" name="condition" id= "less" style="display:none">
			<br>
	
			<input type="button" value="조건" id="conditionButton" onclick="makeCondition()" >
			<input type="button" value="조건 검색" id="conditionSearch" onclick="doSearch()" >

		<form action="/search" method="POST">
			<input type="text" name="searchDeptno" id="searchDeptno" style="display:on" placeholder="deptno">
			<input type="text" name="searchDname" id="searchDname" style="display:none" placeholder="dname">
			<input type="text" name="searchloc" id="searchloc" style="display:none" placeholder="loc">
			<br>
			<input type="submit" value="검색" >
			<input type="button" value="상세 검색" id="detailSearch" onclick="doAction()" >
			<input type="button" value="사원 검색" id="EmpDeptSearch" onclick="doAction2()" >
			</form>
	</div>
	
	<div align="center">
		<form id = empDeptSearch2 style="display:none" action="/deptof" method="POST">			
				<table align="center" cellpadding="5" cellspacing="2" width="60%" bordercolordark="white" bordercolorlight="black">
					<tr>
						<td align="center"><b>사원검색</b></td>
						<td align="center"><input type="text" name="ename" id="ename"></td>
						<td align="center"><input type="submit" value="부서 검색"></td>
					</tr>
				</table>

		</form>
	</div>
	
<table align="center" border="0" cellpadding="5" cellspacing="2" width="100%" bordercolordark="white" bordercolorlight="black">
	
	<tr>
        <td bgcolor="#336699">
            <p align="center">
            <font color="white"><b><span style="font-size:12pt;">부서번호</span></b></font></p>
        </td>
        <td bgcolor="#336699">
            <p align="center"><font color="white"><b><span style="font-size:12pt;">부서명</span></b></font></p>
        </td>
        <td bgcolor="#336699">
            <p align="center"><font color="white"><b><span style="font-size:12pt;">위치</span></b></font></p>
        </td>
    </tr>

	<!-- 부서 객체 유무 검증 -->
	<c:if test="${empty requestScope.deptList}">    
		<tr>
	        <td colspan="5">
	            <p align="center"><b><span style="font-size:12pt;">등록된 부서가 존재하지 않습니다.</span></b></p>
	        </td>
	    </tr>
	</c:if>
	<!-- 반복 출력 -->
	<c:forEach items="${requestScope.deptList}" var="dept">
		    <tr>
		        <td bgcolor="">
		            <p align="center">
			            <span style="font-size:12pt;">
			            	<!-- 부서번호 -->
			            	<b>${dept.deptno}</b>
			            </span>
		            </p>
		        </td>
		        <td bgcolor="">
					<p align="center">
						<span style="font-size:12pt;">
							<!--
								부서명 클릭 시, 부서번호로 해당부서 상세정보 출력
							 -->
							<b>
								<a href="dept/${dept.deptno}">${dept.dname}</a>
							</b>
						</span>
					</p>
		        </td>
		        <td bgcolor="">
		            <p align="center">
		            	<span style="font-size:12pt;">
		            		<!-- 부서위치 -->
		             		<b>${dept.loc}</b>
		             	</span>
		             </p>
		        </td>
		    </tr>
    </c:forEach>
</table>
<hr>
<div align=center>
	<!-- 메인으로 클릭 시, 모든 부서 정보 출력 -->
	<span style="font-size:12pt;"><input type="button" value="메인으로" onclick="location.href='/main'"></span>
	<!-- 부서생성 클릭 시, 새로운 부서 정보 입력 페이지로 이동 -->
	<span style="font-size:12pt;"><input type="button" value="부서생성" onclick="location.href='/dept'"></span>
</div>
</c:if>
<c:if test="${empty sessionScope.userId}">
   <p align="center"><b><span style="font-size:12pt;">로그인이 필요한 서비스입니다.</span></b></p>
</c:if>

<%@ include file="footer.jsp" %>

<script>

function doSearch(){
	 let detailForm = document.getElementById("detailForm");
	  let deptno = document.getElementById("more").value;
	  let condition = document.getElementById("less").value;
	  // type:hidden, 
	  // name:_method, 
	  // value:'DELETE' 값을 가지는 input 태그 내부에서 생성!
 	  let input = document.createElement('input'); 
	  input.type = 'hidden'; 
	  input.name = '_method'; 
	  input.value  = 'SELECT'; 
	  detailForm.appendChild(input); 
	  
	  detailForm.action = '/api/dynamic-sql/' +deptno+'/' + condition;
	  detailForm.method = 'POST';
	  detailForm.submit();
} 	

	function makeCondition(){
		
		document.getElementById("more").style.display="inline";
		document.getElementById("less").style.display="inline";
	
		
	}
	

	function doAction(){
		if(document.getElementById("searchDname").style.display == "none"){
			document.getElementById("searchDname").style.display="inline";
			document.getElementById("searchloc").style.display="inline";
		}else{
			document.getElementById("searchDname").style.display="none";
			document.getElementById("searchloc").style.display="none";
		}
	}
	
	function doAction2(){
		document.getElementById("empDeptSearch2").style.display="inline";
	}


</script>


</body>
</html>