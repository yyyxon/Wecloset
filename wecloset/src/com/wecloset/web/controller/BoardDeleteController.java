package com.wecloset.web.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wecloset.web.Action;
import com.wecloset.web.BoardConstants;
import com.wecloset.web.CommonResource;
import com.wecloset.web.dao.BoardDAO;
import com.wecloset.web.dto.BoardDTO;

public class BoardDeleteController implements Action {

	private final Logger logger = LogManager.getLogger(BoardDeleteController.class);
	
	private static BoardDeleteController instance;
	
	private BoardDeleteController() {
	}
	
	public static BoardDeleteController getInstance() {				
		if (instance == null)
			instance = new BoardDeleteController();
		return instance;
	}

	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		if (request.getMethod().equals("POST")){
			doPost(request, response);					
		}
	}

	
	
	private void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {					
		/** 로그인 체크 */
		if(CommonResource.getInstance().loginCheck(request, response) ) return;
		
		
		/** 게시판 유형 체크  , 게시번호 널 체크 */
		String boardName=request.getParameter("boardName");		
		String boardID=request.getParameter("boardID");	
		if(boardName==null|| boardName.equals("")|| boardID==null || boardID.equals("")){
			response.sendRedirect(request.getContextPath()+"/index.jsp");
			return;
		}		
		
		BoardDAO boardDAO=BoardDAO.getInstance();		
		BoardDTO getBoardInfo=boardDAO.getBoardBno(Integer.parseInt(boardID));
		
		
		if(getBoardInfo.getFilesystemName()!=null && !getBoardInfo.getFilesystemName().equals("")) {
			String savePath =request.getServletContext().getRealPath("/upload/").replaceAll("\\\\", "/");
			savePath=savePath+getBoardInfo.getFileDirectory()+getBoardInfo.getFilesystemName();
			try{
				File file=new File(savePath);
				if(file.exists()) {
					file.delete();
				}
				
				/** 데이터 베이스에서 삭제 처리  */
				boardDAO.boardDelete(boardID ,  getBoardInfo.getFileID());						
			}catch (Exception e) {
				logger.catching(e);				
			}
						
		}else{
			boardDAO.boardDelete(boardID ,  null);
		}
	
		response.sendRedirect(request.getContextPath()+"/WeclosetServlet?command=boardList&boardName="+boardName); 
	}
	

	
}
