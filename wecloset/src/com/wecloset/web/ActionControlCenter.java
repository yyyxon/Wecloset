package com.wecloset.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wecloset.web.controller.AttachmentsDeleteController;
import com.wecloset.web.controller.BoardDeleteController;
import com.wecloset.web.controller.BoardListController;
import com.wecloset.web.controller.BoardSearchController;
import com.wecloset.web.controller.BoardUnavailableController;
import com.wecloset.web.controller.BoardUpdateController;
import com.wecloset.web.controller.BoardViewController;
import com.wecloset.web.controller.BoardWriteController;
import com.wecloset.web.controller.CalendarAjaxListController;
import com.wecloset.web.controller.CalendarDeleteController;
import com.wecloset.web.controller.CalendarUpdateController;
import com.wecloset.web.controller.CalendarViewController;
import com.wecloset.web.controller.CalendarWriteController;
import com.wecloset.web.controller.CommentsDeleteController;
import com.wecloset.web.controller.CommentsListController;
import com.wecloset.web.controller.CommentsUpdateController;
import com.wecloset.web.controller.CommentsWriteController;
import com.wecloset.web.controller.DailyAjaxListController;
import com.wecloset.web.controller.DailyUpdateController;
import com.wecloset.web.controller.DailyWriteController;
import com.wecloset.web.controller.LikeUpdateController;

public class ActionControlCenter {

	private final Logger logger = LogManager.getLogger(ActionControlCenter.class);
	
	private static ActionControlCenter instance;

	private ActionControlCenter() {
		super();
	}

	public static ActionControlCenter getInstance() {
		if (instance == null)
			instance = new ActionControlCenter();			
		return instance;
	}

	/**
	 * command 에서 넘어온 파라미터 값이 존재하면 실행
	 * @param command
	 * @return
	 */
	public Action getAction(String command){		
	
		Action action = null;				
		try{
			
			action=BoardListController.getInstance();
			
			if (command.equals("boardList")) {
				action=BoardListController.getInstance();				
			}else if(command.equals("boardWrite")) {
				action=BoardWriteController.getInstance();				
			}else if(command.equals("boardView")) {
				action=BoardViewController.getInstance();					
			}else if(command.equals("boardUpdate")) {
				action=BoardUpdateController.getInstance();					
			}else if(command.equals("boardUnavailable")) {
				action=BoardUnavailableController.getInstance();					
			}else if(command.equals("boardDelete")) {
				action=BoardDeleteController.getInstance();					
			}else if(command.equals("boardSearch")) {
				action=BoardSearchController.getInstance();					
			}else if(command.equals("attachmentsDelete")) {
				action=AttachmentsDeleteController.getInstance();					
			}else if(command.equals("commentWrite")) {
				action=CommentsWriteController.getInstance();					
			}else if(command.equals("commentList")) {
				action=CommentsListController.getInstance();					
			}else if(command.equals("commentUpdate")) {
				action=CommentsUpdateController.getInstance();					
			}else if(command.equals("commentDelete")) {
				action=CommentsDeleteController.getInstance();					
			}else if(command.equals("commentDelete")) {
				action=CommentsDeleteController.getInstance();					
			}else if(command.equals("dailyWrite")) {
				action=DailyWriteController.getInstance();					
			}else if(command.equals("dailyUpdate")) {
				action=DailyUpdateController.getInstance();					
			}else if(command.equals("likeUpdate")) {
				action=LikeUpdateController.getInstance();					
			}else if(command.equals("dailyAjaxList")) {
				action=DailyAjaxListController.getInstance();					
			}else if(command.equals("calendar")) {
				action=CalendarViewController.getInstance();					
			}else if(command.equals("calendarWrite")) {
				action=CalendarWriteController.getInstance();					
			}else if(command.equals("calendarUpdate")) {
				action=CalendarUpdateController.getInstance();					
			}else if(command.equals("calendarDelete")) {
				action=CalendarDeleteController.getInstance();					
			}else if(command.equals("calendarAjaxList")) {
				action=CalendarAjaxListController.getInstance();					
			}else{
				
			}	
			
					
		}catch(IndexOutOfBoundsException  e1){
			e1.printStackTrace();		
		}catch(NullPointerException e2){
			e2.printStackTrace();		
		}catch (Exception e) {
			e.printStackTrace();			
		}
		return action;
	}

	
	
	
}
