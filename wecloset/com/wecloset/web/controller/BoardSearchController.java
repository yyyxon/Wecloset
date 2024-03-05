package com.wecloset.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wecloset.web.Action;
import com.wecloset.web.BoardConstants;
import com.wecloset.web.CommonResource;
import com.wecloset.web.dao.BoardDAO;

import com.wecloset.web.dto.BoardDTO;
import config.PageMakerAndSearch;


public class BoardSearchController implements Action {


	private final Logger logger = LogManager.getLogger(BoardSearchController.class);
	
	private static BoardSearchController instance;
	
	private BoardSearchController() {
	}
	
	public static BoardSearchController getInstance() {				
		if (instance == null)
			instance = new BoardSearchController();
		return instance;
	}

	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {			
		String pageUrl ="/WEB-INF/jsp/board_search.jsp";	

		PageMakerAndSearch pageMaker=new PageMakerAndSearch();			
		String page=request.getParameter("page");
		String perPageNum=request.getParameter("perPageNum");
		String searchType=request.getParameter("searchType");
		String keyword=request.getParameter("keyword");
		String boardName=request.getParameter("boardName");	
		
		/** 검색  체크 */		
		if(searchType==null || searchType.equals(""))pageMaker.setSearchType("all");
		else pageMaker.setSearchType(searchType);
		
		if(keyword==null || keyword.equals(""))pageMaker.setKeyword("all");
		else pageMaker.setKeyword(keyword);
		
		if(page!=null && !page.equals("")) pageMaker.setPage(Integer.parseInt(page));
		else pageMaker.setPage(1);
		
		if(perPageNum!=null && !perPageNum.equals("")) pageMaker.setPerPageNum(Integer.parseInt(perPageNum));		
		else pageMaker.setPerPageNum(10);
		

		if(boardName!=null && !boardName.equals("") && !boardName.equals("all")) {		
			pageMaker.setBoardGroup(BoardConstants.getBoardGroup(boardName));
		}else{
			pageMaker.setBoardGroup(null);	
		}
			
		
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
