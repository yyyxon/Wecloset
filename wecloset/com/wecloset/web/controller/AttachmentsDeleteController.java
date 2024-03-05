package com.wecloset.web.controller;

import java.io.File;
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
import com.wecloset.web.dao.BoardDAO;
import com.wecloset.web.dto.AttachmentsDTO;

public class AttachmentsDeleteController implements Action {

	private final Logger logger = LogManager.getLogger(AttachmentsDeleteController.class);

	private static AttachmentsDeleteController instance;

	private AttachmentsDeleteController() {
	}

	public static AttachmentsDeleteController getInstance() {
		if (instance == null)
			instance = new AttachmentsDeleteController();
		return instance;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/** 로그인 체크 */
		//if (CommonResource.getInstance().loginCheck(request, response))
		//	return;
		
		String fileID=request.getParameter("fileID");
		BoardDAO boardDAO = BoardDAO.getInstance();
		AttachmentsDTO getAttachmentsDTO = boardDAO.getAttacheInfo(Integer.valueOf(fileID));
		
		/** getAttachmentsDTO null 값 체크 및 작성자와 로그인한 유저가 같은지 체크 */
//		if (getAttachmentsDTO == null
//				|| !getAttachmentsDTO.getUserID().equals((String) request.getSession().getAttribute("userID"))) {
//			response.sendRedirect(request.getContextPath() + "/index.jsp");
//			return;
//		}

		
		ObjectMapper mapper = new ObjectMapper();
		PrintWriter out = response.getWriter();
		Map<String, Object> resultMap = new HashMap<String, Object>();
				
		//String savePath=request.getAttribute("SystemFileDirectory");		
		String savePath =request.getServletContext().getRealPath("/upload/").replaceAll("\\\\", "/");
		savePath=savePath+getAttachmentsDTO.getFileDirectory()+getAttachmentsDTO.getFilesystemName();
		try{
			File file=new File(savePath);
			if(file.exists()) {
				file.delete();
			}
			
			boardDAO.attacheDelete(Integer.valueOf(fileID));
			resultMap.put("msg", "success");				
		}catch (Exception e) {
			logger.catching(e);
			resultMap.put("msg", "error");	
		}finally{
			out.print(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resultMap));	
		    out.flush();
		    out.close();	
		}
					
	}


	
	

}
