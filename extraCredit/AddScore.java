package extraCredit;

import java.util.*;
import java.util.Comparator;
public class AddScore{
	public float score;
	public ArrayList<ArrayList<Float>> table;
	public ArrayList<ArrayList<Float>> tableWithScore;
	public int[] v;
	public AddScore(ArrayList<ArrayList<Float>> table, int[] v){
		this.table = table;
		this.v = v;
		tableWithScore = ScoreAdd();
	}
	//private float score;
	//caculate the score of each tuple and add to each tuple
	
	public  ArrayList<ArrayList<Float>> ScoreAdd(){
		ArrayList<ArrayList<Float>> tableWithScore = new ArrayList<ArrayList<Float>>();
		for (ArrayList<Float> tuple : table){
			 score = 0.00f;
			 ArrayList<Float> tupleWithScore = new ArrayList<Float>();
			 tupleWithScore.addAll(tuple);
			for (int i = 0; i < v.length;i++)
			{
				
				score += tuple.get(i)*v[i];
				
			}
			tupleWithScore.add(score);
			tableWithScore.add(tupleWithScore);
		}
		//descending order
		Collections.sort(tableWithScore, new Comparator<ArrayList<Float>> () {
		    @Override
		    public int compare(ArrayList<Float> a, ArrayList<Float> b) {
		        return b.get(v.length).compareTo(a.get(v.length));
		    }
		});
//		for(int i = 0; i <tableWithScore.size();i++){
//		System.out.println(tableWithScore.get(i));
//	}
		return tableWithScore;
	}

}

	
	
	
	


