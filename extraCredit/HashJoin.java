package extraCredit;

import java.util.*;
import source.*;

public class HashJoin {
 
	private Integer newId;
	public ArrayList<Integer> leftId;
	public ArrayList<Integer> rightId;
	
	/* hash join on two tables deleteing ids in two tables and resign new id*/
   public ArrayList< ArrayList<Integer> > hashJoin(ArrayList< ArrayList<Integer> > relation1, int idx1,
            ArrayList< ArrayList<Integer> > relation2, int idx2 ) {
	    leftId = new ArrayList<Integer>();
	    rightId = new ArrayList<Integer>();
        ArrayList< ArrayList<Integer> > result = new ArrayList<>();
        Map<Integer, ArrayList< ArrayList<Integer> >> map = new HashMap<>();
        for (ArrayList<Integer> tuple : relation1) {
            ArrayList< ArrayList<Integer> >  v = map.getOrDefault(Math.round(tuple.get(idx1)%10), new ArrayList<>());
            v.add(tuple);
            map.put(Math.round(tuple.get(idx1)%10), v);
        }
       
         newId = 0;
        for (ArrayList<Integer>  tuple : relation2) {
            ArrayList< ArrayList<Integer> >  lst = map.get(Math.round(tuple.get(idx2)%10));
            if (lst != null) {
                lst.stream().forEach(r ->{
                    if(tuple.get(idx2).equals(r.get(idx1))){
                        ArrayList<Integer> inter = new ArrayList<Integer>();
                       inter.addAll(r);
                        inter.addAll(tuple);
                        //delete the id
                        inter.set(0,newId);
                        inter.remove(r.size());
                    	result.add(inter);
                    	newId = newId+1;
                    }
                });
            }
        }
        if(result.size() !=0){
//        	for(ArrayList<Integer> r : result){
//            	System.out.println(r);
//            }
        }
        else{
        	// System.out.println("Hash Join has no results");
        }
        return result;
    }
   
   //hash Rank join on table contaioning score ( not deleting id) 
   public ArrayList< ArrayList<Integer> > hashRankJoin(ArrayList< ArrayList<Integer> > relation1, int idx1,
           ArrayList< ArrayList<Integer> > relation2, int idx2 ) {
       ArrayList< ArrayList<Integer> > result = new ArrayList<>();
       //build hash table 
       Map<Integer, ArrayList< ArrayList<Integer> >> map = new HashMap<>();
       for (ArrayList<Integer> tuple : relation1) {
           ArrayList< ArrayList<Integer> >  v = map.getOrDefault(Math.round(tuple.get(idx1)%10), new ArrayList<>());
           v.add(tuple);
           map.put(Math.round(tuple.get(idx1)%10), v);
       }
       
       for (ArrayList<Integer>  tuple : relation2) {
           ArrayList< ArrayList<Integer> >  lst = map.get(Math.round(tuple.get(idx2)%10));
           if (lst != null) {
               lst.stream().forEach(r ->{
            	   if(tuple.get(idx2).equals(r.get(idx1))){
                       ArrayList<Integer> inter = new ArrayList<Integer>();
               		   Integer score = r.get(r.size()-1)+ tuple.get(tuple.size()-1);
                       inter.addAll(r);
                       inter.addAll(tuple);
                       inter.add(score);
                       inter.remove(r.size()-1);
                       inter.remove(inter.size()-2);
                   	result.add(inter);
                   }
               });
           }
       }
       if(result.size() !=0){
//       	for(ArrayList<Integer> r : result){
//           	System.out.println(r);
//           }
       }
       else{
       	//System.out.println("Hash Join has no results");
       }
       return result;
   }
   
   public  ArrayList< ArrayList<Integer> > multiFileHashRankJoin( ArrayList<ArrayList< ArrayList<Integer> >> multiFiles, 
		   ArrayList<ArrayList<Integer>> AttrToJoin ){
		/*
	   attriToJoin is like [[1,3,2,3],[1,4,3,2]] which means: file1 join file2 with condition file1.att3 = file2.att3; 
	   and then join file3 with condition file1.attr4 = file3.attr2 
	  */

	   ArrayList< ArrayList<Integer> > result = new ArrayList< ArrayList<Integer> >();
	   result.addAll(multiFiles.get(0));
	   for(int i =1; i < multiFiles.size();i++){
		   int idxRight = AttrToJoin.get(i-1).get(3);
		   int idxLeft = AttrToJoin.get(i-1).get(1);
		   int leftFileNumber = AttrToJoin.get(i-1).get(0);
		   for (int j = 0; j < leftFileNumber-1; j++){
			   idxLeft = idxLeft + multiFiles.get(j).get(0).size()-1;
		   }
		   result = hashRankJoin(result, idxLeft, multiFiles.get(i), idxRight);
	   }
	   return result;

   }
}

