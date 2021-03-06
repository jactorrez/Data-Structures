package structs.trees;

import java.util.Comparator;

import structs.maps.AbstractSortedMap;
import structs.maps.SortedTableMap;
import structs.priorityqueue.Entry;
import structs.trees.TwoFourTree.TwoFourNode;

public class TwoFourTreeMap<K,V> extends AbstractSortedMap<K,V>{
	
	protected TwoFourTree<K,V> tree = new TwoFourTree<>();
	
	/* Constructs an empty tree and uses the given Comparator */
	public TwoFourTreeMap(Comparator<K> c){
		super(c);
	}
	
	/* Constructs an empty tree */
	public TwoFourTreeMap(){
		super();
	}
	
	/* Searches through tree for a given key, starting at the given node */
	public Position<SortedTableMap<K,V>> treeSearch(TwoFourNode<K,V> node, K key, boolean rebalance){
		TwoFourNode<K,V> nextNode = null;
		
		if(isExternal(node)){
			return parent(node);				// key not found, return parent node 
		} else if(is4Node(node) && rebalance){
			node = split(node);
		}
		
		/* Returns node to inspect next. If key is found in current node, current node is returned */
		nextNode = compareEntries(node, key);
		
		if(nextNode != node){
			return treeSearch(nextNode, key, rebalance);
		} else{
			return node;
		}
	}
	
	/* Performs the split function on a 4-node to prevent/fix an overflow */
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
		if(tree.isEmpty()){
			tree.addRoot(key, value);
			
			return value;
		}
		@SuppressWarnings("unchecked")
		TwoFourNode<K,V> entrySlot = (TwoFourNode<K,V>) treeSearch(getRoot(), key, true);
		SortedTableMap<K,V> nodeTable = entrySlot.getTable();
		int oldChildAmount = entrySlot.getChildAmount();
		V nodeValue = nodeTable.put(key, value);
		if(isDeepInternal(entrySlot)){
			int newChildAmount = entrySlot.getChildAmount();
			checkChildProp(oldChildAmount, newChildAmount);
		}

		return nodeValue;
	}
	
	public void checkChildProp(int oldAmount, int newAmount){
		int diff = newAmount - oldAmount;
	}
	
	/* Returns the value associated with the specified key (or else null) */
	public V get(K key){
		TwoFourNode<K,V> foundNode = (TwoFourNode<K,V>) treeSearch(getRoot(), key, true);
		SortedTableMap<K,V> nodeTable = foundNode.getTable();
		V nodeValue = nodeTable.get(key);
		return nodeValue;
	}
	
	/* Removes the entry with the given key from the node */
//	public V remove(K key){
//		TwoFourNode<K,V> foundNode = (TwoFourNode<K,V>) treeSearch(getRoot(), key, false);
//		if(isDeepInternal(foundNode) && foundNode.contains(key)){
//			if(foundNode)
//		}
//	}
	
	/* Tests if given node is deepest internal node (before reaching an external node) */
	public boolean isDeepInternal(TwoFourNode<K,V> node){
		if(isExternal(node.getLeft())){
			return true;
		} else {
			return false;
		}
	}
	
	public TwoFourNode<K,V> compareEntries(TwoFourNode<K,V> node, K key){

		SortedTableMap<K,V> table = node.getTable();
		int pairLimit = table.size() - 1;
		
		int compareFirst = compare(key, table.firstEntry());
		int compareLast = compare(key, table.lastEntry());
		
		if(compareFirst < 0){
			return node.getLeft();
		} else if(compareLast > 0){
			return node.getRight();
		} else {
			for(int x = 0; x < pairLimit; x++){
				K k1 = table.getIndex(x).getKey();
				K k2 = table.getIndex(x+1).getKey(); 
				
				int k1Comp = compare(key, k1);
				int k2Comp = compare(key, k2);
				if(k1Comp == 0 || k2Comp == 0){
					return node;
				} else if((k1Comp > 0) && (k2Comp < 0)){
					return node.getChild(x+1);
				}
			}
		}
		return null;
	}
	
	public TwoFourNode<K,V> getRoot(){
		return (TwoFourNode<K,V>) tree.root();
	}
	
	/* Tests if the given node is a 2-node */
	public boolean is2Node(TwoFourNode<K,V> node){
		return tree.is2Node(node);
	}
	
	/* Tests if the given node is a 3-node */
	public boolean is3Node(TwoFourNode<K,V> node){
		return tree.is3Node(node);
	}
	
	/* Tests if the given node is a 4-node */
	public boolean is4Node(TwoFourNode<K,V> node){
		return tree.is4Node(node);
	}
	
	/* Tests if the given node is external */
	public boolean isExternal(TwoFourNode<K,V> node){
		return tree.isExternal(node);
	}
	
	/* Tests if the given node is internal */
	public boolean isInternal(TwoFourNode<K,V> node){
		return tree.isInternal(node);
	}
	
	/* Retrieves the parent child of the given node */
	public TwoFourNode<K,V> parent(TwoFourNode<K,V> node){
		return node.getParent();
	}
	
	/* Retrieves the left child of the given node */
	public TwoFourNode<K,V> left(TwoFourNode<K,V> node){
		return node.getLeft();
	}
	
	/* Retrieves the right child of the given node */
	public TwoFourNode<K,V> right(TwoFourNode<K,V> node){
		return node.getRight();
	}
	
	/* Retrieves the middle child of the given node */
	public TwoFourNode<K,V> middle(TwoFourNode<K,V> node){
		return node.getMiddle();
	}
	
	/* Retrieves the left-middle child of the given node */
	public TwoFourNode<K,V> leftMiddle(TwoFourNode<K,V> node){
		return node.getLeftMid();
	}
	
	/* Retrieves the right-middle child of the given node */
	public TwoFourNode<K,V> rightMiddle(TwoFourNode<K,V> node){
		return node.getRightMid();
	}
	
	/* Tests if the given node is the root of the tree */
	public boolean isRoot(TwoFourNode<K,V> node){
		return (tree.root() == node);
	}
	
	/* Tests if the given node is the left child of the given parent node */
	public boolean isLeftChild(TwoFourNode<K,V> parent, TwoFourNode<K,V> child){
		return (parent.getLeft() == child);
	}
	
	/* Tests if the given node is the right child of the given parent node */
	public boolean isRightChild(TwoFourNode<K,V> parent, TwoFourNode<K,V> child){
		return (parent.getRight() == child);
	}
	
	/* Tests if the given node is the middle child of the given parent node */
	public boolean isMiddleChild(TwoFourNode<K,V> parent, TwoFourNode<K,V> child){
		return (parent.getMiddle() == child);
	}
	
	/* Test utilities */
	public static void main(String[] args){
		
	}

	@Override
	public Entry<K, V> firstEntry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entry<K, V> lastEntry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entry<K, V> ceilingEntry(K key) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entry<K, V> floorEntry(K key) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entry<K, V> lowerEntry(K key) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entry<K, V> higherEntry(K key) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Entry<K, V>> subMap(K fromKey, K toKey) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public V remove(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

}
