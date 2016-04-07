package init;

import lucene.Repository;

public class Test {
//https://sites.google.com/a/uniroma1.it/achillepaolone/
	public static void main(String[] args) throws Exception {
		Repository rep= new Repository();
		rep.create();
		rep.searchSimilary("https://sites.google.com/a/uniroma1.it/achillepaolone/");
	}
	
}
