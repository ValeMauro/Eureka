package parsing;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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

import init.Result;

public class PageParser {
	final String ROOT = "websites";

	public PageParser(){
		File root = new File(ROOT);
		// se la cartella non esiste la crea
		if (!root.exists()) {
		    try{
		        root.mkdir();
		    } 
		    catch(SecurityException e){
		        e.getMessage();
		    }        
		}

	}

	public void parsePage(LinkedList<Result> lista){

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
				res.setHtml(responseString);

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
				
				//salva la pagina su locale
				saveToLocal(res, responseString);

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
		Document doc = new Document("");

		//faccio il parsing dell'html
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

	private void saveToLocal(Result res, String responseString) {
		String folder_name = res.getSource().replace(" ", "_");
		File folder = new File(ROOT, folder_name);
		if (!folder.exists()){
//			try{
		        folder.mkdir();
//		    } 
//		    catch(SecurityException e){
//		        e.getMessage();
//		    }    
		}
		File[] folder_list = folder.listFiles();
		int counter = folder_list.length+1;
		String file_name = "";
		String url = res.getUrl();
//		String extention = url.substring(url.lastIndexOf('.'));
//		file_name = folder_name + Integer.toString(counter) + extention;
		if(!url.substring(url.lastIndexOf('.')).equals(".pdf")){
			file_name = folder_name + Integer.toString(counter) + ".html";
		} else file_name = folder_name + Integer.toString(counter) + ".pdf";
		
		//setto il nome del file in res
		res.setFileName(file_name);
		File file = new File(folder, file_name);
		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw= new BufferedWriter(fw);
			bw.write(responseString);
			bw.flush();
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
