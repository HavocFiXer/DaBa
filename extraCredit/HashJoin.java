package extraCredit;

import java.util.*;

 
public class HashJoin {
 
    private float i;
    public ArrayList<Float> leftId;
    public ArrayList<Float> rightId;
    
    
   public ArrayList< ArrayList<Float> > hashJoin(ArrayList< ArrayList<Float> > relation1, int idx1,
            ArrayList< ArrayList<Float> > relation2, int idx2) {
        leftId = new ArrayList<Float>();
        rightId = new ArrayList<Float>();
       // i = 0.00f;
        ArrayList< ArrayList<Float> > result = new ArrayList<>();
        Map<Integer, ArrayList< ArrayList<Float> >> map = new HashMap<>();
        //Map<Integer, ArrayList< ArrayList<Float> >> map2 = new HashMap<>();
        for (ArrayList<Float> tuple : relation1) {
            ArrayList< ArrayList<Float> >  v = map.getOrDefault(Math.round(tuple.get(idx1)%10), new ArrayList<>());
            v.add(tuple);
            map.put(Math.round(tuple.get(idx1)%10), v);
        }
        
//        for (ArrayList<Float> tuple : relation2) {
//            ArrayList< ArrayList<Float> >  v2 = map2.getOrDefault(Math.round(tuple.get(idx2)%10), new ArrayList<>());
//            v2.add(tuple);
//            map2.put(Math.round(tuple.get(idx2)%10), v2);
//        }
//        Object[] key = map.keySet().toArray();
//        for ( int j = 0; j<key.length;j++){
//          ArrayList< ArrayList<Float> >  lst1 = map.get(key[j]);
//          ArrayList< ArrayList<Float> >  lst2 = map2.get(key[j]);
//          if(lst1 != null & lst2!= null){
//              for(ArrayList<Float> tuple : lst1){
//                  lst2.stream().forEach(r ->{
//                  if(tuple.get(idx1).equals(r.get(idx2))){
//                   ArrayList<Float> inter = new ArrayList<Float>();
//                   leftId.add(tuple.get(0));
//                   rightId.add(r.get(0));
//                   inter.addAll(tuple);
//                   inter.addAll(r);
//                   //delete the id
//                   inter.set(0,i);
//                   i = i+1.00f;
//                   result.add(inter);
//               }
//              
//          });
//        }
//        }
//        }
        
        
        // i = 0.00f;
        for (ArrayList<Float>  tuple : relation2) {
            ArrayList< ArrayList<Float> >  lst = map.get(Math.round(tuple.get(idx2)%10));
            if (lst != null) {
                
                lst.stream().forEach(r ->{

                    if(tuple.get(idx2).equals(r.get(idx1)))
                    {
                        ArrayList<Float> inter = new ArrayList<Float>();
                        leftId.add(r.get(0));
                        rightId.add(tuple.get(0));
                        inter.addAll(r);
                        inter.addAll(tuple);
                        //delete the id
                        inter.set(0,i);
                        inter.remove(r.size());
                        result.add(inter);
                        i = i+1.00f;
                    }
                });
                
                
            }
        }
        if(result.size() !=0)

        {
//          for(ArrayList<Float> r : result){
//              System.out.println(r);
//            }
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
//      ArrayList< ArrayList<Float> > table1 = new ArrayList< ArrayList<Float> >();
//      ArrayList<Float> test1 = new ArrayList<Float>();
//      test1.add(1.00f);
//      test1.add(2.00f);
//      test1.add(1.00f);
//      test1.add(2.00f);
//      test1.add(1.00f);
//      test1.add(2.00f);
//      table1.add(test1);
//      ArrayList<Float> test2 = new ArrayList<Float>();
//      test2.add(3.00f);
//      test2.add(4.00f);
//      test2.add(3.00f);
//      test2.add(4.00f);
//      test2.add(3.00f);
//      test2.add(4.00f);
//      table1.add(test2);
//      ArrayList< ArrayList<Float> > table2 = new ArrayList< ArrayList<Float> >();
//      ArrayList<Float> test3 = new ArrayList<Float>();
//      test3.add(6.00f);
//      test3.add(7.00f);
//      test3.add(6.00f);
//      test3.add(7.00f);
//      test3.add(6.00f);
//      test3.add(7.00f);
//      ArrayList<Float> test4 = new ArrayList<Float>();
//      test4.add(3.00f);
//      test4.add(9.00f);
//      test4.add(3.00f);
//      test4.add(9.00f);
//      test4.add(3.00f);
//      test4.add(9.00f);
//      table2.add(test3);
//      table2.add(test4);
//      System.out.println(table1);
//      System.out.println(table2);
//
//        hashJoin(table1, 5, table2, 5).stream()
//                .forEach(r -> System.out.println(r));
//    }
}

