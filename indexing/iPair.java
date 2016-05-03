package iPair;

public class iPair<Key extends Comparable<Key>, Value>{
	private Key key;
	private Value value;
	public iPair(Key key, Value value){
		this.key=key;
		this.value=value;
	}
}
