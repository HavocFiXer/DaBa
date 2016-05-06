package IntBTree;

import java.util.ArrayList;

public class IntBTree{
	private static int M=4;
	private Node root;
	private int height;
	private int N;

	private ArrayList<Integer> keyList=new ArrayList<Integer>();
	private ArrayList<ArrayList<Integer>> valueList=new ArrayList<ArrayList<Integer>>();

	private static class Node{
		private int m;
		private Element[] children=new Element[M];
		private Node(int k){
			m=k;
		}
	}

	private static class Element{
		private int key;
		private ArrayList<Integer> value;
		private Node next;
		public Element(int key, ArrayList<Integer> value, Node next){
			this.key=key;
			this.value=value;
			this.next=next;
		}
	}

	public IntBTree(){
		root=new Node(0);
	}

	public boolean isEmpty(){
		return size()==0;
	}

	public int size(){
		return N;
	}

	public int height(){
		return height;
	}

	public void setLimit(int k){
		M=k;
	}

	public ArrayList<Integer> getValue(int key){
		return search(root, key, height);
	}
	
	public ArrayList<Integer> getKeyList(){
		return keyList;
	}

	public ArrayList<ArrayList<Integer>> getValueList(){
		return valueList;
	}

	private ArrayList<Integer> search(Node x, int key, int ht){
		if(ht==0){
			for(int i=0;i<x.m;++i){
				if(key==x.children[i].key) return x.children[i].value;
			}
		}
		else{
			for(int i=0;i<x.m;++i){
				if(i+1==x.m||key>x.children[i+1].key) return search(x.children[i].next,key,ht-1);
			}
		}
		return null;
	}

	public void insert(int key, ArrayList<Integer> value){
		Node u=ins(root,key,value,height);
		++N;
		if(u==null) return;

		Node t = new Node(2);
		t.children[0]=new Element(root.children[0].key,null,root);
		t.children[1]=new Element(u.children[0].key,null,u);
		root=t;
		++height;
	}

	private Node ins(Node h, int key, ArrayList<Integer> value, int ht){
		int po;
		Element t=new Element(key,value,null);
		if(ht==0){
			for(po=0;po<h.m;++po){
				if(key>h.children[po].key) break;
			}
		}
		else{
			for(po=0;po<h.m;++po){
				if(po+1==h.m||key>h.children[po+1].key){
					Node u=ins(h.children[po++].next,key,value,ht-1);
					if(u==null) return null;
					t.key=u.children[0].key;
					t.next=u;
					break;
				}
			}
		}
		for(int i=h.m;i>po;--i){
			h.children[i]=h.children[i-1];
		}
		h.children[po]=t;
		++h.m;
		if(h.m<M) return null;
		else{
			Node sec=new Node(M/2);
			h.m=M/2;
			for(int i=0;i<M/2;++i){
				sec.children[i]=h.children[M/2+i];
			}
			return sec;
		}
	}

	public void setKeyValueLists(){
		Traverse(root,height);
	}

	private void Traverse(Node h, int ht){
		if(ht==0){
			for(int i=0;i<h.m;++i){
				this.keyList.add(h.children[i].key);
				this.valueList.add(h.children[i].value);
			}
		}
		else{
			for(int i=0;i<h.m;++i){
				Traverse(h.children[i].next,ht-1);
			}
		}
	}
}
