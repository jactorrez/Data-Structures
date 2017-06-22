package Trees;

import java.util.Comparator;

import Map.TreeMap;
import PriorityQueue.Entry;

/* An implementation of a sorted map using a red-black tree */
public class RBTreeMap<K,V> extends TreeMap<K,V>{
	
	/* Constructs an empty map using the natural ordering of keys */
	public RBTreeMap(){
		super();
	}
	
	/* Constructs an empty map using the given comparator to order keys */
	public RBTreeMap(Comparator<K> comp){
		super(comp);
	}
	
	// we use the inherited aux field with convention that 0=black and 1=red
	// (new leaves will be black by default, as aux=0)
	
	private boolean isBlack(Position<Entry<K,V>> p){
		return tree.getAux(p) == 0;
	}
	
	private boolean isRed(Position<Entry<K,V>> p){
		return tree.getAux(p) == 1;
	}
	
	private void makeBlack(Position<Entry<K,V>> p){
		return tree.setAux(p,0);
	}
	
	private void makeRed(Position<Entry<K,V>> p){
		tree.setAux(p,1);
	}
	
	private void setColor(Position<Entry<K,V>> p, boolean toRed){
		tree.setAux(p, toRed ? 1 : 0);
	}
	
	/* Overrides the TreeMap rebalancing hook that is called after an insertion */
	protected void rebalanceInsert(Position<Entry<K,V>> p){
		if(!isRoot(p)){
			makeRed(p);			// the new internal node is initially colored red
			resolveRed(p);		// but this may cause a double-red problem
		}
	}
	
	/* Remedies potential double-red violation above red position p */
	private void resolveRed(Position<Entry<K,V>> p){
		Position<Entry<K,V>> parent, uncle, middle, grand; // used in case analysis 
		parent = parent(p);
		
		if(isRed(parent)){								   // double-red problem exists
			uncle = tree.sibling(parent);
			if(isBlack(uncle)){							   // Case 1: misshapen 4-node
				middle = restructure(p);				   // do trinode restructuring 
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
			Position<Entry<K,V>> sib = sibling(p);
			if(isInternal(sib) && (isBlack(sib) || isInternal(left(sib)))){
				remedyDoubleBlack(p);
			}
		}
	}
	
	/* Remedies a presumed double-black violation at given (nonroot) position  */
	private void remedyDoubleBlack(Position<Entry<K,V>> p){
		Position<Entry<K,V>> z = parent(p);
		Position<Entry<K,V>> y = sibling(p);
		
		if(isBlack(y)){
			if(isRed(left(y)) || isRed(right(y))){		  // Case 1: trinode restructuring  
				Position<Entry<K,V>> x = (isRed(left(y)) ? left(y) : right(y));
				Position<Entry<K,V>> middle = restructure(x);
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
			rotate(y);
			makeBlack(y);
			makeRed(z);
			remedyDoubleBlack(p);						  // restart the process at p
		}
	}
}
