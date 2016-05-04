package extraCredit;
import java.util.*;

public class Chunk  {
	private ArrayList<ArrayList<Float>> table;
	int ChunkSize;
	public ArrayList<ArrayList<ArrayList<Float>>> Chunks;
	double ChunkMaxIndex;
	public Chunk(ArrayList<ArrayList<Float>> table, int ChunkSize){
		this.table = table;
		this.ChunkSize = ChunkSize;
		if(this.table.size()/(double)ChunkSize == Math.floor(table.size()/(double)ChunkSize) ){
			ChunkMaxIndex = Math.floor(table.size()/(double)ChunkSize) -1;
		}
		else{
			ChunkMaxIndex = Math.floor(table.size()/(double)ChunkSize);
		}
		this.Chunks = splitTable(table);
//		System.out.println("ChunkNumber in constructor "+ Chunks.size());
//		Chunks.stream().forEach(r->{
//			System.out.println(r);
//		});
		
	}
	public ArrayList<ArrayList<ArrayList<Float>>> splitTable(ArrayList<ArrayList<Float>> table){
		ArrayList<ArrayList<ArrayList<Float>>> Chunks = new ArrayList<ArrayList<ArrayList<Float>>>();
		if(ChunkSize >= table.size()){
			Chunks.add(table);
		}
		else{

			for(int i = 0;i<= ChunkMaxIndex;i++ ){
				ArrayList<ArrayList<Float>> interResult = new ArrayList<ArrayList<Float>>();
				for(int j = 0; j< ChunkSize;j++){
					if(i*ChunkSize+j <table.size()-1)
					{
						interResult.add(table.get(i*ChunkSize+j));
						
					}
					else{
						break;
					}
				}
				Chunks.add(interResult);
			}
		}

		return Chunks;
	}
	public float getPmin(ArrayList<ArrayList<Float>> tableChunk){
		int tupleLength = tableChunk.get(0).size();
		float p = tableChunk.get(tableChunk.size()-1).get(tupleLength-1);
//		System.out.println(p);
		return p;
	}
	public float[] getChunksPmin(Chunk CKS){
		ArrayList<ArrayList<ArrayList<Float>>> CK = CKS.Chunks;
		float[] Pmin = new float[CK.size()];
		for(int i = 0;i< CK.size();i++)
		{
			Pmin[i] = getPmin(CK.get(i));
		}

		return Pmin;
	}
}
