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
		
		if(userID == null) { //로그인 안 한 경우
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
		
		function chatBoxFunction() {
			var userID = '<%= userID %>';
			//현재 로그인한 사용자의 아이디
			$.ajax({
				type: "POST",
				url: "./chatBox", //ChatBoxServlet에 데이터 전송
				data: {
					userID: encodeURIComponent(userID),
				},
				success: function(data) { 
					if(data == "") return; //결과 데이터가 비어있다면 그냥 리턴
					$('#boxTable').html(''); //비어있지 않으면 boxTable의 html 영역을 공백으로 초기화
					var parsed = JSON.parse(data); //ajax로 통신해서 받아온 데이터를 JSON으로 파싱한 값 저장
					var result = parsed.result;
					for(var i = 0; i < result.length; i++) {
						if(result[i][0].value == userID) {
							//특정한 메세지를 보낸 사람이 나인지 상대방인지에 따라서 다른 방식으로 데이터를 출력
							result[i][0].value = result[i][1].value;
						} else {
							result[i][1].value = result[i][0].value;
						}
						addBox(result[i][0].value, result[i][1].value, result[i][2].value, result[i][3].value, result[i][4].value, result[i][5].value);
					}
				}
			});
		}
		
		//화면에 각각의 메세지 목록을 출력해주는 함수
		function addBox(lastID, toID, chatContent, chatTime, unread, profile) {
			//그 행을 눌렀을 때 chat.jsp 페이지로 이동
			$('#boxTable').append('<tr onclick="location.href=\'chat.jsp?toID=' + encodeURIComponent(toID) + '\'">' +
					'<td style="width: 150px;">' + 
					'<img class="media-object img-circle" style="margin: 0 auto; max-width: 40px; max-height: 40px;" src = "' + profile + '">' +
					'<h5>' + lastID + '</h5></td>' +
					'<td>' +
					'<h5>' + chatContent +
					' <span class="label label-info">' + unread + '</span></h5>' +
					'<div class="pull-right">' + chatTime + '</div>' +
					'</td>' +
					'</tr>');
		}
		
		//3초에 한번씩 채팅목록 갱신
		function getInfiniteBox() {
			setInterval(function() {
				chatBoxFunction();
			}, 3000);
		}
	</script>
</head>
<body>
	<!--상단메뉴	 -->
	<%@ include file="/WEB-INF/jsp/include/nav_top_menu.jsp" %>
	<!--//상단메뉴 -->

	
	<!-- 채팅목록 디자인 시작 -->
	<div class="container">
	<p style="text-align:center; padding:10px; padding-top:30px"> <img src="images/logo/chat.png"> </p>
		<table class="table" style="margin: 0 auto;">
			<thead>
				<tr>
					<th><h4>채팅 목록</h4></th>
				</tr>
			</thead>
			<div style="overflow-y: auto; width: 100%; max-height: 450px;">
				<table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd; margin: 0 auto;">
					<tbody id="boxTable">
					</tbody>
				</table>
			</div>
		</table>
	</div>
	<!-- 채팅목록 디자인 끝 -->
	
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
	<!-- 모달 팝업창 끝 -->

	<%
		session.removeAttribute("messageContent");
	 	session.removeAttribute("messageType");
		}
	%>
	
	<script>
		$('#messageModal').modal("show");
	</script>
	
	<%
		if(userID != null) { //현재 사용자의 ID가 null이 아니라면 = 성공적으로 로그인이 된 상태라면
	%>
		<script type="text/javascript">
			$(document).ready(function() { //웹 문서가 로딩 되면 바로 해당 함수를 실행
				getUnread(); //안 읽은 메세지 개수를 페이지가 로딩 되자마자 출력
				getInfiniteUnread();
				chatBoxFunction();
				getInfiniteBox();
			});
			//반복적으로 현재 사용자가 읽지 않은 메세지를 불러오는 함수를 실행시키도록 함
		</script>
	<%	
		}
	
	%>
	
	 
	 <div style="margin-top: 50px"></div>
	<%@ include file="/WEB-INF/jsp/include/footer.jsp" %>
	
</body>
</html>