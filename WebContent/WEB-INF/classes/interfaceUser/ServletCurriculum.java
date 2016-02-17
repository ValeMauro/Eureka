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
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String Curriculum = ""+req.getParameter("curriculum");
		char a34 = 34;//serve per i doppi apici 34 in ascii ""
		int numero=0;
		//	DocParser CV =new DocParser();
		//	TagParser pippo=new TagParser();
		
		TagParser tagParser = new TagParser();
		List<String> listaAttributi = new LinkedList<String>();
	try {
			listaAttributi.addAll(tagParser.parseTags(Curriculum));
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
	+"	alert('L attributo è gia nella lista');"
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
		 "<form name='box_Attributi' id='box_Attributi' action="+a34+"servletFinale"+a34+" method="+a34+"get"+a34+">"		
		
		 +"<DIV ALIGN="+a34+"center"+a34+">"
		
		 
		//pulsante di fine
		+ "<input type="+a34+"submit"+a34+" value="+a34+"fine"+a34+" name="+a34+"Submit!"+a34+">"
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
				//testo del curriculum nascosto
		 		+"<textarea onSubmit='return false' hidden =true name="+a34+"curriculum"+a34+" rows="+a34+"20"+a34+" cols="+a34+"20"+a34+" >"+Curriculum+"</textarea>"
		
		//text nascosta che contiene il numero di tutti gli attributi con nome Attributi+numero da 0 in poi che l'utente vuole per essere ricercato		
				+ "<input type='text' hidden='1' name='NumeroDiOggettiInseritiDallUtente' hidden="+a34+"1"+a34+" value="+a34+numero+a34+">"
		
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
}
