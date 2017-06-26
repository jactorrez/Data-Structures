package Trees;

import java.util.Comparator;

import Map.AbstractSortedMap;
import Map.SortedTableMap;
import PriorityQueue.Entry;
import Trees.TwoFourTree.TwoFourNode;

public class TwoFourTreeMap<K,V> extends AbstractSortedMap<K,V>{
	
	protected TwoFourTree<K,V> tree = new TwoFourTree<>();
	
	public TwoFourTreeMap(Comparator<K> c){
		super(c);
	}
	
	public TwoFourTreeMap(){
		super();
	}
	
	/* Searches through tree for a given key, starting at the given node */
	public Position<SortedTableMap<K,V>> treeSearch(TwoFourNode<K,V> node, K key, boolean isInsert){
		TwoFourNode<K,V> nextNode = null;
		
		if(isExternal(node)){
			return parent(node);				// key not found, return empty external node
		} else if(is4Node(node) && isInsert){
			node = split(node);
		}

		if(is2Node(node)){
			nextNode = compare2Node(node, key);
			
		} else if(is3Node(node)){
			nextNode = compare3Node(node, key);
			
		} else if(is4Node(node)){
			nextNode = compare4Node(node, key);
		}
		
		if(nextNode != node){
			return treeSearch(nextNode, key, isInsert);
		} else{
			return node;
		}
	}
	
	public TwoFourNode<K,V> split(TwoFourNode<K,V> node){
		SortedTableMap<K,V> table = node.getTable();
		
		/* Values stored inside the node */
		MapEntry<K,V> firstEntry = table.getIndex(0);
		MapEntry<K,V> middleEntry = table.getIndex(1);
		MapEntry<K,V> lastEntry = table.getIndex(2);
		
		/* Nodes referenced by 4-node node */ 
		TwoFourNode<K,V> leftNode = node.getLeft();
		TwoFourNode<K,V> leftMid = node.getLeftMid();
		TwoFourNode<K,V> rightMid = node.getRightMid();
		TwoFourNode<K,V> right = node.getRight();
		
		/* Constructing new 2-nodes */
		TwoFourNode<K,V> parentNode;
		TwoFourNode<K,V> newLeft = tree.create2Node(firstEntry, null, leftNode, leftMid);
		TwoFourNode<K,V> newRight = tree.create2Node(lastEntry, null, rightMid, right);
		
		if(isRoot(node)){
			// construct new root node
			parentNode = tree.create2Node(middleEntry, null, newLeft, newRight);
			tree.changeRoot(parentNode);
			return parentNode;
		} else{
			parentNode = parent(node);
			parentNode.getTable().putEntry(middleEntry);
			
			if(is2Node(parentNode)){
				// check if current node is left or right child of parent 2-node

				if(isLeftChild(parentNode, node)){
					parentNode.setLeft(newLeft);
					parentNode.setMiddle(newRight);
				} else if(isRightChild(parentNode, node)){
					parentNode.setMiddle(newLeft);
					parentNode.setRight(newRight);
				}
				
			} else if(is3Node(parentNode)){
				// check if current node is left-most child
				if(isLeftChild(parentNode, node)){
					parentNode.setLeft(newLeft);
					parentNode.setLeftMid(newRight);
				// check if current node is right-most child
				} else if(isMiddleChild(parentNode, node)){
					parentNode.setLeftMid(newLeft);
					parentNode.setRightMid(newRight);
				// check if current node is middle child 
				} else if(isRightChild(parentNode, node)){
					parentNode.setRightMid(newLeft);
					parentNode.setRight(newRight);
				}
			}

			return parentNode;
		}
	}
	
	/* Adds entry or associates the given value with the given key, returning any overridden value */
	
