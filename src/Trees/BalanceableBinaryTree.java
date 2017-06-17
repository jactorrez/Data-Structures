package Trees;

import PriorityQueue.Entry;

public class BalanceableBinaryTree<K,V> extends LinkedBinaryTree<Entry<K,V>>{
	
	// positional-based methods related to aux field 
	public int getAux(Position<Entry<K,V>> p){
		return ((BSTNode<Entry<K,V>>) p).getAux();
	}
	
	public void setAux(Position<Entry<K,V>> p, int value){
		((BSTNode<Entry<K,V>>) p).setAux(value);
	}
	
	// Override node factory function to produce a BSTNode (rather than a Node)
	protected Node<Entry<K,V>> createNode(Entry<K,V> e, Node<Entry<K,V>> parent, Node<Entry<K,V>> left,
										  Node<Entry<K,V>> right){
		return new BSTNode(e, parent, left, right);
	}
	
	/* Relinks a parent node with its oriented child node */
	private void relink(Node<Entry<K,V>> parent, Node<Entry<K,V>> child, boolean makeLeftChild){
		child.setParent(parent);
		if(makeLeftChild){
			parent.setLeft(child);
		} else {
			parent.setRight(child);
		}
	}
	
	/* Rotates Position p above its parent */
	public void rotate(Position<Entry<K,V>> p){
		Node<Entry<K,V>> x = validate(p);
		Node<Entry<K,V>> y = x.getParent();		// we assume this exists 
		Node<Entry<K,V>> z = y.getParent();		// grandparent (possibly null)
		
		if(z == null){
			root = x;
			x.setParent(null);
		} else{
			relink(z,x,y == z.getLeft());		// x becomes direct child of z
		}
		
		// now rotate x and y, including transfer of middle subtree
		
		if(x == y.getLeft()){
			relink(y, x.getRight(), true);		// x's right child becomes y's left
			relink(x, y, false);				// y becomes x's right child
		} else {
			relink(y, x.getLeft(), false);		// x's left child becomes y's right 
			relink(x, y, true);					// y becomes left child of x
		}
	}
	
	
	/* Performs a trinode restructuring of Position x with its parent/grandparent */
	public Position<Entry<K,V>> restructure(Position<Entry<K,V>> x){
		Position<Entry<K,V>> y = parent(x);
		Position<Entry<K,V>> z = parent(y);
		
		if((x == right(y)) == (y == right(z))){		// matching alignments
			rotate(y);
			return y;		
		} else {
			rotate(x);
			rotate(x);
			return x;
		}
		
	}

	/* ---- nested BSTNode class ---- */
	protected static class BSTNode<E> extends Node<E>{
		int aux = 0;
		
		BSTNode(E e, Node<E> parent, Node<E> leftChild, Node<E> rightChild){
			super(e, parent, leftChild, rightChild);
		}
		
		public int getAux(){
			return aux;
		}
		
		public void setAux(int value){
			aux = value;
		}
	}
}
