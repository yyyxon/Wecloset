package com.wecloset.web.controller;

import java.io.IOException;
import java.util.List;

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

import config.PageMakerAndSearch;


public class BoardListController implements Action {


	private final Logger logger = LogManager.getLogger(BoardListController.class);
	
	private static BoardListController instance;
	
	private BoardListController() {
	}
	
	public static BoardListController getInstance() {				
		if (instance == null)
			instance = new BoardListController();
		return instance;
	}

	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/** 로그인 체크 */
		if(CommonResource.getInstance().loginCheck(request, response) ) return;	
		
		
		/** 게시판 유형 체크 */
		String boardName=request.getParameter("boardName");		
		if(!BoardConstants.getInstance().boardNameCheck(boardName, request)) {
			response.sendRedirect(request.getContextPath()+"/index.jsp");
			return;
		}
		
		String pageUrl ="/WEB-INF/jsp/board_list.jsp";	
		if(boardName.equals("daily")) {
			pageUrl ="/WEB-INF/jsp/daily_list.jsp";			
		}
		
		
		
		PageMakerAndSearch pageMaker=new PageMakerAndSearch();		
		String page=request.getParameter("page");
		String perPageNum=request.getParameter("perPageNum");
		pageMaker.setUserID((String)request.getSession().getAttribute("userID"));
		pageMaker.settingPage(page, perPageNum, boardName);		
	
		BoardDAO boardDAO=BoardDAO.getInstance();		
		Integer totalCount = boardDAO.boardTotalCount(pageMaker);
		pageMaker.setTotalCount(totalCount);
		
		
		List<BoardDTO> boardList=boardDAO.getListBoard(pageMaker);
		String boardPagination=pageMaker.boardPagination();
			
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("pagination", boardPagination);
		request.setAttribute("pageMaker", pageMaker);		
		request.setAttribute("boardList", boardList);
	
		RequestDispatcher dispatcher = request.getRequestDispatcher(pageUrl);
		dispatcher.forward(request, response);
	}

	

	
	
	
}
