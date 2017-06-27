package Trees;

import Map.SortedTableMap;
import PriorityQueue.Entry;
import Trees.TwoFourTree.TwoFourNode;


public class TwoFourTree<K,V> extends AbstractTree<SortedTableMap<K,V>>{
	
	protected TwoFourNode<K,V> root = null;
	private int size = 0;

	public TwoFourTree(){}; 		// constructs an empty 2-4 tree
	
	/* ------ Utilities ----- */ 
	
	protected TwoFourNode<K,V> validate(Position<SortedTableMap<K,V>> p) throws IllegalArgumentException{
		if(!(p instanceof TwoFourNode))
			throw new IllegalArgumentException("Not valid position type");
		TwoFourNode<K,V> node = (TwoFourNode<K,V>) p;
		
		if(node.getParent() == node)
			throw new IllegalArgumentException("p is no longer in the tree");
		
		return node;
	}
		
	/* Returns the number of nodes in the tree */
	public int size(){
		return size;
	}
	
	/* Returns the root Position in the tree (null if tree is empty) */
	public Position<SortedTableMap<K,V>> root() throws IllegalArgumentException{
		return root;
	}
	
	/* Adds root to empty tree */
	public void addRoot(TwoFourNode<K,V> node){
		root = node;
	}
	
	/* Changes root to given node, else if tree has no nodes, makes given node new root */
	public void changeRoot(TwoFourNode<K,V> newRoot) {
		if(size() > 0){
			root = newRoot;
		} else {
			addRoot(newRoot);
		}
	}
	
	/* Tests if current node is a 2-node */
	public boolean is2Node(TwoFourNode<K,V> n){
		return n.size() == 1;
	}

	/* Tests if current node is a 3-node */
	public boolean is3Node(TwoFourNode<K,V> n){
		return n.size() == 2;
	}

	/* Tests if current node is a 4-node */
	public boolean is4Node(TwoFourNode<K,V> n){
		return n.size() == 3;
	}
	
	public boolean isExternal(TwoFourNode<K,V> n){
		return (numChildren(n) == 0);
	}
	
	public boolean isInternal(TwoFourNode<K,V> n){
		return (numChildren(n) > 0);
	}
	
	public int numChildren(TwoFourNode<K,V> n){
		int amount =  0; 
		
		if(n.getLeft() != null){
			amount++;
		}
		
		if(n.getLeftMid() != null){
			amount++;
		}
		
		if(n.getMiddle() != null){
			amount++;
		}
		
		if(n.getRightMid() != null){
			amount++;
		}
		
		if(n.getRight() != null){
			amount++;
		}
		
		return amount;
	}
	
	
	
	
	/* ------ Functions to create a 2-node, 3-node, or 4-node node ------ */
	
	/* Create 2-node */ 
	public TwoFourNode<K,V> create2Node(Entry<K,V> firstVal, TwoFourNode<K,V> parent, TwoFourNode<K,V> left,
								       TwoFourNode<K,V>right){
		return new TwoFourNode<K,V>(firstVal, parent, left, right);
	}
	
	/* Create 3-node */ 
	public TwoFourNode<K,V> create3Node(Entry<K,V> firstVal, Entry<K,V> secondVal, TwoFourNode<K,V> parent, TwoFourNode<K,V> left,
								        TwoFourNode<K,V> middle, TwoFourNode<K,V> right){
		return new TwoFourNode<K,V>(firstVal, secondVal, parent, left, middle, right);
	}
	
	/* Create 4-node */ 
	public TwoFourNode<K,V> create4Node(Entry<K,V> firstVal, Entry<K,V> secondVal, Entry<K,V> thirdVal, TwoFourNode<K,V> parent, TwoFourNode<K,V> left,
										TwoFourNode<K,V> middleLeft, TwoFourNode<K,V> middleRight, TwoFourNode<K,V> right){
		return new TwoFourNode<K,V>(firstVal, secondVal, thirdVal, parent, left, middleLeft, middleRight, right);
	}
	
	/* ----- Nested 2-4 Node class ----- */
	public static class TwoFourNode<K,V> implements Position<SortedTableMap<K,V>>{ 
		// Instance fields 
		private SortedTableMap<K,V> table = new SortedTableMap<>(3);	// reference to collection of 
		
		private TwoFourNode<K,V> parent;						// reference to parent
		private TwoFourNode<K,V> left;							// reference to left child
		private TwoFourNode<K,V> right;							// reference to right child
		private TwoFourNode<K,V> middle;						// reference to middle child (for 2 node)
		private TwoFourNode<K,V> middleLeft;					// reference to left-middle child (for 4 node)
		private TwoFourNode<K,V> middleRight;					// reference to right-middle child (for 4 node)
		
		/* --- Constructors --- */
		
