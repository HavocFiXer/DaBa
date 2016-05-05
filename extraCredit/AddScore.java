package extraCredit;

import java.util.*;
import java.util.Comparator;
public class AddScore{
	public Integer score;
	public ArrayList<ArrayList<Integer>> table;
	public ArrayList<ArrayList<Integer>> tableWithScore;
	public int[] v;
	public AddScore(ArrayList<ArrayList<Integer>> table, int[] v){
		this.table = table;
		this.v = v;
		tableWithScore = ScoreAdd();
	}
	//private Integer score;
	//caculate the score of each tuple and add to each tuple
	
	public  ArrayList<ArrayList<Integer>> ScoreAdd(){
		ArrayList<ArrayList<Integer>> tableWithScore = new ArrayList<ArrayList<Integer>>();
		for (ArrayList<Integer> tuple : table){
			 score = 0;
			 ArrayList<Integer> tupleWithScore = new ArrayList<Integer>();
			 tupleWithScore.addAll(tuple);
			for (int i = 0; i < v.length;i++)
			{
				
				score += tuple.get(i)*v[i];
				
			}
			tupleWithScore.add(score);
			tableWithScore.add(tupleWithScore);
		}
		//descending order
		Collections.sort(tableWithScore, new Comparator<ArrayList<Integer>> () {
		    @Override
		    public int compare(ArrayList<Integer> a, ArrayList<Integer> b) {
		        return b.get(v.length).compareTo(a.get(v.length));
		    }
		});
//		for(int i = 0; i <tableWithScore.size();i++){
//		System.out.println(tableWithScore.get(i));
//	}
		return tableWithScore;
	}

}

	
	
	
	


