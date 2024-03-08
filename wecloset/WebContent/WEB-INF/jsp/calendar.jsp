<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>	
	<meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>우리들의 옷장, We closet!</title>
    <link rel=" shortcut icon" href="image/favicon.ico">
    <link rel="stylesheet" href="${ConPath}/calendar/vendor/css/fullcalendar.min.css" />
    <link rel="stylesheet" href="${ConPath}/calendar/vendor/css/bootstrap.min.css">    
    <link rel="stylesheet" href='${ConPath}/calendar/vendor/css/select2.min.css' />
    <link rel="stylesheet" href='${ConPath}/calendar/vendor/css/bootstrap-datetimepicker.min.css' />
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Open+Sans:400,500,600">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="${ConPath}/calendar/css/main.css">	 
    <link rel="stylesheet" href="${ConPath}/css/custom.css">
    <script src="${ConPath}/calendar/vendor/js/jquery.min.js"></script>
    <script type="text/javascript">
  	 const $ConPath='${ConPath}';
  	 const  $userID='${sessionScope.userID}';
  	 const $userName='${USER.userName}';
  	</script>   
  	<script src="${ConPath}/js/custom.js"></script>  
    <script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
</head>
<body>
	<input type="hidden" id="editUserName" value="${USER.userName}">
	<input type="hidden" id="editUserID" value="${sessionScope.userID}">
	
   <%@ include file="./include/nav_top_menu.jsp" %>
	
	<div class="container">

   <!-- 일자 클릭시 메뉴오픈 -->
        <div id="contextMenu" class="dropdown clearfix">
            <ul class="dropdown-menu dropNewEvent" role="menu" aria-labelledby="dropdownMenu"
                style="display:block;position:static;margin-bottom:5px;">
                <li><a tabindex="-1" href="#" class="c_menuList">세일</a></li>
                <li><a tabindex="-1" href="#" class="c_menuList">시즌</a></li>
                <li><a tabindex="-1" href="#" class="c_menuList">체험단</a></li>
                <li><a tabindex="-1" href="#" class="c_menuList">래플</a></li>
                <li class="divider"></li>
                <li><a tabindex="-1" href="#" data-role="close">Close</a></li>
            </ul>
        </div>

        <div id="wrapper">
            <div id="loading"></div>
            <div id="calendar"></div>
        </div>


        <!-- 일정 추가 MODAL -->
        <div class="modal fade" tabindex="-1" role="dialog" id="eventModal">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title"></h4>
                    </div>
                    <div class="modal-body">

                        <div class="row">
                            <div class="col-xs-12">
                                <label class="col-xs-4" for="edit-allDay">하루종일</label>
                                <input class='allDayNewEvent' id="edit-allDay" type="checkbox"></label>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-xs-12">
                                <label class="col-xs-4" for="edit-title">일정명</label>
                                <input class="inputModal" type="text" name="edit-title" id="edit-title"
                                    required="required" />
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <label class="col-xs-4" for="edit-start">시작</label>
                                <input class="inputModal" type="text" name="edit-start" id="edit-start" />
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <label class="col-xs-4" for="edit-end">끝</label>
                                <input class="inputModal" type="text" name="edit-end" id="edit-end" />
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <label class="col-xs-4" for="edit-type">구분</label>
                                <select class="inputModal" type="text" name="edit-type" id="edit-type" onchange="editColorChange()">   
		 							<option value="세일">세일</option>
		                            <option value="시즌">시즌</option>
		                            <option value="체험단">체험단</option>
		                            <option value="래플">래플</option>                                    
                                </select>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <label class="col-xs-4" for="edit-color">색상</label>
                                <select class="inputModal" name="color" id="edit-color" disabled="disabled" style="display: none">
                                    <option value="#d9534f" style="color:#d9534f;">빨간색</option>
                                    <option value="#5cb85c" style="color:#5cb85c;">녹색</option>
                                    <option value="#5bc0de" style="color:#5bc0de;">하늘색</option>
                                    <option value="#f0ad4e" style="color:#f0ad4e;">주황</option>
                                    <option value="#f06595" style="color:#f06595;">핑크색</option>
                                    <option value="#63e6be" style="color:#63e6be;">연두색</option>
                                    <option value="#a9e34b" style="color:#a9e34b;">초록색</option>
                                    <option value="#4d638c" style="color:#4d638c;">남색</option>
                                    <option value="#495057" style="color:#495057;">검정색</option>
                                </select> 
                                
<div  id="edit-color-progress" > 
                               
 <div class="progress danger display-none"  id="progress-menu-1">
  <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="40"aria-valuemin="0" aria-valuemax="100" style="width:100%">세일</div> 
</div>


 <div class="progress success display-none" id="progress-menu-2">
  <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40"aria-valuemin="0" aria-valuemax="100" style="width:100%">시즌</div> 
</div>

 <div class="progress info display-none" id="progress-menu-3">
  <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="40"aria-valuemin="0" aria-valuemax="100" style="width:100%">체험단</div> 
