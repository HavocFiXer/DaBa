package source;

import java.util.*;


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
	public ArrayList<String> AttrResult;


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
		
	}
	
	
	public RankJoin(ArrayList<String> fileNames, ArrayList<Integer> vArrayList, int K,ArrayList<ArrayList<Integer>> AttrToJoin){
	   	   multiFiles = new ArrayList<ArrayList< ArrayList<Integer> >>();
	   	   this.v = new int[vArrayList.size()];
	   	   for(int i = 0; i < vArrayList.size(); i++){
	   		   this.v[i] = vArrayList.get(i);
	   	   }
	   	   
		   this.K = K;
		   this.AttrToJoin = AttrToJoin;
		   filesAmount = fileNames.size();

		   chunkSize = K*100;
		   int accumulatIndex = 0;
		   eachFileSize = new ArrayList<Integer> ();
			multiV = new ArrayList<int[]>();
			AttrResult = new ArrayList<String>();
			AttrResult.add("score");

		   //read files and add score
	   	   for(int i = 0; i < filesAmount; i++){
	   		Initiate init = new Initiate();
			Initiate.DataInit t = init.InitRun(fileNames.get(i));
			eachFileSize.add(t.table.get(0).size());
			int[] interV = new int[eachFileSize.get(i)];
			
			
			for(int j = 0;j<eachFileSize.get(i);j++){
				interV[j] = v[accumulatIndex +j];
			}
			accumulatIndex += eachFileSize.get(i);

			multiV.add(interV);
			multiFiles.add(new AddScore(t.table, multiV.get(i)).ScoreAdd());
			
			AttrResult.addAll(t.title);
	   	   }

	   }
	   
	   
	
/* for compute F socre*/	

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
		int threshold = 0;

		
		for(int i = 0; i< leftChunkNumber;i++){
			for(int j = 0;j< rightChunkNumber;j++){
				HashJoin HJN = new HashJoin();
				Integer T1 = F(pLeftMax,pRightMin[j]);
				Integer T2 = F(pLeftMin[i],pRightMax);
				threshold = Math.max(T1,T2 );
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
			System.out.println(entry.getValue().toString()+entry.getKey()   );
		}
		
		
	}
	/*
	 RankJoin starts algorithm start here!!
	 */
	
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
		
		/*Split each file into chunks*/
		for(int i = 0;i < filesAmount;i++){
			Chunk eachTableSplit = new Chunk(multiFiles.get(i),chunkSize);
			eachFileChunkAmount.add((int)eachTableSplit.ChunkMaxIndex+1);
			pMax[i] = multiFiles.get(i).get(0).get(multiFiles.get(i).get(0).size()-1);
			pMin.add(eachTableSplit.getChunksPmin(eachTableSplit));
			multiFilesSplited.add(eachTableSplit.Chunks);
		}
		//get bruteForth scan order
		BruteForthLoop order = new BruteForthLoop(eachFileChunkAmount);
//		System.out.println("BruteForthLoop" +order.BruteForthOrder);

		for(int i = 0; i < order.BruteForthOrder.size(); i++){

			//put the chunks to join in one List
			ArrayList<ArrayList< ArrayList<Integer> >> ChunksToJoin = new ArrayList<ArrayList< ArrayList<Integer> >>();
			ArrayList<Integer> orderOfChunk = order.BruteForthOrder.get(i);

			
			int[] pMinChunksToJoin = new int[filesAmount];
			for(int count = 0 ; count<orderOfChunk.size(); count++){
				ChunksToJoin.add(multiFilesSplited.get(count).get(orderOfChunk.get(i)));
				pMinChunksToJoin[count] = pMin.get(count)[orderOfChunk.get(i)];
			}
			
			/* Hash join retrived chunks and add the result into priority queue*/
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
		}

		System.out.println(pQueue.minheap.toString()); 
		
		ArrayList<String> AR = new ArrayList<String>();
		AR = AttrResult;
		for (int count = 0;  count < AR.size();  count ++){
			System.out.format("%9s\t", AR.get(count));
		}
		
		System.out.format("\n");
		while (!pQueue.minheap.isEmpty()){
			ArrayList<Integer> printTuple = new ArrayList<Integer>();
			int printScore = 0;
			int min =pQueue.minheap.poll();
            for(Map.Entry<ArrayList<Integer>, Integer >  entry : pQueue.tupleAndScore.entrySet() ){
            	if(entry.getValue().equals(min)){
            		printTuple = entry.getKey();
            		printScore=entry.getValue();
            	}
            } 
            printScore = pQueue.tupleAndScore.get(printTuple);
            System.out.format("%8d\t",printScore);
    		System.out.format("%8d\t", printTuple);
    		
//    		for (int i=0; i< t.title.size()-1; i++){
//    			System.out.format("%8d\t", t.btIDIsIndex.getValue(printTuple).get(i) );		
//    		}	            	
//			System.out.format("%8d",printScore);
			System.out.format("\n");			
		}
		

//		for(Map.Entry<ArrayList<Integer>, Integer > entry : pQueue.tupleAndScore.entrySet()){
////			System.out.println(entry.getKey() + ": " + entry.getValue());
//			System.out.format("%9s\t",entry.getValue().toString() );
//			for(int i = 0;i<entry.getKey().size();i++)
//			System.out.format("%9s\t",entry.getKey().get(i)   );
//			System.out.format("\n");
//			
//		}
		

	}
	
	
	
}
