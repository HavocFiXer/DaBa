package source;

import indexing.BTree;

import java.util.ArrayList;
import java.util.Map;

public class NaivePQ {
	public void NaiveRun(Initiate.DataInit t, int[] v, int K){
		
		FixSizedPriorityQueue<Float,Integer> pQueue = new FixSizedPriorityQueue<Float,Integer>(K);        
		for(int i=0; i < t.attrTable.size(); i++){
			ArrayList<Float> row = t.attrTable.get(i);
			Float score = 0.0f; //Float.valueOf(0);
			for(int j=0; j < row.size(); j++){				
				score +=  v[j]*row.get(j);
			}			
			pQueue.addRecord(score, i);
		}
		
		System.out.print("topk scores:");
		while (!pQueue.minheap.isEmpty()) {
			System.out.print(pQueue.minheap.poll() + ", ");
		}

		System.out.println("\ntop k id and the attributes: ");
		for(Map.Entry<Integer, Float> entry : pQueue.idAndAttri.entrySet()){	
			
 			System.out.println(entry.getKey() + 
 				t.btIDIsIndex.getValue(entry.getKey()).toString().replace(",","").replace("[", " ").replace("]"," ") 
 				+ entry.getValue() );
		}
		
		
	}	

}
