package hit;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hit.AnonymousVote.VoteInfo;

/**
 * Servlet implementation class EndVoteServlet
 */
@WebServlet("/EndVoteServlet")
public class EndVoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EndVoteServlet() {
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
		VoteInfo[] res = StaticInfo.endVote(roomId);
		String s = "";
		if(res!=null){
			for(int i=0;i<res.length;i++){
				s+=res[i].item+":"+res[i].num+"\n";
			}
		}
		StaticInfo.setResult(roomId, s);
		response.sendRedirect("vote.jsp?voteResult="+s);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
