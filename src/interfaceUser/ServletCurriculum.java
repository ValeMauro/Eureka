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

public class ServletCurriculum extends HttpServlet {
	private List<String> l= new LinkedList<String>();
	private String cv="";
	
//	private class Curriculum{
//		private List<String> listaAttributi= new LinkedList<String>();
//		private String cv="";
//		
//		public Curriculum(){}
//		
//		public List<String> getListaAttributi(){
//			return listaAttributi;
//		}
//		
//		public String getCv(){
//			return cv;
//		}
//		
//		public void setListaAttributi(List<String> list){
//			listaAttributi= list;
//		}
//		
//		public void setCv(String s){
//			cv=s;
//		}
//		
//		
//		
//	}
//	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.cv = ""+req.getParameter("curriculum").toString();
		
		char a34 = 34;//serve per i doppi apici 34 in ascii ""
		int numero=0;
		//	DocParser CV =new DocParser();
		//	TagParser pippo=new TagParser();
		
		TagParser tagParser = new TagParser();
		List<String> listaAttributi = new LinkedList<String>();
	try {
			listaAttributi.addAll(tagParser.parseTags(this.cv));
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

		//apertura html
		resp.getWriter().println("<html>"+
				"<head>"+
				"<title>SistemaDiRicercaCV</title>"+
				"</head>"+
				"<body>"+

				"<script type="+a34+"text/javascript"+a34+">"+

				//funzione javascript per eliminare attributi
				"function EliminaValore(name,testo){"
				+ "name.hidden="+a34+"1"+a34+";"
				+ "testo.value='NonSonoUnTag'"
				+ "}"+
				

				//funzione javascript per aggiungere attributi
				"function AggiungiValore(){"+
				"var ValoreAttributo = document.PerAggiungere.ValoreDaAggiungere.value;"
				
				+"if(ValoreAttributo.charAt(0)=="+a34+""+a34+"){"
					+"alert("+a34+"Scrivi l'attributo che manca"+a34+")"
				+"}else{"
				+"if(ValoreAttributo.charAt(0)=="+a34+" "+a34+"){"
					+"alert("+a34+"Elimina gli spazi iniziali"+a34+")"
				+"}else{"
	//controllo che il valore non sia gia stato inserito			
					+ "var numeroInt = document.box_Attributi.NumeroDiOggettiInseritiDallUtente.value;"
					//controllo che gia non ci sia
					+"var cEDoppione=(1==0);"
					+"var stringa='';"
					+"var comando='';"

					+"for (var i = 0; i < numeroInt; i++) {"
					+"	comando='stringa=document.box_Attributi.AttributoTxt'+i+'.value';"
					+"	eval(comando);"
					+"	cEDoppione=cEDoppione || (stringa==ValoreAttributo);"
					+"}"

					+"if (cEDoppione){"
					+"	alert("+a34+"L' attributo è gia nella lista"+a34+");"
					+"}else{" 
		
								
				+ "var numero = document.box_Attributi.NumeroDiOggettiInseritiDallUtente.value;"
				+ "var box = document.getElementById('box_Attributi');"
				+ "var righe="+a34+""+a34+"+box.innerHTML;"

				+ "righe = righe + "+a34+" <input type='text' hidden=true name='AttributoTxt"+a34+"+numero+"+a34+"' id='AttributoTxt"+a34+"+numero+"+a34+"' value='"+a34+"+ValoreAttributo+"+a34+"' >"+a34+" ;"
				+ "righe = righe + "+a34+" <input type='button' onclick='EliminaValore(this,AttributoTxt"+a34+"+numero+"+a34+")' name='Attributo"+a34+"+numero+"+a34+"' value='"+a34+"+ValoreAttributo+"+a34+"' >"+a34+" ;"

				+ "box.innerHTML=righe;"
				+ "numero++;"
				+ "document.box_Attributi.NumeroDiOggettiInseritiDallUtente.value=''+numero;"
				+ "}"
				+"}"
				+"}"
				+"}"+
				//chiusura script java
				"</script>"+


				//messaggio iniziale blu
				"<div ALIGN="+a34+"center"+a34+">"+
				"<font face="+a34+"Times New Roman"+a34+" size="+a34+"5"+a34+" color="+a34+"blue"+a34+">"+
				"Aiutaci a trovarti:hai le seguenti caratteristiche?<br/>"+
				"</font>"+
				"</div>"+


				//istruzioni in rosso
				"<div ALIGN="+a34+"center"+a34+">"+
				"<font face="+a34+"Times New Roman"+a34+" size="+a34+"2"+a34+" color="+a34+"red"+a34+">"+
				"pensiamo che hai queste caratteristiche o conoscenze con le quali poi verrai ricercato<br/>"
				+ "se pensi di averle altre sotto c'è una barra per aggiungerle con un pulsante laterale "+a34+"+"+a34+" <br/>"
				+ "se invece pensi di non averle basta cliccarci sopra e spariranno"+
				"</font>"+
				"</div>"+

				//form o box dove andranno tutti gli attributi
				
				//pulsante di fine
		 "<form name='box_Attributi' id='box_Attributi' action="+a34+"servletCurriculum"+a34+" method="+a34+"post"+a34+">"		
		
		 +"<DIV ALIGN="+a34+"center"+a34+">"
		
		 
		//pulsante di fine
		+ "<br/><input type="+a34+"submit"+a34+" value="+a34+"Fine"+a34+" name="+a34+"Submit!"+a34+"><br/><br/>"
		+ "</DIV>");



		// invio la richiesta a lorella e lei mi risponde

		// creo check list con i valori che lorella mi da
		//in questo modo

		String ValoreAttributo="";
		for(String s: listaAttributi){
		
		ValoreAttributo=s;
		resp.getWriter().println(""
				+"<input type='text' hidden=true name='AttributoTxt"+numero+"' id='AttributoTxt"+numero+"' value='"+ValoreAttributo+"' >"
				+"<input type='button' onclick='EliminaValore(this,AttributoTxt"+numero+")' name='Attributo"+numero+"' value='"+ValoreAttributo+"' >"
				);
		numero++;	
		}

		
		
		//chiusura box per aggiungere gli attributi
		resp.getWriter().println(""
	//			//testo del curriculum nascosto
	//	 		+"<textarea onSubmit='return false' hidden =true name="+a34+"curriculum"+a34+" rows="+a34+"20"+a34+" cols="+a34+"20"+a34+" >"+Curriculum+"</textarea>"
		
		//text nascosta che contiene il numero di tutti gli attributi con nome Attributi+numero da 0 in poi che l'utente vuole per essere ricercato		
				+ "<input type='text' hidden='1' name='NumeroDiOggettiInseritiDallUtente' value="+a34+numero+a34+">"
		
				+ "</form>"
				//aggiunta di altri attributi
				+ "<div ALIGN="+a34+"center"+a34+">"
				+ "<form name='PerAggiungere' onSubmit='return false'>"
				+ "	<input type='text' id=ValoreDaAggiungere name='ValoreDaAggiungere' >"
				+ "	<input type='button' value='+' onclick='AggiungiValore()' onfocus='this.blur()'>"
				+ "</form></div>"+

		
	

		//chiusura html
		 "</body></html>");
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		char a34 = 34;//serve per i doppi apici 34 in ascii ""
		
		
		
	//curriculum
	//	String CurriculumPPP = ""+req.getParameter("curriculum");
	//	String Curriculum=CurriculumPPP.toString();
		
		
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
			tagParser.saveCV(this.cv,ListaAttributi);
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		//apertura html
			resp.getWriter().println("<html>"+
					"<head>"+
					"<title>SistemaDiRicercaCV</title>"+
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
