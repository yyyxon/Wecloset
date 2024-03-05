package chat;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ChatUnreadServlet")
public class ChatUnreadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String userID = request.getParameter("usreID");
		//특정한 사용자가 읽지 않은 메세지를 출력하기위해 사용자 아이디 값을 가져옴
		
		if(userID == null || userID.equals("")) {
			response.getWriter().write("0");
			//아이디 값이 존재하지 않는 경우 0을 출력해서 오류가 발생했음을 사용자에게 전달
		} else {
			userID = URLDecoder.decode(userID, "UTF-8");
			HttpSession session = request.getSession();
			if(!URLDecoder.decode(userID,"UTF-8").equals((String) session.getAttribute("userID"))) {
				response.getWriter().write("");
				return;
			}
			response.getWriter().write(new ChatDAO().getAllUnreadChat(userID) + "");
			//문자열로 바꿔서 출력할 수 있도록 "" 붙임
			//특정한 사용자가 읽지 않는 메세지의 개수를 사용자에게 전달
			
		}
	}
}
