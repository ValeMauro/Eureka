package statistic;

import java.util.LinkedList;

import org.apache.lucene.queryparser.classic.ParseException;

import init.Result;
import lucene.Repository;

public class Performance {
	
	public void precision (LinkedList<Result> res, String name) throws ParseException{
		//Precision: |doc attinenti|&&|doc recuperati| on doc recuperati
		//Recall: |doc attinenti|&&|doc recuperati| on doc attinenti
		Repository r= new Repository();
		LinkedList<Result> resSource= r.searchSource(name);
		int docAtt= resSource.size();
		int docRec=res.size();
		if(docAtt==0 || docRec==0) {
			System.out.println("Errore liste vuote");
			return;
		}
		int docAttRec= count(resSource,res);
		double precision=docAttRec/docRec;
		double recall=docAttRec/docAtt;
		System.out.println("Precision: "+precision+ "    Recall: "+recall);
	}

	private int count(LinkedList<Result> resSource, LinkedList<Result> res) {
		int i=0;
		for (Result result : resSource) {
			if(isInList(result,res)) i++;
		}
		return i;
	}

	private boolean isInList(Result r,LinkedList<Result> res) {
		boolean b=false;
		for (Result result : res) {
			if((r.getUrl()).equals(result.getUrl())) b=true;
		}
		return b;
	}

}
