package source;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class NaivePQ {
	public void NaiveRun(ArrayList<ArrayList<Float>> table, int[] v, int K){
		
		FixSizedPriorityQueue<Float> pQueue = new FixSizedPriorityQueue<Float>(K);        
		for(int i=0; i<table.size(); i++){
			ArrayList<Float> row = table.get(i);
			Float score = 0.0f; //Float.valueOf(0);
			for(int j=1; j<row.size(); j++){				
				score +=  v[j]*row.get(j);
			}			
			pQueue.addRecord(score, Float.valueOf(i));
		}         


//		Iterable<Float> iter = new Iterable<Float>() {
//			public Iterator<Float> iterator() {
//				return pQueue.minheap.iterator();
//			}
//		};
//		for (Float item : iter) {
//			System.out.print(item + ", ");
//		}
		
//		System.out.println("PriorityQueue transversal after sort:-----------------------------------");
		while (!pQueue.minheap.isEmpty()) {
			System.out.print(pQueue.minheap.poll() + ", ");
		}

		System.out.println("\n\ntop k id and its scoring: ");
		for(Map.Entry<Float, Float> entry : pQueue.idAndAttri.entrySet()){
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
		
		
	}	

}
