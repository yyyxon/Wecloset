package com.wecloset.web.dto;

import lombok.Data;

@Data
public class CalendarDTO {
	
	private int calendarId;
	private int _id;
	private String title;
	private String description;
	private String start;
	private String end;
	private String type;
	private String username;
	private String userID;
	private String backgroundColor;
	private String textColor;
	private boolean allDay;
	private String actionType;
	
	
	
	@Override
	public String toString() {
		return "CalendarDTO [calendarId=" + calendarId + ", _id=" + _id + ", title=" + title + ", description="
				+ description + ", start=" + start + ", end=" + end + ", type=" + type + ", username=" + username
				+ ", userID=" + userID + ", backgroundColor=" + backgroundColor + ", textColor=" + textColor
				+ ", allDay=" + allDay + ", actionType=" + actionType + "]";
	}

	
	
	

	
	
	
	
}

