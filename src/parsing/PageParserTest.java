package parsing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class PageParserTest {

	public static void main(String[] args) throws IOException {

		HttpGet request = new HttpGet("http://www.oracle.com/");
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpResponse response = httpClient.execute(request);			
		HttpEntity entity = response.getEntity();
		String responseString = EntityUtils.toString(entity, "UTF-8");
		System.out.println(responseString);
		
//		URL oracle;
//		oracle = new URL("http://www.oracle.com/");
//		try {
//			BufferedReader in = new BufferedReader(
//					new InputStreamReader(oracle.openStream()));
//
//			String inputLine;
//			while ((inputLine = in.readLine()) != null)
//				System.out.println(inputLine);
//			in.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
