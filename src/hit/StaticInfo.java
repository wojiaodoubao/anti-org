package hit;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import hit.AnonymousVote.VoteInfo;

public class StaticInfo {
	public static class RoomInfo{
		int playerNum;
		AnonymousVote vote;
		String result;
		public RoomInfo(){
			playerNum = 0;
			vote = null;
		}
	}
	static{
		roomMap = new HashMap<Integer,RoomInfo>();
		random = new Random();
	}
	/**
	 * 为房间roomId增加一名玩家，并返回玩家在房间的Id (房间玩家数量自增1)
	 * */
	synchronized public static Integer createPlayerByRoomId(int roomId){
		int i = roomMap.get(roomId).playerNum++;
		return i;
	}
	/**
	 * 获取房间roomId玩家数量
	 * */
	synchronized public static Integer getPlayerNumByRoomId(int roomId){
		return isRoomExist(roomId)?roomMap.get(roomId).playerNum:-1;
	}
	/**
	 * 创建新房间，并返回房间Id
	 * */
	synchronized public static Integer createRoom(){
		int roomId = Math.abs(random.nextInt());
		while(isRoomExist(roomId))
			roomId = Math.abs(random.nextInt());
		roomMap.put(roomId, new RoomInfo());
		return roomId;
	}
	public static boolean isRoomExist(int roomId){
		return roomMap.get(roomId)!=null;
	}
	public static boolean isOnVote(int roomId){
		return isRoomExist(roomId)?roomMap.get(roomId).vote!=null:false;
	}
	public static void startVote(int roomId){
		if(!isRoomExist(roomId))return;
		String[] items = {"YES","NO"};
		roomMap.get(roomId).vote = new AnonymousVote(items);
	}
	/**
	 * 返回当前总投票数
	 * */
	public static int vote(int roomId,int index){
		return isOnVote(roomId)?roomMap.get(roomId).vote.vote(index):null;
	}
	synchronized public static VoteInfo[] endVote(int roomId){
		if(!isOnVote(roomId))return null;
		VoteInfo[] result = roomMap.get(roomId).vote.endVote();
		roomMap.get(roomId).vote = null;
		return result;
	}
	public static int fetchVoteNum(int roomId){
		if(!isOnVote(roomId))return -1;
		return roomMap.get(roomId).vote.getCurVoteNum();
	}
	public static String fetchResult(int roomId){
		if(!isRoomExist(roomId))return null;
		return roomMap.get(roomId).result;
	}
	public static void setResult(int roomId,String s){
		if(!isRoomExist(roomId))return;
		roomMap.get(roomId).result = s;		
	}
	private static Random random;
	private static Map<Integer,RoomInfo> roomMap;//roomId-roomInfo
	public static final String ROOM_ID = "room_id";
	public static final String PLAYER_ID = "player_id";
}
