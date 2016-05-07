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
		
		/*print out the results, as a ascending order of score */
		for (int i=0; i<t.title.size(); i++){
			System.out.format("%5s\t", t.title.get(i));		
		}	
		System.out.format("%5s\t","score");
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
                	pQueue.idAndScore.remove(entry.getKey());
            		System.out.format("%5d\t", printID);
            		for (int i=0; i<t.title.size()-1; i++){
            			System.out.format("%5d\t", t.btIDIsIndex.getValue(printID).get(i) );		
            		}	            	
        			System.out.format("%5d",printScore);
        			System.out.format("\n");
        			break;
            	}
            }
			
		}
		
	}
}
