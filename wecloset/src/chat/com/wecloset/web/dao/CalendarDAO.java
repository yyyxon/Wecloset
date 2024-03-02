package com.wecloset.web.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wecloset.web.dto.CalendarDTO;

import config.MybatisService;
import user.UserDTO;

public class CalendarDAO {

	private final Logger logger = LogManager.getLogger(CalendarDAO.class);
	
	private CalendarDAO(){}
	
	private static CalendarDAO instance;
	
	public static CalendarDAO getInstance(){
		if(instance==null){
			instance=new CalendarDAO();
		}
		return instance;
	}
	
	
	
	
	/** calendar 등록
	 * 	 
	 * @param calendarDTO
	 * @return
	 */
	public Integer insertCalendar(CalendarDTO calendarDTO){
		SqlSession sqlSession=MybatisService.getFactory().openSession();
		Integer result=0;
		try{			
			result =sqlSession.insert("calendar.insertCalendar", calendarDTO);			
			sqlSession.commit();
		}catch(Exception e){	
			sqlSession.rollback();
			e.printStackTrace();
		}finally{				
			MybatisService.sessionClose(sqlSession);
		}
		return result;
	}



	/** calendar 수정
	 * 
	 * @param calendarDTO
	 * @return
	 */
	public Integer updateCalendar(CalendarDTO calendarDTO) {
		SqlSession sqlSession=MybatisService.getFactory().openSession();
		Integer result=0;
		try{			
			result=sqlSession.update("calendar.updateCalendar", calendarDTO);
			sqlSession.commit();
		}catch(Exception e){	
			sqlSession.rollback();
			e.printStackTrace();
		}finally{				
			MybatisService.sessionClose(sqlSession);
		}
		return result;
		
	}



	/** calendar 정보 불러오기
	 * 
	 * @param calendarDTO
	 * @return
	 */
	public CalendarDTO getInfoCalendar(CalendarDTO calendarDTO) {		
		CalendarDTO calendarDTO2=null;		
		SqlSession sqlSession=null;
		try{
			sqlSession=MybatisService.getFactory().openSession();		
			calendarDTO2=sqlSession.selectOne("calendar.getInfoCalendar", calendarDTO);			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			MybatisService.sessionClose(sqlSession);
		}
		return calendarDTO2;	
	}



	/** calendar 삭제
	 * 
	 * @param calendarDTO
	 * @return
	 */
	public Integer deleteCalendar(CalendarDTO calendarDTO) {
		SqlSession sqlSession=MybatisService.getFactory().openSession();
		Integer result=0;
		try{			
			result=sqlSession.delete("calendar.deleteCalendar", calendarDTO);
			sqlSession.commit();
		}catch(Exception e){	
			sqlSession.rollback();
			e.printStackTrace();
		}finally{				
			MybatisService.sessionClose(sqlSession);
		}
		return result;
	}




	public List<CalendarDTO> calendarList(Map<String, Object> map) {
		List<CalendarDTO> calList=null;
		SqlSession sqlSession=null;
		try{
			sqlSession=MybatisService.getFactory().openSession();		
			calList=sqlSession.selectList("calendar.calendarList", map);			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			MybatisService.sessionClose(sqlSession);
		}
		return calList;
	}



	/** 드래그로 수정
	 * 
	 * @param calendarDTO
	 * @return
	 */
	public Integer updateDragCalendar(CalendarDTO calendarDTO) {
		SqlSession sqlSession=MybatisService.getFactory().openSession();
		Integer result=0;
		try{			
			result=sqlSession.update("calendar.updateDragCalendar", calendarDTO);
			sqlSession.commit();
		}catch(Exception e){	
			sqlSession.rollback();
			e.printStackTrace();
		}finally{				
			MybatisService.sessionClose(sqlSession);
		}
		return result;
	}




	public List<UserDTO> calendarMonthUser(Map<String, Object> map) {
		List<UserDTO>  calList=null;
		SqlSession sqlSession=null;
		try{
			sqlSession=MybatisService.getFactory().openSession();		
			calList= sqlSession.selectList("calendar.calendarMonthUser", map);			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			MybatisService.sessionClose(sqlSession);
		}
		return calList;
	}




	public List<UserDTO> calendarWriteUser(Object obj) {
		List<UserDTO>  calList=null;
		SqlSession sqlSession=null;
		try{
			sqlSession=MybatisService.getFactory().openSession();		
			calList= sqlSession.selectList("calendar.calendarWriteUser", obj);			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			MybatisService.sessionClose(sqlSession);
		}
		return calList;
	}

	
	
	
	
	
}





