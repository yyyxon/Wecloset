package com.wecloset.web.dto;

import java.sql.Date;

import config.PageMakerAndSearch;
import lombok.Data;


@Data
public class CommentDTO  {
		
	private int num;
	private int commentID;	
	private int boardID;
	private String userID;	
	private String userName;
	private int boardGroup;	
	private String boardName;
	private String commentContent;
	private String userGender;
	private String userProfile;	
	private String regDate;
	private String updateDate;
	
	
	@Override
	public String toString() {
		return "CommentDTO [num=" + num + ", commentID=" + commentID + ", boardID=" + boardID + ", userID=" + userID
				+ ", userName=" + userName + ", boardGroup=" + boardGroup + ", boardName=" + boardName
				+ ", commentContent=" + commentContent + ", regDate=" + regDate + ", updateDate=" + updateDate + "]";
	}
	
	
}
