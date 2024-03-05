package chat;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ChatListServlet")
public class ChatListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String fromID = request.getParameter("fromID");
		String toID = request.getParameter("toID");
		String listType = request.getParameter("listType");
		if(fromID == null || fromID.equals("") || toID == null || toID.equals("")
				|| listType == null || listType.equals("")) {
			response.getWriter().write("");
		} 
		else if (listType.equals("ten")) response.getWriter().write(getTen(URLDecoder.decode(fromID, "UTF-8"), URLDecoder.decode(toID, "UTF-8")));
		else {
			try {
				HttpSession session = request.getSession();
				if(!URLDecoder.decode(fromID, "UTF-8").equals((String) session.getAttribute("userID"))) { //URLDecoder.decode 써주기!
					response.getWriter().write("");
					return;
				}
				response.getWriter().write(getID(URLDecoder.decode(fromID, "UTF-8"), URLDecoder.decode(toID, "UTF-8"), listType));
			} catch (Exception e) {
				response.getWriter().write("");
			}
		}	
	}
	
	//채팅 내역 가져옴 최근 채팅내역 100개까지
	public String getTen(String fromID, String toID) {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<ChatDTO> chatList = chatDAO.getChatListByRecent(fromID, toID, 100);
		if(chatList.size() == 0) return ""; //채팅이 없으면 공백 반환
		for(int i = 0; i < chatList.size(); i++) {
			result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getToID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"}]");
			
			if(i != chatList.size() -1) result.append(","); //채팅 목록에서 마지막이 아니면 콤마를 찍어줌
		}
		result.append("], \"last\":\"" + chatList.get(chatList.size() - 1).getChatID() + "\"}");
		chatDAO.readChat(fromID, toID);
		//채팅내역을 가져왔다는 건 모두 채팅을 읽었다는 것이므로 채팅 읽음 처리 해줌
		return result.toString();
	}
	
	public String getID(String fromID, String toID, String chatID) {
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		ChatDAO chatDAO = new ChatDAO();
		ArrayList<ChatDTO> chatList = chatDAO.getChatListByID(fromID, toID, chatID);
		if(chatList.size() == 0) return ""; //채팅이 없으면 공백 반환
		for(int i = 0; i < chatList.size(); i++) {
			result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getToID() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");
			result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"}]");
			
			if(i != chatList.size() -1) result.append(","); //채팅 목록에서 마지막이 아니면 콤마를 찍어줌
		}
		result.append("], \"last\":\"" + chatList.get(chatList.size() - 1).getChatID() + "\"}");
		chatDAO.readChat(fromID, toID);
		return result.toString();
	}
}
