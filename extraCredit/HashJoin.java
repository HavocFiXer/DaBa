package extraCredit;

import java.util.*;
import source.*;

public class HashJoin {
 
	private Integer i;
	public ArrayList<Integer> leftId;
	public ArrayList<Integer> rightId;
	
	
   public ArrayList< ArrayList<Integer> > hashJoin(ArrayList< ArrayList<Integer> > relation1, int idx1,
            ArrayList< ArrayList<Integer> > relation2, int idx2 ) {
	    leftId = new ArrayList<Integer>();
	    rightId = new ArrayList<Integer>();
	   // i = 0.00f;
        ArrayList< ArrayList<Integer> > result = new ArrayList<>();
        Map<Integer, ArrayList< ArrayList<Integer> >> map = new HashMap<>();
        //Map<Integer, ArrayList< ArrayList<Integer> >> map2 = new HashMap<>();
        for (ArrayList<Integer> tuple : relation1) {
            ArrayList< ArrayList<Integer> >  v = map.getOrDefault(Math.round(tuple.get(idx1)%10), new ArrayList<>());
            v.add(tuple);
            map.put(Math.round(tuple.get(idx1)%10), v);
        }
//        for (ArrayList<Integer> tuple : relation2) {
//            ArrayList< ArrayList<Integer> >  v2 = map2.getOrDefault(Math.round(tuple.get(idx2)%10), new ArrayList<>());
//            v2.add(tuple);
//            map2.put(Math.round(tuple.get(idx2)%10), v2);
//        }
//        Object[] key = map.keySet().toArray();
//        for ( int j = 0; j<key.length;j++){
//        	ArrayList< ArrayList<Integer> >  lst1 = map.get(key[j]);
//        	ArrayList< ArrayList<Integer> >  lst2 = map2.get(key[j]);
//        	if(lst1 != null & lst2!= null){
//        		for(ArrayList<Integer> tuple : lst1){
//                  lst2.stream().forEach(r ->{
//        			if(tuple.get(idx1).equals(r.get(idx2))){
//        			 ArrayList<Integer> inter = new ArrayList<Integer>();
//        			 leftId.add(tuple.get(0));
//        			 rightId.add(r.get(0));
//        			 inter.addAll(tuple);
//        			 inter.addAll(r);
//        			 //delete the id
//        			 inter.set(0,i);
//        			 i = i+1.00f;
//        			 result.add(inter);
//        		 }
//        		
//        	});
//        }
//        }
//        }
        
         i = 0;
        for (ArrayList<Integer>  tuple : relation2) {
            ArrayList< ArrayList<Integer> >  lst = map.get(Math.round(tuple.get(idx2)%10));
            if (lst != null) {
                lst.stream().forEach(r ->{
                    if(tuple.get(idx2).equals(r.get(idx1))){
                        ArrayList<Integer> inter = new ArrayList<Integer>();
//                      leftId.add(r.get(0));
//                		rightId.add(tuple.get(0));
                        inter.addAll(r);
                        inter.addAll(tuple);
                        //delete the id
                        inter.set(0,i);
                        inter.remove(r.size());
                    	result.add(inter);
                    	i = i+1;
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
        	System.out.println("Hash Join has no results");
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
//                       System.out.println(score);
                   	result.add(inter);
//                   	i = i+1;
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
	   // read files 
//	   	   ArrayList<ArrayList< ArrayList<Integer> >> multiFiles = new ArrayList<ArrayList< ArrayList<Integer> >>();
//	   for(String str : fileName){
//			Initiate init = new Initiate();
//			Initiate.DataInit t = init.InitRun(str);
//		   multiFiles.add(t.attrTable); //need to recheck!!!!!!
//	   }
	   ArrayList< ArrayList<Integer> > result = new ArrayList< ArrayList<Integer> >();
	   result.addAll(multiFiles.get(0));
	   for(int i = 1; i < multiFiles.size();i++){
//		   System.out.println("i "+i);
//		   System.out.println("multiFiles.size() "+multiFiles.size());

		   
		   int idxRight = AttrToJoin.get(i-1).get(3);
		   int idxLeft = 0;
		   for (int j = 0; j< i-1;j++){
			   idxLeft = idxLeft + multiFiles.get(i-1).get(0).size();
		   }
		   idxLeft = idxLeft + AttrToJoin.get(i-1).get(1);
//		   System.out.println(" idxLeft " + idxLeft+ " idxRight "+ idxRight);
		   result = hashRankJoin(result, idxLeft, multiFiles.get(i), idxRight);
//		   System.out.println(" idxLeft " + idxLeft+ " idxRight "+ idxRight);
	   }
	   return result;

   }
//    public static void main(String[] args) {
//    	
//    	
//    	ArrayList< ArrayList<Integer> > table1 = new ArrayList< ArrayList<Integer> >();
//    	ArrayList<Integer> test1 = new ArrayList<Integer>();
//    	test1.add(1.00f);
//    	test1.add(2.00f);
//    	test1.add(1.00f);
//    	test1.add(2.00f);
//    	test1.add(1.00f);
//    	test1.add(2.00f);
//    	table1.add(test1);
//    	ArrayList<Integer> test2 = new ArrayList<Integer>();
//    	test2.add(3.00f);
//    	test2.add(4.00f);
//    	test2.add(3.00f);
//    	test2.add(4.00f);
//    	test2.add(3.00f);
//    	test2.add(4.00f);
//    	table1.add(test2);
//    	ArrayList< ArrayList<Integer> > table2 = new ArrayList< ArrayList<Integer> >();
//    	ArrayList<Integer> test3 = new ArrayList<Integer>();
//    	test3.add(6.00f);
//    	test3.add(7.00f);
//    	test3.add(6.00f);
//    	test3.add(7.00f);
//    	test3.add(6.00f);
//    	test3.add(7.00f);
//    	ArrayList<Integer> test4 = new ArrayList<Integer>();
//    	test4.add(3.00f);
//    	test4.add(9.00f);
//    	test4.add(3.00f);
//    	test4.add(9.00f);
//    	test4.add(3.00f);
//    	test4.add(9.00f);
//    	table2.add(test3);
//    	table2.add(test4);
//    	System.out.println(table1);
//    	System.out.println(table2);
//
//        hashJoin(table1, 5, table2, 5).stream()
//                .forEach(r -> System.out.println(r));
//    }
}

