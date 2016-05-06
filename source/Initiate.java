package source;

//import indexing.BTree;
import indexing.IntBTree;

import java.util.ArrayList;

/*Initiate performs:
1. construction of the relation Table.
2. construction of a dense primary B-tree index on the attribute Id.
3. construction of a dense secondary B-tree index on each of the remaining attributes
A1, A2, . . . , AN.
 */
public class Initiate{
	
	public static class DataInit{
		public ArrayList<ArrayList<Integer>> table;
		public ArrayList<String> title;
		//ArrayList<BTree<Integer, Integer>> btArrayAttrIsIndex;
		ArrayList<IntBTree> btArrayAttrIsIndex;
		//BTree<Integer, ArrayList<Integer>> btIDIsIndex;	
		IntBTree btIDIsIndex;	
	}
	
	public DataInit InitRun(String FileName){		
		DataInit t = new DataInit();
		//read in the table
		ReadCSV readcsv = new ReadCSV();
		readcsv.Readrun(FileName);
		t.table = readcsv.table;
		t.title = readcsv.title;
		
	/*build B-tree for on each attributes, each B-tree array contains
	 * #N <attribute, ID> trees with attributes is index*/
		//t.btArrayAttrIsIndex = new ArrayList<BTree<Integer, Integer>>();
		t.btArrayAttrIsIndex = new ArrayList<IntBTree>();
		for(int col=1; col < t.table.get(0).size(); ++col){
			//BTree<Integer, Integer> btree = new BTree<Integer, Integer>();
			IntBTree btree = new IntBTree();
			for(int row=0; row < t.table.size(); ++row){
				ArrayList<Integer> tmp=new ArrayList<Integer>();
				tmp.add(t.table.get(row).get(0));
				btree.insert(t.table.get(row).get(col), tmp);
			}
			btree.setKeyValueLists();
			//t.btArrayAttrIsIndex.add(btree);
			t.btArrayAttrIsIndex.add(btree);
		}	
	/*build B-tree on id as index, attributes array as value */	
		//t.btIDIsIndex = new BTree();		
		t.btIDIsIndex = new IntBTree();		
		for(int row=0; row<t.table.size(); ++row){
			ArrayList<Integer> attrs = new ArrayList<Integer>();
			for(int col=1; col < t.table.get(0).size(); ++col){
				attrs.add(t.table.get(row).get(col));
			}
			t.btIDIsIndex.insert(t.table.get(row).get(0), attrs);
		}
		return t;
	}	

}
