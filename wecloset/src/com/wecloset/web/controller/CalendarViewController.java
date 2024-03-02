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
import com.wecloset.web.CommonResource;
import com.wecloset.web.dao.CalendarDAO;

import user.UserDAO;
import user.UserDTO;

public class CalendarViewController implements Action {

	private final Logger logger = LogManager.getLogger(CalendarViewController.class);
	
	private static CalendarViewController instance;
	
	private CalendarViewController() {
	}
	
	public static CalendarViewController getInstance() {				
		if (instance == null)
			instance = new CalendarViewController();
		return instance;
	}

	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/** 로그인 체크 */
		if(CommonResource.getInstance().loginCheck(request, response) ) return;		
		String pageUrl ="/WEB-INF/jsp/calendar.jsp";		
		UserDTO user = new UserDAO().getUser((String) request.getSession().getAttribute("userID"));
		
		CalendarDAO calendarDAO=CalendarDAO.getInstance();
		List<UserDTO> userList=calendarDAO.calendarWriteUser(null);	
		
		request.setAttribute("USER", user);
		request.setAttribute("userList", userList);
		RequestDispatcher dispatcher = request.getRequestDispatcher(pageUrl);
		dispatcher.forward(request, response);	
	}

	

		
	
	
}
