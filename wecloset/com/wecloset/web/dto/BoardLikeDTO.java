package com.wecloset.web.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class BoardLikeDTO {

	private int likeID;
	private String userID;
	private int boardID;
	private String boardName;
	private int boardGroup;
	
	/** 1 - 좋아요.  2 - 좋아요 취소 */
	private int state;
	private Date regDate;
	
	
	
	@Override
	public String toString() {
		return "BoardLikeDTO [likeID=" + likeID + ", userID=" + userID + ", boardID=" + boardID + ", boardName="
				+ boardName + ", boardGroup=" + boardGroup + ", state=" + state + ", regDate=" + regDate + "]";
	}
	
		
}
