package com.wecloset.web;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/** 게시판 메뉴  */
public class BoardConstants {
	
	final static List<String> boardNameArr=java.util.Collections.unmodifiableList(Arrays.asList("ppippi", "free", "question", "review", "daily", "fleaMarket", "stationery"));
	final static List<Integer>boardGroup=java.util.Collections.unmodifiableList(Arrays.asList(0,1,2,3,4,5,6));
	final static List<String>boardNameArrKor=java.util.Collections.unmodifiableList(Arrays.asList("삐삐", "자유", "질문", "후기", "데일리룩", "플리마켓", "문방구"));
	

	private static BoardConstants instance;
	
	private BoardConstants() {
	}
	
	public static BoardConstants getInstance() {				
		if (instance == null)
			instance = new BoardConstants();
		return instance;
	}
	
	
	public boolean boardNameCheck(String boardName, HttpServletRequest request) {
		if(boardName==null || boardName.equals("")) {
			return false;			
		}
		
		if(boardNameArr.contains(boardName)){			
			request.setAttribute("boardName", boardName);
			request.setAttribute("boardNameKor", getBoardNameKor(boardName));
			request.setAttribute("boardGroup", BoardConstants.getBoardGroup(boardName));
			return true;
		}		
		return false;	
	}	
	
	
	public static String getBoardNameKor(String boardName) {					
		for(int i=0; i<boardNameArr.size(); i++){
			if(boardNameArr.get(i).equals(boardName)) {
				return boardNameArrKor.get(i);
			}
		}		
		return null;		
	}
	
	
	public static Integer getBoardGroup(String boardName) {					
		for(int i=0; i<boardNameArr.size(); i++){
			if(boardNameArr.get(i).equals(boardName)) {
				return boardGroup.get(i);
			}
		}		
		return null;		
	}
	
	
	public static String calendarMkdirs(String realPath) {
		 Calendar cal = Calendar.getInstance();		 
		 String calendarDir=String.format("%04d-%02d-%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
		 String dateString=realPath+calendarDir;		 
		 		 
		 File f = new File(dateString);				
		if(!f.exists()) {
			try {
				f.mkdir();
			} catch (Exception e) {				
				e.printStackTrace();
				return null;
			}
		}
		return calendarDir;
	}
	
		
	
	/** 파일이 저장될 '년/월/일' 정보 생성 */
	public static  String calcPath(String uploadPath) {
       Calendar cal = Calendar.getInstance();
       // 역슬래시 + 2017
       String yearPath = File.separator + cal.get(Calendar.YEAR);

       // /2017 +/+ 10 한자리 월 일경우 01, 02 형식으로 포멧
       String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);

       // /2017/10 +/ + 22
       String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));

       // 년월일 폴더 생성하기
       makeDir(uploadPath, yearPath, monthPath, datePath);
     
       return datePath;
   }

     /** 실질 적인 날짜 폴더 생성 */
	public static  void makeDir(String uploadPath, String... paths) {
       if (new File(paths[paths.length - 1]).exists()) {
           // 년 월 일 에서 일 배열 paths 에서 paths -1 은 일 즉 해당일의 폴더가 존재하면 return
           return;
       }

       for (String path : paths) {
           File dirPath = new File(uploadPath + path);
           if (!dirPath.exists()) {
               // 년 월일에 대한 해당 폴더가 존재하지 않으면 폴더 생성
               dirPath.mkdir();
           }
       }

   }

   
	
	public static boolean fileExtensionCheck(String fileExt) {
   	 if(fileExt.equalsIgnoreCase("jpg") ||
	        		fileExt.equalsIgnoreCase("jpeg") ||
	        		fileExt.equalsIgnoreCase("gif") ||
	        		fileExt.equalsIgnoreCase("png") ||
	        		fileExt.equalsIgnoreCase("bmp") ||
	        		fileExt.equalsIgnoreCase("mp3") ||
	        		fileExt.equalsIgnoreCase("mp4") ||
	        		fileExt.equalsIgnoreCase("xml") ||
	        		fileExt.equalsIgnoreCase("xls") ||
	        		fileExt.equalsIgnoreCase("xlsx") ||
	        		fileExt.equalsIgnoreCase("txt") ||
	        		fileExt.equalsIgnoreCase("hwp") ||
	        		fileExt.equalsIgnoreCase("pdf") ||
	        		fileExt.equalsIgnoreCase("zip") ||
	        		fileExt.equalsIgnoreCase("ppt") ||
	        		fileExt.equalsIgnoreCase("pptx") ||
	        		fileExt.equalsIgnoreCase("doc") ||
	        		fileExt.equalsIgnoreCase("avi") || 
	        		fileExt.equalsIgnoreCase("mkv") ||
	        		fileExt.equalsIgnoreCase("webm") || 
	        		fileExt.equalsIgnoreCase("ogg") || 
	        		fileExt.equalsIgnoreCase("docx") ) {
	            
   		 return true;
	        } else {
	        	return false;
	        }

   }
	

	
	
}
