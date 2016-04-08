package init;

import java.util.LinkedList;

import lucene.Repository;

public class Test {
//https://sites.google.com/a/uniroma1.it/achillepaolone/
	public static void main(String[] args) throws Exception {
		Repository rep= new Repository();
		rep.create();
//		rep.searchSimilary("https://sites.google.com/a/uniroma1.it/achillepaolone/");
		LinkedList<Similar> sims= rep.searchName("Amedeo");
		for (Similar similar : sims) {
			System.out.println(similar.getName()+" "+similar.getGrade());
		}
	}
	
}
