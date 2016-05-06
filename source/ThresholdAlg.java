package source;

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
				//calculate score of records for each id
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
	
		/*print out the results, as a ascending order of score */
		for (int i=0; i<t.title.size(); i++){
			System.out.format("%8s\t", t.title.get(i));		
		}	
		System.out.format("%8s\t","score");
		System.out.format("\n");
		//print out each record with(id, attris, score)
		while (!pQueue.minheap.isEmpty()){
			int printID=0;
			int printScore=0;
			int min =pQueue.minheap.poll();
            for(Map.Entry<Integer, Integer> entry : pQueue.idAndScore.entrySet() ){
            	if(entry.getValue().equals(min)){
            		printID=entry.getKey();
            		printScore=entry.getValue();
            	}
            }
    		System.out.format("%8d\t", printID);
    		for (int i=0; i<t.title.size()-1; i++){
    			System.out.format("%8d\t", t.btIDIsIndex.getValue(printID).get(i) );		
    		}	            	
			System.out.format("%8d",printScore);
			System.out.format("\n");			
		}	
		
		
	}	
}
