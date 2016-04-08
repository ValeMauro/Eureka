package statistic;

import java.io.IOException;
import java.util.LinkedList;

import org.apache.lucene.queryparser.classic.ParseException;

import init.Result;
import lucene.Repository;

public class Performance {
	
	public static void precision (LinkedList<Result> res, String name) throws ParseException, IOException{
		//Precision: |doc attinenti|&&|doc recuperati| on doc recuperati
		//Recall: |doc attinenti|&&|doc recuperati| on doc attinenti
		Repository r= new Repository();
		r.create();
		LinkedList<Result> resSource= r.searchSource(name);
		int docAtt= resSource.size();
		int docRec=res.size();
		if(docAtt==0 || docRec==0) {
			System.out.println("Errore liste vuote");
			return;
		}
		int docAttRec= count(resSource,res);
		double precision=(double)docAttRec/docRec;
		double recall=(double)docAttRec/docAtt;
		System.out.println("\n"+"Precision: "+docAttRec+"/"+docRec+" = "+precision
				+ "    Recall: "+docAttRec+"/"+docAtt+" = "+recall);
	}

	private static int count(LinkedList<Result> resSource, LinkedList<Result> res) {
		int i=0;
		for (Result result : resSource) {
			if(isInList(result,res)) i++;
		}
		return i;
	}

	private static boolean isInList(Result r,LinkedList<Result> res) {
		boolean b=false;
		for (Result result : res) {
			if((r.getUrl()).equals(result.getUrl())) b=true;
		}
		return b;
	}

}
