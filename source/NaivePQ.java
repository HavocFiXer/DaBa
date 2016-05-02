package source;

import indexing.BTree;

import java.util.ArrayList;
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
		
//construct a B-tree with id as its index
		BTree<Float, ArrayList<Float>> btIDIsIndex = new BTree<Float, ArrayList<Float>>();		
		for(int row=0; row<table.size(); ++row){
			ArrayList<Float> attrs = new ArrayList<Float>();
			for(int col=1; col < table.get(0).size(); ++col){
				attrs.add(table.get(row).get(col));
			}
			btIDIsIndex.put(table.get(row).get(0), attrs);			
		}
		
		while (!pQueue.minheap.isEmpty()) {
			System.out.print(pQueue.minheap.poll() + ", ");
		}

		System.out.println("\ntop k id and the attributes: ");
		for(Map.Entry<Float, Float> entry : pQueue.idAndAttri.entrySet()){
			//System.out.println(entry.getKey() + ": " + entry.getValue());
			System.out.println(entry.getKey() + btIDIsIndex.getValue(entry.getKey()).toString() );
			//System.out.println(table.get( Math.round(entry.getKey()) ));
		}
		
		
	}	

}
