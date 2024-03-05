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
import com.wecloset.web.CommonResource;
import com.wecloset.web.dao.CalendarDAO;
import com.wecloset.web.dto.CalendarDTO;

import user.UserDAO;
import user.UserDTO;

public class CalendarDeleteController implements Action  {
	
	
	private final Logger logger = LogManager.getLogger(CalendarDeleteController.class);
	
	private static CalendarDeleteController instance;
	
	private CalendarDeleteController() {
	}
	
	public static CalendarDeleteController getInstance() {				
		if (instance == null)
			instance = new CalendarDeleteController();
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
		String jsonData=request.getParameter("eventData");		
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> resultMap = new HashMap<String, Object>();
	
		try {			
			CalendarDTO calendarDTO = mapper.readValue(jsonData, CalendarDTO.class); 			
			calendarDTO.setUserID((String) request.getSession().getAttribute("userID"));					
			CalendarDAO calendarDAO=CalendarDAO.getInstance();
			//calendarDTO.setCalendarId(calendarDTO.get_id());
			
			logger.info(" 파라미터  {}", calendarDTO.toString());
			
			/** 작성자 확인 */
			/*
			CalendarDTO  getSystemCalendarInfo=calendarDAO.getInfoCalendar(calendarDTO);
			if(getSystemCalendarInfo.getUserID().equals(calendarDTO.getUserID())){
				calendarDAO.deleteCalendar(calendarDTO);									
				resultMap.put("msg", "success");				
			}else {
				resultMap.put("msg", "erro_user");
			}
			*/
			calendarDAO.deleteCalendar(calendarDTO);									
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
