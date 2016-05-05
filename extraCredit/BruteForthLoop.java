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
//				System.out.println("i "+i+" j " + j);
				interresult.add(j);
				
			}
			A.add(interresult);
		}
//		System.out.println("A "+ A);
		for(int i= 0;i< eachFileChunkAmount.get(0); i++){
			ArrayList<Integer> sub = new ArrayList<Integer>();
			sub.add(A.get(0).get(i));
//			System.out.println("SUB "+ sub);
//			System.out.println("bruteForth in before "+ i  +BruteForthOrder);
			Loop(1,i, sub);
//			System.out.println("bruteForth in "+ i  +BruteForthOrder);

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
//			System.out.println("in if" +" array "+array +" element " + element);
//			System.out.println("in if add "+A.get(array).get(element));
//			ArrayList<Integer> subresult = new ArrayList<Integer>();
//			subresult.addAll(sublist);
//			subresult.add(A.get(array).get(element));
////			sublist.add(A.get(array).get(element));
//			System.out.println( "sublist  "+ sublist);
//
//			BruteForthOrder.add(subresult);
//			System.out.println("bruteForth in if "  +BruteForthOrder);

			
		}
		else{
			for(int i= 0;i< eachFileChunkAmount.get(array);i++){
//				System.out.println("in i "+i+" array "+array +" element " + element);
				ArrayList<Integer> subl = new ArrayList<Integer>();
				subl.addAll(sublist);
				subl.add(A.get(array).get(i));
//				System.out.println( "sublist not in if "+ subl);
//				System.out.println("bruteForth in "+ "else " +i +BruteForthOrder);

				Loop( array+1,i,subl);
			}
		}
	}
//	public static void main(String[] args) {
//		ArrayList<Integer> test1 = new ArrayList<Integer>();
//		test1.add(4000);
//		test1.add(4000);
//		test1.add(2);
//		test1.add(1);
//		BruteForthLoop test = new BruteForthLoop(test1);
//		System.out.println(test.BruteForthOrder.size());
//		System.out.println(test.BruteForthOrder);

//	}
}
