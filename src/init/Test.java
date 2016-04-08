package init;

import java.io.IOException;
import java.util.LinkedList;

import org.apache.lucene.queryparser.classic.ParseException;

import lucene.Repository;
import statistic.Performance;

public class Test {
//https://sites.google.com/a/uniroma1.it/achillepaolone/
	public static void main(String[] args) throws Exception {
//		order();
// 		statistic();
		similar();		
	}
	
	
		private static void similar() throws IOException, ParseException {
			Repository rep= new Repository();
			rep.create();
			LinkedList<Result> res=rep.searchLucene("Achille");
			LinkedList<Similar> sims= rep.similary(res, "Achille");
			for (Similar similar : sims) {
				System.out.println(similar.getName()+" "+similar.getGrade());
			}
	
		}


		private static void statistic() throws IOException, ParseException {
			Repository rep= new Repository();
			rep.create();
			LinkedList<Result> res=rep.searchLucene("Arbizzani");
			Performance.precision(res, "Arbizzani");
			res=rep.searchLucene("Eugenio");
			Performance.precision(res, "Eugenio");
			res=rep.searchLucene("Ugo");
			Performance.precision(res, "Ugo");
			res=rep.searchLucene("Paolone Achille");
			Performance.precision(res, "Paolone Achille");
		
	}


		private static void order() {
			LinkedList<Result> list= new LinkedList<Result>();
			Result r1= new Result();
			r1.setDate("02/11/1800");
			Result r2= new Result();
			Result r3= new Result();
			r3.setDate("ciao");
			Result r4= new Result();
			r4.setDate("05/12/1900");
			Result r5= new Result();
			r5.setDate("falso");
			list.add(r1);
			list.add(r2);
			list.add(r3);
			list.add(r4);
			list.add(r5);
			LinkedList<Result> res= Result.orderByDate(list);
			for (Result result : res) {
				System.out.println(result.getDate());
			}
		}
		
	
		
	
}
