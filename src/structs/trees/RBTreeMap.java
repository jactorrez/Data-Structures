package structs.trees;

import java.util.Comparator;

import structs.maps.TreeMap;
import structs.priorityqueue.Entry;

/* An implementation of a sorted map using a red-black tree */
public class RBTreeMap<K,V> extends TreeMap<K,V>{
	
	// Override tree field to instead instantiate an RBTree
	RBTree<K,V> tree = new RBTree<>();
	
	/* Constructs an empty map using the natural ordering of keys */
	public RBTreeMap(){
		super();
	}
	
	/* Constructs an empty map using the given comparator to order keys */
	public RBTreeMap(Comparator<K> comp){
		super(comp);
	}
	
	// New leaves will be black by default, as color=0 for every new instance of an RBNode
	
	/* Check if given node is a black node */
	private boolean isBlack(Position<Entry<K,V>> p){
		return tree.getColor(p) == 0;
	}
	
	/* Check if given node is red */
	private boolean isRed(Position<Entry<K,V>> p){
		return tree.getColor(p) == 1;
	}
	
	/* Makes given node black */
	private void makeBlack(Position<Entry<K,V>> p){
		tree.setColor(p,0);
	}
	
	/* Makes given node red */
	private void makeRed(Position<Entry<K,V>> p){
		tree.setColor(p,1);
	}
	
	/* Alternative way to set color of a given node */
	private void setColor(Position<Entry<K,V>> p, boolean toRed){
		tree.setColor(p, toRed ? 1 : 0);
	}
	
	/* Overrides the TreeMap rebalancing hook that is called after an insertion */
	protected void rebalanceInsert(Position<Entry<K,V>> p){
		if(!isRoot(p)){
			makeRed(p);			// the new internal node is initially colored red
			resolveRed(p); 		// but this may cause a double-red problem
		}
	}
	
	/* Remedies potential double-red violation above red position p */
	private void resolveRed(Position<Entry<K,V>> p){
		Position<Entry<K,V>> parent, uncle, middle, grand; // used in case analysis 
		parent = parent(p);
		
		if(isRed(parent)){								   // double-red problem exists
			uncle = tree.sibling(parent);
			if(isBlack(uncle)){							   // Case 1: misshapen 4-node
				middle = tree.restructure(p);				   // do trinode restructuring 
				makeBlack(middle);
				makeRed(left(middle));
				makeRed(right(middle));
			} else {									  // Case 2: overfull 5-node
				makeBlack(parent);						  // perform recoloring
				makeBlack(uncle);
				grand = parent(parent);
				if(!isRoot(grand)){
					makeRed(grand);						  // grandparent becomes red
					resolveRed(grand);					  // recur at red grandparent
				}
			}
		}
	}
	
	/* Overrides the TreeMap rebalancing hook that is called after a deletion */
	protected void rebalanceDelete(Position<Entry<K,V>> p){
		if(isRed(p)){								      // deleted parent was black
			makeBlack(p);								  // so this restores black depth
		} else if(!isRoot(p)){
			Position<Entry<K,V>> sib = tree.sibling(p);
			if(isInternal(sib) && (isBlack(sib) || isInternal(left(sib)))){
				remedyDoubleBlack(p);
			}
		}
	}
	
	/* Remedies a presumed double-black violation at given (nonroot) position  */
	private void remedyDoubleBlack(Position<Entry<K,V>> p){
		Position<Entry<K,V>> z = parent(p);
		Position<Entry<K,V>> y = tree.sibling(p);
		
		if(isBlack(y)){
			if(isRed(left(y)) || isRed(right(y))){		  // Case 1: trinode restructuring  
				Position<Entry<K,V>> x = (isRed(left(y)) ? left(y) : right(y));
				Position<Entry<K,V>> middle = tree.restructure(x);
				setColor(middle, isRed(z)); 			  // root of restructured subtree gets z's old color
				makeBlack(left(middle));
				makeBlack(right(middle));
			} else {							          // Case 2: recoloring
				makeRed(y);
				if(isRed(z)){
					makeBlack(z);				          // problem is resolved
				} else if(!isRoot(z)){					  
					remedyDoubleBlack(z);				  // propagate the problem
				}
			}
		} else {
			tree.rotate(y);
			makeBlack(y);
			makeRed(z);
			remedyDoubleBlack(p);						  // restart the process at p
		}
	}
}
