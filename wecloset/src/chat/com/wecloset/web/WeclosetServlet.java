package com.wecloset.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wecloset.web.controller.CommentsWriteController;


@WebServlet("/WeclosetServlet")
public class WeclosetServlet extends HttpServlet {

	private final Logger logger = LogManager.getLogger(WeclosetServlet.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		
		String command=request.getParameter("command");				
		if(command==null || command.equals("")) {
			response.sendRedirect(request.getContextPath() +"/WeclosetServlet?command=main");
			return;
		}	

		logger.debug("\n\n\n\n\n boardServlet 에서 요청을 받음을 확인  - command 값 : {} "  , command );	
		
		ActionControlCenter af =ActionControlCenter.getInstance();
		Action action =af.getAction(command);
		
		/** 공통  */
		CommonResource.getInstance().getCommonVariable(request);
		response.setContentType("text/html; charset=UTF-8");		
		
		if(action!=null){
			action.execute(request, response);
		}			
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	
}
