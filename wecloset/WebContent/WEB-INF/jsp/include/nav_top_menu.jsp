<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  	
	<nav class="navbar navbar-default">
		<div class="navber-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${pageContext.request.contextPath}/">We closet</a>
		</div>
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

		<c:set var="urlPath" value="${pageContext.request.servletPath}"/>
		
			<ul class="nav navbar-nav">
				<li class="${fn:contains(urlPath, 'index') ? 'active' :''}" ><a href="${pageContext.request.contextPath}/">HOME</a></li>
				<li class="${fn:contains(urlPath, 'calendar') ? 'active' :''}" ><a href="${pageContext.request.contextPath}/WeclosetServlet?command=calendar">캘린더</a></li>
				<li class="${param.boardName eq 'ppippi' ? 'active' : '' }" ><a href="${pageContext.request.contextPath}/WeclosetServlet?command=boardList&boardName=ppippi">삐삐</a></li>
				<li class="${param.boardName eq 'free' ? 'active' : '' }"><a href="${pageContext.request.contextPath}/WeclosetServlet?command=boardList&boardName=free">자유</a></li>
				<li class="${param.boardName eq 'question' ? 'active' : '' }"><a href="${pageContext.request.contextPath}/WeclosetServlet?command=boardList&boardName=question">질문</a></li>
				<li class="${param.boardName eq 'review' ? 'active' : '' }"><a href="${pageContext.request.contextPath}/WeclosetServlet?command=boardList&boardName=review">후기</a></li>
				<li class="${param.boardName eq 'daily' ? 'active' : '' }"><a href="${pageContext.request.contextPath}/WeclosetServlet?command=boardList&boardName=daily">데일리룩</a></li>
				<li class="${param.boardName eq 'fleaMarket' ? 'active' : '' }"><a href="${pageContext.request.contextPath}/WeclosetServlet?command=boardList&boardName=fleaMarket">플리마켓</a></li>
				<li class="${param.boardName eq 'stationery' ? 'active' : '' }"><a href="${pageContext.request.contextPath}/WeclosetServlet?command=boardList&boardName=stationery">문방구</a></li>
				<li class="${fn:contains(urlPath, 'box') ? 'active' :''}"><a href="box.jsp">채팅 <span id="unread" class="label label-info"></span></a></li>
				<li class="${fn:contains(urlPath, 'find') ? 'active' :''}"><a href="find.jsp">친구찾기</a></li>				
				<li>
					 <div class="searchbar">
				          <input class="search_input" type="text" name="" placeholder="검색" id="webSearch"  value="${param.keyword}">
				          <a href="#" class="search_icon" onclick="webSearch(this)"><i class="fa fa-search"></i></a>
			        </div>
				</li>			
			</ul>

			
			<c:choose>
				<c:when test="${empty sessionScope.userID}">
					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown">
							<a href="#" class="dropdown-toggle"
								data-toggle="dropdown" role="buton" aria-haspopup="true"
								aria-expanded="false"> ₍ᐢ•ᴥ•ᐢ₎っ⌁♡⌁⋆
							</a>
							<ul class="dropdown-menu">
								<li><a href="login.jsp">로그인</a></li>
								<li><a href="join.jsp">회원가입</a></li>
							</ul>
						</li>
					</ul>
				</c:when>
				<c:otherwise>
					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown">
							<a href="#" class="dropdown-toggle"
								data-toggle="dropdown" role="buton" aria-haspopup="true"
								aria-expanded="false"> ₍ᐢ•ᴥ•ᐢ₎っ⌁❤⌁⋆ 
							</a>
							<ul class="dropdown-menu">
								<li><a href="update.jsp">내 정보 수정</a></li>
								<li><a href="profileUpdate.jsp">프로필 사진 수정</a></li>
								<li><a href="logoutAction.jsp">로그아웃</a></li>
							</ul>
						</li>
					</ul>
				</c:otherwise>
			</c:choose>
			

		</div>
		
	</nav>
	
	


<script>
$(function(){	
    $("#webSearch").keydown(function (key) {
        if(key.keyCode == 13){
        	webSearch();
        }
    });
    webSearch = function (){
        var keyword=$("#webSearch").val();
        location.href='${pageContext.request.contextPath}/WeclosetServlet?command=boardSearch&keyword='+keyword;
    }; 
});
</script>
	
	