</div>

 <div class="progress warning display-none" id="progress-menu-4">
  <div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="40"aria-valuemin="0" aria-valuemax="100" style="width:100%">래플</div> 
</div>


</div>
                                
                            </div>
                            
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <label class="col-xs-4" for="edit-desc">설명</label>
                                <textarea rows="4" cols="50" class="inputModal" name="edit-desc"
                                    id="edit-desc"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer modalBtnContainer-addEvent">
                        <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
                        <button type="button" class="btn btn-primary" id="save-event">저장</button>
                    </div>
                    <div class="modal-footer modalBtnContainer-modifyEvent">
                        <button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
                        <button type="button" class="btn btn-danger" id="deleteEvent">삭제</button>
                        <button type="button" class="btn btn-primary" id="updateEvent">저장</button>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->



        <div class="panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title">필터</h3>
            </div>
            <div class="panel-body">
                <div class="col-lg-6">
                    <label for="calendar_view">구분별</label>
                    <div class="input-group">
                        <select class="filter" id="type_filter" multiple="multiple">
                            <option value="세일">세일</option>
                            <option value="시즌">시즌</option>
                            <option value="체험단">체험단</option>
                            <option value="래플">래플</option>
                        </select>
                    </div>
                </div>
                
                
                
                <div class="col-lg-6">               
 <div class="progress">
  <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="40"aria-valuemin="0" aria-valuemax="100" style="width:100%">세일</div> 
</div>

 <div class="progress">
  <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40"aria-valuemin="0" aria-valuemax="100" style="width:100%">시즌</div> 
</div>

 <div class="progress">
  <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="40"aria-valuemin="0" aria-valuemax="100" style="width:100%">체험단</div> 
</div>

 <div class="progress">
  <div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="40"aria-valuemin="0" aria-valuemax="100" style="width:100%">래플</div> 
</div>
                </div>


  
                
                
                <div class="col-lg-6" style="display:none ">
                    <label for="calendar_view">등록자</label>
                    <div class="input-group" id="calendarUserList">  
                    
                    	<c:forEach items="${userList}" var="row">
                    	   <label class="checkbox-inline">
 								<input class='filter' type="checkbox" value="${row.userID}" checked>${row.userID}
 					       </label> 
                    	</c:forEach>
               
					<!--
  						<label class="checkbox-inline">
 							<input class='filter' type="checkbox" value="정연" checked>정연
 						</label>                                
                        <label class="checkbox-inline">
                        	<input class='filter' type="checkbox" value="다현" checked>다현                        
                        </label>
                        <label class="checkbox-inline">
                        	<input class='filter' type="checkbox" value="사나" checked>사나
                        </label>
                        <label class="checkbox-inline">
                        	<input class='filter' type="checkbox" value="나연" checked>나연
                        </label>
                        <label class="checkbox-inline">
                        	<input class='filter' type="checkbox" value="지효" checked>지효
                        </label>                         
                        -->                                                    
                    </div>
                </div>

            </div>
        </div>
        <!-- /.filter panel -->

	
	</div>


<script id="calendarUserTemplate" type="text/x-handlebars-template">
{{#each .}}
  <label class="checkbox-inline" style="display:none" >
	<input class='filter' type="checkbox" value="{{userID}}"  checked>{{userID}}                        
  </label> 
{{/each}}  
</script>

	
	
	<%@ include file="./include/footer.jsp" %>
    <script src="${ConPath}/calendar/vendor/js/bootstrap.min.js"></script>
    <script src="${ConPath}/calendar/vendor/js/moment.min.js"></script>
    <script src="${ConPath}/calendar/vendor/js/fullcalendar.min.js"></script>
    <script src="${ConPath}/calendar/vendor/js/ko.js"></script>
    <script src="${ConPath}/calendar/vendor/js/select2.min.js"></script>
    <script src="${ConPath}/calendar/vendor/js/bootstrap-datetimepicker.min.js"></script>
    <script src="${ConPath}/calendar/js/main.js"></script>
    <script src="${ConPath}/calendar/js/addEvent.js"></script>
    <script src="${ConPath}/calendar/js/editEvent.js"></script>
    <script src="${ConPath}/calendar/js/etcSetting.js"></script>

<script>

$(function(){
	$(".c_menuList").click(function(event){
		$("#edit-color-progress .progress").css("display", "none");		
		var str=$(this).text();
		if(str==="세일"){			
			$("#edit-color").val("#d9534f").prop("selected", true);
			$("#progress-menu-1").css("display", "block");
		}else if(str==="시즌"){			
			$("#edit-color").val("#5cb85c").prop("selected", true);
			$("#progress-menu-2").css("display", "block");
		}else if(str==="체험단"){			
			$("#edit-color").val("#5bc0de").prop("selected", true);
			$("#progress-menu-3").css("display", "block");
		}else if(str==="래플"){			
			$("#edit-color").val("#f0ad4e").prop("selected", true);
			$("#progress-menu-4").css("display", "block");
		}
	});
})

</script>

</body>
</html>