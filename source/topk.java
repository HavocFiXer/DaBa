package source;

import indexing.BTree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import source.Initiate.DataInit;

public class topk {
	public static void main(String[] args) {
		int K=5;  //top k
		int N=17; //number of attributes		
		BTree.M = 60;
		
		Initiate init = new Initiate();
		Initiate.DataInit t = init.InitRun("src/data/NBA.csv");
		
		int[] v = new int[N];
		for (int i=0; i<v.length; i++){
			v[i]=1;
		}
		
		System.out.println("Naive method result: ");
		NaivePQ naive = new NaivePQ();
		naive.NaiveRun(t, v, K);

		System.out.println("TopK method result: ");
		ThresholdAlg ta = new ThresholdAlg();
		ta.ThresholdAlgRun(t, v, K);
		
	}	

}
