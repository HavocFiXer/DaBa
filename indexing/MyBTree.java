package MyBTree;

import java.util.ArrayList;


public class MyBTree<Key extends Comparable<Key>, Value>{
	private BTreeNode root;
	private static int childrenLimit;
	private int height;
	private int totalKeyValuePair;

	private static class BTreeElement{
		private Comparable key;
		private Object value;
		private BTreeNode nextNode;
		public BTreeElement(Comparable key, Object value, BTreeNode nextNode){
			this.key=key;
			this.value=value;
			this.nextNode=nextNode;
		}
	}

	private static class BTreeNode{
		private int childrenNumber;
		private BTreeElement[] children = new BTreeElement[childrenLimit];
		public BTreeNode(int number){
			childrenNumber=number;
		}
	}

	public MyBTree(int limit){
		childrenLimit=limit;
		root=new BTreeNode(0);
	}

	private Value searchValue(BTreeNode searchNode, Key searchKey, int searchHeight) {
		if(searchKey==null){
			throw new NullPointerException("NULL key");
		}
		if(searchHeight==0){
			for(int i=0;i<searchNode.childrenNumber;++i){
				if(((Comparable)searchNode.children[i]).compareTo((Comparable)searchKey)==0){
					return (Value)searchNode.children[i].value;
				}
			}
		}
		else{
			for(int i=0;i<searchNode.childrenNumber;++i){
				if(i+1==searchNode.childrenNumber||((Comparable)searchNode.children[i]).compareTo((Comparable)searchKey)<0){//<0?
					return searchValue(searchNode.children[i].nextNode,searchKey,searchHeight-1);
				}
			}
		}
		return null;
	}

	public Value getValue(Key key){
		return searchValue(root, key, height);
	}

	private BTreeNode insertPair(BTreeNode insertNode, Key insertKey, Value insertValue, int insertHeight)
	{
		int position;
		BTreeElement tmpElement=new BTreeElement(insertKey,insertValue,null);
		if(insertHeight==0){
			for(position=0;position<insertNode.childrenNumber;++position){
				if(((Comparable)insertNode.children[position]).compareTo((Comparable)insertKey)<0) break;
			}
		}
		else{
			for(position=0;position<insertNode.childrenNumber;++position){
				if(position+1==insertNode.childrenNumber||((Comparable)insertNode.children[position+1]).compareTo((Compareble)insertKey)<0){
					BTreeNode tmpNode=insertPair(insertNode.children[position].nextNode,intserKey,insertValue,insertHeight-1);
					++position;
					if(insertNode==null) return null;
					tmpElement.key=tmpNode.children[0].key;
					tmpElement.nextNode=tmpNode;
					break;
				}
			}
		}
		for(int i=insertNode.childrenNumber;i>position;--i){
			insertNode.children[i]=insertNode.children[i-1];
		}
		insertNode.children[position]=tmpElement;
		++insertNode.childrenNumber;
		if(insertNode.childrenNumber<childrenLimit) return null;
		else{
			BTreeNode secNode=new BTreeNode(childrenLimit/2);
			insertNode.childrenNumber=childrenLimit/2;
			for(int i=0;i<childrenLimit/2;++i){
				secNode.children[i]=insertNode.children[childrenLimit/2+i];
			return secNode;
			}
		}
	}

	public void insert(Key key, Value value){
		if(key==null){
			throw new NullPointerException("NULL key!");
		}
		BTreeNode tmpNode=insertPair(root,key,value,height);
		++totalKeyValuePair;
		if(tmpNode==null) return;

		BTreeNode newRoot=new BTreeNode(2);
		newRoot.children[0]=new BTreeElement(root.children[0].key,null,root);
		newRoot.children[1]=new BTreeElement(tmpNode.children[0].key,null,tmpNode);
		root=newRoot;
		++height;
	}
}
