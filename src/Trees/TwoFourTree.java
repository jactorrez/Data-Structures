package Trees;

import Map.SortedTableMap;
import PriorityQueue.Entry;

public class TwoFourTree<K,V> {

	
	/* ---- Nested 2-4 Node class ---- */
	protected static class TwoFourNode<K,V>{ 
		// Instance fields 
		private SortedTableMap<K,V> table = new SortedTableMap<>(3);	// reference to collection of 
		
		private TwoFourNode<K,V> left;							// reference to left child
		private TwoFourNode<K,V> right;							// reference to right child
		private TwoFourNode<K,V> middle;						// reference to middle child (for 2 node)
		private TwoFourNode<K,V> middleLeft;					// reference to left-middle child (for 4 node)
		private TwoFourNode<K,V> middleRight;					// reference to right-middle child (for 4 node)
		
		// Constructors
		/* Constructor for 2-node */
		public TwoFourNode(Entry<K,V> firstVal, TwoFourNode<K,V> left, TwoFourNode<K,V> right){
			table.putEntry(firstVal);
			this.left = left;
			this.right = right;
		}
		
		/* Constructor for 3-node */
		public TwoFourNode(Entry<K,V> firstVal, Entry<K,V> secondVal, TwoFourNode<K,V> left, 
						   TwoFourNode<K,V> middle, TwoFourNode<K,V> right){
			table.putEntry(firstVal);
			table.putEntry(secondVal);
			this.left = left;
			this.right = right;
			this.middle = middle;
		}
		
		/* Constructor for 4-node */
		public TwoFourNode(Entry<K,V> firstVal, Entry<K,V> secondVal, Entry<K,V> thirdVal, TwoFourNode<K,V> left, 
						   TwoFourNode<K,V> middleLift, TwoFourNode<K,V> middleRight, TwoFourNode<K,V> right){
			table.putEntry(firstVal);
			table.putEntry(secondVal);
			table.putEntry(thirdVal);
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
				return this.middleRight;
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
		
	}
	
}
