package Trees;

import PriorityQueue.Entry;
import Trees.BalanceableBinaryTree.BSTNode;
import Trees.LinkedBinaryTree.Node;

public class RBTree<K,V> extends BalanceableBinaryTree<K,V> {
	
	// Override node factory function to produce a RBNode (rather than a BSTNode) 
	protected Node<Entry<K,V>> createNode(Entry<K,V> e, Node<Entry<K,V>> parent, Node<Entry<K,V>> left,
			  Node<Entry<K,V>> right){
	return new RBNode(e, parent, left, right);
	}
	
	
	// positional-based methods related to aux field 
	public int getColor(Position<Entry<K,V>> p){
		return ((RBNode<Entry<K,V>>) p).getColor();
	}
	
	public void setColor(Position<Entry<K,V>> p, int value){
		((RBNode<Entry<K,V>>) p).setColor(value);
	}
	
	/* ---- Nested RB-Tree Node ---- */
	protected static class RBNode<E> extends Node<E>{
		int color = 0;
		
		RBNode(E e, Node<E> parent, Node<E> leftChild, Node<E> rightChild){
			super(e, parent, leftChild, rightChild);
		}
		
		public int getColor(){
			return color;
		}
		
		public void setColor(int color){
			if(color < 0 || color > 1){
				throw new IllegalArgumentException();
			}
			
			this.color = color;
		}
	}
}
