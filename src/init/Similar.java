package init;

import java.util.LinkedList;

public class Similar {
	private String name;
	private int grade;
	
	public Similar(String n, int g) {
		name=n;
		grade=g;
	}
	
	public Similar() {
		name="";
		grade=0;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	
	public static LinkedList<Similar> create(LinkedList<String> n){
		LinkedList<Similar> simList= new LinkedList<Similar>();
		LinkedList<Similar> list= new LinkedList<Similar>();
		for (String str : n) {
			int i=count(str,n);
			Similar sim= new Similar(str,i);
			if(!isIn(sim,simList)) simList.add(sim);
		}
		list=orderList(simList);
		return list;
	}
	
	private static LinkedList<Similar> orderList(LinkedList<Similar> simList) {
		LinkedList<Similar> orderList= new LinkedList<Similar>();
		while(!simList.isEmpty()){
			Similar max= getMax(simList);
			orderList.add(max);
			simList.remove(max);
		}
		return orderList;
	}

	private static Similar getMax(LinkedList<Similar> simList) {
		Similar max= new Similar();
		for (Similar sim : simList) {
			if(max.getGrade()<sim.getGrade()) max=sim;
		}
		return max;
	}

	private static boolean isIn(Similar sim, LinkedList<Similar> simList) {
		for (Similar similar : simList) {
			if(similar.getName().equals(sim.getName())) return true;
		}
		return false;
	}

	private static int count(String string, LinkedList<String> list) {
		int i=0;
		for (String str : list) {
			if(str.equals(string)) i++;
		}
		return i;
	}

}
