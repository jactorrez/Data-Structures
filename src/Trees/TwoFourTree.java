package Trees;

import Map.SortedTableMap;
import PriorityQueue.Entry;


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
		
		/* Get table */
		public SortedTableMap<K,V> getTable(){
			return table;
		}
		
	}
	
}