		/* Constructor for 2-node */
		public TwoFourNode(Entry<K,V> firstVal, TwoFourNode<K,V> parent, TwoFourNode<K,V> left, TwoFourNode<K,V> right){
			table.putEntry(firstVal);
			this.parent = parent;
			this.left = left;
			this.right = right;
		}
		
		/* Constructor for 3-node */
		public TwoFourNode(Entry<K,V> firstVal, Entry<K,V> secondVal, TwoFourNode<K,V> parent, TwoFourNode<K,V> left, 
						   TwoFourNode<K,V> middle, TwoFourNode<K,V> right){
			table.putEntry(firstVal);
			table.putEntry(secondVal);
			this.parent = parent;
			this.left = left;
			this.right = right;
			this.middle = middle;
		}
		
		/* Constructor for 4-node */
		public TwoFourNode(Entry<K,V> firstVal, Entry<K,V> secondVal, Entry<K,V> thirdVal, TwoFourNode<K,V> parent, TwoFourNode<K,V> left, 
						   TwoFourNode<K,V> middleLift, TwoFourNode<K,V> middleRight, TwoFourNode<K,V> right){
			table.putEntry(firstVal);
			table.putEntry(secondVal);
			table.putEntry(thirdVal);
			this.parent = parent;
			this.left = left;
			this.right = right;
			this.middleLeft = middleLift;
			this.middleRight = middleRight; 
		}
		
		/* Amount of entries in current node */
		public int size(){
			return table.size();
		}
		
		// Accessor methods 
		
		/* Get child at the given position */
		public TwoFourNode<K,V> getChild(int i){
			TwoFourNode<K,V> test = this;
			
			if(i == 1){
				return this.getLeft();
			} else if(is2Node()){
				if(i == 2){
					return this.getRight();
				}
			} else if(is3Node()){
				if(i == 2){
					return this.getMiddle();
				} else if(i == 3){
					return this.getRight();
				}
			} else if(is4Node()){
				if(i == 2){
					return this.getLeftMid();
				} else if(i == 3){
					return this.getRightMid();
				} else if(i == 4){
					return this.getRight();
				}
			} 
			
			return null;
		}
		
		/* Get table of entries from node */
		public SortedTableMap<K,V> getElement(){
			return table;
		}
		
		/* Get parent */
		public TwoFourNode<K,V> getParent(){
			return this.parent;
		}
		
		/* Get left child */
		public TwoFourNode<K,V> getLeft(){
			return this.left;
		}
		
		/* Get right child */
		public TwoFourNode<K,V> getRight(){
			return this.right; 
		}
		
		/* Get middle child, only if it is a 3-node node */
		public TwoFourNode<K,V> getMiddle(){
			if(size() > 2){
				return this.middle;
			}
			
			return null; 
		}
		
		/* Get left-middle child, only if it is a 4-node */
		public TwoFourNode<K,V> getLeftMid(){
			if(size() == 4){
				return this.middleLeft;
			}
			
			return null;
		}
		
		/* Get right-middle child, only if it is a 4-node */
		public TwoFourNode<K,V> getRightMid(){
			if(size() == 4){
				return this.middleRight;
			}
			
			return null;
		}
		
		/* Tests if current node is a 2-node */
		public boolean is2Node(){
			return this.size() == 1;
		}

		/* Tests if current node is a 3-node */
		public boolean is3Node(){
			return this.size() == 2;
		}

		/* Tests if current node is a 4-node */
		public boolean is4Node(){
			return this.size() == 3;
		}
		
		/* Get table */
		public SortedTableMap<K,V> getTable(){
			return table;
		}
		
		/* ----- Update methods ----- */
		
		/* Set parent of current node */
		public void setParent(TwoFourNode<K,V> n){
			this.parent = n;
		}
		
		/* Set left child of current node */
		public void setLeft(TwoFourNode<K,V> n){
			this.left = n;
			n.setParent(this);
		}
		
		/* Set right child of current node */
		public void setRight(TwoFourNode<K,V> n){
			this.right = n;
			n.setParent(this);
		}
		
		/* Set middle child of current node */
		public void setMiddle(TwoFourNode<K,V> n){
			if(size() == 2){
				this.middle = n;
				n.setParent(this);
			} 
		}
		
		/* Set middle-left child of current node */
		public void setLeftMid(TwoFourNode<K,V> n){
			if(size() == 3){
				this.middleLeft = n;
				n.setParent(this);
			} else {
				throw new IllegalArgumentException();
			}
		}
		
		/* Set middle-right child of current node */
		public void setRightMid(TwoFourNode<K,V> n){
			if(size() == 3){
				this.middleRight = n;
				n.setParent(this);
			} else {
				throw new IllegalArgumentException();
			}
		}
	}
	
}