	public V put(K key, V value){
		TwoFourNode<K,V> root = (TwoFourNode<K,V>) tree.root();
		TwoFourNode<K,V> entrySlot = (TwoFourNode<K,V>) treeSearch(root, key, true);
		SortedTableMap<K,V> nodeTable = entrySlot.getTable();
		V nodeValue = nodeTable.put(key, value);
		return nodeValue;
	}
	
	
	public TwoFourNode<K,V> compare2Node(TwoFourNode<K,V> node, K key){
		Entry<K,V> firstEntry = node.getTable().firstEntry();
		
		int compare = compare(key, firstEntry);
		
		if(compare == 0){
			return node;
		} else if(compare < 0){
			return node.getLeft();
		} else if(compare > 0){
			return node.getRight();
		} else {
			return null;
		}

	}
	
	public TwoFourNode<K,V> compare3Node(TwoFourNode<K,V> node, K key){
		
		SortedTableMap<K,V> table = node.getTable();

		for(int i = 0; i < 2; i++){
			int compare = compare(key, table.getIndex(i));
			
			if(compare == 0){
				return node; 
			} else if((i == 0) && (compare < 0)){
				return node.getLeft();
			} else if((i == 0) && (compare > 0)){
				continue;
			} else if((i == 1) && (compare < 0)){
				return node.getMiddle();
			} else if((i == 1) && (compare > 0)){
				return node.getRight();
			}
		}

		return null;
	}
	
	public TwoFourNode<K,V> compare4Node(TwoFourNode<K,V> node, K key){	
		SortedTableMap<K,V> table = node.getTable();

		for(int i = 0; i < 3; i++){
			int compare = compare(key, table.getIndex(i));
			
			if(compare == 0){
				return node; 
			} else if((i == 0) && (compare < 0)){
				return node.getLeft();
			} else if((i == 0) && (compare > 0)){
				continue;
			} else if((i == 1) && (compare < 0)){
				return node.getLeftMid();
			} else if((i == 1) && (compare > 0)){
				continue;
			} else if((i == 2) && (compare > 0)){
				return node.getRight();
			} else if((i == 2) && (compare < 0)){
				return node.getRightMid();
			}
		}
		
		return null;
	}
	
	public boolean is2Node(TwoFourNode<K,V> node){
		return tree.is2Node(node);
	}
	
	public boolean is3Node(TwoFourNode<K,V> node){
		return tree.is3Node(node);
	}
	
	public boolean is4Node(TwoFourNode<K,V> node){
		return tree.is4Node(node);
	}
	
	public boolean isExternal(TwoFourNode<K,V> node){
		return tree.isExternal(node);
	}
	
	public boolean isInternal(TwoFourNode<K,V> node){
		return tree.isInternal(node);
	}
	
	public TwoFourNode<K,V> parent(TwoFourNode<K,V> node){
		return node.getParent();
	}
	
	public TwoFourNode<K,V> left(TwoFourNode<K,V> node){
		return node.getLeft();
	}
	
	public TwoFourNode<K,V> right(TwoFourNode<K,V> node){
		return node.getRight();
	}
	
	public TwoFourNode<K,V> middle(TwoFourNode<K,V> node){
		return node.getMiddle();
	}
	
	public TwoFourNode<K,V> leftMiddle(TwoFourNode<K,V> node){
		return node.getLeftMid();
	}
	
	public TwoFourNode<K,V> rightMiddle(TwoFourNode<K,V> node){
		return node.getRightMid();
	}
	
	public boolean isRoot(TwoFourNode<K,V> node){
		return (tree.root() == node);
	}
	
	public boolean isLeftChild(TwoFourNode<K,V> parent, TwoFourNode<K,V> child){
		return (parent.getLeft() == child);
	}
	
	public boolean isRightChild(TwoFourNode<K,V> parent, TwoFourNode<K,V> child){
		return (parent.getRight() == child);
	}
	
	public boolean isMiddleChild(TwoFourNode<K,V> parent, TwoFourNode<K,V> child){
		return (parent.getMiddle() == child);
	}
	
	public static void main(String[] args){
		
	}

}
