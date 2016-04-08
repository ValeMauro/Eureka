package init;

import java.util.LinkedList;

import lucene.Repository;

public class Test {
//https://sites.google.com/a/uniroma1.it/achillepaolone/
	public static void main(String[] args) throws Exception {
		Repository rep= new Repository();
		rep.create();
//		rep.searchSimilary("https://sites.google.com/a/uniroma1.it/achillepaolone/");
//		LinkedList<Similar> sims= rep.searchName("Amedeo");
//		for (Similar similar : sims) {
//			System.out.println(similar.getName()+" "+similar.getGrade());
//		}
//		order();
		LinkedList<Result> res=rep.searchLucene("Achille");
		System.out.println(res.size());
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
