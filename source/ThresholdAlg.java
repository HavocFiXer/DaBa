package source;

import indexing.BTree;

import java.util.ArrayList;
import java.util.Map;

public class ThresholdAlg {
	public void ThresholdAlgRun(ArrayList<ArrayList<Float>> table, int[] v, int K){
		int N = table.get(0).size()-1; //number of attributes
		
	/*build B-tree for on each attributes, each B-tree array contains
	 * #N <attribute, ID> trees with attributes is index*/
		ArrayList<BTree<Float, Float>> btArrayAttrIsIndex = new ArrayList<BTree<Float, Float>>();		
		for(int col=1; col < table.get(0).size(); ++col){
			BTree<Float, Float> btree = new BTree<Float, Float>();
			for(int row=0; row < table.size(); ++row){
				btree.put(table.get(row).get(col), table.get(row).get(0));
			}
			btree.toString();
			btArrayAttrIsIndex.add(btree);
		}	
	/*build B-tree on id as index, attributes array as value */	
		BTree<Float, ArrayList<Float>> btIDIsIndex = new BTree<Float, ArrayList<Float>>();		
		for(int row=0; row<table.size(); ++row){
			ArrayList<Float> attrs = new ArrayList<Float>();
			for(int col=1; col < table.get(0).size(); ++col){
				attrs.add(table.get(row).get(col));
			}
			btIDIsIndex.put(table.get(row).get(0), attrs);
		}
		
	//perform threshold algorithm 	
		FixSizedPriorityQueue<Float> pQueue = new FixSizedPriorityQueue<Float>(K);
		ArrayList<Float> usedIDs = new ArrayList<Float>();
		
		for(int row=0; row < table.size(); ++row){
			// for each row, we calculate table.get(0).size() times of scores				
			for(int attr=0; attr<N; ++attr){
				// find the id of row row in attribute attr, and if the id has been used, then skip
				float score = 0.0f;
				float id = btArrayAttrIsIndex.get(attr).valueList.get(row);
				if(usedIDs.contains(id)){
					continue;
				}
				
				for(int j=0; j<N; ++j){
					if(j == attr){
						score += v[j]*btArrayAttrIsIndex.get(attr).keyList.get(row);
						continue;
					}
					score += v[j]*btIDIsIndex.getValue(id).get(j);
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
				threshold += btArrayAttrIsIndex.get(col).keyList.get(row)*v[col];
			}
			if(pQueue.minheap.peek() >= threshold){
				break;
			}
		}

		System.out.println(pQueue.minheap.toString());

		for(Map.Entry<Float, Float> entry : pQueue.idAndAttri.entrySet()){
			//System.out.println(entry.getKey() + ": " + entry.getValue());
			System.out.println(entry.getKey() + btIDIsIndex.getValue(entry.getKey()).toString() );
		}
		
	}	
}
