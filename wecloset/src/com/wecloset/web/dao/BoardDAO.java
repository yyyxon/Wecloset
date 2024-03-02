package com.wecloset.web.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wecloset.web.dto.AttachmentsDTO;
import com.wecloset.web.dto.BoardDTO;
import com.wecloset.web.dto.CommentDTO;

import config.MybatisService;

public class BoardDAO {

	private final Logger logger = LogManager.getLogger(BoardDAO.class);
	
	private BoardDAO(){}
	
	private static BoardDAO instance;
	
	public static BoardDAO getInstance(){
		if(instance==null){
			instance=new BoardDAO();
		}
		return instance;
	}
	
	
	/**
	 * 1.게시판 목록 출력
	 * @param obj
	 * @return
	 */
	public List<BoardDTO> getListBoard(Object obj){
		List<BoardDTO> getListBoard=null;
		SqlSession sqlSession=null;
		try{
			sqlSession=MybatisService.getFactory().openSession();		
			getListBoard=sqlSession.selectList("board.getListBoard", obj);			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			MybatisService.sessionClose(sqlSession);
		}
		return getListBoard;
	}
	
	
	
	/**
	 * 2. 게시판 갯수
	 * @param obj
	 * @return
	 */
	public Integer boardTotalCount(Object obj) {
		Integer totalCount=0;
		SqlSession sqlSession=null;
		try{
			sqlSession=MybatisService.getFactory().openSession();		
			totalCount=sqlSession.selectOne("board.boardTotalCount", obj);				
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			MybatisService.sessionClose(sqlSession);
		}
		return totalCount;		
	}
	
	
	
	/**
	 * 3. 게시판 등록
	 * @param object
	 */
	public Integer insertBoard(BoardDTO boardDTO, AttachmentsDTO attachmentsDTO){
		SqlSession sqlSession=MybatisService.getFactory().openSession();
		Integer result=0;
		try{
			
			/** 1) 글 등록 */
			sqlSession.insert("board.insertBoard", boardDTO);
			
			if(attachmentsDTO!=null && attachmentsDTO.getFilesystemName()!=null) {
				/** 2) 첨부파일 등록 */
				logger.info("insert key 값 :  {} ", boardDTO.getBoardID());	
				attachmentsDTO.setBoardID(boardDTO.getBoardID());											
				sqlSession.insert("board.insertAttachements", attachmentsDTO);
			}
			
			result=1;
			sqlSession.commit();
		}catch(Exception e){	
			sqlSession.rollback();
			e.printStackTrace();
		}finally{				
			MybatisService.sessionClose(sqlSession);
		}
		return result;
	}

	
	/**  
	 * 4. 첨부파일 등록
	 * @param attachmentsDTO
	 * @return
	 */
	public Integer insertAttachements(AttachmentsDTO attachmentsDTO){
		SqlSession sqlSession=MybatisService.getFactory().openSession();
		Integer result=0;
		try{
			if(attachmentsDTO!=null && attachmentsDTO.getFilesystemName()!=null) {									
				result=sqlSession.insert("board.insertAttachements", attachmentsDTO);
			}			
			sqlSession.commit();
		}catch(Exception e){	
			e.printStackTrace();
		}finally{				
			MybatisService.sessionClose(sqlSession);
		}
		return result;
	}
	

	/**
	 * 5.게시판 정보 불러오기
	 * @param boardGroup
	 * @return
	 */
	public BoardDTO getBoard(Object obj) {
		BoardDTO boardDTO=null;		
		SqlSession sqlSession=null;
		try{
			sqlSession=MybatisService.getFactory().openSession();		
			boardDTO=sqlSession.selectOne("board.getBoard", obj);			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			MybatisService.sessionClose(sqlSession);
		}
		return boardDTO;		
	}

	
	
	public BoardDTO getBoardBno(int boardID) {
		BoardDTO boardDTO=null;		
		SqlSession sqlSession=null;
		try{
			sqlSession=MybatisService.getFactory().openSession();		
			boardDTO=sqlSession.selectOne("board.getBoardBno", boardID);			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			MybatisService.sessionClose(sqlSession);
		}
		return boardDTO;
	}
	
	

