package source;

import indexing.BTree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class topk {
	public static void main(String[] args) {
		int K=5;  //top k
		int N=18; //number of attributes		
		BTree.M = 60;
		
		ReadCSV readcsv = new ReadCSV();
		ArrayList<ArrayList<Float>> table = readcsv.Readrun("src/data/test.csv");
		
		int[] v = new int[N];
		for (int i=0; i<v.length; i++){
			v[i]=1;
		}
		
		System.out.println("Naive method result: ");
		NaivePQ naive = new NaivePQ();
		naive.NaiveRun(table, v, K);

		System.out.println("TopK method result: ");
		ThresholdAlg ta = new ThresholdAlg();
		ta.ThresholdAlgRun(table, v, K);

		// hashJoin
		int[] hv = new int[N*2]; 
		for (int i=0; i<hv.length; i++){
			hv[i]=1;
		}
		System.out.println("Hash Join method: ");
		HashJoin hs = new HashJoin();
		ArrayList<ArrayList<Float>> hashResult = hs.hashJoin(table,5,table,15);
		System.out.println("Hash Join method finished" + " and the number of tuples get: " + 
		hashResult.size()+ " table "+table.size());
		//System.out.println(hs.leftId);
		//System.out.println(hs.rightId);
		System.out.println("Hash Join topk method result: ");
		ta.ThresholdAlgRun(hashResult,hv,K);
		
		//add score 
		System.out.println("Add score and ordering: ");
		AddScore tableWithScore = new AddScore(table, v);
		tableWithScore.ScoreAdd(table, v);
		
		
	}	

}
