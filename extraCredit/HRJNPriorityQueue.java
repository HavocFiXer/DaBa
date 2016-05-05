package extraCredit;

import java.util.*;

public class HRJNPriorityQueue<E extends Comparable<E>> {
	public PriorityQueue<E> minheap;
	public Map<ArrayList<Integer>,E > tupleAndScore;
	private int maxSize;
	
	public HRJNPriorityQueue(int maxSize){
		if (maxSize <= 0)
            throw new IllegalArgumentException();
        this.maxSize = maxSize;

        this.minheap = new PriorityQueue<E>(maxSize, new Comparator<E>(){
        	public int compare(E o1, E o2) {
            // max heap, o2-o1; min heap, o1-o2, change e.compareTo(peek) for compare rule
        	return (o1.compareTo(o2));    
            }
        });   
        this.tupleAndScore = new HashMap<ArrayList<Integer>, E >();
	}
    public void addRecord(E score, ArrayList<Integer> tuple ) {
        if (minheap.size() < maxSize){ //less than maxSize, add directly
            minheap.add(score);
            tupleAndScore.put(tuple,score  );
        } else { // heap is full
            E min = minheap.peek();
            if (score.compareTo(min) > 0) { //compare new element with root, store larger
                minheap.poll();
                minheap.add(score);
                // remove <ID, min> from the queue
                // there may be more than one entry with the value min in the queue, and pick whichever
                for(Map.Entry<ArrayList<Integer>, E > entry : tupleAndScore.entrySet()){
                	if(entry.getValue().equals(min)){
                		tupleAndScore.remove(entry.getKey());
                		break;
                	}
                }
                tupleAndScore.put(tuple,score );

            }
        }
    }
}

