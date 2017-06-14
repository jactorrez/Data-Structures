package Map;

import java.util.Iterator;
import java.util.NoSuchElementException;

import LinkedList.SListNode;
import PriorityQueue.Entry;

public class EntryLinkedList<K,V> {

	protected LinkedListNode<K,V> head;
	protected int size; 
	
	public EntryLinkedList(){
		head = null;
		size = 0;
	}
	
	public EntryLinkedList(K key, V value){
		head = new LinkedListNode<K,V>(key, value);
		size = 1;
	}
	
	/* ------ Utility Methods ------ */
	
	// Find last element in the list
	private LinkedListNode<K,V> traverse(){
		
		if(size == 0){
			return null;
		}
		
		LinkedListNode<K,V> tmp = head;
		
		while(tmp.getNext() != null){
			tmp = tmp.getNext();
		}
		
		return tmp;
	}
	/* ------------------------ */
	
	/*	
	 * Get size of list
	 */
	public int getSize(){
		return size;
	}
	
	/*
	 * Check if list is empty
	 */
	public boolean isEmpty(){
		return size == 0;
	}
	
	/* 
	 *  Add node 
	 */
	public LinkedListNode<K,V> add(K key, V value){
		
		// List is empty
		if(size == 0){
			addFirst(key, value);
			return getFirst();
		}		
		
		LinkedListNode<K,V> lastNode = traverse();
		lastNode.setNext(new LinkedListNode<K,V>(key, value));
		size++;
		
		return getLast();
	}
	
	/*
	 * Remove first node
	 */
	
	public void removeFirst(){
		head = head.getNext();
		size--;
	}
	
	/*
	 * Remove node with given key
	 */
	public LinkedListNode<K,V> remove(K key){
		// List is empty 
		if(size == 0) 
			throw new RuntimeException("List empty, unable to delete");
		
		// Delete the head node
		if(head.getKey().equals(key)){
			head = head.getNext();
			size--;
			return head;
		}
		
		LinkedListNode<K,V> current = head;
		LinkedListNode<K,V> prev = null; 
		
		while(current != null && !(current.getKey().equals(key))){
			prev = current;
			current = current.getNext();
		}
		
		if(current == null)
			throw new RuntimeException("Element not found");
		
		prev.setNext(current.getNext());
		size--;
		
		return current;
	}
	
	/*	
	 * Add node at beginning of list
	 */
	public void addFirst(K key, V value){
		if(head == null){
			head = new LinkedListNode<K,V>(key, value);
			size++;
			return;
		}
		
		head = new LinkedListNode<K,V>(key, value, head);
		size++;
	}
	
	public LinkedListNode<K,V> getFirst(){
		if(head == null)
			throw new RuntimeException("List is empty, there is no first object"); 
		
		return head;
	}
	
	/*
	 * Insert item after a given node based on key
	 */
	public void insertAfter(K findKey, K key, V value){
		LinkedListNode<K,V> tmp = head;
		
		while(tmp != null && !(tmp.getKey().equals(findKey))){
			tmp = tmp.getNext();
		}
		
		if(tmp == null)
			throw new RuntimeException("Item not found");
		
		tmp.setNext(new LinkedListNode<K,V>(key, value, tmp.getNext()));
		size++;
	}
	
	/*
	 * Insert item before a given node based on key
	 */
	public void InsertBefore(K findKey, K key, V value){
		LinkedListNode<K,V> tmp = head;
		
		while((tmp != null) && !(tmp.getNext().getKey().equals(findKey))){
			tmp = tmp.getNext();
		}
		
		if(tmp == null)
			throw new RuntimeException("Item not found");
		
		tmp.setNext(new LinkedListNode<K,V>(key, value, tmp.getNext()));
		size++;
	}
	
	 /*
	  * Returns the last element in the list.
	  */
   public LinkedListNode<K,V> getLast(){
	  if(head == null) 
		  throw new NoSuchElementException();
	  
	  return traverse();
    }
	   
	/*
	 *  Removes all nodes from the list.
	*/
	public void clear(){
	      head = null;
	}
	
	/*
	 * Returns the node with the given key
	 */
	
	public LinkedListNode<K,V> find(K key){
		
		LinkedListNode<K,V> tmp = head;
		
		while((tmp != null) && !(tmp.getKey().equals(key))){
			tmp = tmp.getNext();
		}
		
		if(tmp == null)
			throw new RuntimeException("No item exists at this index");
		
		return tmp;	
	}
	

	private class EntryIterator implements Iterator<Entry<K,V>>{
		private LinkedListNode<K,V> current = head;
		
		public boolean hasNext(){
			return (current.getNext() != null);
		}
		
		public LinkedListNode<K,V> next(){
			if(current == null) 
				throw new NoSuchElementException();
			
			return current.getNext();
		}
		
		public void remove(){
			throw new UnsupportedOperationException();
		}
	}
	
	private class EntryIterable implements Iterable<Entry<K,V>>{
		public Iterator<Entry<K,V>> iterator(){
			return new EntryIterator();
		}
	}
	
	/* Returns an iterable collection of all key-value entries of the map */
	public Iterable<Entry<K,V>> entrySet(){
		return new EntryIterable();
	}
}
