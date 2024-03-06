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
		if (userID == null) { //로그인이 되어 있지 않은 상태라면
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "현재 로그인이 되어 있지 않습니다.");
			response.sendRedirect("index.jsp");
			return;
		}
	%>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="css/bootstrap.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" href="css/custom.css">
	<title>우리들의 옷장, We closet!</title>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>
	<script type="text/javascript">
		function findFunction() {
			var userID = $('#findID').val(); //사용자가 입력한 찾을 아이디 값
			$.ajax({
				type: "POST",
				url: './UserFindServlet',
				data: {userID: userID},
				success: function(result) {
					if(result == -1) { //친구 찾기 실패
						$('#checkMessage').html('친구를 찾을 수 없습니다.');
						$('#checkType').attr('class', 'modal-content panel-warning');
						failFriend();	
					} else { //친구 찾기 성공
						$('#checkMessage').html('친구 찾기에 성공했습니다.');
						$('#checkType').attr('class', 'modal-content panel-success');
						var data = JSON.parse(result);
						var profile = data.userProfile;
						getFriend(userID, profile);
					} 
					$('#checkModal').modal("show");
				}
			});
		}
		
		function getFriend(findID, userProfile) {
			$('#friendResult').html('<thead>' +
					'<tr>' +
					'<th><h4>검색 결과</h4></th>' +
					'</tr>' +
					'</thead>' +
					'<tbody>' +
					'<tr>' +
					'<td style="text-align: center;">' +
					'<img class="media-object img-circle" style="max-width: 150px; margin: 0px auto;" src="' + userProfile +'">' +
					'<h3>' + findID + '</h3><a href="chat.jsp?toID=' + encodeURIComponent(findID) + '" class="btn btn-primary pull-right">' + '메시지 보내기</a></td>' +
					//메시지 보내기를 누르면 찾은 아이디와 바로 채팅 할 수 있도록 찾은 아이디와의 채팅창 페이지로 이동
					'</tr>' +
					'</tbody>');
		}
		
		function failFriend() {
			$('#friendResult').html('')
		}
		
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
	<!-- find.jsp는 반드시 로그인이 되어 있는 상태여야 들어올 수 있음 -->
	<!--상단메뉴  -->
	<%@ include file="/WEB-INF/jsp/include/nav_top_menu.jsp" %>
	<!--//상단메뉴	-->

	
	<p style="text-align:center; padding:10px; padding-top:30px"> <img src="images/logo/buddy.png"> </p>
	
	<!-- 친구 찾기 테이블 시작 -->
	<div class="container">
		<table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd;">
			<thead>
				<tr>
					<th colspan="2"><h4>친구 찾기</h4></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td style="width: 110px;"><h5>친구 아이디</h5></td>
					<td><input class="form-control" type="text" id="findID" maxlength="20" placeholder="찾을 아이디를 입력하세요."></td>
				</tr>
				<tr>
					<td colspan="2"><button class="btn btn-primary pull-right" onclick="findFunction();">검색</button></td>
				</tr>
			</tbody>
		</table>
	</div>
	<!-- 친구 찾기 테이블 끝 -->
	
	<!-- 친구 검색 결과 테이블 시작 -->
	<div class="container">
		<table id="friendResult" class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd;" >
		</table>
	</div>
	<!-- 친구 검색 결과 테이블 끝 -->
	
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
	<%
		session.removeAttribute("messageContent");
	 	session.removeAttribute("messageType");
		}
	%>
	<!-- 모달 팝업창 끝 -->
	
	<!-- 체크 모달 시작 -->
	<div class="modal fade" id="checkModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="vertical-alignment-helper">
			<div class="modal-dialog vertical-align-center">
				<div id="checkType" class="modal-content panel-info">
					<div class="modal-header panel-heading">
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times</span>
							<span class="sr-only">Close</span>
						 </button>
						 <h4 class="modal-title">
						 	확인 메시지
						 </h4>
					</div>
					<div id="checkMessage" class="modal-body">
					</div>
					<div class="modal-footer">
					 	<button type="button" class="btn btn-primary" data-dismiss="modal">확인</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 체크 모달 끝 -->
	
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


	 <div style="margin-top: 260px"></div>
	<%@ include file="/WEB-INF/jsp/include/footer.jsp" %>
	
</body>
</html>