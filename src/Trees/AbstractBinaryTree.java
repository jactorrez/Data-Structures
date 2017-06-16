package Trees;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBinaryTree<E> extends AbstractTree<E> implements BinaryTree<E>{
	
	/*
	 * Return's the Position of p's sibling (or null if no sibling exists)
	 */
	public Position<E> sibling(Position<E> p) throws IllegalArgumentException{
		Position<E> parent = parent(p);
		if(parent == null){
			return null;
		}
		
		if(p == left(parent)){
			return right(parent);
		} else {
			return left(parent);
		}
	}
	
	/*
	 * Returns the number of children of Position p
	 */
	public int numChildren(Position<E> p){
		int count = 0;
		if(left(p) != null){
			count++;
		} 
		if(right(p) != null){
			count++;
		}
		return count;
	}
	
	/*
	 * Returns an iterable collection of the Positions representing p's children
	 */
	
	public Iterable<Position<E>> children(Position<E> p){
		List<Position<E>> snapshot = new ArrayList<>(2);
		
		if(left(p) != null){
			snapshot.add(left(p));
		}
		
		if(right(p) != null){
			snapshot.add(right(p));
		}
		
		return snapshot;
	}
	
	/*
	 * Adds positions of the subtree rooted at Position p to the given snapshot
	 */
	private void inorderSubtree(Position<E> p, List<Position<E>> snapshot){
		if(left(p) != null){
			inorderSubtree(left(p), snapshot);
		}
		snapshot.add(p);
		
		if(right(p) != null){
			inorderSubtree(sibling(p), snapshot);
		}
	}
	
	/*
	 * Returns an itearble collection of positions of the tree, reported in order
	 */
	public Iterable<Position<E>> inorder(){
		List<Position<E>> snapshot = new ArrayList<>();
		if(!isEmpty()){
			inorderSubtree(root(), snapshot);	// fill the snapshot recursively
		}
		return snapshot;
	}
	
	/*
	 * Overrides positions to make inorder the default order for binary trees
	 */
	public Iterable<Position<E>> positions(){
		return inorder();
	}
	
}
