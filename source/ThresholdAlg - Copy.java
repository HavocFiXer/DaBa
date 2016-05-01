package source;

import indexing.BTree;

import java.util.ArrayList;
import java.util.Map;

public class ThresholdAlg {
	public void ThresholdAlgRun(ArrayList<ArrayList<Float>> table, int[] v, int K){
		int N = table.get(0).size()-1; //number of attributes
		
	/*build B-tree for id and each attributes, each B-tree array contains
	 * #N <attribute, ID> trees or #N <ID, attribute> */
		ArrayList<BTree<Float, Float>> btArrayAttrIsIndex = new ArrayList<BTree<Float, Float>>();
		ArrayList<BTree<Float, Float>> btArrayIDIsIndex = new ArrayList<BTree<Float, Float>>();
		for(int col=1; col < table.get(0).size(); ++col){
			BTree<Float, Float> btree = new BTree<Float, Float>();
			BTree<Float, Float> btree2 = new BTree<Float, Float>();
			for(int row=0; row < table.size(); ++row){
				btree.put(table.get(row).get(col), table.get(row).get(0));
				btree2.put(table.get(row).get(0), table.get(row).get(col));
			}
			btree.toString();
			btArrayAttrIsIndex.add(btree);
			btree2.toString();
			btArrayIDIsIndex.add(btree2);
		}	
			
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
					score += v[j]*btArrayIDIsIndex.get(j).getValue(id);
				}
				pQueue.addRecord(score, id);
				usedIDs.add(id);
			}
			
			if(pQueue.minheap.size() < K){
				continue;
			}
			
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
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
		
	}	
}
