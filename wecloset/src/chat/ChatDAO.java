package chat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import user.DBConnectionMgr;

public class ChatDAO {
	
    private DBConnectionMgr pool = null;

    public ChatDAO() {
        try {
            pool = DBConnectionMgr.getInstance();
        } catch (Exception e) {
            System.out.println("Error !!");
        }
    }
    
    //채팅 내역
    public ArrayList<ChatDTO> getChatListByID(String fromID, String toID, String chatID) {
    	ArrayList<ChatDTO> chatList = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = pool.getConnection();
            String strQuery = "select * from CHAT where ((fromID = ? AND toID = ?) OR (fromID = ? AND toID = ? )) AND chatID > ? ORDER BY chatTime";
            pstmt = con.prepareStatement(strQuery);
            pstmt.setString(1, fromID);
            pstmt.setString(2, toID);
            pstmt.setString(3, toID);
            pstmt.setString(4, fromID);
            pstmt.setInt(5, Integer.parseInt(chatID));
            rs = pstmt.executeQuery();
            chatList = new ArrayList<ChatDTO>();
            while (rs.next()) {
            	ChatDTO chat = new ChatDTO();
            	chat.setChatID(rs.getInt("chatID"));
            	chat.setFromID(rs.getString("fromID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
            	chat.setToID(rs.getString("toID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
                chat.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
                int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
                String timeType = "오전";
                if(chatTime >= 12) {
                	timeType = "오후";
                	chatTime -= 12;
                }
                chat.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + ":" + rs.getString("chatTime").substring(14, 16) + "");
                chatList.add(chat);
            }
        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return chatList; //리스트 반환
    }
    
    public ArrayList<ChatDTO> getChatListByRecent(String fromID, String toID, int number) {
    	ArrayList<ChatDTO> chatList = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = pool.getConnection();
            String strQuery = "select * from CHAT where ((fromID = ? AND toID = ?) OR (fromID = ? AND toID = ? )) AND chatID > (SELECT MAX(chatID) - ? FROM CHAT WHERE (fromID = ? AND toID = ? ) OR (fromID = ? AND toID = ?)) ORDER BY chatTime";
            pstmt = con.prepareStatement(strQuery);
            pstmt.setString(1, fromID);
            pstmt.setString(2, toID);
            pstmt.setString(3, toID);
            pstmt.setString(4, fromID);
            pstmt.setInt(5, number);
            pstmt.setString(6, fromID);
            pstmt.setString(7, toID);
            pstmt.setString(8, toID);
            pstmt.setString(9, fromID);
            rs = pstmt.executeQuery();
            chatList = new ArrayList<ChatDTO>();
            while (rs.next()) {
            	ChatDTO chat = new ChatDTO();
            	chat.setChatID(rs.getInt("chatID"));
            	chat.setFromID(rs.getString("fromID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
            	chat.setToID(rs.getString("toID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
                chat.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
                int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
                String timeType = "오전";
                if(chatTime >= 12) {
                	timeType = "오후";
                	chatTime -= 12;
                }
                chat.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + ":" + rs.getString("chatTime").substring(14, 16) + "");
                chatList.add(chat);
            }
        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return chatList; //리스트 반환
    }

    //특정한 사용자가 채팅에서 최근에 주고받은 모든 메세지 리스트를 출력
    public ArrayList<ChatDTO> getBox(String userID) {
    	ArrayList<ChatDTO> chatList = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String strQuery = "SELECT * FROM CHAT WHERE chatID IN (SELECT MAX(chatID) FROM CHAT WHERE toID = ? OR fromID = ? GROUP BY fromID, toID)";
        try {
            con = pool.getConnection();
            pstmt = con.prepareStatement(strQuery);
            pstmt.setString(1, userID);
            pstmt.setString(2, userID);
            rs = pstmt.executeQuery();
            chatList = new ArrayList<ChatDTO>();
            while (rs.next()) {
            	ChatDTO chat = new ChatDTO();
            	chat.setChatID(rs.getInt("chatID"));
            	chat.setFromID(rs.getString("fromID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
            	chat.setToID(rs.getString("toID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
                chat.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
                int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
                String timeType = "오전";
                if(chatTime >= 12) {
                	timeType = "오후";
                	chatTime -= 12;
                }
                chat.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + ":" + rs.getString("chatTime").substring(14, 16) + "");
                chatList.add(chat);
            }
            
            for(int i = 0; i < chatList.size(); i++) {
            	ChatDTO x = chatList.get(i);
            	for(int j = 0; j < chatList.size(); j++) {
            		ChatDTO y = chatList.get(j);
            		if(x.getFromID().equals(y.getToID()) && x.getToID().equals(y.getFromID())) {
            			if(x.getChatID() < y.getChatID()) {
            				chatList.remove(x);
            				i--;
            				break;
            			} else {
            				chatList.remove(y);
            				j--;
            			}
            		}
            	}
            }

        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return chatList; //리스트 반환
    }
    
    //채팅전송기능
    public int submit(String fromID, String toID, String chatContent) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = pool.getConnection();
            String strQuery = "INSERT INTO CHAT VALUES (NULL, ?, ?, ?, NOW(), 0)"; 
            //NULL=채팅번호가 자동으로 증가하도록, 메세지를 처음 보낼 땐 읽지 않음이니까 0
            pstmt = con.prepareStatement(strQuery);
            pstmt.setString(1, fromID);
            pstmt.setString(2, toID);
            pstmt.setString(3, chatContent);
            return pstmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return -1; //데이터베이스 오류
    }
    
    //메세지 읽음 처리
    public int readChat(String fromID, String toID) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = pool.getConnection();
            String strQuery = "UPDATE CHAT SET chatRead = 1 WHERE (fromID = ? AND toID = ?)"; 
            pstmt = con.prepareStatement(strQuery);
            pstmt.setString(1, toID);
            pstmt.setString(2, fromID);
            //from 과 to id를 바꿔 적어서 받는 사람 입장에서 읽음 표시가 되도록
            return pstmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return -1; //데이터베이스 오류
    }
    
    //현재 읽지 않은 모든 메시지 개수 확인
    public int getAllUnreadChat(String userID) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = pool.getConnection();
            String strQuery = "SELECT COUNT(chatID) FROM CHAT WHERE toID = ? AND chatRead = 0"; 
            pstmt = con.prepareStatement(strQuery);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();
            if(rs.next()) {
            	return rs.getInt("COUNT(chatID)");
            }
            return 0;
        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return -1; //데이터베이스 오류
    }
    
    //현재 읽지 않은 대화상대별 메시지 개수 확인
    public int getUnreadChat(String fromID, String toID) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = pool.getConnection();
            String strQuery = "SELECT COUNT(chatID) FROM CHAT WHERE fromID = ? AND toID = ? AND chatRead = 0"; 
            pstmt = con.prepareStatement(strQuery);
            pstmt.setString(1, fromID);
            pstmt.setString(2, toID);
            rs = pstmt.executeQuery();
            if(rs.next()) {
            	return rs.getInt("COUNT(chatID)");
            }
            return 0;
        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return -1; //데이터베이스 오류
    }
    
}
