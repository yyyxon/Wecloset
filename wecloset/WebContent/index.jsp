<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<%
		String userID = null;
		if (session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}
	%>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="css/bootstrap.css">
	<link rel="stylesheet" href="css/custom.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	<title>우리들의 옷장, We closet!</title>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>
	<script type="text/javascript">
	
		function getUnread() {
			$.ajax({
				type: "POST",
				url: "./chatUnread",
				data: {
					usreID: encodeURIComponent('<%= userID %>'),
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
		
	</script>
</head>
<body>

	<!--상단메뉴	시작 -->
	<%@ include file="/WEB-INF/jsp/include/nav_top_menu.jsp" %>
	<!--//상단메뉴		끝 -->

	<div style="text-align: center;">
		<img style="max-width: 100%;" src="images/home.png">
	</div>
	
	
	<!-- 모달 팝업창 시작 -->
	<%
		String messageContent = null;
		if(session.getAttribute("messageContent") != null) {
			messageContent = (String) session.getAttribute("messageContent");
		}
		String messageType = null;
		if(session.getAttribute("messageType") != null) {
			messageType = (String) session.getAttribute("messageType");
		}
		if(messageContent != null) {
	%>
	<div class="modal fade" id="messageModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="vertical-alignment-helper">
			<div class="modal-dialog vertical-align-center">
				<div class="modal-content <% if(messageType.equals("오류 메시지")) out.println("panel-warning"); else out.println("panel-success"); %>">
					<div class="modal-header panel-heading">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times</span>
							<span class="sr-only">Close</span>
						 </button>
						 <h4 class="modal-title">
						 	<%= messageType %>
						 </h4>
					</div>
					<div class="modal-body">
					 	<%= messageContent %>
					</div>
					<div class="modal-footer">
					 	<button type="button" class="btn btn-primary" data-dismiss="modal">확인</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		$('#messageModal').modal("show");
	</script>
	<!-- 모달 팝업창 끝 -->
	<%
		session.removeAttribute("messageContent");
	 	session.removeAttribute("messageType");
		}
	%>
	
	<%
		if(userID != null) { //현재 사용자의 ID가 null이 아니라면 = 성공적으로 로그인이 된 상태라면
	%>
		<script type="text/javascript">
			$(document).ready(function() { //웹 문서가 로딩 되면 바로 해당 함수를 실행
				getUnread(); //안 읽은 메세지 개수를 페이지가 로딩 되자마자 출력
				getInfiniteUnread();
			});
			//반복적으로 현재 사용자가 읽지 않은 메세지를 불러오는 함수를 실행시키도록 함
		</script>
	<%	
		}
	
	%>
</body>
</html>