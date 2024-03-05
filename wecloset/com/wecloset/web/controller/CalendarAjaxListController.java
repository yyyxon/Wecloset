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
import com.wecloset.web.CommonResource;
import com.wecloset.web.dao.CalendarDAO;
import com.wecloset.web.dto.CalendarDTO;

import user.UserDTO;


public class CalendarAjaxListController implements Action {


	private final Logger logger = LogManager.getLogger(CalendarAjaxListController.class);
	
	private static CalendarAjaxListController instance;
	
	private CalendarAjaxListController() {
	}
	
	public static CalendarAjaxListController getInstance() {				
		if (instance == null)
			instance = new CalendarAjaxListController();
		return instance;
	}

	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		/** 로그인 체크 */
		if(CommonResource.getInstance().loginCheck(request, response) ) return;
		
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> resultMap = new HashMap<String, Object>();
				
		
		try {
	
			CalendarDAO calendarDAO=CalendarDAO.getInstance();
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("start", startDate);
			map.put("end", endDate);
			map.put("userID", (String)request.getSession().getAttribute("userID"));
			List<CalendarDTO>  calendarList=calendarDAO.calendarList(map);			
			List<UserDTO> userList=calendarDAO.calendarMonthUser(map);			
			resultMap.put("msg", "success");
			resultMap.put("calendarList", calendarList);
			resultMap.put("userList", userList);		
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
