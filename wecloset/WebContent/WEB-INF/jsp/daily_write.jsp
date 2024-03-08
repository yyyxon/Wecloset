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
	
	<p style="text-align:center; padding:7px; padding-top:70px"> 
		<img src="images/logo/l_daily.png"> 	
	</p>

		<form name="multiform" id="dailyWrite_frm" action="${ConPath}/WeclosetServlet?command=dailyWrite"
                                      method="POST" enctype="multipart/form-data">				
		
			<table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th colspan="3"><h4>게시물 작성</h4></th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="width: 110px;"><h5>아이디</h5></td>
						<td><h5>${sessionScope.userID}</h5>						
					</tr>
					<tr>
						<td style="width: 110px;"><h5>파일 업로드</h5></td>
						<td colspan="2">
							<input type="file" name="boardFile" class="file" id="boardFile">
							<div class="input-group col-xs-12">
								<span class="input-group-addon"><i class="glyphicon glyphicon-picture"></i></span>
								<input type="text" class="form-control input-lg" disabled placeholder="파일을 업로드하세요.">
								<span class="input-group-btn">
									<button class="browse btn btn-primary input-lg" type="button"><i class="glyphicon glyphicon-search"></i> 파일 찾기</button>
								</span>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="3" class="text-right">
							<h5 style="color: red;"></h5>							
							<input class="btn btn-primary" type="button" value="등록" onclick="dailydWrite()"> 
							<input class="btn btn-primary" type="button" value="목록" onclick="javascript:window.history.back()"> 						
						</td>
					</tr>
					
	<!-- 				<tr id="result" >					
					</tr> -->
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
function dailydWrite(){	 
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
	$('#dailyWrite_frm').submit();	 
}


$(function(){
	
	$('#dailyWrite_frm').ajaxForm({	
		   beforeSubmit: function (data, frm, opt) {			
		       return true;
		   },
		   success: function(data, statusText){         		       
	       	 var pasreData=JSON.parse(data);
		     var msg=pasreData.msg;
		     console.dir(pasreData);
		     
			if(msg=="error"){        		 
	       		alert("등록 오류 입니다.");	        	        	
	       	 }else if(msg=="error_file_upload"){
	       		alert("파일 업로드 형식에 맞지 않습니다.");	       	 
	       	 }if(msg=='success'){    
	       		 console.log(pasreData.filesystemName);
	       		alert("등록 되었습니다.");
	       		location.href=$ConPath+"/WeclosetServlet?command=boardList&boardName=daily";		       		
	       		return;
	       	 }else{
	       		alert("오류 입니다.");	
	       		location.reload();	
	       	 }	        		
		             	 
		   },
		   error: function(e){
		       alert("파일 업로드 오류 입니다. 파일 용량은 10M 이하로 업로드 해주세요.");
		       console.log(e);		       
		   }            		  	
	});
	
});
</script>
	
</body>
</html>