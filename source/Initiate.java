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
		ArrayList<ArrayList<Integer>> table;
		ArrayList<String> title = new ArrayList<>();
		ArrayList<BTree<Integer, Integer>> btArrayAttrIsIndex;
		BTree<Integer, ArrayList<Integer>> btIDIsIndex;	
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
		t.btArrayAttrIsIndex = new ArrayList<BTree<Integer, Integer>>();		
		for(int col=1; col < t.table.get(0).size(); ++col){
			BTree<Integer, Integer> btree = new BTree<Integer, Integer>();
			for(int row=0; row < t.table.size(); ++row){
				btree.put(t.table.get(row).get(col), t.table.get(row).get(0));
			}
			btree.toString();
			t.btArrayAttrIsIndex.add(btree);
		}	
	/*build B-tree on id as index, attributes array as value */	
		t.btIDIsIndex = new BTree<Integer, ArrayList<Integer>>();		
		for(int row=0; row<t.table.size(); ++row){
			ArrayList<Integer> attrs = new ArrayList<Integer>();
			for(int col=1; col < t.table.get(0).size(); ++col){
				attrs.add(t.table.get(row).get(col));
			}
			t.btIDIsIndex.put(t.table.get(row).get(0), attrs);
		}
		return t;
	}	

}
