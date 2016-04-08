package lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.jsoup.parser.Parser;

import init.Result;
import init.Similar;

public class Repository {
	private Directory index;
	private StandardAnalyzer analyzer;
	private IndexSearcher searcher;
	private IndexWriter w;
	private LinkedList<Result> results;
	private int hitsPerPage = 50; // Numero massimo di risultati restituiti
									// dalla search
	private final String SOURCE = "source";
	private final String FILENAME = "filename";
	private final String HTML = "html";
	private final String TITLE = "title";
	private final String SUBTITLE = "subtitle";
	private final String TEXT = "text";
	private final String URL = "url";
	private final String DATE = "date";
	private final String RANK = "rank";
	private final int titleRank = 20;
	private final int subtitleRank = 10;
	private final double textRank = 0.5;

	public Repository() {
	}

	// creazione directory di Lucene
	public void create() {
		analyzer = new StandardAnalyzer();
		results = new LinkedList<Result>();
		try {
			File f = new File("myLucene");
			// if (!f.exists()) {
			// index = FSDirectory.open(Paths.get("myLucene"));
			// //Creazione DataSet
			// //TODO
			// }
			//
			// else
			index = FSDirectory.open(Paths.get("myLucene"));
		} catch (IOException e) {
			System.out.println("Errore apertura file myLucene");
		}
	}

	// aggiungi un Result a Lucene
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
			doc.add(new TextField(URL, res.getUrl(), Field.Store.YES));
			doc.add(new TextField(DATE, res.getDate(), Field.Store.YES));
			doc.add(new DoubleField(RANK, res.getRank(), Field.Store.YES));

			// aggiungo doc al file
			w.addDocument(doc);
			w.close();
		} catch (IOException e) {
			System.out.println("Errore writer");
		}
	}

	public void addAll(LinkedList<Result> list) {
		for (Result result : list) {
			if (result != null)
				add(result);
		}
	}

	// conta url associati ad una source

	public LinkedList<Result> searchSource(String querystr) throws IOException, ParseException {

		try {
			Query query = new QueryParser(SOURCE, analyzer).parse(querystr);

			// apro l'indice di lettura del file
			IndexReader reader = DirectoryReader.open(index);
			searcher = new IndexSearcher(reader);

			TopDocs docs = searcher.search(query, hitsPerPage);
			ScoreDoc[] hits = docs.scoreDocs;

			// Ricavo da ogni documento che matcha con la query
			for (int i = 0; i < hits.length; i++) {
				int docId = hits[i].doc;

				Document doc = searcher.doc(docId);
				addInResultList(doc, docId, querystr);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return results;

	}

	// cercare nel file di Lucene
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
				addInResultList(doc, docId, querystr);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return results;

	}

	private void addInResultList(Document doc, int docId, String querystr) {
		if (!isOnResult(docId)) {
			Result res = new Result();
			res.setId(docId);
			res.setSource(doc.get(SOURCE));
			res.setFileName(doc.get(FILENAME));
			res.setHtml(doc.get(HTML));
			res.setTitle(doc.get(TITLE));
			res.setSubtitle(doc.get(SUBTITLE));
			res.setText(doc.get(TEXT));
			res.setUrl(doc.get(URL));
			res.setDate(doc.get(DATE));
			double rank = makeValue(res, querystr);
			res.setRank(rank);
			results.add(res);
		}
	}

	private boolean isOnResult(int docId) {
		for (Result res : results) {
			if (res.getId() == docId)
				return true;
		}
		return false;
	}

	private double makeValue(Result res, String querystr) {
		double rank = 0;
		// scompongo querystr in un array di parole
		String[] tokens = querystr.split("[\\W]");
		// verifico il match di ogni parola in titolo, subtitolo e testo
		for (String s : tokens) {
			if (res.getTitle().contains(s))
				rank = rank + titleRank;
			if (res.getSubtitle().contains(s))
				rank = rank + subtitleRank;
			int count = counter(res.getText(), s);
			rank = rank + (count * textRank);
		}
		return rank;
	}

	private int counter(String text, String word) {
		int counter = 0;
		int length = word.length();
		for (int i = 0; i < text.length() - length; i++) {
			if ((text.substring(i, i + length)).equalsIgnoreCase(word))
				counter++;
		}
		return counter;
	}

	// query <- nome cercato dall'utente
	public LinkedList<Similar> similary(LinkedList<Result> res, String query) throws IOException, ParseException {
		LinkedList<String> urls = new LinkedList<String>();
		LinkedList<String> temp = new LinkedList<String>();
		LinkedList<Similar> sim = new LinkedList<Similar>();
		for (Result r : res) {
			if (r != null)
				if (r.getUrl() != null && !r.getUrl().equals(""))
					urls.add(r.getUrl());
		}
		// temp <- lista di nomi di Utenti con urls in comune con query
		temp = searchAllSimilary(urls);
		if (temp.isEmpty())
			sim = searchName(query);
		else
			sim = Similar.create(temp);
		return sim;
	}

	public LinkedList<Similar> searchName(String query) {
		String[] words = query.split("[\\W]");
		LinkedList<Similar> sims = new LinkedList<Similar>();
		LinkedList<String> names = new LinkedList<String>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("Nominativi.txt"));
			String s = br.readLine();
			while (s != null) {
				for (int i = 0; i < words.length; i++) {
					if (s.contains(words[i]) && !names.contains(s) && !s.equals(query) && !s.equals(query + " "))
						names.add(s);
				}
				s = br.readLine();
			}
			if (!names.isEmpty()){
				for (String str : names) {
					Similar sim = new Similar(str, 0);
					sims.add(sim);
				}
			}
			else sims= searchRandom(query);
		} catch (IOException e) {
			System.out.println("Errore lettura file Nominativi");
		}
		return sims;
	}

	private LinkedList<Similar> searchRandom(String query) {
		LinkedList<Similar> sims = new LinkedList<Similar>();
		LinkedList<String> names = new LinkedList<String>();
		BufferedReader br;
		try {
			
			br = new BufferedReader(new FileReader("Nominativi.txt"));
			String s= br.readLine();
			while (s != null) {
				//Cerca tutti i nomi con la stessa iniziale di query
				if(s.substring(0,1).equals(query.substring(0, 1))){
					names.add(s);
				}
				s = br.readLine();
			}
			for (String str : names) {
				Similar sim = new Similar(str, 0);
				sims.add(sim);
			}
		} catch (IOException e) {
			System.out.println("Errore lettura file Nominativi");
		}
		return sims;
	}

	private LinkedList<String> searchAllSimilary(LinkedList<String> urls) throws IOException, ParseException {
		LinkedList<String> names = new LinkedList<String>();
		for (String s : urls) {
			names.addAll(searchSimilary(s));
		}
		return names;
	}

	public LinkedList<String> searchSimilary(String querystr) throws IOException, ParseException {
		LinkedList<String> names = new LinkedList<String>();
		try {
//			Query query = new QueryParser(URL, analyzer).parse(querystr);
			Query query =new QueryParser(URL, analyzer).parse(QueryParser.escape(querystr));
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
