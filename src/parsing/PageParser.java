package parsing;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import init.Result;

public class PageParser {
	
public void ParsePage(LinkedList<Result> lista){
		
		for (Result res : lista) {
			try {
				//prende la pagina html dall'url dato
				HttpGet request = new HttpGet(res.getUrl());
				HttpClient httpClient = HttpClientBuilder.create().build();
				HttpResponse response = httpClient.execute(request);
				Header[] headers = response.getAllHeaders();
				HttpEntity entity = response.getEntity();
				//trasformo la pagina html in stringa
				String responseString = EntityUtils.toString(entity, "UTF-8");
				
				//setto il testo di res
				res.setText(responseString);
				
				//parso la data di ultima modifica a partire dall'header della risposta http
				for (Header header : headers) {
					if(header.getName().equals("Last-Modified")){
						SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
						Date date = new Date();
						try {
							date = sdf.parse(header.getValue());
						} catch (ParseException e) {
							System.out.println("Errore di parsing dell'header last-modified");
						}
						DateFormat sdf_out = new SimpleDateFormat("dd/MM/yyyy");
						String date_out = sdf_out.format(date);
						res.setDate(date_out);
					}

				}
				
				//parsa la pagina html in Result res
				parseHTML(res, responseString);
				
				//print di check
//				System.out.println("Title: " + res.getTitle());
//				System.out.println("SubTitles: " + res.getSubtitle());
//				System.out.println("Last-modified: " + res.getDate());
				
			} catch (ClientProtocolException e) {
				e.getMessage();
			} catch (UnsupportedOperationException e) {
				e.getMessage();
			} catch (IOException e) {
				e.getMessage();
			}
			
		}
		
	}

//dato un file, lo parsa in un oggetto Result
	private void parseHTML(Result res, String html) {
		// TODO sistemare bene in modo che compili il Result
				Document doc = new Document("");
				
				//faccio il parsing dell'html tramite JSoup
				doc = Jsoup.parse(html);
				
				//riempio result
				res.setTitle(doc.select("title").text());
				
				//TODO lasciamo gli h1/h2/h3 come subtitle? facciamo variabile apposta?
				Elements subtitles_el = doc.select("h1");
				subtitles_el.addAll(doc.select("h2"));
				subtitles_el.addAll(doc.select("h3"));
				String subtitles = "";
				for (Element e : subtitles_el){
					subtitles = subtitles+e.text()+" ";
				}
				res.setSubtitle(subtitles);
				
	}
}
