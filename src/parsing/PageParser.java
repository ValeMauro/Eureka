package parsing;

import java.io.IOException;
import java.util.LinkedList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import init.Result;

public class PageParser {
	
public void ParsePage(LinkedList<Result> lista){
		
		for (Result res : lista) {
			try {
				//prende la pagina html dall'url dato
				HttpGet request = new HttpGet(res.getUrl());
				HttpClient httpClient = HttpClientBuilder.create().build();
				HttpResponse response = httpClient.execute(request);			
				HttpEntity entity = response.getEntity();
				//trasformo la pagina html in stringa
				String responseString = EntityUtils.toString(entity, "UTF-8");
				
				//parsa la pagina html in Result res
				parse(res, responseString);
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

//dato un file, lo parsa in un oggetto Result
	private void parse(Result res, String html) {
		// TODO sistemare bene in modo che compili il Result
				Document doc = new Document("");
				
				//setto il testo di res
				res.setText(html);
				
				//faccio il parsing dell'html tramite JSoup
				doc = Jsoup.parse(html);
				
				//riempio result
				res.setTitle(doc.select("title").toString());
				

				/* ESEMPIO DI USO JSOUP
				try {
					doc = Jsoup.parse(file, "UTF-8", "");
				} catch (IOException e) {
					System.out.println("Errore di parsing JSoup");
				}
				Elements ids = doc.select("div[id^=desk] p");
				for (Element id : ids){
					System.out.println("\n"+id.text());
				}
				 */
	}
}
