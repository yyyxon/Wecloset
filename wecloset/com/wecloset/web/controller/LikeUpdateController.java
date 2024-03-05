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
import com.wecloset.web.dto.BoardLikeDTO;

public class LikeUpdateController implements Action {

	private final Logger logger = LogManager.getLogger(LikeUpdateController.class);
	
	private static LikeUpdateController instance;
	
	private LikeUpdateController() {
	}
	
	public static LikeUpdateController getInstance() {				
		if (instance == null)
			instance = new LikeUpdateController();
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
		response.setContentType("text/html; charset=UTF-8");	
		String jsonData=request.getParameter("jsonData");
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> resultMap = new HashMap<String, Object>();
						
		try {
		
			/** json  파싱 */		
			BoardLikeDTO boardLikeDTO = mapper.readValue(jsonData, BoardLikeDTO.class); 
			boardLikeDTO.setBoardGroup(BoardConstants.getBoardGroup(boardLikeDTO.getBoardName()));
			boardLikeDTO.setUserID((String) request.getSession().getAttribute("userID"));
			
			BoardDAO boardDAO=BoardDAO.getInstance();
			
			/** 좋아요 삭제. */
			boardDAO.deleteLike(boardLikeDTO);
			if(boardLikeDTO.getState()==1){
				/**  좋아요.추가 */
				boardDAO.insertLike(boardLikeDTO);				
			}					      
			resultMap.put("msg", "success");	
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
