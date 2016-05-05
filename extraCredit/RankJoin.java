package extraCredit;

import java.util.*;
import source.*;

public class RankJoin {
	private ArrayList<ArrayList<Integer>> leftTable;
	private ArrayList<ArrayList<Integer>> rightTable;
	public ArrayList<ArrayList< ArrayList<Integer> >> multiFiles;
	int[] v;
	ArrayList<int[]> multiV;
	int filesAmount;
	int K;
	int chunkSize;
	int leftAttrSize;
	int rightAttrSize;
	int leftTableSize;
	int rightTableSize;
	ArrayList<Integer> eachFileSize;
	ArrayList<ArrayList<Integer>> AttrToJoin;
//	private Integer pLeftMax;
//	private Integer pLeftMin;
//	private Integer pRightMax;
//	private Integer pRightMin;

	/*Constructor*/
	public RankJoin(ArrayList<ArrayList<Integer>> table1,ArrayList<ArrayList<Integer>> table2, int [] v, int K){
		//score and ordering 
		this.v = v;
		this.K = K;
		chunkSize = K*100;
		this.leftAttrSize = table1.get(0).size();
		this.rightAttrSize = table2.get(0).size();
		leftTableSize = table1.size();
		rightTableSize = table2.size();
		int[] vLeft = new int[leftAttrSize];
		int[] vRight = new int[rightAttrSize];
		for(int i = 0; i < leftAttrSize; i++)
		{
			vLeft[i]=v[i];
		}
		for(int i = 0; i < rightAttrSize; i++)
		{
			vRight[i] = v[v.length-1-i];
		}
		this.leftTable =  new AddScore(table1, vLeft).ScoreAdd();
		this.rightTable = new AddScore(table2,vRight).ScoreAdd();
//		System.out.println("join table size" + leftTable.size() + " and " + rightTable.size());
		
	}
	
	
	public RankJoin(ArrayList<String> fileNames, int[] v, int K,ArrayList<ArrayList<Integer>> AttrToJoin){
	   	   multiFiles = new ArrayList<ArrayList< ArrayList<Integer> >>();
	   	   this.v = v;
		   this.K = K;
		   this.AttrToJoin = AttrToJoin;
		   filesAmount = fileNames.size();
//			System.out.println("fileNames.size() "+ fileNames.size());

		   chunkSize = K*100;
		   int accumulatIndex = 0;
		   eachFileSize = new ArrayList<Integer> ();
			multiV = new ArrayList<int[]>();


		   //read files and add score
	   	   for(int i = 0; i < filesAmount; i++){
	   		Initiate init = new Initiate();
			Initiate.DataInit t = init.InitRun(fileNames.get(i));
//			System.out.println("i "+i +" RankJoin t.attrTable.size() "+ t.attrTable.size());
			eachFileSize.add(t.table.get(0).size());
			int[] interV = new int[eachFileSize.get(i)];
			
			
			for(int j = 0;j<eachFileSize.get(i);j++){
//				System.out.println("accumulatIndex "+accumulatIndex+" j "+j);

				interV[j] = v[accumulatIndex +j];

				
			}
			accumulatIndex += eachFileSize.get(i);
//			System.out.println("accumulatIndex "+accumulatIndex);

			multiV.add(interV);
			multiFiles.add(new AddScore(t.table, multiV.get(i)).ScoreAdd());
	   	   }

		   
	   }
	   
	   
	
/*for compute F socre*/	

	public int F(int left, int right)
	{
		return left+right;
	}
	
	/* two table Rank join method*/
	public void RankJoinRun(int idx1,int idx2){
		int pLeftMax = leftTable.get(0).get(leftAttrSize);
		int pRightMax = rightTable.get(0).get(rightAttrSize);
		Chunk leftChunks = new Chunk(leftTable,chunkSize);
		Chunk rightChunks = new Chunk(rightTable,chunkSize);
		Integer[] pLeftMin = leftChunks.getChunksPmin(leftChunks);
		Integer[] pRightMin = rightChunks.getChunksPmin(rightChunks);
		HRJNPriorityQueue<Integer> pQueue = new HRJNPriorityQueue<Integer>(K);
		int leftChunkNumber = leftChunks.Chunks.size();
		int rightChunkNumber = rightChunks.Chunks.size();
//		int i = 0;
//		int j = 0;
		int threshold = 0;
//		System.out.println("leftChunkNumber" + leftChunkNumber+ " and " + rightTable.size());

//		while(true){
//			HashJoin HJN = new HashJoin();
//			Integer T1 = F(pLeftMax,pRightMin[j]);
//			Integer T2 = F(pLeftMin[i],pRightMax);
//			threshold = Math.max(T1,T2 );
//			ArrayList<ArrayList<Integer>> HJNResult = HJN.hashRankJoin(leftChunks.Chunks.get(i), idx1, rightChunks.Chunks.get(j), idx2);
////			for (ArrayList<Integer> r: HJNResult ){
////				ArrayList<Integer> inter = new ArrayList<Integer>();
////				inter.addAll(r);
////				inter.remove(inter.size()-1);
////				pQueue.addRecord(r.get(r.size()-1), inter);
////			}
//			HJNResult.stream().forEach(r->{
//				ArrayList<Integer> inter = new ArrayList<Integer>();
//				inter.addAll(r);
//				inter.remove(inter.size()-1);
//
//				pQueue.addRecord(r.get(r.size()-1), inter);
//			} );
//			if(pQueue.minheap.size() < K){
//				continue;
//			}
//			
//			if(pQueue.minheap.peek() >= threshold){
//				break;
//			}
//			
//			
//			//Choose the input to retrive
//			else{
//				if(T1>=T2 ){
//					if(j<rightChunkNumber-1){
//						j++;
//					}
//					else{
//						i++;
//						j=0;
//					}
//				}
//				else{
//					if(i<leftChunkNumber-1){
//						i++;
//						j = 0;
//					}
//					else{
//						j++;
//						i = 0;
//					}
//				}
//			}
//
//		}
		
		for(int i = 0; i< leftChunkNumber;i++){
			for(int j = 0;j< rightChunkNumber;j++){
//				System.out.println("i" + i +" j " +j );
				HashJoin HJN = new HashJoin();
				Integer T1 = F(pLeftMax,pRightMin[j]);
				Integer T2 = F(pLeftMin[i],pRightMax);
				threshold = Math.max(T1,T2 );
				System.out.println("threshold" + threshold);
				ArrayList<ArrayList<Integer>> HJNResult = HJN.hashRankJoin(leftChunks.Chunks.get(i), idx1, rightChunks.Chunks.get(j), idx2);

				HJNResult.stream().forEach(r->{
					ArrayList<Integer> inter = new ArrayList<Integer>();
					inter.addAll(r);
					inter.remove(inter.size()-1);
	
					pQueue.addRecord(r.get(r.size()-1), inter);
				} );
				if(pQueue.minheap.size() < K){
					continue;
				}
				
				if(pQueue.minheap.peek() >= threshold){
					break;
				}

			}
		}
		System.out.println(pQueue.minheap.toString());

		for(Map.Entry<ArrayList<Integer>, Integer > entry : pQueue.tupleAndScore.entrySet()){
//			System.out.println(entry.getKey() + ": " + entry.getValue());
			System.out.println(entry.getValue().toString()+entry.getKey()   );
		}
		
		
	}
	
