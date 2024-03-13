<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>   
<c:set var="boardDr" value="1111" />

	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="${ConPath}/css/bootstrap.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="${ConPath}/css/custom.css">
	<title>우리들의 옷장, We closet!</title>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="${ConPath}/js/bootstrap.js"></script>
	<script src="${ConPath}/js/jquery.form.min.js"></script>
	
	<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.min.css" media="screen">
	<script src="${ConPath}/js/jquery.fancybox.js"></script>
	
	<script type="text/javascript">
  	 const $ConPath='${ConPath}';
  	 const  $userID='${sessionScope.userID}';
  	</script>   
  	<script src="${ConPath}/js/custom.js"></script>  
	
	<script type="text/javascript">	
		function getUnread() {
			$.ajax({
				type: "POST",
				url: '${ConPath}'+"/chatUnread",
				data: {
					usreID: encodeURIComponent('${sessionScope.userID}'),
					//userID 값 전송
				},
				success: function(result) { 
					if(result >= 1) {
						showUnread(result);
						//성공적으로 데이터가 전달이 된 후 결과값이 1보다 크다면 정상적인 데이터가 들어온 것이므로 
						//사용자의 화면에 현재 읽지 않은 메세지를 showUnread 함수를 이용해 출력
					} else {
						showUnread('');
						//그렇지 않은 경우 공백 출력
					}
				}
			});
		}
		
		function getInfiniteUnread() {
			//반복적으로 일정한 주기마다 서버한테 현재 사용자가 읽지 않은 메세지의 개수를
			//알려달라고 요청하는 함수
			setInterval(function() {
				getUnread();
			}, 4000); //4초마다 1번 씩 getUnread 함수 수행
		}
		
		function showUnread(result) {
			$('#unread').html(result);
			//unread라는 아이디값을 가진 원소에 result를 매개변수로 전달
		}	
		
		function passwordCheckFunction() {
			var userPassword1 = $('#userPassword1').val();
			var userPassword2 = $('#userPassword2').val();
			if(userPassword1 != userPassword2) {
				$('#passwordCheckMessage').html('비밀번호가 서로 일치하지 않습니다.');
			} else {
				$('#passwordCheckMessage').html('');
			}
		}
	</script>