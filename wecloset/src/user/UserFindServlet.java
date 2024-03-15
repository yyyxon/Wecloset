package user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UserFindServlet")
public class UserFindServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String userID = request.getParameter("userID");
		
		if(userID == null || userID.equals("")) { //사용자의 데이터 전달이 되지 않은 경우
			response.getWriter().write("-1");
		} else if(new UserDAO().registerCheck(userID) == 0) { //회원가입이 정상적으로 되어 있는 경우
			try {
				response.getWriter().write(find(userID));
			} catch (Exception e) {
				e.printStackTrace();
				response.getWriter().write("-1");
			}
		} else { //회원가입이 안 되어있는 경우
			response.getWriter().write("-1");
		}
	}
	
	public String find(String userID) throws Exception {
		StringBuffer result = new StringBuffer("");
		//사용자의 프로필 출력
		result.append("{\"userProfile\":\"" + new UserDAO().getProfile(userID) + "\"}");
		return result.toString();
	}
}
