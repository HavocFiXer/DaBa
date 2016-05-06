package source;

import indexing.BTree;

import java.util.ArrayList;
import java.util.Map;

public class ThresholdAlg {
	public void ThresholdAlgRun(Initiate.DataInit t, int[] v, int K, int N){
		
		//perform threshold algorithm 	
		FixSizedPriorityQueue<Integer,Integer> pQueue = new FixSizedPriorityQueue<Integer,Integer>(K);
		ArrayList<Integer> usedIDs = new ArrayList<Integer>();
		
		for(int row=0; row < t.table.size(); ++row){
			// for each row, we calculate table.get(0).size() times of scores				
			for(int col=0; col<N; ++col){
				// find the id of row row in attribute attr, and if the id has been used, then skip
				Integer score = 0;
				Integer id = t.btArrayAttrIsIndex.get(col).getValueList().get(row).get(0);
				if(usedIDs.contains(id)){
					continue;
				}
				
				for(int j=0; j<N; ++j){
					if(j == col){
						score += v[j]*t.btArrayAttrIsIndex.get(col).getKeyList().get(row);
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
			Integer threshold = 0;
			for(int col=0; col<N; col++){
				threshold += v[col]*t.btArrayAttrIsIndex.get(col).getKeyList().get(row);
			}
			if(pQueue.minheap.peek() >= threshold){
				break;
			}
		}

		//System.out.println("topk scores:");
		//System.out.println(pQueue.minheap.toString());
//		for(Map.Entry<Integer, Integer> entry : pQueue.idAndAttri.entrySet()){
// 			System.out.println(entry.getKey() + 
// 	 		t.btIDIsIndex.getValue(entry.getKey()).toString().replace(",","").replace("[", " ").replace("]"," ") 
// 	 		+ entry.getValue() );
//		}
		
		for (int i=0; i<t.title.size(); i++){
			System.out.format("%5s\t", t.title.get(i));		
		}	
		System.out.format("score");
		System.out.format("\n");
		for(Map.Entry<Integer, Integer> entry : pQueue.idAndAttri.entrySet()){			
			System.out.format("%5d\t", entry.getKey());
			for (int i=0; i<t.title.size()-1; i++){
				System.out.format("%5d\t", t.btIDIsIndex.getValue(entry.getKey()).get(i) );		
			}	
			System.out.format("%5d",entry.getValue());
			System.out.format("\n");
		}		
		
		
	}	
}
