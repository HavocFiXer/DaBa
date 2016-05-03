package extraCredit;

import java.util.*;
import java.util.Comparator;
public class AddScore{
	public float score;
	public ArrayList<ArrayList<Float>> table;
	public int[] v;
	public AddScore(ArrayList<ArrayList<Float>> table, int[] v){
		this.table = table;
		this.v = v;
	}
	//private float score;
	//caculate the score of each tuple and add to each tuple
	
	public  ArrayList<ArrayList<Float>> ScoreAdd(ArrayList<ArrayList<Float>> Htable, int[] hv){
		ArrayList<ArrayList<Float>> tableWithScore = new ArrayList<ArrayList<Float>>();
		for (ArrayList<Float> tuple : Htable){
			 score = 0.00f;
			 ArrayList<Float> tupleWithScore = new ArrayList<Float>();
			 tupleWithScore.addAll(tuple);
			for (int i = 1; i < tuple.size();i++)
			{
				
				score += tuple.get(i)*hv[i-1];
				
			}
			tupleWithScore.add(score);
			tableWithScore.add(tupleWithScore);
		}
		//descending order
		Collections.sort(tableWithScore, new Comparator<ArrayList<Float>> () {
		    @Override
		    public int compare(ArrayList<Float> a, ArrayList<Float> b) {
		        return b.get(hv.length+1).compareTo(a.get(hv.length+1));
		    }
		});
		for(int i = 0; i <tableWithScore.size();i++){
		System.out.println(tableWithScore.get(i));
	}
		return tableWithScore;
	}

}

	
	
	
	


