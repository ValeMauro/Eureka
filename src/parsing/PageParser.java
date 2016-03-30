package parsing;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

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

				//salva la pagina in locale
				savePageToLocal(responseString);
				//pulisce l'html della pagina e lo salva in res
				String cleanText = cleanHtmlText(responseString);
				res.setText(cleanText);
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

	//metodo per salvare la pagina html in locale
	private void savePageToLocal(String responseString) {
		// TODO Auto-generated method stub
		
	}

	//metodo per pulire la pagina html
	private String cleanHtmlText(String string) {
		// TODO Auto-generated method stub
		return null;
	}

}
