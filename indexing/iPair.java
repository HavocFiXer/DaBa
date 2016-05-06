package iPair;

public class iPair<Key extends Comparable<Key>, Value>{
	private Key key;
	private Value value;
	public iPair(Key key, Value value){
		this.key=key;
		this.value=value;
	}
	public int valueCompare(iPair ipair){
		return ((Comparable)key).compareTo((Comparable)ipair.key);
	}
}
