package extraCredit;

import java.util.*;
import source.*;

public class RankJoin {
	private ArrayList<ArrayList<Float>> leftTable;
	private ArrayList<ArrayList<Float>> rightTable;
	public ArrayList<ArrayList< ArrayList<Float> >> multiFiles;
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
//	private float pLeftMax;
//	private float pLeftMin;
//	private float pRightMax;
//	private float pRightMin;

	/*Constructor*/
	public RankJoin(ArrayList<ArrayList<Float>> table1,ArrayList<ArrayList<Float>> table2, int [] v, int K){
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
	
	public RankJoin(ArrayList<String> fileNames, int[] v, int K,ArrayList<ArrayList<Integer>> AttrToJoin){
	   	   multiFiles = new ArrayList<ArrayList< ArrayList<Float> >>();
	   	   this.v = v;
		   this.K = K;
		   this.AttrToJoin = AttrToJoin;
		   filesAmount = fileNames.size();
//			System.out.println("fileNames.size() "+ fileNames.size());

		   chunkSize = K*2;
		   int accumulatIndex = 0;
		   eachFileSize = new ArrayList<Integer> ();
			multiV = new ArrayList<int[]>();


		   //read files and add score
	   	   for(int i = 0; i < filesAmount; i++){
	   		Initiate init = new Initiate();
			Initiate.DataInit t = init.InitRun(fileNames.get(i));
//			System.out.println("i "+i +" RankJoin t.attrTable.size() "+ t.attrTable.size());
			eachFileSize.add(t.attrTable.get(0).size());
			int[] interV = new int[eachFileSize.get(i)];
			
			
			for(int j = 0;j<eachFileSize.get(i);j++){
//				System.out.println("accumulatIndex "+accumulatIndex+" j "+j);

				interV[j] = v[accumulatIndex +j];

				
			}
			accumulatIndex += eachFileSize.get(i);
//			System.out.println("accumulatIndex "+accumulatIndex);

			multiV.add(interV);
			multiFiles.add(new AddScore(t.attrTable, multiV.get(i)).ScoreAdd());
	   	   }

		   
	   }
	   
	   
	
	

	public float F(float left, float right)
	{
		return left+right;
	}
	
	public void RankJoinRun(int idx1,int idx2){
		float pLeftMax = leftTable.get(0).get(leftAttrSize);
		float pRightMax = rightTable.get(0).get(rightAttrSize);
		Chunk leftChunks = new Chunk(leftTable,chunkSize);
		Chunk rightChunks = new Chunk(rightTable,chunkSize);
		float[] pLeftMin = leftChunks.getChunksPmin(leftChunks);
		float[] pRightMin = rightChunks.getChunksPmin(rightChunks);
		HRJNPriorityQueue<Float> pQueue = new HRJNPriorityQueue<Float>(K);
		int leftChunkNumber = leftChunks.Chunks.size();
		int rightChunkNumber = rightChunks.Chunks.size();
//		int i = 0;
//		int j = 0;
		float threshold = 0.00f;

//		while(true){
//			HashJoin HJN = new HashJoin();
//			float T1 = F(pLeftMax,pRightMin[j]);
//			float T2 = F(pLeftMin[i],pRightMax);
//			threshold = Math.max(T1,T2 );
//			ArrayList<ArrayList<Float>> HJNResult = HJN.hashRankJoin(leftChunks.Chunks.get(i), idx1, rightChunks.Chunks.get(j), idx2);
////			for (ArrayList<Float> r: HJNResult ){
////				ArrayList<Float> inter = new ArrayList<Float>();
////				inter.addAll(r);
////				inter.remove(inter.size()-1);
////				pQueue.addRecord(r.get(r.size()-1), inter);
////			}
//			HJNResult.stream().forEach(r->{
//				ArrayList<Float> inter = new ArrayList<Float>();
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
		
		for(int i = 0; i< leftChunkNumber-1;i++){
			for(int j = 0;j< rightChunkNumber-1;j++){
				HashJoin HJN = new HashJoin();
				float T1 = F(pLeftMax,pRightMin[j]);
				float T2 = F(pLeftMin[i],pRightMax);
				threshold = Math.max(T1,T2 );
				ArrayList<ArrayList<Float>> HJNResult = HJN.hashRankJoin(leftChunks.Chunks.get(i), idx1, rightChunks.Chunks.get(j), idx2);

				HJNResult.stream().forEach(r->{
					ArrayList<Float> inter = new ArrayList<Float>();
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
				if(pQueue.minheap.peek() >= threshold){
					break;
				}
			}
		}
		System.out.println(pQueue.minheap.toString());

		for(Map.Entry<ArrayList<Float>, Float > entry : pQueue.tupleAndScore.entrySet()){
//			System.out.println(entry.getKey() + ": " + entry.getValue());
			System.out.println(entry.getValue().toString()+entry.getKey()   );
		}
		
		
	}
	
	public float getSum(float[] a,int  n){	
		 return n > 0 ? a[n-1] + getSum(a, n - 1) : 0;
		 }
	
	public float getThreshold(float[] pMax, float[] pMin)
	{
		float threshold = 0;
		float sumPMax = getSum(pMax,pMax.length);
		threshold = sumPMax-pMax[0]+pMin[0];
		for (int i = 0;i<pMin.length;i++){
			float value = sumPMax-pMax[i]+pMin[i];
			if(value > threshold){
				threshold = value;
			}
		}
			
		return threshold;
	}
	
	
	
	public void MultiFilesRankJoinRun(){


		//Begins!!!!!
		float threshold = 0.00f;
		HRJNPriorityQueue<Float> pQueue = new HRJNPriorityQueue<Float>(K);
		ArrayList<Integer> eachFileChunkAmount = new ArrayList<Integer>();
		float[] pMax = new float[filesAmount];
		ArrayList<float[]> pMin = new ArrayList<float[]> ();
		ArrayList<ArrayList<ArrayList< ArrayList<Float> >>> multiFilesSplited = new ArrayList<ArrayList<ArrayList< ArrayList<Float> >>>();
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
			ArrayList<ArrayList< ArrayList<Float> >> ChunksToJoin = new ArrayList<ArrayList< ArrayList<Float> >>();
			ArrayList<Integer> orderOfChunk = order.BruteForthOrder.get(i);
			float[] pMinChunksToJoin = new float[filesAmount];
			orderOfChunk.stream().forEach(r->{
				int indexOfR = orderOfChunk.indexOf(r);
				ChunksToJoin.add(multiFilesSplited.get(indexOfR).get(r));
				pMinChunksToJoin[indexOfR] = pMin.get(indexOfR)[r];
			});
			HashJoin HJN = new HashJoin();
			ArrayList<ArrayList<Float>> HJNResult = HJN.multiFileHashRankJoin(ChunksToJoin, AttrToJoin);
			threshold = getThreshold(pMax, pMinChunksToJoin);
			HJNResult.stream().forEach(r->{
				ArrayList<Float> inter = new ArrayList<Float>();
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

		for(Map.Entry<ArrayList<Float>, Float > entry : pQueue.tupleAndScore.entrySet()){
//			System.out.println(entry.getKey() + ": " + entry.getValue());
			System.out.println(entry.getValue().toString()+entry.getKey()   );
		}
		

	}
	
	
	
}
