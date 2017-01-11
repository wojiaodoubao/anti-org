package hit;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class VoteNumServlet
 */
@WebServlet("/VoteNumServlet")
public class VoteNumServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VoteNumServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		int roomId = (int)session.getAttribute(StaticInfo.ROOM_ID);
		int voteNum = StaticInfo.fetchVoteNum(roomId);
		int playerNum = StaticInfo.getPlayerNumByRoomId(roomId);
		String result = StaticInfo.fetchResult(roomId);
		response.getWriter().println(voteNum+"#"+playerNum+"#"+result);
		response.flushBuffer();
//		System.out.println(voteNum);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
