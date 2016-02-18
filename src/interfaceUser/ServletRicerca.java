package interfaceUser;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mashape.unirest.http.exceptions.UnirestException;

import tokenization.DocParser;
import tokenization.TagParser;

public class ServletRicerca extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String query= ""+req.getParameter("query");
		char a34 = 34;//ascii34 è il carattere "
		char a13=13;//ascii13 è andare a capo

		List<DocParser> result= new LinkedList<DocParser>();
		TagParser tagParser=new TagParser();
		try {
			result = tagParser.parseQuery(query);
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		}
		
		
		// qui pensavo di mettere jsp

		//apertura html
		resp.getWriter().println(""+
				"<html>"+
				"<head>"+
				"<title>TestForm</title>"+
				"<style type="+a34+"text/css"+a34+">"+
				"<!--"+
				"#txt {"+
				"height: 70%;"+
				"width: 60%;"+
				"}"+
				"-->"+
				"</style>"+
				"</head>"+
				"<body>"+
				"<script type="+a34+"text/javascript"+a34+">"+

				//funzione javascript per eliminare attributi
				"function StringiEstendi(testoCv){"
				+ "if(testoCv.hidden==false){"
				+ "testoCv.hidden=true;"
				+ "}else{"
				+ "testoCv.hidden=false;"
				+ "}"
				+ "}"+
				
				"function SubmitSenzaSpazi(testo){"
					+"var query=testo;"
					+"if(query.charAt(0)=="+a34+""+a34+"){"
						+"alert("+a34+"Scrivi quello che mi vuoi chiedere"+a34+")"
					+"}else{"
					+"if(query.charAt(0)=="+a34+" "+a34+"){"
						+"alert("+a34+"Elimina gli spazi iniziali"+a34+")"
					+"}else{"
						+"document.campoDiRicerca.submit();"
					+"}"
					+"}"
				+"}"+
				
				"</script>"+
		//link per inserire curriculum
				"<DIV ALIGN="+a34+"right"+a34+">"+
					"<font face="+a34+"Times New Roman"+a34+" size="+a34+"3"+a34+"><dfn>"+
					"<a href="+a34+"inserisci_curricula.html"+a34+">inserisci il tuo cv</a> <br/>"+
				"</dfn></font></div>"+

		//barra per cercare in modo piu specifico per l'utente

				"<DIV ALIGN="+a34+"center"+a34+">"+
				"<form onSubmit='return false' name='campoDiRicerca' id='campoDiRicerca' action="+a34+"servletRicerca"+a34+" method="+a34+"get"+a34+">"+

					"<font face="+a34+"Times New Roman"+a34+" size="+a34+"4"+a34+" color="+a34+"blue"+a34+">"+
						"Chi Stai Cercando?"+
					"</font>"+

					" <input onSubmit='return false' type="+a34+"search"+a34+" value="+a34+query+a34+" name="+a34+"query"+a34+" >"+a13+

					"<input onSubmit='return false' onclick='SubmitSenzaSpazi(query.value)' type="+a34+"button"+a34+" value="+a34+"Cerca"+a34+" name="+a34+"Submit!"+a34+">"+a13+
				"</form>"+a13+
				"</div>"+

		// ricordare all'utente che cosa aveva cercato
				"<DIV ALIGN="+a34+"center"+a34+">"+a13+
					"<font face="+a34+"Times New Roman"+a34+" size="+a34+"2"+a34+" color="+a34+"red"+a34+">"+
						"<dfn> "+"Mi hai chiesto : " + a34 + query + a34+" </dfn>"+
					"</font>"+a13+
				"</div>");
				
		
		
		
		// mi risponde lorella cosa metto? Link?
//			TagParser parser= new TagParser();
//			List<DocParser> result = new LinkedList<DocParser>();
		//	result = parser.parseQuery(query);
			
			
			
		/* for each di tutti i documenti in result e 
		 * estrapolare una lista di tutti i con primi 40 caratteri per isarli come link
		 * che se uno ci clicca apre il curriculum
		 * 
		 * vedere iteratore in valeria
		 * 
		 * 
	*/
				
		resp.getWriter().println("<form id='MostraRisultati'>");
		
		String esempioCV="";
		String esempioCVLink="";
		int NumeroDelLink=0;
		
		for(DocParser dp: result){
			esempioCV=""+dp.getText();
			if(esempioCV.length()>23){
				esempioCVLink= ""+esempioCV.substring(0, 20)+"...";
			}else{
				esempioCVLink=esempioCV;
			}
			NumeroDelLink++;
		resp.getWriter().println(""+
				"<input type='button' onclick='StringiEstendi(testoCv"+NumeroDelLink+")' value='"+esempioCVLink+"'></br>"+
				"<DIV ALIGN="+a34+"center"+a34+">"+
				"<textarea hidden=true id='txt' name='testoCv"+NumeroDelLink+"' >"+ esempioCV +"</textarea>"
				+"</DIV>"
				);
		}
						
		
		
		
		resp.getWriter().println("</form>");		
		
		/*
		//forse cercavi
		resp.getWriter().println(""+
				"<font face="+a34+"Times New Roman"+a34+" size="+a34+"2"+a34+" color="+a34+"red"+a34+">"+
					"<dfn> <br/>Forse Cercavi <br/> <dfn> "+
				"</font>");

		
		///		mi viene passata la lista dei suggerimenti e li metto come bottoni
		///		 	esempio bottone
		String value="pippo";
		resp.getWriter().println(""+
				"<form action="+a34+"servletRicerca"+a34+" method="+a34+"get"+a34+">"+a13+
					"<input type="+a34+"submit"+a34+" value="+a34+value+a34+" name="+a34+"query"+a34+">"+a13+
				"</form>");

		*/
		
		
		
		//Chiusura Pagina
		resp.getWriter().println(""+
				"</body>"+
				"</html>");


	}
}
