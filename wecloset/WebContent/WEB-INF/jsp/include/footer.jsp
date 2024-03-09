<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<form action="${pageContext.request.contextPath}/WeclosetServlet" id="frm_action" >
	<input type="hidden" name="command" id="frm_command"  />
	<input type="hidden" name="returnURL" id="returnURL" />
	<input type="hidden" name="boardName" value="${boardName}"  id="boardName"/>
	<input type="hidden" name="boardID" id="frm_boardID" />
</form>

<form class="form-block" id="frm_board_list">
	<input type="hidden" name="boardName" value="${boardName}" />
	<input type="hidden" name="command"  value="board"  />
	<input type="hidden" name="page"  id="page"  />
	<input type="hidden" name="searchType" value="${param.searchType}"  />
	<input type="hidden" name="keyword"  value="${param.keyword}"  />
	<input type="hidden" name="perPageNum"  value="${pageMaker.perPageNum}"  />
</form>

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

<script>
$(function(){
	//상세 페이지 이동
	$(".tr-board").click(function(e){
		if($userID==""){
			alert("로그인 후 이용 가능합니다.");
			return false;
		}
		var boardid=$(this).attr("data-boardid");
		var boardName=$(this).attr("data-boardName");
		if(boardName!="" && boardName!=undefined){
			$("#boardName").val(boardName);
		}		
		boardDetail(boardid);
	});
	
	$("#updateForm").click(function(){
		$("#frm_boardID").val($(this).attr("data-boardid"));
		//$("#returnURL").val($(location).attr("search"));
		if($("#boardName").val()=="daily"){
			$("#frm_command").val("dailyUpdate");
		}else{
			$("#frm_command").val("boardUpdate");	
		}		
		$("#frm_action").submit();
	});
	
	   
	   
	$(".zoom").hover(function(){			
		$(this).addClass('transition');
		}, function(){	        
		$(this).removeClass('transition');
	});
	

	
});

// 페이지 이동
function boardPagination(page){	
	var command='${param.command}';
	var boardName='${param.boardName}';
	if(command=="boardSearch"){
		$("#frm_board_list input[name=command]").val('boardSearch');
		$("#frm_board_list input[name=boardName]").val(boardName);
	}
	$("#page").val(page);
	$("#frm_board_list").submit();
}

function boardDetail(boardid){
	$("#frm_boardID").val(boardid);
	$("#frm_command").val("boardView");
	$("#frm_action").submit();
}

function boardUpdate(){
	alert("수정 했습니다.");
}

function goBoardList(){
	var uri=$(location).attr('search');
	$("#returnURL").val($(location).attr("search"));
	$("#frm_command").val("board");
	$("#frm_action").submit();
}


function boardUnavailable(boardID){
	if(confirm("정말 삭제 하시겠습니까?")){
		$("#frm_command").val("boardUnavailable");
		$("#frm_boardID").val(boardID);
		$("#frm_action").attr("method", "post");
		$("#frm_action").submit();
	}
}

function boardDelete(boardID){
	if(confirm("정말 삭제 하시겠습니까?")){
		$("#frm_command").val("boardDelete");
		$("#frm_boardID").val(boardID);
		$("#frm_action").attr("method", "post");
		$("#frm_action").submit();
	}
}

function resize(obj) {
	  obj.style.height = "1px";
	  obj.style.height = (12+obj.scrollHeight)+"px";
}


function btnLike(event){
	 var boardID=$(event).attr('data-boardid');	
 	 if($(event).hasClass('btn_unlike')){
		 	if(likeUpdate(2, boardID)==true){		 				 		
		 	    $(event).removeClass('btn_unlike');	   		   
	   		    $('#ani_heart_m_'+boardID).removeClass('hi');
		   	    $('#ani_heart_m_'+boardID).addClass('bye');		   	    
		 	}
	  }else{	   		  
		    if(likeUpdate(1, boardID)==true){
		   	    $(event).addClass('btn_unlike');
		   	    $('#ani_heart_m_'+boardID).addClass('hi');
		   	    $('#ani_heart_m_'+boardID).removeClass('bye');
		   	   
		    }  
	  }
}


function likeUpdate(state, boardID){
		 
	if($userID==""){
		alert("로그인 후  가능합니다.");
		return false;
	}
	
	var boardName=$("#boardName").val();
	var boardID=boardID;	
	var obj = { state:state, boardName: boardName, boardID:boardID };
		
	$.ajax({
	     type : 'post',
	     url : $ConPath+'/WeclosetServlet',
	     data :{
	    	 command:'likeUpdate',
	    	 jsonData:JSON.stringify(obj),
	     },
	     dataType : 'json',
	     success : function(resultJson){ 
	    	 var msg=resultJson.msg;
	 		 if(msg=="success"){
	 			var likeCount=$("#likeCount").text();
	 			if(parseInt(state)==1){		 			
			   	    likeCount= parseInt(likeCount)+1
			   	 	$("#likeCount").text(likeCount);
	 			}else{				   
			   	     if(parseInt(likeCount)>0){
				   	    likeCount=parseInt(likeCount)-1;				   	 	
			   	     }
	 			}
	 			$("#likeCount").text(likeCount);
	 		 }else if(msg=="error"){
	 			
	 		 }
	     },error: function(result){
	    	 console.dir(result);

	     }	     	     
	 });
	
	return true;
}
</script>

	


<footer style="color: #2E2E2E; text-align: center; padding: 20px; background-color: #FFFFFF; background-size: 0px 0px;">
		<div class="container">						
			<p style="font-size:9pt;">COPYRIGHT@2020.WECLOSET. All rights reserved.</p>			
		</div>
</footer>
