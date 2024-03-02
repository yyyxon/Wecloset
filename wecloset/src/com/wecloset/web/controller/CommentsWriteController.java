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
import com.wecloset.web.Action;
import com.wecloset.web.BoardConstants;
import com.wecloset.web.CommonResource;
import com.wecloset.web.dao.BoardDAO;
import com.wecloset.web.dto.CommentDTO;

public class CommentsWriteController implements Action  {
	
	
	private final Logger logger = LogManager.getLogger(CommentsWriteController.class);
	
	private static CommentsWriteController instance;
	
	private CommentsWriteController() {
	}
	
	public static CommentsWriteController getInstance() {				
		if (instance == null)
			instance = new CommentsWriteController();
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
		
		String jsonData=request.getParameter("jsonData");
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> resultMap = new HashMap<String, Object>();
	
		try {
			/** json  파싱 */		
			CommentDTO commentParam = mapper.readValue(jsonData, CommentDTO.class); 
			commentParam.setBoardGroup(BoardConstants.getBoardGroup(commentParam.getBoardName()));
			commentParam.setUserID((String) request.getSession().getAttribute("userID"));
			//logger.info("\n\n\n commentParam - toString : " + commentParam.toString());
			
			BoardDAO boardDAO=BoardDAO.getInstance();
			boardDAO.insertComment(commentParam);						
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
