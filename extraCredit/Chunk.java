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
//		System.out.println("Constructor ChunkNumber"+ ChunkMaxIndex +"sijfids"+this.table.size()/(double)ChunkSize);
	}
	public ArrayList<ArrayList<ArrayList<Float>>> splitTable(ArrayList<ArrayList<Float>> table){
		ArrayList<ArrayList<ArrayList<Float>>> Chunks = new ArrayList<ArrayList<ArrayList<Float>>>();
		if(ChunkSize >= table.size()){
			Chunks.add(table);
		}
		else{
//			System.out.println("MaxIndex"+ChunkMaxIndex);

			for(int i = 0;i<= ChunkMaxIndex;i++ ){
				ArrayList<ArrayList<Float>> interResult = new ArrayList<ArrayList<Float>>();
				for(int j = 0; j< ChunkSize;j++){
					if(i*ChunkSize+j <table.size())
					{
						interResult.add(table.get(i*ChunkSize+j));
//						if(i > ChunkMaxIndex-2)
//						{
//							System.out.println((i*ChunkSize+j) + "tablesize "+(table.size()-1) );
//							System.out.println("System"+(table.size()-1)+" Tuple "+table.get(table.size()-1) );
//
//						}
					}
					else{
//						System.out.println((i*ChunkSize+j) + "tablesize "+(table.size()-1) );
						break;
					}
				}
//				System.out.println("interresult size" + interResult.size());
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
//		System.out.println("CK.size "+CK.size() );
//		System.out.println("CK.last "+CK.get(CK.size()-1) );

		float[] Pmin = new float[CK.size()];
		for(int i = 0;i< CK.size();i++)
		{
			Pmin[i] = getPmin(CK.get(i));
//			System.out.println("i: "+i+" Pmin.length "+Pmin.length);
		}

		return Pmin;
	}
}
