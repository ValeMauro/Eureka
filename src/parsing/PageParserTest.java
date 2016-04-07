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
import lucene.Repository;

public class PageParserTest {

	public static void main(String[] args) throws IOException {
		LinkedList<Result> lista = UrlFile.readFileProva();
		PageParser pp = new PageParser();
		Repository rep = new Repository();
		rep.create();
		pp.parsePage(lista);
		rep.addAll(lista);
		
		
		
//		LinkedList<Result> lista = new LinkedList<Result>();
//		Result res = new Result();
//		res.setUrl("https://sites.google.com/a/uniroma1.it/achillepaolone/");
//		res.setSource("Paolone Achille");
//		lista.add(res);
//		
//		PageParser pp = new PageParser();
//		
//		pp.ParsePage(lista);
//		
//		
	}

}
