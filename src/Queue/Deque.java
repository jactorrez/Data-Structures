package Queue;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item>{
	// instance variables
	public int size;
	public ListNode sentinel;
	
	// constructor 
	public Deque(){
		sentinel = new ListNode(null, sentinel, sentinel);
		size = 0;
	}
	
	public Deque(Item item){
		sentinel.next = sentinel.prev = new ListNode(item);
		size = 1;
	}
	
	public boolean isEmpty(){
		return (size == 0 ? true : false);
	}
	
	public int size(){
		return size;
	}
	
	public void addFirst(Item data) throws NullPointerException{
		
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
	
	public void addLast(Item data) throws NullPointerException{
		
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
	
	public Item removeFirst(){
		if(isEmpty()){
			throw new RuntimeException("List empty, nothing to delete");
		}
		
		ListNode currentFirst = sentinel.next;
		sentinel.next = currentFirst.next;
		sentinel.next.prev = null;
		size--;
		
		return currentFirst.data;
	}
	
	public Item removeLast(){
		if(isEmpty()){
			throw new RuntimeException("List is empty, nothing to delete");
		}
		
		ListNode currentLast = sentinel.prev;
		sentinel.prev = currentLast.prev;
		sentinel.prev.next = null;
		size--;
		
		return currentLast.data;
	}
	
	private void checkIfNull(Item data){
		if(data == null){
			throw new NullPointerException("Attempted to add a null value");
		}
	}
	
	public Iterator<Item> iterator(){
		return new DequeIterator();
	}
	
	private class DequeIterator implements Iterator<Item>{
		
		private ListNode node = sentinel.next; 
		
		@Override
		public boolean hasNext(){
			ListNode nextNode = node.next;
			
			if(nextNode == sentinel || nextNode == null){
				return false;
			} else{
				return true;
			}
		}
		
		@Override
		public Item next(){
			return node.next.data;
		}
		
		@Override
		public void remove(){
			throw new UnsupportedOperationException();
		}
	}

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
		Deque<Integer> test = new Deque<>(new Integer(5));
		test.addFirst(new Integer(4));
		
	}
	
	
}
