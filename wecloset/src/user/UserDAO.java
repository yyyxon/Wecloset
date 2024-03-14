package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

public class UserDAO {
	
    private DBConnectionMgr pool = null;
   
    HttpServletRequest request;
   
    public UserDAO() {
        try {
            pool = DBConnectionMgr.getInstance();
        } catch (Exception e) {
            System.out.println("Error !!");
        }
    }
    
    public int login(String userID, String userPassword) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = pool.getConnection();
            String strQuery = "select * from USER where userID = ?";
            pstmt = con.prepareStatement(strQuery);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
            	if(rs.getString("userPassword").equals(userPassword)) {
            		return 1; //로그인 성공
            	}
            	return 2; // 비밀번호 틀림
            } else {
            	return 0; // 해당 사용자가 존재하지 않음
            }
        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return -1;
    }
    

    public int registerCheck(String userID) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = pool.getConnection();
            String strQuery = "select * from USER where userID = ?";
            pstmt = con.prepareStatement(strQuery);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();
            if (rs.next() || userID.equals("")) {
            	return 0; //이미 존재하는 회원
            } else {
            	return 1; //가입 가능한 회원 아이디
            }
        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return -1;
    }
    
    public int register(String userID,String userPassword,String userName,String userAge, String userGender,String userEmail,String userProfile) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = pool.getConnection();
            String strQuery = "insert into USER values(?,?,?,?,?,?,?)";
            pstmt = con.prepareStatement(strQuery);
            pstmt.setString(1, userID);
            pstmt.setString(2, userPassword);
            pstmt.setString(3, userName);
            pstmt.setInt(4, Integer.parseInt(userAge));
            pstmt.setString(5, userGender);
            pstmt.setString(6, userEmail);
            pstmt.setString(7, userProfile);
            return pstmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return -1;
    }
    
    public UserDTO getUser(String userID) {
    	UserDTO user = new UserDTO();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = pool.getConnection();
            String strQuery = "select * from USER where userID = ?";
            pstmt = con.prepareStatement(strQuery);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
            	user.setUserID(userID);
            	user.setUserPassword(rs.getString("userPassword"));
            	user.setUserName(rs.getString("userName"));
            	user.setUserAge(rs.getInt("userAge"));
            	user.setUserGender(rs.getString("userGender"));
            	user.setUserEmail(rs.getString("userEmail"));
            	user.setUserProfile(rs.getString("userProfile"));
            }
        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return user;
    }
    
    public int update(String userID, String userPassword, String userName, String userAge, String userGender, String userEmail) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = pool.getConnection();
            String strQuery = "UPDATE USER SET userPassword = ?, userName = ?, userAge = ?, userGender = ?, userEmail = ? WHERE userID = ?";
            pstmt = con.prepareStatement(strQuery);
            pstmt.setString(1, userPassword);
            pstmt.setString(2, userName);
            pstmt.setInt(3, Integer.parseInt(userAge));
            pstmt.setString(4, userGender);
            pstmt.setString(5, userEmail);
            pstmt.setString(6, userID);
            return pstmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return -1;
    }
    
    public int profile(String userID, String userProfile) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = pool.getConnection();
            String strQuery = "UPDATE USER SET userProfile = ? WHERE userID = ?";
            pstmt = con.prepareStatement(strQuery);
            pstmt.setString(1, userProfile);
            pstmt.setString(2, userID);
            return pstmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        return -1;
    }
    
    //사용자의 프로필 경로 획득
    public String getProfile(String userID) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        

        
        try {
            con = pool.getConnection();
            String strQuery = "SELECT userProfile, userGender FROM USER WHERE userID = ?";
            pstmt = con.prepareStatement(strQuery);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();
            
            
            if (rs.next()) {
            	if(rs.getString("userProfile").equals("")) { //프로필 경로가 공백인 경우
            		//return "http://localhost:8080/UserChat/images/icon.png"; //기본이미지 경로를 리턴
            		//return "images/icon.png";
            		
            		if(rs.getString("userGender").equals("남자")) {
            			return "images/man.jpg";
            		}else if(rs.getString("userGender").equals("여자")) {
            			return "images/woman.png";
            		}else{
            			return "images/icon.png";
            		}
            		
            	} //공백이 아닌 경우
            	//return "http://localhost:8080/UserChat/upload/" + rs.getString("userProfile"); //사용자의 프로필 경로를 리턴
            	return "upload/" + rs.getString("userProfile"); //사용자의 프로필 경로를 리턴
            }
        } catch (Exception ex) {
            System.out.println("Exception" + ex);
        } finally {
            pool.freeConnection(con, pstmt, rs);
        }
        //return "http://localhost:8080/UserChat/images/icon.png"; //오류가 날 경우 기본이미지 경로 리턴
        return "images/icon.png"; //오류가 날 경우 기본이미지 경로 리턴
    }

}
