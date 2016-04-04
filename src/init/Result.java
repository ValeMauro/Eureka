package init;

import java.util.Iterator;
import java.util.LinkedList;

public class Result {
	private int id;
	private String source;
	private String fileName;
	private String title;
	private String subtitle;
	private String text;
	private String url;
	private String date;  //format "gg/mm/aaaa"
	private double rank;
	
	public Result(){}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getSubtitle() {
		return subtitle;
	}
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public double getRank() {
		return rank;
	}
	public void setRank(double rank) {
		this.rank = rank;
	}
	
	public static LinkedList<Result> orderByRank(LinkedList<Result> list){
		LinkedList<Result> res= new LinkedList<Result>();
		while(!list.isEmpty()){
			res.add(maxRank(list));
		}
		return res;
	}
	
	private static Result maxRank(LinkedList<Result> list) {
		Result max= new Result();
		Iterator<Result> it= list.iterator();
		max= it.next();
		while(it.hasNext()){
			Result temp=it.next();
			if(max.getRank() < temp.getRank()) max=temp;
		}
		list.remove(max);
		return max;
	}

	public static LinkedList<Result> orderByDate(LinkedList<Result> list){
		LinkedList<Result> res= new LinkedList<Result>();
		while(!list.isEmpty()){
			res.add(moreLastDate(list));
		}
		return res;
	}
	
	private static Result moreLastDate(LinkedList<Result> list) {
		Result max= new Result();
		Iterator<Result> it= list.iterator();
		max= it.next();
		while(it.hasNext()){
			Result temp=it.next();
			max=moreLastDate(max,temp);
		}
		list.remove(max);
		return max;
	}

	private static Result moreLastDate(Result d1, Result d2){
		String date1=d1.getDate();
		String date2=d2.getDate();
		int g1= Integer.parseInt(date1.substring(0, 2));
		int m1= Integer.parseInt(date1.substring(3, 5));
		int a1= Integer.parseInt(date1.substring(6, 10));
		int g2= Integer.parseInt(date2.substring(0, 2));
		int m2= Integer.parseInt(date2.substring(3, 5));
		int a2= Integer.parseInt(date2.substring(6, 10));
		if(a1==a2){
			if (m1==m2){
				if (g1==g2) return d1;
				else {
					if(g1>g2) return d1;
					else return d2;
				}
			}else {
				if(m1>m2) return d1;
				else return d2;
			}
		}else {
			if(a1>a2) return d1;
			else return d2;
		}
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	


}
