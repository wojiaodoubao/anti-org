package hit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnonymousVote{
	public static class VoteInfo{
		String item;
		int num;
		public VoteInfo(String item){
			this(item,0);
		}
		public VoteInfo(String item,int num){
			this.item = item;
			this.num = num;
		}
	}
	private VoteInfo[] vote;
	private int voteNum;
	private int curVoteNum;
	public AnonymousVote(String[] items,int voteNum){
		if(items==null)return;
		vote = new VoteInfo[items.length];
		for(int i=0;i<items.length;i++)
			vote[i] = new VoteInfo(items[i]);
		this.voteNum = voteNum;
		this.curVoteNum = 0;
	}
	public AnonymousVote(String[] items){
		this(items,-1);
	}	
	public AnonymousVote(List<String> items){
		this(items,-1);
	}
	public AnonymousVote(List<String> items,int voteNum){
		this((String[])items.toArray(),voteNum);
	}
	public void reset(){
		if(vote==null)return;
		for(int i=0;i<vote.length;i++){
			vote[i].num=0;
		}
		curVoteNum=0;
	}
	public int vote(int index){
		if(vote==null)return curVoteNum;
		if(index<0||index>vote.length-1)return curVoteNum;
		if(voteNum>=0&&curVoteNum>=voteNum)return curVoteNum;
		vote[index].num++;
		curVoteNum++;
		return curVoteNum;
	}
	public int vote(String item){
		if(vote==null)return curVoteNum;
		for(int i=0;i<vote.length;i++){
			if(vote[i].item.equals(item))return vote(i);
		}
		return curVoteNum;
	}
	public int getvoteNum(){
		return voteNum;
	}
	public int getCurVoteNum(){
		return curVoteNum;
	}
	private VoteInfo[] copyVote(){
		VoteInfo[] copy = new VoteInfo[vote.length];
		for(int i=0;i<vote.length;i++){
			copy[i] = new VoteInfo(vote[i].item,vote[i].num);
		}
		return copy;
	}
	public VoteInfo[] endVote(){
		VoteInfo[] result = getResult();
		reset();
		return result;
	}
	public VoteInfo[] getResult(){
		return copyVote();
	}
}