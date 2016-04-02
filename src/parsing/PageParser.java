package parsing;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import init.Result;

public class PageParser {
	private final String PATH = "websites";
	private LinkedList<Result> resultSet;

	public PageParser(){
		resultSet = new LinkedList<Result>();
	}

	//carica i file della cartella websites e li parsa usando la lib JSoup
	public LinkedList<Result> ParsePages(){

		File input = new File(PATH);
		File[] st = input.listFiles();
		for (int i = 0; i < st.length; i++) {
			if(st[i].isDirectory()){
				File file = new File(PATH, st[i].getName());
				File[] fileList = file.listFiles();
				for(int j = 0; j < fileList.length; j++){
					if(fileList[i].isFile()){
						//System.out.println(fileList[i].getName());
						resultSet.add(parse(st[i]));
					}
				}
			}
		}
		return resultSet;
	}

	//dato un file, lo parsa in un oggetto Result
	private Result parse(File file) {
		// TODO sistemare bene in modo che compili il Result
		Document doc = new Document("");

		try {
			doc = Jsoup.parse(file, "UTF-8", "");
		} catch (IOException e) {
			System.out.println("Errore di parsing JSoup");
		}
		Elements ids = doc.select("div[id^=desk] p");
		for (Element id : ids){
			System.out.println("\n"+id.text());
		}

		return null;
	}
}
