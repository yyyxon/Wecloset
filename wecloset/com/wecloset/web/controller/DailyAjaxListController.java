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


public class DailyAjaxListController implements Action {


	private final Logger logger = LogManager.getLogger(DailyAjaxListController.class);
	
	private static DailyAjaxListController instance;
	
	private DailyAjaxListController() {
	}
	
	public static DailyAjaxListController getInstance() {				
		if (instance == null)
			instance = new DailyAjaxListController();
		return instance;
	}

	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		/** 로그인 체크 */
		//if(CommonResource.getInstance().loginCheck(request, response) ) return;
		
		/** 게시판 유형 체크 */
		String boardName=request.getParameter("boardName");		
		if(!BoardConstants.getInstance().boardNameCheck(boardName, request)) {
			response.sendRedirect(request.getContextPath()+"/index.jsp");
			return;
		}
		
		
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> resultMap = new HashMap<String, Object>();
				
		
		try {
			
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
			
			String endingPage="none";	
			if(boardList==null||boardList.size()==0) endingPage="yes";
			
			resultMap.put("totalCount", totalCount);
			resultMap.put("pagination", boardPagination);
			resultMap.put("pageMaker", pageMaker);		
			resultMap.put("boardList", boardList);
			resultMap.put("endingPage", endingPage);
		
	    }catch(Exception e) {			
				resultMap.put("msg", "error");					
				logger.catching(e);
		}finally{
				out.print(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultMap));	
			    out.flush();
			    out.close();	
		}
		
		
	}

	

	
	
	
}
