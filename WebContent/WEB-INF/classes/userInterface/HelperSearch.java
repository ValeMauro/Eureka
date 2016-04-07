package userInterface;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;


public class HelperSearch {
	private String query;
	private HttpServletRequest request;
	private Map<String,String> errori;
	
	public HelperSearch(HttpServletRequest request) throws ServletException {
		this.query = request.getParameter("query");
		this.request = request;
		this.errori = new HashMap<String, String>();
	}
	
	public boolean validate(){
		boolean everythingOk = true;
		
		if(query==null || query.equals("")){
			everythingOk=false;
			errori.put("query", "campo obbligatorio");
			}
		
		request.setAttribute("errori", errori);
		return everythingOk;
	}

}
