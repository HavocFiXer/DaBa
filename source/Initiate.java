package source;

import indexing.BTree;
import java.util.ArrayList;

/*Initiate performs:
1. construction of the relation Table.
2. construction of a dense primary B-tree index on the attribute Id.
3. construction of a dense secondary B-tree index on each of the remaining attributes
A1, A2, . . . , AN.
 */
public class Initiate{
	
	static class DataInit{
		ArrayList<ArrayList<Float>> attrTable;
		ArrayList<Integer> idArray;
		ArrayList<BTree<Float, Integer>> btArrayAttrIsIndex;
		BTree<Integer, ArrayList<Float>> btIDIsIndex;	
	}
	
	public DataInit InitRun(String FileName){		
		DataInit t = new DataInit();
		//read in the table, save id in a 1D integer array, save attributes as 2D float array		
		ReadCSV readcsv = new ReadCSV();
		readcsv.Readrun(FileName);
		t.attrTable = readcsv.attrFloat;
		t.idArray = readcsv.idInt;
		
	/*build B-tree for on each attributes, each B-tree array contains
	 * #N <attribute, ID> trees with attributes is index*/

		t.btArrayAttrIsIndex = new ArrayList<BTree<Float, Integer>>();		
		for(int col=0; col < t.attrTable.get(0).size(); ++col){
			BTree<Float, Integer> btree = new BTree<Float, Integer>();
			for(int row=0; row < t.attrTable.size(); ++row){
				btree.put(t.attrTable.get(row).get(col), t.idArray.get(row));
			}
			btree.toString();
			t.btArrayAttrIsIndex.add(btree);
		}	
	/*build B-tree on id as index, attributes array as value */	
		t.btIDIsIndex = new BTree<Integer, ArrayList<Float>>();		
		for(int row=0; row<t.attrTable.size(); ++row){
			ArrayList<Float> attrs = new ArrayList<Float>();
			for(int col=0; col < t.attrTable.get(0).size(); ++col){
				attrs.add(t.attrTable.get(row).get(col));
			}
			t.btIDIsIndex.put(t.idArray.get(row), attrs);
		}
		return t;
	}	

}
