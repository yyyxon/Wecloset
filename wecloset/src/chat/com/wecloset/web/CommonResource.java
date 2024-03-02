package com.wecloset.web;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wecloset.web.controller.BoardListController;
import com.wecloset.web.controller.BoardWriteController;
 


public class CommonResource {
	
	private final Logger logger = LogManager.getLogger(CommonResource.class);
	
	private static CommonResource instance;
	
	private CommonResource() {
	}
	
	public static CommonResource getInstance() {				
		if (instance == null)
			instance = new CommonResource();
		return instance;
	}
	

	
	/** 
	 * 공통 변수 설정
	 * @param request
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void getCommonVariable(HttpServletRequest request) throws FileNotFoundException, IOException {	
		/**
		 * 	application-context.properties 값 가져오기	
		 */
		ServletContext sc = request.getServletContext();
	    Properties properties = new Properties();
        properties.load(new FileReader(sc.getRealPath(sc.getInitParameter("contextConfigLocation"))));
                      
		request.setAttribute("ConPath", request.getContextPath());		
		for (Object object: properties.keySet()) {          
            request.setAttribute((String)object, (String)properties.get(object));
        }	
				
	}

	
	/**
	 * 로그인  체크
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public boolean loginCheck(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException{
		HttpSession session=request.getSession();
		String userID=(String)session.getAttribute("userID");
		logger.debug("로그인 체크 : {}", request.getMethod());
		if(userID == null) { 
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "현재 로그인이 되어 있지 않습니다.");
			response.sendRedirect(request.getContextPath()+"/index.jsp");
			return true;
		}		
		return false;
	}

	
}
