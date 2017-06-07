package Trees;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractTree<E> implements Tree<E>{
	
	public boolean isInternal(Position<E> p){
		return numChildren(p) > 0;
	}
	
	public boolean isExternal(Position<E> p){
		return numChildren(p) == 0; 
	}
	
	public boolean isRoot(Position<E> p){
		return p == root();
	}
	
	public boolean isEmpty(){
		return size() == 0;
	}
	
	/*
	 * Returns the number of levels separating Position p from the root
	 */
	public int depth(Position<E> p){
		if(isRoot(p)){
			return 0;
		} else {
			return 1 + depth(parent(p));
		}
	}
	
	/*
	 * Returns the height of the tree
	 */
	private int heightBad(){	// worst-case running time: quadratic! 
		int h = 0;
		for(Position<E> p : positions()){
			if(isExternal(p)){
				h = Math.max(h, depth(p));
			}	
		}
		return h;
	}
	
	// improved version of height
	
	public int height(Position<E> p){
		int h = 0;
		for(Position<E> c : children(p)){
			h = Math.max(h, 1 + height(c));
		}
		return h; 
	}
	
	// nested ElementIterator class
	private class ElementIterator implements Iterator<E>{
		Iterator<Position<E>> postIterator = positions().iterator();
		public boolean hasNext(){
			return postIterator.hasNext();
		}
		
		public E next(){
			return postIterator.next().getElement();
		}
		
		public void remove(){
			postIterator.remove();
		}
	}
	
	public Iterator<E> iterator(){
		return new ElementIterator();
	}
	
	public Iterable<Position<E>> preorder(){
		List<Position<E>> snapshot = new ArrayList<>();
		if(!isEmpty()){
			preorderSubtree(root(), snapshot);
		}
		
		return snapshot;
	}
	
	public Iterable<Position<E>> postorder(){
		List<Position<E>> snapshot = new ArrayList<>();
		if(!isEmpty()){
			preorderSubtree(root(), snapshot);
		}
		
		return snapshot;
	}
	
	/*
	 * Adds positions of the subtree rooted at Position p to the given snapshot
	 */
	private void preorderSubtree(Position<E> p, List<Position<E>> snapshot){
		snapshot.add(p);
		for(Position<E> c :children(p)){
			preorderSubtree(c, snapshot);
		}
	}
	
	private void postorderSubtree(Position<E> p, List<Position<E>> snapshot){
		for(Position<E> c : children(p)){
			postorderSubtree(c, snapshot);
		}
		snapshot.add(p);
	}
}
