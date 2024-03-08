<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<!--헤드  -->
	<%@ include file="./include/head.jsp" %>	
	<!--//헤드 -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
	
	<link rel="stylesheet" href="${ConPath}/css/magnific-popup.css">
	<script src="${ConPath}/js/jquery.magnific-popup.min.js"></script>
<style type="text/css">
.img-thumbnail{
	width: 100px;
	height: 100px;
}

</style>	
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
					<td class="td-fafafa" ><h5>제목</h5></td>
					<td colspan="3"><h5>${board.boardTitle}</h5></td>
				</tr>
				<tr>
					<td class="td-fafafa"><h5>작성자</h5></td>
					<td colspan="3"><h5>${board.userName}(${board.userID})</h5></td>
				</tr>
				<tr>
					<td class="td-fafafa" ><h5>작성날짜</h5></td>
					<td><h5>
					<%-- <fmt:formatDate value="${board.boardDate}" pattern="yyyy-MM-dd HH:ss"/> --%>
					${board.boardDateStr}
					</h5></td>
					<td><h5>조회수</h5></td>
					<td><h5>${board.boardHit}</h5></td>
				</tr>
				<tr>
				<!-- 글 내용 -->
					<td class="td-fafafa"> <h5>글 내용</h5></td>
					<td colspan="3" class="text-left"><h5>${board.boardContent}</h5>
					
			 <br><br><p>		
				<c:if test="${not empty board.filesystemName}">				  									
					<c:choose>
						<c:when test="${board.extension eq 'gif' or board.extension eq 'png' or  board.extension eq 'jpg' or  board.extension eq 'jpeg'}">
							<a href="${ConPath}/upload${board.fileDirectory}${board.filesystemName}" target="_blank" class="img-wrapper fancybox img_popup_zoom">
								<img src="${ConPath}/upload${board.fileDirectory}${board.filesystemName}" class="img-responsive img-thumbnail" >				
							</a>
							<a href="${ConPath}/FileDownload?f=${board.filesystemName}&ori=${board.originalFileName}&fd=${board.fileDirectory}" >
							 <i class="fa fa-download" aria-hidden="true" style="margin-left:10px; font-size:50px;"></i>
						   </a>
						</c:when>
						<c:otherwise>
						   <a href="${ConPath}/FileDownload?f=${board.filesystemName}&ori=${board.originalFileName}&fd=${board.fileDirectory}" >
							 <i class="fa fa-file" aria-hidden="true" style="font-size:50px;"></i>
						   </a>
						</c:otherwise>
					</c:choose>
				</c:if>
			 </p>	
					</td>
				<!-- 끝 -->
				</tr>
<%-- 				<tr>
					<td style="background-color: #fafafa; color: #000000; width: 80px;"><h5>첨부파일</h5></td>
					<td colspan="3" class="text-left profile-img">
		
				<c:if test="${not empty board.filesystemName}">				  				
					<c:choose>
						<c:when test="${board.extension eq 'gif' or board.extension eq 'png' or  board.extension eq 'jpg' or  board.extension eq 'jpeg'}">
							<a href="${ConPath}/upload${board.fileDirectory}${board.filesystemName}" target="_blank" class="img-wrapper fancybox img_popup_zoom">
								<img src="${ConPath}/upload${board.fileDirectory}${board.filesystemName}" class="img-responsive img-thumbnail" >
							</a>
						</c:when>
						<c:otherwise>
						   <a href="${ConPath}/FileDownload?f=${board.filesystemName}&ori=${board.originalFileName}&fd=${board.fileDirectory}" >
							 <i class="fa fa-file" aria-hidden="true" style="color:#2353a5;font-size:100px;"></i>
						   </a>
						</c:otherwise>
					</c:choose>
				</c:if>
					</td>
				</tr> --%>
			</thead>
			<tbody>
				<tr>
					<td colspan="5" style="text-align: right;" class="text-right">
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

<script type="text/javascript">
$('.img_popup_zoom').magnificPopup({
    type: 'image',
    closeOnContentClick: true,
    closeBtnInside: true,
    fixedContentPos: true,
    image: {    verticalFit: true    },
    gallery: {   enabled: true    }, // 좌우로 사진 돌려보기 생성
    zoom: {    enabled: true,   duration: 400    }
 });
</script>	
</body>
</html>