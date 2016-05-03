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
		ArrayList<ArrayList<Float>> table = readcsv.Readrun("src/data/NBA.csv");
		
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


		//for hashjoin
		int[] hv = new int[N*2];
		for (int i=0; i<hv.length; i++){
			hv[i]=1;
		}
		System.out.println("Hash Join method result: ");
		HashJoin hs = new HashJoin();
		ArrayList<ArrayList<Float>> hashResult = hs.hashJoin(table,1,table,1);
		System.out.println("Hash Join topk method result: ");
		ta.ThresholdAlgRun(hashResult,hv,K);
		
	}	

}
