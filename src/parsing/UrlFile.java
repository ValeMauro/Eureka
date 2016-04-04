package parsing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;

import init.Result;

public class UrlFile {
	
	public static void writeFile (String name, JSONArray results){
		try {
			FileWriter fw= new FileWriter("UrlBing", true);
			BufferedWriter bw= new BufferedWriter(fw);
			String nameDir= name.replace(" ", "_");
			bw.write("mkdir "+nameDir+"\n");
			bw.write("cd "+nameDir+"\n");
			int resultsLength = results.length();
			for (int i = 0; i < resultsLength && i < 50; i++) {
				JSONObject aResult = results.getJSONObject(i);
			 	if(aResult.get("Url").toString()!=null) 
			 		bw.write("wget "+aResult.get("Url").toString()+"\n");
			}
			bw.write("cd .."+"\n");
			bw.flush();
			bw.close();		
		} catch (IOException e) {
			System.out.println("Errore scrittura file UrlBing");
			}
	}
	

	public static LinkedList<Result> readFile() throws FileNotFoundException {
		LinkedList<Result> res = new LinkedList<Result>();
		String name="";
		try {
			BufferedReader br = new BufferedReader(new FileReader("UrlBing"));
			String s= br.readLine();
			while(s!=null){
				if(s.length()>=5){
					String init=s.substring(0,5);
					if(init.equals("mkdir")) {
						String temp=s.substring(6, s.length());
						name=temp.replace("_", " ");
					}
					else{
						if(init.equals("wget ")){ 
							Result r= new Result();
							r.setSource(name);
							String temp=s.substring(5, s.length());
							r.setUrl(temp);
							res.add(r);
						}
					}
				}
				
				s= br.readLine();
			}
			br.close();
		} catch (IOException e) {
			System.out.println("Errore apertura file UrlBing");
		}
		return res;	
	}
	
	public static LinkedList<Result> readFileProva() throws FileNotFoundException {
		LinkedList<Result> res = new LinkedList<Result>();
		String name="";
		try {
			BufferedReader br = new BufferedReader(new FileReader("UrlBingProva"));
			String s= br.readLine();
			while(s!=null){
				if(s.length()>=5){
					String init=s.substring(0,5);
					if(init.equals("mkdir")) {
						String temp=s.substring(6, s.length());
						name=temp.replace("_", " ");
					}
					else{
						if(init.equals("wget ")){ 
							Result r= new Result();
							r.setSource(name);
							String temp=s.substring(5, s.length());
							r.setUrl(temp);
							res.add(r);
						}
					}
				}
				
				s= br.readLine();
			}
			br.close();
		} catch (IOException e) {
			System.out.println("Errore apertura file UrlBing");
		}
		return res;	
	}
}
