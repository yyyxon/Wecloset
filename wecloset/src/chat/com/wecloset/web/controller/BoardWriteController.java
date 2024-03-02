package com.wecloset.web.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jdt.internal.compiler.ast.ThrowStatement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oreilly.servlet.MultipartFilter;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.wecloset.web.Action;
import com.wecloset.web.BoardConstants;
import com.wecloset.web.CommonResource;
import com.wecloset.web.dao.BoardDAO;
import com.wecloset.web.dto.AttachmentsDTO;
import com.wecloset.web.dto.BoardDTO;

import config.RequestWrapper;

public class BoardWriteController implements Action {

	private final Logger logger = LogManager.getLogger(BoardWriteController.class);
	
	private static BoardWriteController instance;
	
	private BoardWriteController() {
	}
	
	public static BoardWriteController getInstance() {				
		if (instance == null)
			instance = new BoardWriteController();
		return instance;
	}

	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		/** 로그인 체크 */
		if(CommonResource.getInstance().loginCheck(request, response) ) return;
		
		if (request.getMethod().equals("POST")) {
			doPost(request, response);					
		}else{

			/** 게시판 유형 체크 */
			String boardName=request.getParameter("boardName");		
			if(!BoardConstants.getInstance().boardNameCheck(boardName, request)) {
				response.sendRedirect(request.getContextPath()+"/index.jsp");
				return;
			}
						
			String pageUrl ="/WEB-INF/jsp/board_write.jsp";	
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
        File fileDir = new File(savePath);
        if(!fileDir.exists()){
        	fileDir.mkdirs();
        }
		
		String fileDirectory=BoardConstants.calcPath(savePath);
		savePath =savePath+fileDirectory;
				
		try {
			
			multi = new MultipartRequest(request, savePath, fileMaxSize, "UTF-8", new DefaultFileRenamePolicy());
			String boardName = multi.getParameter("boardName");
			int boardGroup =BoardConstants.getBoardGroup(boardName);
			String boardTitle=multi.getParameter("boardTitle");
			String boardContent = multi.getParameter("boardContent");			
			
			if(boardTitle==null || boardTitle.equals("") || boardContent==null|| boardContent.equals("")){
				resultMap.put("msg", "error_param_null");
				return;
			}
			
			
			File file = multi.getFile("boardFile");
			if(file != null) {
				/**
				 * 파일 확장자 체크 				 
				  if(!fileExtensionCheck(FilenameUtils.getExtension(multi.getOriginalFileName("boardFile")))){
					  throw new Exception("not valid");
				  }
				 */	
				  attachmentsDTO =new AttachmentsDTO();
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
						
			boardDTO.setBoardName(boardName);
			boardDTO.setBoardTitle(RequestWrapper.staticCleanXSS(boardTitle));
			boardDTO.setBoardContent(RequestWrapper.staticCleanXSS(boardContent));
			boardDTO.setBoardGroup(boardGroup);
			boardDTO.setUserID((String) request.getSession().getAttribute("userID"));  
			  
			BoardDAO boardDAO =BoardDAO.getInstance();
			/** DB   등록  */
			Integer result=boardDAO.insertBoard(boardDTO, attachmentsDTO);
			  		  
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
