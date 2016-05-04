package extraCredit;

import java.util.*;


public class RankJoin {
	private ArrayList<ArrayList<Float>> leftTable;
	private ArrayList<ArrayList<Float>> rightTable;
	int[] v;
	int K;
	int chunkSize;
	int leftAttrSize;
	int rightAttrSize;
	int leftTableSize;
	int rightTableSize;
//	private float pLeftMax;
//	private float pLeftMin;
//	private float pRightMax;
//	private float pRightMin;

	public RankJoin(ArrayList<ArrayList<Float>> table1,ArrayList<ArrayList<Float>> table2, int [] v, int K){
		//score and ordering 
		this.v = v;
		this.K = K;
		chunkSize = K*2;
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
		int i = 0;
		int j = 0;
		float threshold = 0.00f;

		while(true){
			HashJoin HJN = new HashJoin();
//		System.out.println("i"+i + " j " + j);
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
			
			
			//Choose the input to retrive
			else{
				if(T1>=T2 ){
					if(j<rightChunkNumber-1){
						j++;
					}
					else{
						i++;
						j=0;
					}
				}
				else{
					if(i<leftChunkNumber-1){
						i++;
						j = 0;
					}
					else{
						j++;
						i = 0;
					}
				}
			}

		}
		System.out.println(pQueue.minheap.toString());

		for(Map.Entry<Float, ArrayList<Float>> entry : pQueue.scoreAndTuple.entrySet()){
			//System.out.println(entry.getKey() + ": " + entry.getValue());
			System.out.println(entry.getKey() + entry.getValue().toString() );
		}
		
		
	}
	
	
}
