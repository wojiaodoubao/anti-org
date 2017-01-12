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
 * Servlet implementation class VoteServlet
 */
@WebServlet("/VoteServlet")
public class VoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VoteServlet() {
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
		String type = request.getParameter("type"); 
		if(type==null){
			int voteNum = StaticInfo.fetchVoteNum(roomId);
			int playerNum = StaticInfo.getPlayerNumByRoomId(roomId);
			String result = StaticInfo.fetchResult(roomId);
			response.getWriter().println(voteNum+"#"+playerNum+"#"+result);
			response.flushBuffer();				
		}
		else if(type.equals("start")){
			StaticInfo.startVote(roomId);
		}
		else if(type.equals("vote")){
			int index = Integer.parseInt(request.getParameter("voteIndex"));		
			StaticInfo.vote(roomId, index);			
		}
		else if(type.equals("end")){
			VoteInfo[] res = StaticInfo.endVote(roomId);
			String s = "";
			if(res!=null){
				for(int i=0;i<res.length;i++){
					s+=res[i].item+":"+res[i].num+"\n";
				}
			}
			StaticInfo.setResult(roomId, s);			
		}
		else;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