	/** 6.파일 정보 불러오기 
	 * 
	 * @param fileID
	 * @return
	 */
	public AttachmentsDTO getAttacheInfo(Integer fileID) {
		AttachmentsDTO attachmentsDTO=null;		
		SqlSession sqlSession=null;
		try{
			sqlSession=MybatisService.getFactory().openSession();		
			attachmentsDTO=sqlSession.selectOne("board.getAttacheInfo", fileID);			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			MybatisService.sessionClose(sqlSession);
		}
		return attachmentsDTO;
	}

	
	/** 7.첨부 파일 삭제
	 * 
	 * @param valueOf
	 */
	public int attacheDelete(Integer fileID) {
		int result=0;		
		SqlSession sqlSession=null;
		try{
			sqlSession=MybatisService.getFactory().openSession();		
			result=sqlSession.delete("board.getAttacheDelete", fileID);	
			sqlSession.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			MybatisService.sessionClose(sqlSession);
		}
		return result;
	}


	
	
    /**8.게시판 업데이트
     * 
     * @param boardDTO
     * @param attachmentsDTO
     * @return
     */
	public Integer updateBoard(BoardDTO boardDTO, AttachmentsDTO attachmentsDTO){
		SqlSession sqlSession=MybatisService.getFactory().openSession();
		Integer result=0;
		try{
			
			/** 1) 글 수정 */
			sqlSession.update("board.updateBoard", boardDTO);
			
			if(attachmentsDTO!=null && attachmentsDTO.getFilesystemName()!=null) {
				attachmentsDTO.setBoardID(boardDTO.getBoardID());											
				sqlSession.insert("board.insertAttachements", attachmentsDTO);
			}
			
			result=1;
			sqlSession.commit();
		}catch(Exception e){	
			sqlSession.rollback();
			e.printStackTrace();
		}finally{				
			MybatisService.sessionClose(sqlSession);
		}
		return result;
	}


	/** 9.조회수 증가 */
	public void updateBoardHit(int boardID) {
		SqlSession sqlSession=MybatisService.getFactory().openSession();
		try{			
			sqlSession.update("board.updateBoardHit", boardID);
			sqlSession.commit();
		}catch(Exception e){	
			sqlSession.rollback();
			e.printStackTrace();
		}finally{				
			MybatisService.sessionClose(sqlSession);
		}		
	}

	
	/**
	 * 10. 댓글 등록
	 * @param object
	 */
	public Integer insertComment(CommentDTO commentDTO){
		SqlSession sqlSession=MybatisService.getFactory().openSession();
		Integer result=0;
		try{			
			result=sqlSession.insert("board.insertComment", commentDTO);		
			sqlSession.commit();
		}catch(Exception e){	
			e.printStackTrace();
		}finally{				
			MybatisService.sessionClose(sqlSession);
		}
		return result;
	}
	
	
	/**
	 * 11. 댓글 수정
	 * @param object
	 */
	public Integer updateComment(CommentDTO commentDTO){
		SqlSession sqlSession=MybatisService.getFactory().openSession();
		Integer result=0;
		try{
			result=sqlSession.update("board.updateComment", commentDTO);
			sqlSession.commit();
		}catch(Exception e){	
			sqlSession.rollback();
			e.printStackTrace();
		}finally{				
			MybatisService.sessionClose(sqlSession);
		}
		return result;
	}
	
	
	
