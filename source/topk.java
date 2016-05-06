package source;

//import indexing.BTree;
import indexing.IntBTree;
import source.Initiate.DataInit;
import extraCredit.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class topk {
	public static void main(String[] args) {
		int K=Integer.parseInt(args[0]);  //top k
		int N=Integer.parseInt(args[1]); //number of attributes		
//		BTree.M = 60;
		Initiate.DataInit t= new Initiate.DataInit();
				
		while(true){
			Scanner scanner = new Scanner(System.in);
			String[] input = scanner.nextLine().split(" ");
			ArrayList<String> filepath = new ArrayList<String>();
			ArrayList<ArrayList<Integer>> conditionToJoin = new ArrayList<ArrayList<Integer>>();

			if (input[0].equals("init")){
				Initiate init = new Initiate();
			//	Initiate.DataInit t = init.InitRun("src/data/test.csv");
				String[] path = input[1].split(",");
				Collections.addAll(filepath, path);
				
				if (filepath.size()==1){
					t = init.InitRun(input[1]);
				}else{
					ArrayList<ArrayList<ArrayList<Integer>>> tableList = new ArrayList<ArrayList<ArrayList<Integer>>>();
					ArrayList<ArrayList<String>> titleList = new ArrayList<ArrayList<String>>();
					
					for (int nf=0; nf<filepath.size(); nf++){
						ReadCSV readcsv = new ReadCSV();
						readcsv.Readrun( filepath.get(nf).toString() );
						tableList.add( readcsv.table );	
						titleList.add( readcsv.title );
					}

					String[] condition= input[3].split(",");
					for (int i=0; i<condition.length; i++){
						String[] f = condition[i].split("\\.|=");
						ArrayList<Integer> eachCondition = new ArrayList<Integer>();
						Integer file1 = filepath.indexOf(f[0]);
						eachCondition.add(file1);
						Integer index1= titleList.get(file1).indexOf(f[1]);
						eachCondition.add(index1);
						Integer file2 = filepath.indexOf(f[2]);
						eachCondition.add(file2);
						Integer index2= titleList.get(file2).indexOf(f[3]);
						eachCondition.add(index2);
						conditionToJoin.add(eachCondition);
							}
					}
	
				continue;
				}
			
			
			int[] v = new int[N];
			for (int i=0; i<v.length; i++){
				v[i]=Integer.parseInt(input[i+1]);
			}			

			int[] hv = new int[2*N+2]; //initialize 0 array
			for (int i=1; i<N+1; i++){
				hv[i]=Integer.parseInt(input[i]);
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
			
			if (input[0].equals("run3")){
			//	System.out.println("Rank join result: ");
				RankJoin multiRankJoin = new RankJoin(filepath,hv,K,conditionToJoin);
				multiRankJoin.MultiFilesRankJoinRun();
				
				}
					
		}

		
	}	

}
