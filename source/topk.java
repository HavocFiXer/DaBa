package source;

import indexing.BTree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import source.Initiate.DataInit;

public class topk {
	public static void main(String[] args) {
		int K=Integer.parseInt(args[0]);  //top k
		int N=Integer.parseInt(args[1]); //number of attributes		
		BTree.M = 60;
		Initiate.DataInit t= new Initiate.DataInit();
				
		while(true){
			Scanner scanner = new Scanner(System.in);
			String[] input = scanner.nextLine().split(" ");

			if (input[0].equals("init")){
				Initiate init = new Initiate();
			//	Initiate.DataInit t = init.InitRun("src/data/test.csv");
				t = init.InitRun(input[1]);
				continue;
				}
			
			
			int[] v = new int[N];
			for (int i=0; i<v.length; i++){
				v[i]=Integer.parseInt(input[i+1]);
			}			

			if (input[0].equals("run1")){
			//	System.out.println("Naive method result: ");
				NaivePQ naive = new NaivePQ();
				naive.NaiveRun(t, v, K, N);
				}

			if (input[0].equals("run2")){
			//	System.out.println("TopK method result: ");
				ThresholdAlg ta = new ThresholdAlg();
				ta.ThresholdAlgRun(t, v, K, N);
				}
			
		}

		
	}	

}
