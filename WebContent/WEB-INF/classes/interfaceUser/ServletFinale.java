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

public class ServletFinale extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		char a34 = 34;//serve per i doppi apici 34 in ascii ""
		
	
		
		
	//curriculum
		String Curriculum = ""+req.getParameter("curriculum");
		
		
	//creo la lista di attributi
		List<String> ListaAttributi= new LinkedList<String>();
		String stringaNumeroAttributi= req.getParameter("NumeroDiOggettiInseritiDallUtente");
		//recupero ogni attributoDellaPersna: ogni parametro si chiama 'Attributo"+numero+"'
		int numeroAttributi = Integer.parseInt(stringaNumeroAttributi);
		String Attributo="";
		String NonSonoUnTag="NonSonoUnTag";
		for (int i = 0; i < numeroAttributi; i++) {
			Attributo =""+ req.getParameter("AttributoTxt"+i);
			if (!(Attributo.equals(NonSonoUnTag))) {
				ListaAttributi.add(Attributo);
				}
		}
		
	 	
	//salvataggio curriculum	
		TagParser tagParser = new TagParser();
		/* 
		try {
			tagParser.parseTags(Curriculum);
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		 
		 try {
			tagParser.saveCV(Curriculum,ListaAttributi);
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		

		//apertura html
		resp.getWriter().println("<html>"+
				"<head>"+
				"<title>TestForm</title>"+
				"</head>"+
				"<body>"+

				//link per tornare alla home page
				"<DIV ALIGN="+a34+"right"+a34+">"+
					"<font face="+a34+"Times New Roman"+a34+" size="+a34+"3"+a34+"><dfn>"+
					"<a href="+a34+"index.html"+a34+">ritorna alla home</a> <br/>"+
				"</dfn></font></div>"+

				//messaggio iniziale blu
				"<div style="+a34+"position: absolute; width: 500px; height: 200px; left: 50%; top: 50%; margin-left: -250px; margin-top: -100px; text-align: center;"+a34+">"+
				"<font face="+a34+"Times New Roman"+a34+" size="+a34+"5"+a34+" color="+a34+"blue"+a34+">"+
				"Congratulazioni hai terminato!<br/>"+
				"</font>"+


				//istruzioni in rosso
				"<font face="+a34+"Times New Roman"+a34+" size="+a34+"3"+a34+" color="+a34+"red"+a34+">"+
				"per tonare alla home clicca qua sotto o in alto a destra <br/>"+
				"</font>"+
				
				//altro link alla homepage
				"<font face="+a34+"Times New Roman"+a34+" size="+a34+"3"+a34+"><dfn>"+
				"<a href="+a34+"index.html"+a34+">ritorna alla home</a> <br/>"+
				"</dfn></font>"+
				"</div>"+
				
				//chiusura html
				"</body></html>");
	}
}
