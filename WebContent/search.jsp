<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" import="init.*" %>
<% 
	final String ROOT = "websites";
	String messaggio = "";
	Map<String,String> errori = (Map<String,String>)request.getAttribute("errori");
   	if (errori!= null) 
		messaggio = "Riempire la form correttamente";

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="stylesheet.css" type="text/css" />
<title>Eureka - search</title>
<% if(errori!=null) { 
	out.println("<h2>"+messaggio+"</h2>");
	} %>
</head>
<body>
<div id="maincontent">
	[LOGO]
	<form action="search.do" method="GET">
		<input class="bottone" size="50" type="text" name="query" /><input type="submit"
			value="Conferma" name="conferma" /><input type="reset" value="Reset" />
	</form>
	<% if (errori!=null && errori.get("query")!=null)
		    out.print("<span class=\"errore\">" +errori.get("query")+ "</span>");%>

	<div id="content">
		<%  LinkedList<Result> risultato = (LinkedList<Result>)request.getAttribute("risultato"); %>
	
		<% for(Result r : risultato ){ %>
		<div class="resultItem">	
		<% String folder = r.getSource().replace(" ", "_");
		%>
		<a href="<%= ROOT+"/"+folder+"/"+r.getFileName() %>"><%= r.getTitle() %></a>
		<br />
		<a href="<%= r.getUrl() %>" target="_blank"><%=r.getUrl() %></a>
		<p><%= r.getText().substring(0, 200) %></p>
		</div>
		<% } %>
	</div>
</div>
</body>
</html>