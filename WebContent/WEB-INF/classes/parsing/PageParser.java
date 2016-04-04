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
				
				//setto il testo html di res
				//TODO modificare con setHTML e togliere dal commento
				//res.setText(responseString);
				
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
				
				//salva la pagina html su locale
				saveToLocal(responseString);
				
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
				Document doc = new Document("");
				
				//faccio il parsing dell'html tramite JSoup
				doc = Jsoup.parse(html);
				
				//setto il title di res
				if(doc.title()!=null){
					res.setTitle(doc.title());
				}
				else res.setTitle("");
				
				//text di res viene settato col contenuto di tutti i tag <p>
				//TODO fare le prove con i div?
//				Elements text_el = doc.select("p");
//				String text = "";
//				if(text_el!=null){
//					for (Element e : text_el){
//						text = text+e.text()+ " ";
//					}
//				}
//				res.setText(text);
				Element text_el = doc.select("body").first();
				if(text_el!=null){
					res.setText(text_el.text());
				}
				else res.setText("");
				
				//il primo h1 della pagina è settato come subtitle di res
				Element subtitle_el = doc.select("h1").first();
				if(subtitle_el!=null){
					res.setSubtitle(subtitle_el.text());
				}
				else res.setSubtitle("");
	}
	
	private void saveToLocal(String responseString) {
		// TODO Auto-generated method stub
		//crearsi una variabile string (di classe?) per il nome del file html
	}
}
