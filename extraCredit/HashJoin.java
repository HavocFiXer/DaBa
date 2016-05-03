package extraCredit;

import java.util.*;

 
public class HashJoin {
 

 
   public ArrayList< ArrayList<Float> > hashJoin(ArrayList< ArrayList<Float> > relation1, int idx1,
            ArrayList< ArrayList<Float> > relation2, int idx2) {
 
        ArrayList< ArrayList<Float> > result = new ArrayList<>();
        
        Map<Integer, ArrayList< ArrayList<Float> >> map = new HashMap<>();
 
        for (ArrayList<Float> tuple : relation1) {
            ArrayList< ArrayList<Float> >  v = map.getOrDefault(Math.round(tuple.get(idx1)%10), new ArrayList<>());
            v.add(tuple);
            map.put(Math.round(tuple.get(idx1)%10), v);
        }
        //System.out.println(map);

        for (ArrayList<Float>  tuple : relation2) {

            ArrayList< ArrayList<Float> >  lst = map.get(Math.round(tuple.get(idx2)%10));
            //System.out.println(tuple.get(idx2));

            if (lst != null) {
            	
            	
            	
                lst.stream().forEach(r ->{

                    if(tuple.get(idx2).equals(r.get(idx1)))
                    {
                        ArrayList<Float> inter = new ArrayList<Float>();
                        inter.addAll(r);
                        inter.addAll(tuple);
                    	result.add(inter);
                        //System.out.println(result);

                    }
                });
                
                
            }
        }
        //output 
        if(result.size() !=0)

        {
        	for(ArrayList<Float> r : result){
            	System.out.println(r);
            }
        }
        else
        {
        	System.out.println("Hash Join has no results");
        }
        
        
        return result;
    }
//    public static void main(String[] args) {
//    	
//    	
//    	ArrayList< ArrayList<Float> > table1 = new ArrayList< ArrayList<Float> >();
//    	ArrayList<Float> test1 = new ArrayList<Float>();
//    	test1.add(1.00f);
//    	test1.add(2.00f);
//    	test1.add(1.00f);
//    	test1.add(2.00f);
//    	test1.add(1.00f);
//    	test1.add(2.00f);
//    	table1.add(test1);
//    	ArrayList<Float> test2 = new ArrayList<Float>();
//    	test2.add(3.00f);
//    	test2.add(4.00f);
//    	test2.add(3.00f);
//    	test2.add(4.00f);
//    	test2.add(3.00f);
//    	test2.add(4.00f);
//    	table1.add(test2);
//    	ArrayList< ArrayList<Float> > table2 = new ArrayList< ArrayList<Float> >();
//    	ArrayList<Float> test3 = new ArrayList<Float>();
//    	test3.add(6.00f);
//    	test3.add(7.00f);
//    	test3.add(6.00f);
//    	test3.add(7.00f);
//    	test3.add(6.00f);
//    	test3.add(7.00f);
//    	ArrayList<Float> test4 = new ArrayList<Float>();
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
