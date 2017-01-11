package hit;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class JoinGameServlet
 */
@WebServlet("/JoinGameServlet")
public class JoinGameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JoinGameServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		int roomId = Integer.parseInt(request.getParameter("roomId"));
		if(!StaticInfo.isRoomExist(roomId)){//房间不存在
			response.sendRedirect("index.html");
		}
		else if(session.getAttribute(StaticInfo.ROOM_ID)==null||
				(int)session.getAttribute(StaticInfo.ROOM_ID)!=roomId){//加入新房间
			int playerId = StaticInfo.createPlayerByRoomId(roomId);
			session.setAttribute(StaticInfo.ROOM_ID, roomId);
			session.setAttribute(StaticInfo.PLAYER_ID, playerId);
			response.sendRedirect("vote.jsp");
		}
		else{//加入old房间
			response.sendRedirect("vote.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
