package source;

import indexing.BTree;

import java.util.ArrayList;
import java.util.Map;

public class ThresholdAlg {
	public void ThresholdAlgRun(Initiate.DataInit t, int[] v, int K){
		
		int N = t.attrTable.get(0).size(); //number of attributes

	//perform threshold algorithm 	
		FixSizedPriorityQueue<Float,Integer> pQueue = new FixSizedPriorityQueue<Float,Integer>(K);
		ArrayList<Integer> usedIDs = new ArrayList<Integer>();
		
		for(int row=0; row < t.attrTable.size(); ++row){
			// for each row, we calculate table.get(0).size() times of scores				
			for(int col=0; col<N; ++col){
				// find the id of row row in attribute attr, and if the id has been used, then skip
				float score = 0.0f;
				Integer id = t.btArrayAttrIsIndex.get(col).valueList.get(row);
				if(usedIDs.contains(id)){
					continue;
				}
				
				for(int j=0; j<N; ++j){
					if(j == col){
						score += v[j]*t.btArrayAttrIsIndex.get(col).keyList.get(row);
						continue;
					}
					score += v[j]*t.btIDIsIndex.getValue(id).get(j);
				}
				pQueue.addRecord(score, id);
				usedIDs.add(id);
			}
			
			if(pQueue.minheap.size() < K){
				continue;
			}
			//threshold is the score for each row
			float threshold = 0.0f;
			for(int col=0; col<N; col++){
				threshold += t.btArrayAttrIsIndex.get(col).keyList.get(row)*v[col];
			}
			if(pQueue.minheap.peek() >= threshold){
				break;
			}
		}

		System.out.print("topk scores:");
		System.out.println(pQueue.minheap.toString());

		for(Map.Entry<Integer, Float> entry : pQueue.idAndAttri.entrySet()){
 			System.out.println(entry.getKey() + 
 	 		t.btIDIsIndex.getValue(entry.getKey()).toString().replace(",","").replace("[", " ").replace("]"," ") 
 	 		+ entry.getValue() );
		}
		
	}	
}
