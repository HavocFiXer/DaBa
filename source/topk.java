package source;

import indexing.BTree;
import extraCredit.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class topk {
	public static void main(String[] args) {
		int K=5;  //top k
		int N=18; //number of attributes		
		BTree.M = 60;
		
		ReadCSV readcsv = new ReadCSV();
		ArrayList<ArrayList<Float>> table = readcsv.Readrun("src/data/NBA.csv");
		N = table.get(0).size();
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
		ArrayList<ArrayList<Float>> hashResult = hs.hashJoin(table,2,table,2);
		System.out.println("Hash Join method finished" + " and the number of tuples get: " + 
		hashResult.size()+ " table "+table.size());
//		System.out.println("Hash Join topk method result: ");
//		ta.ThresholdAlgRun(hashResult,hv,K);   //has out of memory problem when the hashResult is very large.
//		
/*  	//for score add part 
		System.out.println("Add score and ordering: ");
		AddScore tableAddScore = new AddScore(table, v);
		ArrayList<ArrayList<Float>> tableWithScore = tableAddScore.tableWithScore;
 */
		System.out.println("Rank Join results: ");
		RankJoin rankJoin = new RankJoin(table,table,hv,10);
		rankJoin.RankJoinRun(5, 17);
	}	

}
