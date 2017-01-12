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
		int playerId = (int)session.getAttribute(StaticInfo.PLAYER_ID);
		String type = request.getParameter("type"); 
		if(type==null){
			int voteNum = StaticInfo.fetchVoteNum(roomId);
			int playerNum = StaticInfo.getPlayerNumByRoomId(roomId);
			String result = StaticInfo.fetchResult(roomId);
			String identity = StaticInfo.getIdentityByPlayerId(roomId, playerId);
			int[] colors = StaticInfo.getColor(roomId);
			String s = voteNum+"#"+playerNum+"#"+result+"#"+identity+"#";
			if(colors!=null){
				for(int i=0;i<colors.length;i++)
					s+=colors[i]+":";
				s=s.substring(0,s.length()-1);
			}
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().println(s);
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
		else if(type.equals("task")){
			int id = Integer.parseInt(request.getParameter("id"));	
			StaticInfo.changeColor(roomId, id);
		}
		else if(type.equals("shuffleIdentity")){
			StaticInfo.shuffleIdentities(roomId);
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
