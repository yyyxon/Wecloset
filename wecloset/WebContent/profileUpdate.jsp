<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="user.UserDTO" %>
<%@ page import="user.UserDAO" %>
<!DOCTYPE html>
<html>
<head>
	<%
		String userID = null;
		if (session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}
		
		if (userID == null) { //로그인이 되어 있지 않은 경우
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "현재 로그인이 되어 있지 않습니다.");
			response.sendRedirect("index.jsp");
			return;
		}
		
		UserDTO user = new UserDAO().getUser(userID); //로그인 되어 있는 경우, 사용자의 정보를 user 객체에 담을 수 있도록 함
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
	
</head>
<body>



	<!--상단메뉴	 -->
	<%@ include file="/WEB-INF/jsp/include/nav_top_menu.jsp" %>
	<!--//상단메뉴	 -->
	
	
	

	<div class="container">
		<form method="post" action="userProfile" enctype="multipart/form-data">
			<table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th colspan="2"><h4>프로필 사진 수정</h4></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="width: 110px;"><h5>아이디</h5></td>
						<td><h5><%= user.getUserID() %></h5>
						<input type="hidden" name="userID" value="<%= user.getUserID() %>"></td>
					</tr>
					<tr>
						<td style="width: 110px;"><h5>이름</h5></td>
						<td><h5><%= user.getUserName() %></h5>
						</td>
					</tr>
					<tr>						
						<td style="width: 110px;"><h5>이미지</h5></td>
						
						 <td class="profile-img">						
						 <%
						 if(user.getUserProfile()==null || user.getUserProfile().equals("")){							 
							 if(user.getUserGender()!=null && user.getUserGender().equals("남자")){							 
							  out.print("<img src='"+request.getContextPath()+"/images/man.jpg' ");
							}else if(user.getUserGender()!=null && user.getUserGender().equals("여자")){
								out.print("<img src='"+request.getContextPath()+"/images/woman.png' ");
							 }
						 }else{
							 out.print("<img src='"+request.getContextPath()+"/upload/"+user.getUserProfile()+"' ");
						 }
						%>
						</td>
					</tr>
										
					<tr>
						<td style="width: 110px;"><h5>사진 업로드</h5></td>
						<td colspan="2">
							<input type="file" name="userProfile" class="file">
							<div class="input-group col-xs-12">
								<span class="input-group-addon"><i class="glyphicon glyphicon-picture"></i></span>
								<input type="text" class="form-control input-lg" disabled placeholder="사진을 업로드 해주세요.">
								<span class="input-group-btn">
									<button class="browse btn btn-primary input-lg" type="button"><i class="glyphicon glyphicon-search"></i> 파일 찾기</button>
								</span>
							</div>
						</td>
					</tr>
					<tr>
						<td style="text-align: left;" colspan="3">
							<h5 style="color: red;"></h5>
							<input class="btn btn-primary pull-right" type="submit" value="등록"> <!-- 사진 등록 -->
						</td>
					</tr>
				</tbody>
			</table>
		</form>
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
	
	<!-- 프로필 사진 업로드 기능 시작 -->
	<script type="text/javascript">
		$(document).on('click', '.browse', function() { //134번 줄의 browse 버튼(파일 찾기) 관련 처리
			var file = $(this).parent().parent().parent().find('.file');
			file.trigger('click');
		});
		$(document).on('change', '.file', function() {
			$(this).parent().find('.form-control').val($(this).val().replace(/C:\\fakepath\\/i, ''));
		});
	</script>
	<!-- 프로필 사진 업로드 기능 끝 -->
	
	
		
</body>
</html>