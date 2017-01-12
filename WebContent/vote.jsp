<%@ page import="hit.*" language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>抵抗组织</title>
	<!-- 引入 Bootstrap -->
	<link href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
	<script language="JavaScript" src="vote.js">
	</script>	    
</head>
<script>
function startVote(){
	var xmlhttp=new XMLHttpRequest();
	xmlhttp.open("GET","VoteServlet?type=start",true);
	xmlhttp.send();  	
}
function vote(index){
	var xmlhttp=new XMLHttpRequest();
	xmlhttp.open("GET",'VoteServlet?type=vote&voteIndex='+index,true);
	xmlhttp.send(); 			
	document.getElementById("infobar").innerHTML = '您投了一票';
	setTimeout(function(){document.getElementById("infobar").innerHTML = '抵抗组织';},1000);
}
function backToIndex(){
	window.location.href='index.html'
}
function endVote(){
	var xmlhttp=new XMLHttpRequest();
	xmlhttp.open("GET",'VoteServlet?type=end',true);
	xmlhttp.send(); 	
}
function changeTask(id){
	var xmlhttp=new XMLHttpRequest();
	xmlhttp.open("GET",'VoteServlet?type=task&id='+id,true);
	xmlhttp.send(); 	
}
function colorClass(id){	
	if(id==1)return 'red';
	else if(id==2)return 'blue';
	else return 'grey';
}
function showIdentity(){
	document.getElementById("infobar").innerHTML = '您的身份是:<b>'+iden+'</b>';
	setTimeout(function(){document.getElementById("infobar").innerHTML = '抵抗组织';},1000);	
}
function shuffleIdentity(){
	var xmlhttp=new XMLHttpRequest();
	xmlhttp.open("GET",'VoteServlet?type=shuffleIdentity',true);
	xmlhttp.send(); 
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
			iden = s[3];//身份
			var colors = s[4].split(":");//颜色
			for(i=0;i<colors.length;i++){
				document.getElementById("task"+i).style="background-color:"+colorClass(colors[i]);
			}
		}
	}	
	xmlhttp.open("GET","VoteServlet",true);
	xmlhttp.send();  
	setTimeout("fetchVoteNum()",200);
}
fetchVoteNum();
var iden='未知';//身份
</script>
<style type="text/css">
html,body{
  margin:0;
  padding:0;
  height:100%;
  width:100%;
}
#container{
  min-height:100%;
  height:auto!important;
  height:100%;
  position:relative;
}
#infobar{
  width:100%;
  height:10%;
  margin:0px;
  text-align:center; 
  font-weight:bold;
  font-size:17px;
}
#page{
  width:100%;
  height:40%;
  margin:0 auto;
}
#roominfo{
  width:50%;
  height:100%;
  float:left;  
  font-size:17px;  
}
.group{
  width:100%;
  height:100%;
}
.item{
  width:100%;
  height:20%;
  float:left; 
}
#buttonsection{
  width:50%;
  height:100%;  
  float:left;   
}
.button{
  width:50%;
  height:20%;
  float:left;
}
#tasksection{
  width:100%;
  height:40%;
  margin:0 auto;
}
.task{
  width:20%;
  height:50px;
  float:left;
  background-color:grey;  
}
#footer{
  width: 100%;
  height: 20%;
}
.votebutton{
  width:50%;
  height:100%;
  float:left;  
}
</style>
<%
int roomId = (int)session.getAttribute(StaticInfo.ROOM_ID);
int playerId = (int)session.getAttribute(StaticInfo.PLAYER_ID);
int playerNum = StaticInfo.getPlayerNumByRoomId(roomId);
%>
<body>
<div id='container'>
  <div class="alert alert-success" role="alert" id="infobar">抵抗组织
  </div><!-- 信息栏 -->

  <div id="page">
    <div class="col-sm-4" id="roominfo">
      <ul class="list-group group">
        <li class="list-group-item item">房间:<%=roomId%></li>
        <li class="list-group-item item">玩家编号:<%=playerId%></li>
        <li class="list-group-item item" id="playerNum">玩家人数:<%=playerNum%></li>
        <li class="list-group-item item" id="voteNum">投票数:</li>
        <li class="list-group-item item" id="result" >投票结果:
        <%if(request.getParameter("voteResult")!=null) {%><%=request.getParameter("voteResult")%><%} %>
        </li>
      </ul>
    </div><!-- 房间信息 -->  
    <div class="buttonsection">	
      <button class="btn btn-lg btn-success button" id="startVote" onclick="startVote()"><%if(StaticInfo.isOnVote(roomId)){ %>重新发起投票<%}else{ %>发起投票<% }%></button>
      <button class="btn btn-lg btn-danger button" id="endVote" onclick="endVote()" <%if(!StaticInfo.isOnVote(roomId)){ %>disabled="disabled"<%} %>>结束投票</button>  
      <button class="btn btn-lg btn-warning button" onclick="shuffleIdentity()" >混洗身份</button>  	
      <button class="btn btn-lg btn-danger button" onclick="showIdentity()" >查看身份</button>    
      <button class="btn btn-lg btn-warning button" id="back" onclick="backToIndex()">返回</button>
    </div><!-- 投票发起与结束 -->
  </div>  
  
  <div id="tasksection">
    <div class="taskBlock task" id="task0" onclick="changeTask(0)" style="background-color:grey"></div>
    <div class="taskBlock task" id="task1" onclick="changeTask(1)" style="background-color:grey"></div>
    <div class="taskBlock task" id="task2" onclick="changeTask(2)" style="background-color:grey"></div>
    <div class="taskBlock task" id="task3" onclick="changeTask(3)" style="background-color:grey"></div>
    <div class="taskBlock task" id="task4" onclick="changeTask(4)" style="background-color:grey"></div>  
  </div><!-- 任务结果 -->
  
  <div id="footer">
    <button class="btn btn-lg btn-success votebutton" id="agree" onclick="vote(0)" <%if(!StaticInfo.isOnVote(roomId)){ %>disabled="disabled"<%} %>>同意</button>
    <button class="btn btn-lg btn-danger votebutton" id="against" onclick="vote(1)" <%if(!StaticInfo.isOnVote(roomId)){ %>disabled="disabled"<%} %>>反对</button>  
  </div><!-- 投票按钮 -->
</div>
</body>
</html>