package com.wecloset.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.wecloset.web.dto.CommentDTO;

import config.PageMakerAndSearch;
import config.RequestWrapper;


public class CommentsListController implements Action  {
	
	
	private final Logger logger = LogManager.getLogger(CommentsListController.class);
	
	private static CommentsListController instance;
	
	private CommentsListController() {
	}
	
	public static CommentsListController getInstance() {				
		if (instance == null)
			instance = new CommentsListController();
		return instance;
	}
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		/** 로그인 체크 */
		//if(CommonResource.getInstance().loginCheck(request, response) ) return;
		
		String boardID=request.getParameter("boardID");
		String boardName=request.getParameter("boardName");
		Integer boardGroup=BoardConstants.getBoardGroup(boardName);
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> resultMap = new HashMap<String, Object>();
				
		try {
					
			PageMakerAndSearch pageMaker=new PageMakerAndSearch();	
			String page=request.getParameter("page");
			String perPageNum=request.getParameter("perPageNum");
			
			if(page!=null && !page.equals("")) pageMaker.setPage(Integer.parseInt(page));
			if(page==null|| page.equals(""))pageMaker.setPage(1);
			if(perPageNum!=null && !perPageNum.equals(""))pageMaker.setPerPageNum(Integer.parseInt(perPageNum));
			
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("boardID", boardID);
			map.put("boardGroup", boardGroup);			
			
			BoardDAO boardDAO=BoardDAO.getInstance();
			Integer commentTotalCount = boardDAO.commentTotalCount(map);					
			pageMaker.setTotalCount(commentTotalCount);
						
			map.put("pageStart", pageMaker.getPageStart());
			map.put("perPageNum", pageMaker.getPerPageNum());
			//logger.info("\n\n   Map : {} "  , map.toString());
			List<CommentDTO> commentList=boardDAO.getListComment(map);
			
			String commentPagination=pageMaker.commentPagination();
			
			resultMap.put("commentPagination", commentPagination);
			resultMap.put("commentTotalCount", commentTotalCount);			
			resultMap.put("commentList",commentList);
			resultMap.put("commentPerPageNum",pageMaker.getPerPageNum());
			resultMap.put("msg", "success");					
		}catch(Exception e) {
			logger.catching(e);
			resultMap.put("msg", "error");			
		}finally{
			out.print(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultMap));
			out.flush();
			out.close();
		}
				
	}



	

}
