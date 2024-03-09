<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script id="commentTemplate" type="text/x-handlebars-template">
{{#each .}}     
		<div class="be-img-comment ">	
			<a href="#" onclick="return false;">
{{#if (isdefined userProfile)}}
	<img src='upload/{{userProfile}}'  class="be-ava-comment" >
	{{else}}
	 {{#if (isMan userGender)}}	
		 <img src='${ConPath}/images/man.jpg'  class="be-ava-comment" >	
	 	{{else}}
		 <img src='${ConPath}/images/woman.png'  class="be-ava-comment" >			
	{{/if}}
{{/if}}

			</a>
		</div>
		<div class="be-comment-content">			
				<span class="be-comment-name">
					<a href="#" onclick="return false;">{{userName}}</a>
					</span>
				<span class="be-comment-time">
					
				{{#if (userCheck userID)}}					
					<a href="#"  onclick="commentUpdate({{commentID}}); return false;" ><i class="fa fa-edit fa-lg"  style="color:black;" ></i></a>
					<a href="#"  onclick="commentDeleteAction({{commentID}}); return false;" ><i class="fa fa-trash fa-lg"  style="color:black;" ></i></a>
					{{else}}
					<a href="#" onclick="chattingAction('{{userID}}'); return false;" title="채팅" ><img src='${ConPath}/images/c1.png'  class="be-ava-chatting" ></a>
				{{/if}}	

					<i class="fa fa-clock-o"></i>
					{{updateDate}}
				</span>

			<p class="be-comment-text">			
				<textarea class="comment_textarea "  onmouseup="resize(this)" onclick="resize(this)"
		 onkeydown="resize(this)" onkeyup="resize(this)" readonly id="commentContent-{{commentID}}" data-userID={{userID}} >{{ commentContent}}</textarea>			 
			</p>
			<p class="text-right" style="display:none" id="commentUpdate-display-{{commentID}}">
 				<button class="btn btn-primary "  style="padding: 3px 6px; font-size:12px;" onclick="commentUpdateAction({{commentID}});">수정</button>
			</p>
		</div>

{{/each}}   
</script>


<div class="container">
<div class="be-comment-block">
	<h1 class="comments-title">댓글 (<span id="commentTotalCount">0</span>)</h1>	
	<div class="be-comment" >
	 <!--
			<div class="be-img-comment">	
				<a href="blog-detail-2.html">
					<img src="https://bootdey.com/img/Content/avatar/avatar1.png" alt="" class="be-ava-comment">
				</a>
			</div>
			<div class="be-comment-content">			
		 	
				<span class="be-comment-name">
						<a href="blog-detail-2.html">폼폼</a>
						</span>
					<span class="be-comment-time">
						<i class="fa fa-clock-o"></i>
						2020.06.12  21:18
					</span>
				<p class="be-comment-text">
					공유 감사합니다.
				</p>
			 
			</div>
		-->
	</div>
	<div id="commentPagination" class="text-center"></div>
		
		
		
	<form class="form-block" id="frm_comment">
		<div class="row">
			<div class="col-xs-12">									
				<div class="form-group">
					<input type="hidden" name="boardName" value="${boardName}" />
					<input type="hidden" name="boardID"  value="${board.boardID}"  />												
					<textarea class="form-input" name="commentContent" id="commentContent" placeholder="댓글을 입력하세요." ></textarea>
				</div>
			</div>
			<a class="btn btn-primary pull-right" href="#"  id="commentWrite" onclick="return false">등록</a>
		</div>
	</form>
	<input type="hidden" name="perPageNum"  id="commentPerPageNum"  value="5"/>	
	<input type="hidden" name="commentPage"  id="commentPage"  value="1"/>			
	
</div>
</div>


<script>
$(function(){
	$("#commentWrite").on("click",function(e){
		commentWrite();
	});		
	commentList(1);
});

/* 댓글 작성 */
function commentWrite(){
	if($userID==""){
		alert("로그인 후 작성 가능합니다.");
		return;
	}
	var commentContent=$("#commentContent").val();
				
	if($.trim(commentContent)==""){
		alert("댓글을 입력하세요.");
		$("#commentContent").focus();
		return;
	}	
	
	const serializedValues = $('#frm_comment').serializeObject();
	
	$.ajax({
	     type : 'post',
	     url : $ConPath+'/WeclosetServlet',
	     data :{
	    	 command:'commentWrite',
	    	 jsonData:JSON.stringify(serializedValues),
	     },
	     dataType : 'json',
	     success : function(resultJson){ 
	    	 var msg=resultJson.msg;
	 		 if(msg=="success"){
	 			$("#commentContent").val("");
	 			commentList(1);
	 		 }else if(msg=="error"){
	 			alert("댓글 등록에 실패 하였습니다.");	 
	 		 }
	     },error: function(result){
	    	 console.dir(result);
	    	 alert("댓글 등록 오류 입니다.");
	     }	     	     
	 });
	
}


/* 댓글 목록 */
function commentList(page){
	var boardID=$("#frm_comment input[name=boardID]").val();
	var boardName=$("#frm_comment input[name=boardName]").val();
	var perPageNum=$("#commentPerPageNum").val();
	$("#commentPage").val(page);
	
	$.ajax({
	     type : 'post',
	     url : $ConPath+'/WeclosetServlet',
	     data :{
	    	 command:'commentList',
	    	 boardName:boardName,
	    	 boardID:boardID,
	    	 perPageNum:perPageNum,
	    	 page:page
	     },
	     dataType : 'json',
	     success : function(resultJson){ 
	      //console.dir(resultJson); 

	 	   //핸들바 템플릿 가져오기
           var source = $("#commentTemplate").html();	 		
           var template = Handlebars.compile(source);

	 	  Handlebars.registerHelper('isdefined', function (value) {
	 		  return value !== "";
	 	  });
		  Handlebars.registerHelper('isMan', function (value) {
		 		  return value=="남자";
		  });	 	   	 	  

		  Handlebars.registerHelper('userCheck', function (userID) {
	 		  return userID==$userID;
		  });

		  
           $(".be-comment").html(template(resultJson.commentList));             
	 	   $("#commentTotalCount").text(resultJson.commentTotalCount);
		   $("#commentPagination").html(resultJson.commentPagination);
		   $("#commentPerPageNum").val(resultJson.commentPerPageNum);	
		   
		   
		   $(".comment_textarea").click();
	     },error: function(result){
	    	 console.dir(result);
	    	 alert("댓글 목록 오류 입니다.");
	     }	     	     
	 });
}

/* 댓글 수정 폼 보기*/
function commentUpdate(commentID){
  $("#commentContent-"+commentID).css("background-color", "white");
  $("#commentUpdate-display-"+commentID).css("display", "block");
  $("#commentContent-"+commentID).removeAttr("readonly");
}

function commentUpdateAction(commentID){
	
	if($userID==""){
		alert("로그인 후 작성 가능합니다.");
		return;
	}
	
	var commentContent=$("#commentContent-"+commentID).val();
	
	if($.trim(commentContent)==""){
		alert("댓글을 입력하세요.");
		$("#commentContent").focus();
		return;
	}	
	
		
	$.ajax({
	     type : 'post',
	     url : $ConPath+'/WeclosetServlet',
	     data :{
	    	 command:'commentUpdate',
	    	 commentID:commentID,
	    	 commentContent:commentContent	    	 
	     },
	     dataType : 'json',
	     success : function(resultJson){ 
	    	 var msg=resultJson.msg;
	 		 if(msg=="success"){	 			
	 			commentList($("#commentPage").val());
	 		 }else if(msg=="error"){
	 			alert("댓글 수정에 실패 하였습니다.");	 
	 		 }
	     },error: function(result){
	    	 console.dir(result);
	    	 alert("댓글 수정 오류 입니다.");
	     }	     	     
	 });
	
	
}

function commentDeleteAction(commentID){
	if(confirm("정말 삭제 하시겠습니까?")){		
			
		$.ajax({
		     type : 'post',
		     url : $ConPath+'/WeclosetServlet',
		     data :{
		    	 command:'commentDelete',
		    	 commentID:commentID   	 
		     },
		     dataType : 'json',
		     success : function(resultJson){ 
		    	 var msg=resultJson.msg;
		 		 if(msg=="success"){	 			
		 			commentList(1);
		 		 }else if(msg=="error"){
		 			alert("댓글 삭제에 실패 하였습니다.");	 
		 		 }
		     },error: function(result){
		    	 console.dir(result);
		    	 alert("댓글 삭제 오류 입니다.");
		     }	     	     
		 });
		
	}	
}

function chattingAction(userID){	
	location.href=$ConPath+"/chat.jsp?toID="+userID;
}
</script>    


