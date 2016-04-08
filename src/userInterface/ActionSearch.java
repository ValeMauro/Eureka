package userInterface;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.lucene.queryparser.classic.ParseException;

import lucene.Repository;
import init.Result;
import init.Similar;

public class ActionSearch {
	
	public String execute(HttpServletRequest request) throws ServletException{
		HelperSearch helper = new HelperSearch(request);
		if(helper.validate()){
			String query = request.getParameter("query");
			query = query.trim();
			String order;
			if(request.getParameter("order")!=null){
				order = request.getParameter("order");	
			} else order ="ByRank";
			Repository rep = new Repository();
			rep.create();
			LinkedList<Result> result = new LinkedList<Result>();
//			LinkedList<Result> resByDate = result;
			LinkedList<Similar> similari = new LinkedList<Similar>();
			try {
				//search in lucene della query
				result = rep.searchLucene(query);
//				resByDate = Result.orderByDate(resByDate);
				similari = rep.similary(result, query);
				
			} catch (IOException | ParseException e) {
				e.getMessage();
			}
			if(order!=null && order.equals("ByDate")){
				result = Result.orderByDate(result);
			}
			HttpSession session = request.getSession(false);
			if (session != null) {session.invalidate();}
			session = request.getSession();
			session.setAttribute("risultato", result);
//			session.setAttribute("resByDate", resByDate);
			session.setAttribute("similari", similari);
			session.setAttribute("query", query);
			return "successo";
		}
		
		return "errore";	
	}

}
