package com.wecloset.web.dto;

import java.sql.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Data
public class BoardDTO extends AttachmentsDTO {
	
	private int num;	
	private String userID;
	private String userName;
	private int boardID;	
	private String boardTitle;
	private String boardContent;
	private Date boardDate;
	private int boardHit;
	private int boardGroup;
	private String boardName;
	private int likeCount;
	private int likeState;	
	private int boardSequence;
	private int boardLevel;
	private int boardAvailable;
	private int commentCount;
	private int attachCount;
	private Date updateDate;
	private String boardDateStr;
	
	
	@Override
	public String toString() {
		return "BoardDTO [num=" + num + ", userID=" + userID + ", userName=" + userName + ", boardID=" + boardID
				+ ", boardTitle=" + boardTitle + ", boardContent=" + boardContent + ", boardDate=" + boardDate
				+ ", boardHit=" + boardHit + ", boardGroup=" + boardGroup + ", boardName=" + boardName + ", likeCount="
				+ likeCount + ", likeState=" + likeState + ", boardSequence=" + boardSequence + ", boardLevel="
				+ boardLevel + ", boardAvailable=" + boardAvailable + ", commentCount=" + commentCount
				+ ", attachCount=" + attachCount + ", updateDate=" + updateDate + ", boardDateStr=" + boardDateStr
				+ "]";
	}
	

	

	
}
