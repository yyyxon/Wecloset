/* ****************
 *  일정 편집
 * ************** */
var editEvent = function (event, element, view) {
	
    $('#deleteEvent').data('id', event._id); //클릭한 이벤트 ID

    $('.popover.fade.top').remove();
    $(element).popover("hide");

    if (event.allDay === true) {
        editAllDay.prop('checked', true);
    } else {
        editAllDay.prop('checked', false);
    }

    if (event.end === null) {
        event.end = event.start;
    }

    if (event.allDay === true && event.end !== event.start) {
        editEnd.val(moment(event.end).subtract(1, 'days').format('YYYY-MM-DD HH:mm'))
    } else {
        editEnd.val(event.end.format('YYYY-MM-DD HH:mm'));
    }

	

    modalTitle.html('일정 수정');
    editTitle.val(event.title);
    editStart.val(event.start.format('YYYY-MM-DD HH:mm'));
    editType.val(event.type);
    editDesc.val(event.description);
    editColor.val(event.backgroundColor).css('color', event.backgroundColor);

    addBtnContainer.hide();
    modifyBtnContainer.show();
    eventModal.modal('show');

	editColorChange();

    //업데이트 버튼 클릭시
    $('#updateEvent').unbind();
    $('#updateEvent').on('click', function () {

        if (editStart.val() > editEnd.val()) {
            alert('끝나는 날짜가 앞설 수 없습니다.');
            return false;
        }

        if (editTitle.val() === '') {
            alert('일정명은 필수입니다.')
            return false;
        }

        var statusAllDay;
        var startDate;
        var endDate;
        var displayDate;

        if (editAllDay.is(':checked')) {
            statusAllDay = true;
            startDate = moment(editStart.val()).format('YYYY-MM-DD');
            endDate = moment(editEnd.val()).format('YYYY-MM-DD');
            displayDate = moment(editEnd.val()).add(1, 'days').format('YYYY-MM-DD');
        } else {
            statusAllDay = false;
            startDate = editStart.val();
            endDate = editEnd.val();
            displayDate = endDate;
        }

        eventModal.modal('hide');

        event.allDay = statusAllDay;
        event.title = editTitle.val();
        event.start = startDate;
        event.end = displayDate;
        event.type = editType.val();
        event.backgroundColor = editColor.val();
        event.description = editDesc.val();

        $("#calendar").fullCalendar('updateEvent', event);
		//console.dir(event);
		
		var eventData = {
            _id: event._id,
            title: event.title,
            start: event.start,
            end: event.end,
            description: event.description,
            type: event.type,
            username: event.username,
            backgroundColor: event.backgroundColor,
            textColor: event.textColor,
            allDay: event.allDay
        };
		
		//console.log("eventData  전환");
		//console.dir(eventData);
		
        //일정 업데이트
        $.ajax({
            type: "post",
            url: $ConPath+"/WeclosetServlet",
            data: {
				command:'calendarUpdate',
                eventData:JSON.stringify(eventData)
            },
			dataType : 'json',
            success: function (response) {
				if(response.msg=="success"){
					//alert('수정 되었습니다.');	
				}else if(response.msg=="erro_user"){
				    alert('작성자만 수정 가능합니다.');					
				}else{
					alert("수정 오류 입니다.");
					location.reload();
				} 
				//location.reload();				              
            }
        });

    });



		
	// 삭제버튼
	$('#deleteEvent').on('click', function () {
	    $('#deleteEvent').unbind();
		if(confirm("정말 삭제 하시겠습니까?")){
				var calendarId=event._id;
				eventData = {
			        calendarId: event._id
			    };
			
			    //삭제시
			    $.ajax({
			        type: "post",
			        url: $ConPath+"/WeclosetServlet",
			        data: {
							command:'calendarDelete',
			                eventData:JSON.stringify(eventData)
			        },
					dataType : 'json',
			        success: function (response) {           
						if(response.msg=="success"){				 
							// alert('삭제 되었습니다.');
						    //$('#deleteEvent').unbind();
							//alert(event._id);
			    			$("#calendar").fullCalendar('removeEvents', event._id);
			   				eventModal.modal('hide');
			
						}else if(response.msg=="erro_user"){
						    alert('등록자만 삭제 가능합니다.');
						}else{
							alert("삭제 처리 오류 입니다.");
							location.reload();
						}  				
						//location.reload();	
			        }
			    });
			
		}
		
	
	});


};
