package com.wecloset.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.wecloset.web.Action;
import com.wecloset.web.BoardConstants;
import com.wecloset.web.CommonResource;
import com.wecloset.web.dao.BoardDAO;
import com.wecloset.web.dto.AttachmentsDTO;
import com.wecloset.web.dto.BoardDTO;

import config.RequestWrapper;

public class DailyUpdateController implements Action {

	private final Logger logger = LogManager.getLogger(DailyUpdateController.class);
	
	private static DailyUpdateController instance;
	
	private DailyUpdateController() {
	}
	
	public static DailyUpdateController getInstance() {				
		if (instance == null)
			instance = new DailyUpdateController();
		return instance;
	}

	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		/** 로그인 체크 */
		if(CommonResource.getInstance().loginCheck(request, response) ) return;
		
		if (request.getMethod().equals("POST")) {
			doPost(request, response);					
		}else{

			/** 게시판 유형 체크  , 게시번호 널 체크 */
			String boardName=request.getParameter("boardName");		
			String boardID=request.getParameter("boardID");	
			if(!BoardConstants.getInstance().boardNameCheck(boardName, request)
				|| boardID==null || boardID.equals("")	
			 ){
				response.sendRedirect(request.getContextPath()+"/index.jsp");
				return;
			}
		
			BoardDAO boardDAO=BoardDAO.getInstance();	
			BoardDTO getBoardInfo=boardDAO.getBoardBno(Integer.parseInt(boardID));
			
			String pageUrl ="/WEB-INF/jsp/daily_update.jsp";					
			
			request.setAttribute("board", getBoardInfo);
			RequestDispatcher dispatcher = request.getRequestDispatcher(pageUrl);
			dispatcher.forward(request, response);			
		}
	}

	
	
	private void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		//response.setContentType("text/html; charset=UTF-8");
		
		BoardDTO boardDTO = new BoardDTO();
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		MultipartRequest multi = null;
		AttachmentsDTO attachmentsDTO=null;
		int fileMaxSize = 10 * 1024 * 1024;
		//String savePath=request.getAttribute("SystemFileDirectory");
		String savePath =request.getServletContext().getRealPath("/upload/").replaceAll("\\\\", "/");
		String fileDirectory=BoardConstants.calcPath(savePath);
		savePath =savePath+fileDirectory;

				
		try {
			
			multi = new MultipartRequest(request, savePath, fileMaxSize, "UTF-8", new DefaultFileRenamePolicy());
			int boardID=Integer.parseInt(multi.getParameter("boardID"));
			
			/** 작성 자 체크 */
			BoardDAO boardDAO=BoardDAO.getInstance();				
			BoardDTO getBoardInfo=boardDAO.getBoardBno(boardID);
			
			
			System.out.println("\n\n\n : boardID  {} " +boardID);
			
			
			/** getBoardInfo null 값 체크 및 작성자와 로그인한 유저가 같은지 체크 */
			if(getBoardInfo==null 
					|| !getBoardInfo.getUserID().equals((String)request.getSession().getAttribute("userID"))
					|| getBoardInfo.getBoardAvailable()!=1		
			){		
				response.sendRedirect(request.getContextPath()+"/index.jsp"); 
				throw new NullPointerException();
			}
			
			
			String boardName = multi.getParameter("boardName");
			int boardGroup =BoardConstants.getBoardGroup(boardName);
						
			File file = multi.getFile("boardFile");
			if(file != null) {
				/**
				 * 파일 확장자 체크 				 
				  if(!fileExtensionCheck(FilenameUtils.getExtension(multi.getOriginalFileName("boardFile")))){
					  throw new Exception("not valid");
				  }
				 */	
				  attachmentsDTO =new AttachmentsDTO();
				  attachmentsDTO.setBoardID(boardID);
				  attachmentsDTO.setUserID((String) request.getSession().getAttribute("userID"));				  				  
				  attachmentsDTO.setBoardGroup(boardGroup);
				  attachmentsDTO.setFilesystemName(multi.getFilesystemName("boardFile"));
				  attachmentsDTO.setOriginalFileName(multi.getOriginalFileName("boardFile"));
				  attachmentsDTO.setFileDirectory(fileDirectory.replace(File.separatorChar, '/')+"/");
				  attachmentsDTO.setExtension(FilenameUtils.getExtension(attachmentsDTO.getOriginalFileName()).toLowerCase());				  
				  attachmentsDTO.setFileSize(String.valueOf(request.getContentLength()));				  
				  resultMap.put("filesystemName", attachmentsDTO.getFilesystemName());	
				  resultMap.put("originalFileName", attachmentsDTO.getOriginalFileName());	
			}	
			boardDTO.setBoardID(boardID);		
			boardDTO.setBoardName(boardName);
			boardDTO.setBoardGroup(boardGroup);
			boardDTO.setUserID((String) request.getSession().getAttribute("userID"));  
			boardDTO.setUserID("anonymous");   			  
			
			/** DB   수정  */
			Integer result=boardDAO.updateBoard(boardDTO, attachmentsDTO);
			  		  
		    if(result==0)resultMap.put("msg", "error");
		    else resultMap.put("msg", "success");			  
		    
		    
		}catch(Exception e) {			
			resultMap.put("msg", "error_file_upload");					
			logger.catching(e);
		}finally{
			out.print(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultMap));	
		    out.flush();
		    out.close();	
		}

	}
	

	
}
