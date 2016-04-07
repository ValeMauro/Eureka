package lucene;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoubleField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import init.Result;
import init.Similar;

public class Repository {
	private Directory index;
	private StandardAnalyzer analyzer;
	private IndexSearcher searcher;
	private IndexWriter w;
	private LinkedList<Result> results;
	private int hitsPerPage = 50; // Numero massimo di risultati restituiti dalla search
	private final String SOURCE = "source";
	private final String FILENAME = "filename";
	private final String HTML = "html";
	private final String TITLE = "title";
	private final String SUBTITLE = "subtitle";
	private final String TEXT = "text";
	private final String URL = "url";
	private final String DATE = "date";
	private final String RANK = "rank";
	private final int titleRank=20;
	private final int subtitleRank=10;
	private final double textRank=0.5;
	
	
	public Repository() {}
	
	//creazione directory di Lucene
	public void create(){
		analyzer = new StandardAnalyzer();
		results= new LinkedList<Result>();
		try {
			File f = new File("myLucene");
//			if (!f.exists()) {
//				index = FSDirectory.open(Paths.get("myLucene"));
//				//Creazione DataSet
//				//TODO
//			}
//
//			else
				index = FSDirectory.open(Paths.get("myLucene"));
		} catch (IOException e) {
			System.out.println("Errore apertura file myLucene");
		}
	}
	
	// aggiungi un cv a Lucene
	public void add(Result res) {
		try {
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			w = new IndexWriter(index, config);

			Document doc = new Document();
			doc.add(new TextField(SOURCE, res.getSource(), Field.Store.YES));
			doc.add(new TextField(FILENAME, res.getFileName(), Field.Store.YES));
			doc.add(new TextField(HTML, res.getHtml(), Field.Store.YES));
			doc.add(new TextField(TITLE, res.getTitle(), Field.Store.YES));
			doc.add(new TextField(SUBTITLE, res.getSubtitle(), Field.Store.YES));
			doc.add(new TextField(TEXT, res.getText(), Field.Store.YES));
			doc.add(new StringField(URL, res.getUrl(), Field.Store.YES));
			doc.add(new StringField(DATE, res.getDate(), Field.Store.YES));
			doc.add(new DoubleField(RANK, res.getRank(), Field.Store.YES));
			
			// aggiungo doc al file
			w.addDocument(doc);
			w.close();
		} catch (IOException e) {
			System.out.println("Errore writer");
		}
	}
	
	public void addAll(LinkedList<Result> list){
		for (Result result : list) {
			if(result!=null) add(result);
		}
	}
	
	//conta url associati ad una source
	
	public LinkedList<Result> searchSource(String name) throws ParseException{

		try {
			Query query = new QueryParser(SOURCE, analyzer).parse(name);
			
			// apro l'indice di lettura del file
			IndexReader reader = DirectoryReader.open(index);
			searcher = new IndexSearcher(reader);

			TopDocs docs = searcher.search(query, hitsPerPage);
			ScoreDoc[] hits = docs.scoreDocs;

			// Ricavo da ogni documento che matcha con la query
			for (int i = 0; i < hits.length; i++) {
				int docId = hits[i].doc;
				
				Document doc = searcher.doc(docId);
				addInResultList(doc,docId,name);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return results;

	}
	
	//cercare nel file di Lucene
	public LinkedList<Result> searchLucene(String querystr) throws IOException, ParseException {
		
		try {
			Query query = new QueryParser(TEXT, analyzer).parse(querystr);
			
			// apro l'indice di lettura del file
			IndexReader reader = DirectoryReader.open(index);
			searcher = new IndexSearcher(reader);

			TopDocs docs = searcher.search(query, hitsPerPage);
			ScoreDoc[] hits = docs.scoreDocs;

			// Ricavo da ogni documento che matcha con la query
			for (int i = 0; i < hits.length; i++) {
				int docId = hits[i].doc;
				
				Document doc = searcher.doc(docId);
				addInResultList(doc,docId,querystr);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return results;

	}

	
	private void addInResultList(Document doc, int docId, String querystr) {
		if(!isOnResult(docId)){
			Result res= new Result();
			res.setId(docId);
			res.setSource(doc.get(SOURCE));
			res.setFileName(doc.get(FILENAME));
			res.setHtml(doc.get(HTML));
			res.setTitle(doc.get(TITLE));
			res.setSubtitle(doc.get(SUBTITLE));
			res.setText(doc.get(TEXT));
			res.setUrl(doc.get(URL));
			res.setDate(doc.get(DATE));
			double rank= makeValue(res,querystr);
			res.setRank(rank);
			results.add(res);
		}	
	}

	private boolean isOnResult(int docId) {
		for (Result res : results) {
			if(res.getId()==docId) return true;
		}
		return false;
	}


	private double makeValue(Result res, String querystr) {
		double rank= 0;
		//scompongo querystr in un array di parole
		String [] tokens = querystr.split("[\\W]");
		//verifico il match di ogni parola in titolo, subtitolo e testo
        for(String s:tokens){
        	if(res.getTitle().contains(s)) rank=rank+titleRank;
    		if(res.getSubtitle().contains(s)) rank=rank+subtitleRank;
    		int count= counter(res.getText(),s);
    		rank= rank+(count*textRank);	
        }
		return rank;		
	}

	private int counter(String text, String word) {
		int counter=0;
		int length= word.length();
		for(int i=0; i<text.length()-length; i++){
			if((text.substring(i, i+length)).equalsIgnoreCase(word)) counter++;
		}
		return counter;
	}
	
	public LinkedList<Similar> similary(LinkedList<Result> res) throws IOException, ParseException{
		LinkedList<String> urls= new LinkedList<String>();
		LinkedList<String> temp= new LinkedList<String>();
		LinkedList<Similar> sim= new LinkedList<Similar>();
		for (Result r : res) {
			if(r!=null) 
				if(r.getUrl()!=null && !r.getUrl().equals("")) urls.add(r.getUrl());
		}
		temp= searchAllSimilary(urls);
		sim=Similar.create(temp);
		return sim;
	}

	private LinkedList<String> searchAllSimilary(LinkedList<String> urls)throws IOException, ParseException {
		LinkedList<String> names= new LinkedList<String>();
		for (String s : urls) {
			names.addAll(searchSimilary(s));
		}
		return names;
	}
	
	private LinkedList<String> searchSimilary(String querystr) throws IOException, ParseException {
		LinkedList<String> names= new LinkedList<String>();
		try {
			Query query = new QueryParser(URL, analyzer).parse(querystr);
			
			// apro l'indice di lettura del file
			IndexReader reader = DirectoryReader.open(index);
			searcher = new IndexSearcher(reader);

			TopDocs docs = searcher.search(query, hitsPerPage);
			ScoreDoc[] hits = docs.scoreDocs;

			// Ricavo da ogni documento che matcha con la query
			for (int i = 0; i < hits.length; i++) {
				int docId = hits[i].doc;
				Document doc = searcher.doc(docId);
				names.add(doc.get(SOURCE));
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	return names;
	}
}
