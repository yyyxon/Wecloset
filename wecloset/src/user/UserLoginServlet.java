package user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String userID = request.getParameter("userID");
		String userPassword = request.getParameter("userPassword");
		
		//아이디나 패스워드가 비어있는 경우
		if(userID == null || userID.equals("") || userPassword == null || userPassword.equals("")) {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "모든 내용을 입력해주세요.");
			response.sendRedirect("login.jsp");
			return;
		}
		
		//UserDAO.java 에서 result 반환값을 설정했음
		//로그인 성공
		int result = new UserDAO().login(userID, userPassword);
		if(result == 1) {
			request.getSession().setAttribute("userID", userID);
			request.getSession().setAttribute("messageType", "성공 메시지");
			request.getSession().setAttribute("messageContent", "반가워요 ⋆⁺₊⋆");
			response.sendRedirect("index.jsp");
		} else if(result == 2) { //비밀번호가 잘못된 경우
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "비밀번호를 다시 확인하세요.");
			response.sendRedirect("login.jsp");
		} else if(result == 0) { //아이디가 없는 경우
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "아이디가 존재하지 않습니다.");
			response.sendRedirect("login.jsp");
		} else if(result == -1) { //데이터베이스 오류
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "데이터베이스 오류가 발생했습니다.");
			response.sendRedirect("login.jsp");
		} 
	}
}
