<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<!--헤드  -->
	<%@ include file="./include/head.jsp" %>	
	<!--//헤드 -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
	
</head>
<body>

	<!--상단메뉴  -->
	<%@ include file="./include/nav_top_menu.jsp" %>
	<!--//상단메뉴  -->
	
	
	<div class="container">
	
	
		<p style="text-align:center; padding:7px; padding-top:70px"> 
			<img src="images/logo/l_${boardName}.png"> 	
		</p>
	
		<table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd">
			<thead>
				<tr>
					<td class="td-fafafa text-center" style="width: auto; height: auto" >
						<a href="#"  class="fancybox text-center" onclick="return false;" >
							<img src="${ConPath}/upload${board.fileDirectory}${board.filesystemName}" class="img-responsive center-block img-fluid "
							style="max-width: 500px; height: auto"
							 >
						</a>
					</td>
					
					<td style="vertical-align: middle;">
					<table class="table">
					    <tr style="display: none;">
							<td class="td-fafafa"><h5>작성자</h5></td>
							<td colspan="3"><h5>${board.userName}(${board.userID})</h5></td>
						</tr>
						<tr>
							<td class="td-fafafa" ><h5>작성날짜</h5></td>
							<td><h5>${board.boardDateStr}
							<%-- <fmt:formatDate value="${board.boardDate}" pattern="yyyy-MM-dd HH:mm"/>
							 --%>
							</h5></td>
						</tr>
						<tr>
							<td class="td-fafafa"><h5>조회수</h5></td>
							<td><h5>${board.boardHit}</h5></td>
						</tr>
						<tr>
							<td class="td-fafafa"><h5>좋아요</h5></td>
							<td><h5><span id="likeCount">${board.likeCount}</span></h5></td>
						</tr>						
						<tr>							
							<td colspan="2">
							 <div class="text-center" >                
								<button type="button" class="btn_like ${board.likeState eq 1 ? 'btn_unlike' :'' }" data-boardid="${board.boardID}" onclick="btnLike(this)" >
								  <span class="img_emoti">좋아요</span>
								  <span class="ani_heart_m ${board.likeState eq 1 ? 'hi' :'' }" id="ani_heart_m_${board.boardID}"></span>
								</button>
								</div>														
					</button>
														
							</td>
						</tr>
					</table>						
					</td>
					
				</tr>
			
			</thead>
			<tbody>
				<tr>
					<td colspan="2" style="text-align: right;" class="text-right">
 					<c:if test="${sessionScope.userID eq board.userID}">
							<a href="#" class="btn btn-primary" id="updateForm" data-boardid="${board.boardID}">
							<i class="glyphicon glyphicon-pencil"></i> 수정</a>
					 		<a href="#" class="btn btn-primary" onclick="boardDelete('${board.boardID}'); return false;">삭제</a>
					</c:if> 
					<a href="${ConPath}/WeclosetServlet?command=boardList&boardName=${boardName}"  class="btn btn-primary" >목록</a>
					</td>
				</tr>
			</tbody>
		</table>
	

	<div class="row">
	<!--댓글	 -->
<%@ include file="./include/board_comments.jsp" %>
	<!--댓글	 -->
	</div>
	
	
		
		
		
	</div>


	<!--모달팝업창	 -->
	<%@ include file="./include/modal_popup.jsp" %>
	<!--//모달팝업창 -->

	
  
  
  	<!--footer	 -->
	<%@ include file="./include/footer.jsp" %>
	<!--//footer  -->

	
</body>
</html>