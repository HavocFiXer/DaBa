package source;
import java.util.*;

public class Chunk  {
	private ArrayList<ArrayList<Integer>> table;
	int ChunkSize;
	public ArrayList<ArrayList<ArrayList<Integer>>> Chunks;
	double ChunkMaxIndex;
	public Chunk(ArrayList<ArrayList<Integer>> table, int ChunkSize){
		this.table = table;
		this.ChunkSize = ChunkSize;
		if(this.table.size()/(double)ChunkSize == Math.floor(table.size()/(double)ChunkSize) ){
			ChunkMaxIndex = Math.floor(table.size()/(double)ChunkSize) -1;
		}
		else{
			ChunkMaxIndex = Math.floor(table.size()/(double)ChunkSize);
		}
		this.Chunks = splitTable(table);
	}
	public ArrayList<ArrayList<ArrayList<Integer>>> splitTable(ArrayList<ArrayList<Integer>> table){
		ArrayList<ArrayList<ArrayList<Integer>>> Chunks = new ArrayList<ArrayList<ArrayList<Integer>>>();
		if(ChunkSize >= table.size()){
			Chunks.add(table);
		}
		else{
			for(int i = 0;i<= ChunkMaxIndex;i++ ){
				ArrayList<ArrayList<Integer>> interResult = new ArrayList<ArrayList<Integer>>();
				for(int j = 0; j< ChunkSize;j++){
					if(i*ChunkSize+j <table.size())
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
	public Integer getPmin(ArrayList<ArrayList<Integer>> tableChunk){
		int tupleLength = tableChunk.get(0).size();
		Integer p = tableChunk.get(tableChunk.size()-1).get(tupleLength-1);
		return p;
	}
	public Integer[] getChunksPmin(Chunk CKS){
		ArrayList<ArrayList<ArrayList<Integer>>> CK = CKS.Chunks;
		Integer[] Pmin = new Integer[CK.size()];
		for(int i = 0;i< CK.size();i++)
		{
			Pmin[i] = getPmin(CK.get(i));
		}

		return Pmin;
	}
}