	public int getSum(int[] a,int  n){	
		 return n > 0 ? a[n-1] + getSum(a, n - 1) : 0;
		 }
	
	public int getThreshold(int[] pMax, int[] pMin)
	{
		int threshold = 0;
		int sumPMax = getSum(pMax,pMax.length);
		threshold = sumPMax-pMax[0]+pMin[0];
		for (int i = 0;i< pMin.length;i++){
			int value = sumPMax-pMax[i]+pMin[i];
			if(value > threshold){
				threshold = value;
			}
		}
			
		return threshold;
	}
	
	
	
	public void MultiFilesRankJoinRun(){


		//Begins!!!!!
		int threshold = 0;
		HRJNPriorityQueue<Integer> pQueue = new HRJNPriorityQueue<Integer>(K);
		ArrayList<Integer> eachFileChunkAmount = new ArrayList<Integer>();
		int[] pMax = new int[filesAmount];
		ArrayList<Integer[]> pMin = new ArrayList<Integer[]> ();
		ArrayList<ArrayList<ArrayList< ArrayList<Integer> >>> multiFilesSplited = new ArrayList<ArrayList<ArrayList< ArrayList<Integer> >>>();
		for(int i = 0;i < filesAmount;i++){
			Chunk eachTableSplit = new Chunk(multiFiles.get(i),chunkSize);
			eachFileChunkAmount.add((int)eachTableSplit.ChunkMaxIndex+1);
			pMax[i] = multiFiles.get(i).get(0).get(multiFiles.get(i).get(0).size()-1);
			pMin.add(eachTableSplit.getChunksPmin(eachTableSplit));
			multiFilesSplited.add(eachTableSplit.Chunks);
		}
		
		//get bruteForth scan order
		BruteForthLoop order = new BruteForthLoop(eachFileChunkAmount);
		
		for(int i = 0; i < order.BruteForthOrder.size(); i++){
			//put the chunks to join in one List
			ArrayList<ArrayList< ArrayList<Integer> >> ChunksToJoin = new ArrayList<ArrayList< ArrayList<Integer> >>();
			ArrayList<Integer> orderOfChunk = order.BruteForthOrder.get(i);
			int[] pMinChunksToJoin = new int[filesAmount];
			orderOfChunk.stream().forEach(r->{
				int indexOfR = orderOfChunk.indexOf(r);
				ChunksToJoin.add(multiFilesSplited.get(indexOfR).get(r));
				pMinChunksToJoin[indexOfR] = pMin.get(indexOfR)[r];
			});
			HashJoin HJN = new HashJoin();
			ArrayList<ArrayList<Integer>> HJNResult = HJN.multiFileHashRankJoin(ChunksToJoin, AttrToJoin);
			threshold = getThreshold(pMax, pMinChunksToJoin);
			HJNResult.stream().forEach(r->{
				ArrayList<Integer> inter = new ArrayList<Integer>();
				inter.addAll(r);
				inter.remove(inter.size()-1);

				pQueue.addRecord(r.get(r.size()-1), inter);
			} );
			if(pQueue.minheap.size() < K){
				continue;
			}
			
			if(pQueue.minheap.peek() >= threshold){
				break;
			}
//			System.out.println("ith finished " + i);
		}

		System.out.println(pQueue.minheap.toString());

		for(Map.Entry<ArrayList<Integer>, Integer > entry : pQueue.tupleAndScore.entrySet()){
//			System.out.println(entry.getKey() + ": " + entry.getValue());
			System.out.println(entry.getValue().toString()+entry.getKey()   );
		}
		

	}
	
	
	
}
