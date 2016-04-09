<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" import="init.*" %>
<% final String ROOT = "websites"; %>
<%  LinkedList<Result> risultato = (LinkedList<Result>)session.getAttribute("risultato"); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="stylesheet.css" type="text/css" />
<title>Eureka - search</title>
</head>
<body>
<div id="content">
	<div id="header">
	<center><a href="index.jsp"><img src="images/logoEureka.png" /></a></center>
	<form action="search.do" method="GET">
		<input class="bottone" size="50" type="text" name="query" value="" />
		<input class="linkButton" type="hidden" value="ByRank" name="order" /><input type="submit"
			value="Conferma" name="conferma" /><input type="reset" value="Reset" />
	</form>
	</div>
	<div class="orderOptions">
			<form action="search.do" method="GET">
			<input class="linkButton" type="hidden" value="<%= (String)session.getAttribute("query") %>" name="query" />
			<input class="linkButton" type="hidden" value="ByRank" name="order" />
			<input class="linkButton" type="submit" value="Più rilevanti" name="conferma" />
			</form>
			<form action="search.do" method="GET">
			<input class="linkButton" type="hidden" value="<%= (String)session.getAttribute("query") %>" name="query" />
			<input class="linkButton" type="hidden" value="ByDate" name="order" />
			<input class="linkButton" type="submit" value="Aggiornati più recentemente" name="conferma" />
			</form>
		</div>
			<div id="sidebar">
		<p class="titleSidebar">Forse ti interessa anche...</p>
			<ul>
				<% LinkedList<Similar> similari = (LinkedList<Similar>)session.getAttribute("similari"); %>
				<% for(Similar s : similari){ %>
				<li>
				<form action="search.do" method="GET">
				<input class="linkButton" type="hidden" value="<%= s.getName()%>" name="query" />
				<input class="linkButton" type="submit" value="<%= s.getName() %> (<%= s.getGrade() %>)" name="conferma" />
				</form>
				</li>
					<% } %>
			</ul>
		</div>
	<div id="maincontent">		
		<% for(Result r : risultato ){ %>
		<div id="resultItem">	
			<% String folder = r.getSource().replace(" ", "_");
			%>
		<p class="itemTitle">
		<a href="<%= ROOT+"/"+folder+"/"+r.getFileName() %>">
			<% String title = r.getTitle();
				if(title.length()>70){title = title.substring(0,70) + "...";} %>
			<%= title %></a></p>
		
		<p class="itemSubtitle"><%= r.getSubtitle() %></p>
		
			<% String testo = r.getText();
			if(testo.length()>=400){testo = testo.substring(0,400)+"...";}%>
		<p><%= testo %></p>
		
		<a class="itemLink" href="<%= r.getUrl() %>" target="_blank">
		<% String url = r.getUrl();
			if(url.length()>80){url = url.substring(0,80) + "...";} %>
		<%= url %></a>
		
		<p class="itemDate"><%= r.getDate() %></p>
		</div>
		<% } %>
	</div>
</div>
</body>
</html>