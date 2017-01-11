<%@ page import="hit.*" language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>抵抗组织</title>
	<!-- 引入 Bootstrap -->
	<link href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">    
</head>
<script>
function startVote(){
	window.location.href='StartVoteServlet'
}
function vote(index){
	window.location.href='VoteServlet?voteIndex='+index
}
function backToIndex(){
	window.location.href='index.html'
}
function endVote(){
	window.location.href='EndVoteServlet'
}
function fetchVoteNum(){
	var xmlhttp=new XMLHttpRequest();
	xmlhttp.onreadystatechange=function()
	{
		if(xmlhttp.readyState==4 && xmlhttp.status==200)
		{
			var s = xmlhttp.responseText.split("#");
			document.getElementById("voteNum").innerHTML='投票数:'+s[0];
			document.getElementById("playerNum").innerHTML='玩家人数:'+s[1];
			if(s[0]!=-1){//已经发起投票
				document.getElementById("startVote").innerHTML="重新发起投票";
				document.getElementById("endVote").disabled=false;
				document.getElementById("agree").disabled=false;
				document.getElementById("against").disabled=false;
				document.getElementById("result").innerHTML="投票结果:";				
			}
			else{
				document.getElementById("startVote").innerHTML="发起投票";
				document.getElementById("endVote").disabled=true;
				document.getElementById("agree").disabled=true;
				document.getElementById("against").disabled=true;	
				document.getElementById("result").innerHTML="投票结果:"+s[2];	
			}
		}
	}	
	xmlhttp.open("GET","VoteNumServlet",true);
	xmlhttp.send();  
	setTimeout("fetchVoteNum()",1000)
}
fetchVoteNum();

</script>
<style type="text/css">
#main {
	max-width:600px;
	margin:0 auto;
	position:relative
}
.simple {
  width: 500px;
  margin: 20px auto;
  -webkit-box-sizing: border-box;
     -moz-box-sizing: border-box;
          box-sizing: border-box;
}
.left-bottom-fixed{
  position: fixed;
  bottom: 0;
  left: 0;
  width:170px;
}
.left-fixed{
  position: fixed;
  left: 0;
  width:200px;
}
.right-fixed{
  position: fixed;
  right: 0;
  width:150px;
}
.right-bottom-fixed{
  position: fixed;
  bottom: 0;
  right: 0;
  width:170px;
}
</style>
<%
int roomId = (int)session.getAttribute(StaticInfo.ROOM_ID);
int playerId = (int)session.getAttribute(StaticInfo.PLAYER_ID);
int playerNum = StaticInfo.getPlayerNumByRoomId(roomId);
%>
<body>
<div id='main' class="simple">

 
  <div class="col-sm-4 left-fixed">
    <ul class="list-group">
      <li class="list-group-item">房间:<%=roomId%></li>
      <li class="list-group-item">玩家编号:<%=playerId%></li>
      <li class="list-group-item" id="playerNum">玩家人数:<%=playerNum%></li>
      <li class="list-group-item" id="voteNum">投票数:</li>
      <li class="list-group-item" id="result" >投票结果:
      <%if(request.getParameter("voteResult")!=null) {%><%=request.getParameter("voteResult")%><%} %>
      </li>
    </ul>
  </div><!-- /.col-sm-4 -->  
  

  <div class="right-fixed">	
    <button class="btn btn-lg btn-success" id="startVote" onclick="startVote()"><%if(StaticInfo.isOnVote(roomId)){ %>重新发起投票<%}else{ %>发起投票<% }%></button>
    <button class="btn btn-lg btn-danger" id="endVote" onclick="endVote()" <%if(!StaticInfo.isOnVote(roomId)){ %>disabled="true"<%} %>>结束投票</button>  
    <button class="btn btn-lg btn-warning" id="back" onclick="backToIndex()">返回</button>
  </div>
  <div>
    <button class="btn btn-lg btn-success left-bottom-fixed" id="agree" onclick="vote(0)" <%if(!StaticInfo.isOnVote(roomId)){ %>disabled="true"<%} %>>同意</button>
    <button class="btn btn-lg btn-danger right-bottom-fixed" id="against" onclick="vote(1)" <%if(!StaticInfo.isOnVote(roomId)){ %>disabled="true"<%} %>>反对</button>  
  </div>
  <div>  
  </div>
</div>
</body>
</html>