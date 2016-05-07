package indexing;

import java.util.ArrayList;

public class IntBTree{

	private Node root;
	private static int Limit=60;
	private int height;
	private ArrayList<Integer> keyList=new ArrayList<Integer>();
	private ArrayList<ArrayList<Integer>> valueList=new ArrayList<ArrayList<Integer>>();

	private class Node{
		private int cnumber;//children number
		private Element[] children=new Element[Limit];
		public Node(int number){
			cnumber=number;
		}
	}

	private class Element{
		private int key;
		private ArrayList<Integer> value;
		private Node next;
		public Element(int inputKey, ArrayList<Integer> inputValue, Node inputNext){
			key=inputKey;
			value=inputValue;
			next=inputNext;
		}
		public Element(int inputKey, ArrayList<Integer> inputValue){
			key=inputKey;
			value=inputValue;
			next=null;
		}
	}

	public IntBTree(){
		root=new Node(0);
	}

	public void setLimit(int limit){
		Limit=limit;
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

	public void setKeyValueLists(){
		Traverse(root,height);
	}

	private void Traverse(Node node, int ht){
		if(ht==0){
			for(int i=0;i<node.cnumber;++i){
				this.keyList.add(node.children[i].key);
				this.valueList.add(node.children[i].value);
			}
		}
		else{
			for(int i=0;i<node.cnumber;++i){
				Traverse(node.children[i].next,ht-1);
			}
		}
	}

	private ArrayList<Integer> search(Node node, int key, int ht){
		if(ht==0){
			for(int i=0;i<node.cnumber;++i){
				if(key==node.children[i].key) return node.children[i].value;
			}
		}
		else{
			for(int i=0;i<node.cnumber;++i){
				if(i+1==node.cnumber||key>node.children[i+1].key) return search(node.children[i].next,key,ht-1);
			}
		}
		return null;
	}

	public void insert(int key, ArrayList<Integer> value){
		Node node=ins(root,key,value,height);
		if(null==node) return;

		Element rootleft=new Element(root.children[0].key,null,root); 
		Element rootright=new Element(node.children[0].key,null,node);

		Node newroot=new Node(2);
		newroot.children[1]=rootright;
		newroot.children[0]=rootleft;
		root=newroot;
		++height;
	}

	private Node ins(Node node, int key, ArrayList<Integer> value, int ht){
		int po;
		Element element=new Element(key,value);
		if(ht==0){
			for(po=0;po<node.cnumber;++po){
				if(key>node.children[po].key) break;
			}
		}
		else{
			for(po=0;po<node.cnumber;++po){
				if(node.children[po+1].key<key||node.cnumber-1==po){
					Node tmpnode=ins(node.children[po++].next,key,value,ht-1);
					if(null==tmpnode) return tmpnode;
					element.key=tmpnode.children[0].key;
					element.value=null;
					element.next=tmpnode;
					break;
				}
			}
		}
		for(int i=node.cnumber;i>po;--i){
			node.children[i]=node.children[i-1];
		}
		node.children[po]=element;
		++node.cnumber;
		if(node.cnumber<Limit) return null;
		else{
			int half=Limit/2;
			Node sec=new Node(half);
			node.cnumber=half;
			for(int i=0;i<half;++i){
				sec.children[i]=node.children[half+i];
			}
			return sec;
		}
	}

}
