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
	
	<div class="container mb50">
	
	<p style="text-align:center; padding:7px; padding-top:70px"> 
		<img src="images/logo/l_${boardName}.png"> 	
	</p>

		<form name="multiform" id="boardUpdate_frm" action="${ConPath}/WeclosetServlet?command=dailyUpdate"
                                      method="POST" enctype="multipart/form-data">				
		
			<table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th colspan="3"><h4>게시물 수정</h4></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="width: 110px;"><h5>아이디</h5></td>
						<td><h5>${sessionScope.userID}</h5>						
					</tr>
					<tr>
						<td style="width: 110px;"><h5>파일 업로드</h5></td>
						<td colspan="2" class="td-fafafa" >
						
				<div id="file_display" class="text-left">							
				<c:if test="${not empty board.filesystemName}">				  				
						<a href="upload${board.fileDirectory}${board.filesystemName}" target="_blank"  onclick="return false"  class="fancybox">
						<img src="${ConPath}/upload${board.fileDirectory}${board.filesystemName}" class="img-fluid" style="max-width:360px;max-height: 349px">
						<span onclick="attachmentsDelte('${board.fileID}')" >X</span>								
					   </a>
				</c:if>
				</div>
							
				<c:if test="${ empty board.filesystemName}">				
							<input type="file" name="boardFile" class="file" id="boardFile">
							<div class="input-group col-xs-12">
								<span class="input-group-addon"><i class="glyphicon glyphicon-picture"></i></span>
								<input type="text" class="form-control input-lg" disabled placeholder="파일을 업로드하세요.">
								<span class="input-group-btn">
									<button class="browse btn btn-primary input-lg" type="button"><i class="glyphicon glyphicon-search"></i> 파일 찾기</button>
								</span>
							</div>
				</c:if>		
							
						</td>
					</tr>
					
					
					<tr>
						<td colspan="3" class="text-right">
							<h5 style="color: red;"></h5>						
							<input type="hidden" name="boardID" id="boardID"  value="${board.boardID}" >	
							<input type="hidden" name="boardName"   value="${boardName}" >								
							<input class="btn btn-primary" type="button" value="수정하기" onclick="dailyUpdate()" data-boardid="${board.boardID}"> 
							<input class="btn btn-primary" type="button" value="상세보기" onclick="boardDetail('${board.boardID}')" data-boardid="${board.boardID}"> 
							<input class="btn btn-primary" type="button" value="목록" onclick="goBoardList()"> 						
						</td>
					</tr>
					
				</tbody>
			</table>
		</form>
	</div>


	<!--모달팝업창	시작 -->
	<%@ include file="./include/modal_popup.jsp" %>
	<!--//모달팝업창	끝 -->

	
  
  
  	<!--footer	시작 -->
  	<div style="margin-top: 150px"></div>
	<%@ include file="./include/footer.jsp" %>
	<!--//footer	끝 -->


<script>
function dailyUpdate(){	 
	var boardFile=$("#boardFile").val();
	if(boardFile==""){
		alert("첨부파일을 선택해 주세요.");
		$("#boardFile").focus();
		return;	
	}	
	
	if($("#boardFile").val()!= "" ){
		var ext = $('#boardFile').val().split('.').pop().toLowerCase();
		      if($.inArray(ext, ['gif','png', 'jpg','jpeg']) == -1) {
			   alert('gif, png, jpg, jpeg 파일만 업로드 할수 있습니다.');
			  return;
		 }
   }

  $('#boardUpdate_frm').submit();	 
}


$(function(){
	
	$('#boardUpdate_frm').ajaxForm({	
		   beforeSubmit: function (data, frm, opt) {			
		       return true;
		   },
		   success: function(data, statusText){         		       
	       	 var pasreData=JSON.parse(data);
		     var msg=pasreData.msg;
		     console.dir(pasreData);
		     
		     if(msg=="error_param_null"){
	       		alert("비 정상적인 파라미터 입력값 오류입니다.");	       	
	       	 }else if(msg=="error"){        		 
	       		alert("등록 오류 입니다.");	        	        	
	       	 }else if(msg=="error_file_upload"){
	       		alert("파일 업로드 형식에 맞지 않습니다.");	       	 
	       	 }if(msg=='success'){    
	       		alert("수정되었습니다.");
	       		location.reload();		       		
	       		return;
	       	 }else{
	       		alert("오류 입니다.");	       		
	       	 }	        		
		       
	       	 location.reload();	       	 
		   },
		   error: function(e){
		       alert("파일 업로드 오류 입니다.");
		       console.log(e);
		       location.reload();	
		   }            		  	
	});
	
});

function attachmentsDelte(fileID){
	if(confirm("첨부 파일을 정말 삭제 하시겠습니까?")){	
		 $.ajax({
	         type : 'post',
	         url : $ConPath+'/WeclosetServlet',
	         data :{
	        	 command:'attachmentsDelete',
	        	 fileID:fileID,
	         },
	         dataType : 'json',
	         success : function(resultJson){     
	        	 var msg=$.trim(resultJson.msg);	        	 
	        	 if(msg=="success"){
	        		 location.reload();
	        	 }else if(msg=="error"){
	        		 alert("삭제 처리 오류 입니다.");
	        	 }
	         },
	         error: function(result){
	        	 console.dir(result);
	        	 alert("처리 오류 입니다.");
	         },
	     });
	}	
}
</script>
	
</body>
</html>