	/**
	 * 12. 댓글 삭제
	 * @param int
	 */
	public Integer deleteComment(int commentID){
		int result=0;		
		SqlSession sqlSession=null;
		try{
			sqlSession=MybatisService.getFactory().openSession();		
			result=sqlSession.delete("board.deleteComment", commentID);	
			sqlSession.commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			MybatisService.sessionClose(sqlSession);
		}
		return result;
	}
	
	
	/**
	 * 13. 댓글 정보
	 * @param object
	 */
	public CommentDTO getComment(int commentID){
		CommentDTO commentDTO=null;		
		SqlSession sqlSession=null;
		try{
			sqlSession=MybatisService.getFactory().openSession();		
			commentDTO=sqlSession.selectOne("board.getComment", commentID);			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			MybatisService.sessionClose(sqlSession);
		}
		return commentDTO;
	}
	
	
	/** 14. 댓글 목록
	 * 
	 * @param commentDTO
	 * @return
	 */	 
	public List<CommentDTO>  getListComment(Object obj){
		List<CommentDTO> list=null;		
		SqlSession sqlSession=null;
		try{
			sqlSession=MybatisService.getFactory().openSession();		
			list=sqlSession.selectList("board.getListComment", obj);			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			MybatisService.sessionClose(sqlSession);
		}
		return list;
	}
	
	
	
	/**
	 * 15. 댓글  갯수
	 * @param obj
	 * @return
	 */
	public Integer commentTotalCount(Object obj) {
		Integer totalCount=0;
		SqlSession sqlSession=null;
		try{
			sqlSession=MybatisService.getFactory().openSession();		
			totalCount=sqlSession.selectOne("board.commentTotalCount", obj);				
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			MybatisService.sessionClose(sqlSession);
		}
		return totalCount;		
	}

	
	/** 16.게시판 숨김
	 * 
	 * @param boardID
	 */
	public boolean boardUnavailable(String boardID) {
		SqlSession sqlSession=MybatisService.getFactory().openSession();
		Integer result=0;
		try{
			result=sqlSession.update("board.boardUnavailable", boardID);
			sqlSession.commit();
		}catch(Exception e){	
			sqlSession.rollback();
			e.printStackTrace();
		}finally{				
			MybatisService.sessionClose(sqlSession);
		}
		return result >0 ? true :false;		
	}


	/**
	 * 17.daily 목록 출력
	 * @param obj
	 * @return
	 */
	public List<BoardDTO> getListDaily(Object obj){
		List<BoardDTO> getListBoard=null;
		SqlSession sqlSession=null;
		try{
			sqlSession=MybatisService.getFactory().openSession();		
			getListBoard=sqlSession.selectList("board.getListDaily", obj);			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			MybatisService.sessionClose(sqlSession);
		}
		return getListBoard;
	}
	
	
	/**
	 * 18. daily 좋아요
	 * @param obj
	 * @return
	 */	
	public boolean insertLike(Object obj){
		SqlSession sqlSession=MybatisService.getFactory().openSession();
		Integer result=0;
		try{
			result=sqlSession.insert("board.insertLike", obj);
			sqlSession.update("board.boardLikeCountPlus", obj);
			sqlSession.commit();
		}catch(Exception e){	
			sqlSession.rollback();
			e.printStackTrace();
		}finally{				
			MybatisService.sessionClose(sqlSession);
		}
		return result >0 ? true :false;		
	}
	
	
	
	/** 19. daily 좋아요 삭제
	 * 
	 * @param obj
	 * @return
	 */
	public boolean deleteLike(Object obj){
		SqlSession sqlSession=MybatisService.getFactory().openSession();
		Integer result=0;
		try{
			result=sqlSession.delete("board.deleteLike", obj);
			sqlSession.update("board.boardLikeCountMinus", obj);
			sqlSession.commit();
		}catch(Exception e){	
			sqlSession.rollback();
			e.printStackTrace();
		}finally{				
			MybatisService.sessionClose(sqlSession);
		}
		return result >0 ? true :false;		
	}


	
	
	public boolean boardDelete(String boardID, Integer fileID) {
		SqlSession sqlSession=MybatisService.getFactory().openSession();
		Integer result=0;
		try{
			if(fileID!=null){
				sqlSession.delete("board.getAttacheDelete", fileID);	
			}
			result=sqlSession.delete("board.boardDelete", boardID);
			sqlSession.commit();
		}catch(Exception e){	
			sqlSession.rollback();
			e.printStackTrace();
		}finally{				
			MybatisService.sessionClose(sqlSession);
		}
		return result >0 ? true :false;		
	}



	
	
	
	
}





