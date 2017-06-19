package Trees;

import java.util.Comparator;

import Map.TreeMap;
import PriorityQueue.Entry;

/* An implementation of a sorted map using an AVL tree */
public class AVLTreeMap<K,V> extends TreeMap<K,V> {
	
	/* Constructs an empty map using the natural ordering for keys */
	public AVLTreeMap(){
		super();
	}
	
	/* Constructs an empty map using the given comparator to order keys */ 
	public AVLTreeMap(Comparator<K> comp){
		super(comp);
	}
	
	/* Returns the height of the given tree position */
	protected int height(Position<Entry<K,V>> p){
		return tree.getHeight(p);
	}
	
	/* Recomputes the height of the given position based on its children's heights */
	protected void recomputeHeight(Position<Entry<K,V>> p){
		tree.setHeight(p, 1 + (Math.max(height(left(p)), height(right(p)))));
	}
	
	/* Returns whether a position has balance factor between -1 and 1 inclusive */
	protected boolean isBalanced(Position<Entry<K,V>> p){
		return (Math.abs(height(left(p)) - height(right(p))) <= 1);
	}
	
	/* Returns a child of p with largest height or, if of equal size, the child with the same orientation */
	protected Position<Entry<K,V>> tallerChild(Position<Entry<K,V>> p){
		if (height(left(p)) > height(right(p))){
			return left(p);							// clear winner
		}
		
		if(height(left(p)) < height(right(p))){
			return right(p);						// clear winner
		}
		
		// equal height children; break tie while matching parent's orientation
		if (isRoot(p)){
			return left(p);								// choice is irrelevant
		}
		
		if(p == left(parent(p))){
			return left(p);								// return aligned child 
		} else {
			return right(p);
		}	
	}
	
	/* Utility used to rebalance after an insert or removal operation. This traverses the path
	 * upward from p, performing a tri-node restructuring when imbalance is found, 
	 * continuing until balance is restored
	 */
	protected void rebalance(Position<Entry<K,V>> p){
		int oldHeight, newHeight;
		
		do {
			oldHeight = height(p);						// not yet recalculated if internal
			if (!isBalanced(p)){						// imbalance detected
				// perform tri-node restructuring, setting p to resulting root,
				// and recompute new local heights after the restructuring
				p = tree.restructure(tallerChild(tallerChild(p)));
				recomputeHeight(left(p));
				recomputeHeight(right(p));
			}
			recomputeHeight(p);
			newHeight = height(p);
			p = parent(p);
		} while (oldHeight != newHeight && p != null);
	}
	
	/* Overrides the TreeMap rebalancing hook that is called after an insertion */
	protected void rebalanceInsert(Position<Entry<K,V>> p){
		rebalance(p);
	}
	
	/* Overrides the TreeMap rebalancing hook that is called after a deletion */
	protected void rebalanceDelete(Position<Entry<K,V>> p){
		if (!isRoot(p)){
			rebalance(parent(p));
		}
	}
	
	public static void main(String[] args){
		AVLTreeMap<Integer,String>  test = new AVLTreeMap<>();
		test.put(5, "Never");
		test.put(6, "Stop");
		test.remove(5);
		System.out.println(test.size());
	}
}

