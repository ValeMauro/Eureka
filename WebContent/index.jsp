<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<% 
	String messaggio = "";
	Map<String,String> errori = (Map<String,String>)request.getAttribute("errori");
   	if (errori!= null) 
		messaggio = "Riempire la form correttamente";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="stylesheet.css" type="text/css" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Eureka - Welcome!</title>
<% if(errori!=null) { 
	out.println("<h2>"+messaggio+"</h2>");
	} %>
</head>
<body>
<div id="indexContent">
<div id="indexHeader">
	<center><a href="index.jsp"><img src="images/logoEureka.png" /></a>
	<br /><br />
	<form action="search.do" method="GET">
		<input class="bottone" size="50" type="text" name="query"/>
		<input class="linkButton" type="hidden" value="ByRank" name="order" />
		<input type="submit" value="Conferma" name="conferma" /><input type="reset"	value="Reset" />
	</form>
	<p class="errori"><% if (errori!=null && errori.get("query")!=null)
		    out.print("<span class=\"errore\">" +errori.get("query")+ "</span>");%>
	</p></center>
</div>
</div>
</body>
</html>