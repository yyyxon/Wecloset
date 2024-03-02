package com.wecloset.web.dto;

import java.sql.Date;

import lombok.Data;

/**
 * 첨부파일
 *
 */
@Data
public class AttachmentsDTO {
	
	private int num;
	private int fileID;
	private int boardID;
	private String userID;
	private int boardGroup;
	private String filesystemName;
	private String originalFileName;
	private String fileDirectory;	
	private String extension;
	private String fileSize;
	private Date regDate;
	
	
	@Override
	public String toString() {
		return "AttachmentsDTO [num=" + num + ", fileID=" + fileID + ", boardID=" + boardID + ", userID=" + userID
				+ ", boardGroup=" + boardGroup + ", filesystemName=" + filesystemName + ", originalFileName="
				+ originalFileName + ", fileDirectory=" + fileDirectory + ", extension=" + extension + ", fileSize="
				+ fileSize + ", regDate=" + regDate + "]";
	}
	
	
	
		
}

