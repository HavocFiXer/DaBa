package source;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
/*Build a PriorityQueue with fixed size k, maintain min at root*/

public class FixSizedPriorityQueue<E extends Comparable<E>, I> {
    public PriorityQueue<E> minheap;
    public Map<I, E> idAndAttri; 	// top k pairs of id and its score
    private int maxSize; // max storage space
 
    public FixSizedPriorityQueue(int maxSize) {
        if (maxSize <= 0)
            throw new IllegalArgumentException();
        this.maxSize = maxSize;

        this.minheap = new PriorityQueue<E>(maxSize, new Comparator<E>(){
        	public int compare(E o1, E o2) {
            // max heap, o2-o1; min heap, o1-o2, change e.compareTo(peek) for compare rule
        	return (o1.compareTo(o2));    
            }
        });   
        this.idAndAttri = new HashMap<I, E>();
	} 
    
    /*if heap size less than k, add records directly; 
    after heap is full,compare new element with root, store larger */
    public void addRecord(E e, I id) {
        if (minheap.size() < maxSize){ 
            minheap.add(e);
            idAndAttri.put(id,  e);
        } else { 
            E min = minheap.peek();
            if (e.compareTo(min) > 0) { 
                minheap.poll();
                minheap.add(e);
                // remove <ID, min> from the queue
                // there may be more than one entry with the value min in the queue, and pick whichever
                for(Map.Entry<I, E> entry : idAndAttri.entrySet()){
                	if(entry.getValue().equals(min)){
                		idAndAttri.remove(entry.getKey());
                		break;
                	}
                }
                idAndAttri.put(id, e);
            }
        }
    }
 
}