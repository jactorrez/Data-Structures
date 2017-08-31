package Queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Deque: A generalization of a stack and a queue that supports inserting and removing items from either
 * the front or the back of the data structure
 */
public class Deque<Item> implements Iterable<Item>{
	// instance variables
	public int size;
	public ListNode sentinel;
	
	// constructor 
	public Deque(){
		sentinel = new ListNode(null);
		sentinel.next = sentinel.prev = sentinel;
		
		size = 0;
	}
	
	// constructor 
	public Deque(Item item){
		sentinel.next = sentinel.prev = new ListNode(item);
		size = 1;
	}
	
	// checks if deque is empty
	public boolean isEmpty(){
		return (size == 0 ? true : false);
	}
	
	// returns the number of items on the deque
	public int size(){
		return size;
	}
	
	// inserts item at the front 
	public void addFirst(Item data){
		
		checkIfNull(data);
			
		if(isEmpty()){
			sentinel.next = sentinel.prev = new ListNode(data);
			size++;
			return;
		}
		
		ListNode prevFirstNode= sentinel.next;
		sentinel.next = prevFirstNode.prev = new ListNode(data, sentinel.next, null);
		size++;
	}
	
	// inserts the item at the end
	public void addLast(Item data){
		
		checkIfNull(data);
		
		if(isEmpty()){
			sentinel.next = sentinel.prev = new ListNode(data);
			size++;
			return;
		}
		
		ListNode prevLastNode = sentinel.prev;
		sentinel.prev = prevLastNode.next = new ListNode(data, null, prevLastNode);
		size++;
	}
	
	// deletes and returns the item at the front
	public Item removeFirst(){
		if(isEmpty()){
			throw new NoSuchElementException("List empty, nothing to delete");
		}
		
		ListNode currentFirst = sentinel.next;
		sentinel.next = currentFirst.next;
		sentinel.next.prev = null;
		size--;
		
		return currentFirst.data;
	}
	
	// deletes and returns the item at the end
	public Item removeLast(){
		if(isEmpty()){
			throw new NoSuchElementException("List is empty, nothing to delete");
		}
		
		ListNode currentLast = sentinel.prev;
		sentinel.prev = currentLast.prev;
		sentinel.prev.next = null;
		size--;
		
		return currentLast.data;
	}
	
	// utility function used to check if data given by API user is null
	private void checkIfNull(Item data){
		if(data == null){
			throw new NullPointerException("Attempted to add a null value");
		}
	}
	
	// returns an iterator over items in order from front to end
	public Iterator<Item> iterator(){
		return new DequeIterator();
	}
		
	private class DequeIterator implements Iterator<Item>{
		private ListNode node = sentinel.next; 
		
		@Override
		public boolean hasNext(){
			ListNode nextNode = node.next;
			
			if(nextNode == null){
				return false;
			} else{
				return true;
			}
		}
		
		@Override
		public Item next(){
			
			if(hasNext()){
				node = node.next;
			} else {
				throw new NoSuchElementException("There are no more items to return");
			}
			return node.data;
		}
		
		@Override
		public void remove(){
			throw new UnsupportedOperationException();
		}
	}
	
	// internal ListNode class used to simulate connected nodes
	private class ListNode{
		public Item data;
		public ListNode next;
		public ListNode prev;
		
		public ListNode(Item data, ListNode next, ListNode prev){
			this.data = data;
			this.next = next;
			this.prev = prev;
		}
		
		public ListNode(Item data){
			this.data = data;
			this.next = null;
			this.prev = null;
		}
	}
	
	// unit testing
	public static void main(String[] args){
		Deque<Integer> test = new Deque<>();
		System.out.println(test.sentinel.next);
	}
}
