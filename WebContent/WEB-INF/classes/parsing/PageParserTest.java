package parsing;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import init.Result;

public class PageParserTest {

	public static void main(String[] args) throws IOException {
		LinkedList<Result> lista = new LinkedList<Result>();
		Result res = new Result();
		res.setUrl("http://jsoup.org/cookbook/introduction/parsing-a-document");
		lista.add(res);
		
		PageParser pp = new PageParser();
		
		pp.ParsePage(lista);
		
		
//		HttpGet request = new HttpGet("http://www.leopardi.it/");
//		HttpClient httpClient = HttpClientBuilder.create().build();
//		HttpResponse response = httpClient.execute(request);
//		Header[] headers = response.getAllHeaders();
//		HttpEntity entity = response.getEntity();
//		//trasformo la pagina html in stringa
//		String responseString = EntityUtils.toString(entity, "UTF-8");
//		for (Header header : headers) {
//			if(header.getName().equals("Last-Modified")){
//				System.out.println(header.getValue());
//				SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
//				Date date = new Date();
//				try {
//					date = sdf.parse(header.getValue());
//					System.out.println(date.toString());
//				} catch (ParseException e) {
//					System.out.println("Errore di parsing dell'header last-modified");
//				}
//				DateFormat sdf_out = new SimpleDateFormat("dd/MM/yyyy");
//				String date_out = sdf_out.format(date);
//				System.out.println(date_out);
//			}
//		}
	}

}
