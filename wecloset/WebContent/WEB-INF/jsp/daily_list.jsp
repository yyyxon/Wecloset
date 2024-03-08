<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<html>
<head>
	<!--헤드 -->
	<%@ include file="./include/head.jsp" %>
<!-- <link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.min.css" media="screen">
<script src="//cdnjs.cloudflare.com/ajax/libs/fancybox/2.1.5/jquery.fancybox.min.js"></script>
  -->
	<!--//헤드 -->	
	<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>	
</head>
<body>
	<!--상단메뉴 -->
	<%@ include file="./include/nav_top_menu.jsp" %>
	<!--//상단메뉴 -->
	
	
	<div class="container">
		<p style="text-align:center; padding:7px; padding-top:70px"> <img src="images/logo/l_daily.png" ></p>
		
		
		<table class="table table-bordered table-hover" style="text-align: center; border: none">
			<tbody>
				<tr>
					<td colspan="5">
			<a href="${pageContext.request.contextPath}/WeclosetServlet?command=dailyWrite" class="btn btn-primary pull-right" type="submit">
						<i class="glyphicon glyphicon-pencil"></i> 글쓰기
					</a>
					</td>
				</tr>
			</tbody>
		</table>



        <div class="row" id="daily_list">        	
		<%-- 			
			<c:forEach items="${boardList}" var="row">
	            <div class="col-lg-4 col-md-4 col-xs-6 thumb">
	                <a href="${ConPath}/upload${row.fileDirectory}${row.filesystemName}?boardID=${row.boardID}" class="fancybox" rel="ligthbox">
	                    <img  src="${ConPath}/upload${row.fileDirectory}${row.filesystemName}" class="zoom img-fluid">                  
	               	</a>
					<div class="text-center" >                
					<button type="button" class="btn_like ${row.likeState eq 1 ? 'btn_unlike' :'' }" data-boardid="${row.boardID}" >
					  <span class="img_emoti">좋아요</span>
					  <span class="ani_heart_m ${row.likeState eq 1 ? 'hi' :'' }"  id="ani_heart_m_${row.boardID}"></span>
					</button>
					</div>
				  </div>
			</c:forEach>  
			--%>       	        	
   		</div>
            


	</div>
	
	<!--모달팝업창 -->
	<%@ include file="./include/modal_popup.jsp" %>
	<!--//모달팝업창 -->
  
  	<!--footer -->
  	<div style="margin-top: 70px"></div>
	<%@ include file="./include/footer.jsp" %>
	<!--//footer -->


	<div tabindex="-1" class="modal fade" id="imgPopupModal" role="dialog" >
		<div class="modal-dialog">
			<div class="modal-content" >
				<div class="modal-header">
					<button class="close" type="button" data-dismiss="modal">×</button> 
				</div>
				<div class="modal-body text-center" style="text-align: center;"></div>
				<div class="modal-footer">
					<button class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	

<form class="form-block" id="frm_dailList" >
	<input type="hidden" name="boardName" value="${boardName}"  onmouseup="" />
	<input type="hidden" name="perPageNum"   value="9"/>	
</form>


<script id="dailyTemplate" type="text/x-handlebars-template">
{{#each .}}   

{{#if (boardAvailableCheck boardAvailable)}}	


  
		<div class="col-lg-4 col-sm-4 col-xs-6">
				
		      <a href="#" class="fancybox" rel="ligthbox" title="Image 1">
	  <img  src="${ConPath}/upload{{fileDirectory}}{{filesystemName}}" class="zoom img-fluid thumbnail img-responsive" onmouseover="dailyZoom(this)"
 onmouseout="dailyZoomOut(this)"  onclick="thumbnailClick(this); return false;">                  
	             </a>
		
				
				<div class="text-center like-div" >                
<a href="${ConPath}/WeclosetServlet?command=boardView&returnURL=&boardName=daily&boardID={{boardID}}" class="btn btn-primary gallery-detail"><i class="fa fa-info"></i> 상세보기</a>
				{{#if (likeStateCheck likeState)}}					
					<button type="button" class="btn_like btn_unlike"  onclick="btnLike(this)" data-boardid="{{boardID}}" > 					
					{{else}}
					<button type="button" class="btn_like" onclick="btnLike(this)"  data-boardid="{{boardID}}" >
				{{/if}}	

					  <span class="img_emoti">좋아요</span>
					 
				{{#if (likeStateCheck likeState)}}					
 					<span class="ani_heart_m hi"  id="ani_heart_m_{{boardID}}"></span> 					
					{{else}}
					 <span class="ani_heart_m "  id="ani_heart_m_{{boardID}}"></span> 
				{{/if}}	

					</button>
					</div>
			</div>



{{/if}}	
{{/each}}   
</script>

	

<script>
/* 데일리 이미지 리스트 */
var page=0;
galleryList();
function galleryList(){
	page=parseInt(page)+1;
	var boardName=$("#frm_dailList input[name=boardName]").val();
	var perPageNum=$("#frm_dailList input[name=perPageNum]").val();
		
	$.ajax({
	     type : 'post',
	     url : $ConPath+'/WeclosetServlet',
	     data :{
	    	 command:'dailyAjaxList',
	    	 boardName:boardName,	    	
	    	 perPageNum:perPageNum,
	    	 page:page
	     },
	     dataType : 'json',
	     success : function(resultJson){ 
	       //console.dir(resultJson); 
	       
	      if(resultJson.endingPage=="yes"){
	    	  //page=0;
	    	  //galleryList();
	      }else{	    	 
	          var source = $("#dailyTemplate").html();	 		
	          var template = Handlebars.compile(source);
			  Handlebars.registerHelper('likeStateCheck', function (value) {
		 		  return value=="1";
		 	  });
			  Handlebars.registerHelper('boardAvailableCheck', function (value) {
		 		  return value=="1";
		 	  });
	          $("#daily_list").append(template(resultJson.boardList));            
	      }

	     },error: function(result){
	    	 console.dir(result);
	    	 alert("오류 입니다.");
	     }	     	     
	 });
}



function thumbnailClick(event){
	$('.thumbnail').click(function() {
		$('.modal-body').empty();
		var title = $(event).parent('a').attr("title");
		
		$($(event).parents('div').html()).appendTo('.modal-body');
		$(".modal-body img").css("max-width", "800px");
		$(".modal-body img").css("max-height", "500px");
		$(".modal-body img").css("display", "inherit");
		$(".modal-body img").removeClass("zoom");
		$(".modal-body .fancybox").attr("onclick","imgPopupModalClose()");
					
		$(".modal-body .like-div  .gallery-detail").css("display", "inline-table");
		$(".modal-body .like-div  .gallery-detail").css("margin-top", "50px");
		$(".modal-body .btn_like").css("display", "none");
		
		$(".modal-body img").attr("onmouseover", "");
		$(".modal-body img").attr("onmouseout", "");
		$(".modal-body img").attr("onclick", "return false;");
		$('#imgPopupModal').modal({
			show : true
		});
	});
}

function dailyZoom(event){
	$(event).addClass('transition');
}

function dailyZoomOut(event){
	$(event).removeClass('transition');
}


var count = 0;
//스크롤 바닥 감지
window.onscroll = function(e) {
//추가되는 임시 콘텐츠
//window height + window scrollY 값이 document height보다 클 경우,
if((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
	//실행할 로직 (콘텐츠 추가)
    count++;
    //article에 추가되는 콘텐츠를 append
    galleryList();
    
 }
};

function imgPopupModalClose() {
	$("button.close").click();
}
</script>  
  
</body>
</html>	