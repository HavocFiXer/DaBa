package source;

import indexing.BTree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import source.Initiate.DataInit;
import extraCredit.*;
public class topk {
	public static void main(String[] args) {
		int K=5;  //top k
		int N=5; //number of attributes		
		BTree.M = 60;
		
		Initiate init = new Initiate();
		Initiate.DataInit t = init.InitRun("src/data/test.csv");
		
		int[] v = new int[N];
		for (int i=0; i<v.length; i++){
			v[i]=1;
		}
		
		System.out.println("Naive method result: ");
		NaivePQ naive = new NaivePQ();
		naive.NaiveRun(t, v, K, N);

		System.out.println("TopK method result: ");
		ThresholdAlg ta = new ThresholdAlg();
		ta.ThresholdAlgRun(t, v, K, N);
		
		// hashJoin
		int[] hv = new int[t.table.get(0).size()*2]; 
		for (int i=0; i<hv.length; i++){
			hv[i]=1;
		}
		
//		System.out.println("Hash Join method: ");
		HashJoin hs = new HashJoin();
////		ReadCSV read = new ReadCSV();
////		read.Readrun("src/data/test.csv");
		ArrayList<ArrayList<Integer>> hashResult = hs.hashJoin(t.table,6,t.table,6);
		System.out.println("Hash Join method finished" + " and the number of tuples get: " + 
		hashResult.size()+ " table "+t.table.size());
//		System.out.println("Hash Join topk method result: ");
////		ta.ThresholdAlgRun(hashResult,hv,K);   //has out of memory problem when the hashResult is very large.
	
/*  	//for score add part 
		System.out.println("Add score and ordering: ");
		AddScore tableAddScore = new AddScore(table, v);
		ArrayList<ArrayList<Float>> tableWithScore = tableAddScore.tableWithScore;
*/
		System.out.println("Rank Join results: ");
		RankJoin rankJoin = new RankJoin(t.table,t.table,hv,K);
		rankJoin.RankJoinRun(7, 5);
		
		//multiFiles RankJoin
//		public RankJoin(ArrayList<String> fileNames, int[] v, int K,ArrayList<ArrayList<Integer>> AttrToJoin)
		ArrayList<String> fileNames = new ArrayList<String> ();
		fileNames.add("src/data/test.csv");
		fileNames.add("src/data/test.csv");
//		fileNames.add("src/data/test.csv");

		System.out.println(fileNames.toString());
		int[] mhv = new int[t.table.get(0).size()*3]; 
		for (int i=0; i<mhv.length; i++){
			mhv[i]=1;
		}
		ArrayList<ArrayList<Integer>> AttrToJoin = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> condition = new ArrayList<Integer>();
		condition.add(1);
		condition.add(7);
		condition.add(2);
		condition.add(5);
		AttrToJoin.add(condition);
//		ArrayList<Integer> condition2 = new ArrayList<Integer>();
//
//		condition2.add(0, 2);
//		condition2.add(1, 8);
//		condition2.add(2, 3);
//		condition2.add(3, 4);
//		AttrToJoin.add(condition2);
	System.out.println("AttrToJoin"+AttrToJoin);


		
		RankJoin multiRankJoin = new RankJoin(fileNames,mhv,K,AttrToJoin);
		multiRankJoin.MultiFilesRankJoinRun();
		

	}	

}
