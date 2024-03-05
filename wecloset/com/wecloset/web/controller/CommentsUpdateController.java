package com.wecloset.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.bcel.internal.generic.INEG;
import com.wecloset.web.Action;
import com.wecloset.web.BoardConstants;
import com.wecloset.web.CommonResource;
import com.wecloset.web.dao.BoardDAO;
import com.wecloset.web.dto.CommentDTO;

public class CommentsUpdateController implements Action  {
	
	
	private final Logger logger = LogManager.getLogger(CommentsUpdateController.class);
	
	private static CommentsUpdateController instance;
	
	private CommentsUpdateController() {
	}
	
	public static CommentsUpdateController getInstance() {				
		if (instance == null)
			instance = new CommentsUpdateController();
		return instance;
	}

	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		/** 로그인 체크 */
		if(CommonResource.getInstance().loginCheck(request, response) ) return;
		
		if (request.getMethod().equals("POST")) {
			doPost(request, response);					
		}
	}

	
	
	private void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
			
		String commentID=request.getParameter("commentID");
		String commentContent=request.getParameter("commentContent");
	
	
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try {
			if(commentID==null || commentID.equals("")||commentContent==null || commentContent.equals("")){
				throw new NullPointerException();	
			}
			
			BoardDAO boardDAO=BoardDAO.getInstance();
			CommentDTO commentDTO=boardDAO.getComment(Integer.parseInt(commentID));
			
			if(!commentDTO.getUserID().equals((String)request.getSession().getAttribute("userID"))){
				throw new Exception();
			}
			
			commentDTO.setCommentContent(commentContent);
			
			/**댓글 수정 */
			boardDAO.updateComment(commentDTO);
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
