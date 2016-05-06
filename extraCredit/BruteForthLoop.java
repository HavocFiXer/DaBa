package extraCredit;

import java.util.ArrayList;

public class BruteForthLoop {
	private ArrayList<Integer> eachFileChunkAmount;
	private ArrayList<ArrayList<Integer>>  A;
	private int FilesCount;
	public ArrayList<ArrayList<Integer>> BruteForthOrder;
	public BruteForthLoop(ArrayList<Integer> eachFileSize){
		this.eachFileChunkAmount = eachFileSize;
		BruteForthOrder = new ArrayList<ArrayList<Integer>>();
		A = new ArrayList<ArrayList<Integer>>();
		FilesCount = eachFileChunkAmount.size();
		for(int i = 0;i < FilesCount; i++ ){
			ArrayList<Integer> interresult = new ArrayList<Integer>();
			for (int j = 0; j < eachFileChunkAmount.get(i); j++){
				interresult.add(j);	
			}
			A.add(interresult);
		}
		for(int i= 0;i< eachFileChunkAmount.get(0); i++){
			ArrayList<Integer> sub = new ArrayList<Integer>();
			sub.add(A.get(0).get(i));
			Loop(1,i, sub);
		}		
	}
	
	public void Loop( int array, int element, ArrayList<Integer> sublist){
		if(array == FilesCount -1){
			for(int lst = 0; lst<eachFileChunkAmount.get(array);lst++){
				ArrayList<Integer> subresult = new ArrayList<Integer>();
				subresult.addAll(sublist);
				subresult.add(A.get(array).get(lst));
				BruteForthOrder.add(subresult);
			}			
		}
		else{
			for(int i= 0;i< eachFileChunkAmount.get(array);i++){
				ArrayList<Integer> subl = new ArrayList<Integer>();
				subl.addAll(sublist);
				subl.add(A.get(array).get(i));
				Loop( array+1,i,subl);
			}
		}
	}
}
