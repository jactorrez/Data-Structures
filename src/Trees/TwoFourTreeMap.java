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
	
	public Position<SortedTableMap<K,V>> treeSearch(TwoFourNode<K,V> node, K key){
		TwoFourNode<K,V> nextNode = null;
		
		if(isExternal(node)){
			return node;				// key not found, return empty external node
		}
		
		SortedTableMap<K,V> table = node.getTable();
		
		if(is2Node(node)){
			nextNode = compare2Node(node, key);
			
		} else if(is3Node(node)){
			nextNode = compare3Node(node, key);
			
		} else if(is4Node(node)){
			nextNode = compare4Node(node, key);
		}
		
		if(nextNode != node){
			return treeSearch(nextNode, key);
		} else{
			return node;
		}
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
	
}
