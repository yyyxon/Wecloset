<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<!--헤드	시작 -->
	<%@ include file="./include/head.jsp" %>
	<!--//헤드	끝 -->
</head>
<body>
	<!--상단메뉴	시작 -->
	<%@ include file="./include/nav_top_menu.jsp" %>
	<!--//상단메뉴		끝 -->

	<div class="container">	
		<p class="container-main-img"> 
			<img src="images/logo/l_${boardName}.png">
		</p>
				
		<!-- 공통 테이블  게시판 목록 -->
		<%@ include file="./include/board_list_table.jsp" %>	
		<!--//공통 테이블  게시판 목록 -->		
	</div>
	
		
	<!--모달팝업창	시작 -->
	<%@ include file="./include/modal_popup.jsp" %>
	<!--//모달팝업창	끝 -->
  
  	<!--footer	시작 -->
  	<div style="margin-top: 50px"></div>
	<%@ include file="./include/footer.jsp" %>
	<!--//footer	끝 -->
	
</body>
</html>