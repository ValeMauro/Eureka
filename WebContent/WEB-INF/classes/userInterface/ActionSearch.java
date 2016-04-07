package userInterface;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.lucene.queryparser.classic.ParseException;

import lucene.Repository;
import init.Result;

public class ActionSearch {
	
	public String execute(HttpServletRequest request) throws ServletException{
		HelperSearch helper = new HelperSearch(request);
		if(helper.validate()){
			String query = request.getParameter("query");
			Repository rep = new Repository();
			rep.create();
			LinkedList<Result> result = new LinkedList<Result>();
			//System.out.println(query);
			try {
				//search in lucene della query
				result = rep.searchLucene(query);
			} catch (IOException | ParseException e) {
				e.getMessage();
			}
			request.setAttribute("risultato", result);
			return "successo";
		}
		
		return "errore";	
	}

}
