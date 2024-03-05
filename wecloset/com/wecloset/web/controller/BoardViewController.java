package com.wecloset.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
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

public class BoardViewController implements Action {

	private final Logger logger = LogManager.getLogger(BoardViewController.class);
	
	private static BoardViewController instance;
	
	private BoardViewController() {
	}
	
	public static BoardViewController getInstance() {				
		if (instance == null)
			instance = new BoardViewController();
		return instance;
	}

	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/** 로그인 체크 */
		if(CommonResource.getInstance().loginCheck(request, response) ) return;
		
		/** 게시판 유형 체크  , 게시번호 널 체크 */
		String boardName=request.getParameter("boardName");		
		String boardID=request.getParameter("boardID");	
		if(!BoardConstants.getInstance().boardNameCheck(boardName, request)
			|| boardID==null || boardID.equals("")	
		 ){
			response.sendRedirect(request.getContextPath()+"/index.jsp");
			return;
		}
	
		String pageUrl ="/WEB-INF/jsp/board_view.jsp";				
		if(boardName.equals("daily")) {
			pageUrl ="/WEB-INF/jsp/daily_view.jsp";	
		}
		
		
		
		BoardDAO boardDAO=BoardDAO.getInstance();			
				
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("boardID", Integer.parseInt(boardID));
		map.put("userID", (String)request.getSession().getAttribute("userID"));
		map.put("boardGroup", BoardConstants.getBoardGroup(boardName));		
		BoardDTO getBoardInfo=boardDAO.getBoard(map);
				
		
		/** 널 및 숨김 처리 체크 */
		if(getBoardInfo==null || getBoardInfo.getBoardAvailable()!=1){		
			response.sendRedirect(request.getContextPath()+"/index.jsp"); 
			return;
		}
		
		/** 조회수 증가 **/
		boardDAO.updateBoardHit(Integer.parseInt(boardID));
		
	
		
		if(getBoardInfo.getBoardContent()!=null && !getBoardInfo.getBoardContent().equals("")) {
			String boardConnt=getBoardInfo.getBoardContent().replaceAll("\n", "<br>");
			getBoardInfo.setBoardContent(boardConnt);
		}
				
		request.setAttribute("board", getBoardInfo);				
		RequestDispatcher dispatcher = request.getRequestDispatcher(pageUrl);
		dispatcher.forward(request, response);	
	}

	

		
	
	
}
