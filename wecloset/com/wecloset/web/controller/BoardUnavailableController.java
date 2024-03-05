package com.wecloset.web.controller;

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

public class BoardUnavailableController implements Action {

	private final Logger logger = LogManager.getLogger(BoardUnavailableController.class);
	
	private static BoardUnavailableController instance;
	
	private BoardUnavailableController() {
	}
	
	public static BoardUnavailableController getInstance() {				
		if (instance == null)
			instance = new BoardUnavailableController();
		return instance;
	}

	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		if (request.getMethod().equals("POST")){
			doPost(request, response);					
		}
	}

	
	
	private void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {					
	
		
		/** 게시판 유형 체크  , 게시번호 널 체크 */
		logger.info("\n\n *** 게시판 유형 체크  ");
		String boardName=request.getParameter("boardName");		
		String boardID=request.getParameter("boardID");	
		if(boardName==null|| boardName.equals("")|| boardID==null || boardID.equals("")){
			response.sendRedirect(request.getContextPath()+"/index.jsp");
			return;
		}		
		
		BoardDAO boardDAO=BoardDAO.getInstance();		
		BoardDTO getBoardInfo=boardDAO.getBoardBno(Integer.parseInt(boardID));
		
		if(!boardName.equals("daily")) {

			/** 로그인 체크 */
			if(CommonResource.getInstance().loginCheck(request, response) ) return;
			
			/** getBoardInfo null 값 체크 및 작성자와 로그인한 유저가 같은지 체크 */
			if(getBoardInfo==null || !getBoardInfo.getUserID().equals((String)request.getSession().getAttribute("userID"))){		
				response.sendRedirect(request.getContextPath()+"/index.jsp"); 
				return;
			}
		}

		
		/** 데이터 베이스에서 숨김 처리  */
		boardDAO.boardUnavailable(boardID);
		response.sendRedirect(request.getContextPath()+"/WeclosetServlet?command=boardList&boardName="+boardName); 
	}
	

	
}
