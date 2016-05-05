package source;

import java.util.ArrayList;
import java.util.Map;

public class NaivePQ {
	public void NaiveRun(Initiate.DataInit t, int[] v, int K, int N){
		
		FixSizedPriorityQueue<Integer,Integer> pQueue = new FixSizedPriorityQueue<Integer,Integer>(K);        
		for(int i=0; i < t.table.size(); i++){
			ArrayList<Integer> row = t.table.get(i);
			Integer id = t.table.get(i).get(0);
			
			Integer score = 0; 
			for(int j=1; j < N+1; j++){				
				score +=  v[j-1]*row.get(j);
			}			
			pQueue.addRecord(score, id);
		}
		
		System.out.println("topk scores:");
		while (!pQueue.minheap.isEmpty()) {
			System.out.print(pQueue.minheap.poll() + ", ");
		}

		System.out.println(t.title);	 
		for(Map.Entry<Integer, Integer> entry : pQueue.idAndAttri.entrySet()){			
 			System.out.println(entry.getKey() + 
 				t.btIDIsIndex.getValue(entry.getKey()).toString().replace(",","").replace("[", " ").replace("]"," ") 
 				+ entry.getValue() );
		}
		
		
	}	

}
