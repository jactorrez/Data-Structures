package structs.lists;

public class CircularlyLinkedList<E> {
	
	// instance variables 
	private Node<E> tail = null; 	// we store tail (but not head)
	private int size = 0;			// number of nodes in the list
	
	// constructor
	public CircularlyLinkedList(){};	// constructs an initially empty list
	
	// accessor methods
	
	/* Returns the current size of the linked list */
	public int size(){
		return size;
	}
	
	/* Tests whether the current list is empty or not */
	public boolean isEmpty(){
		return size == 0;
	}
	
	/* Returns the current first item (head node) in the linked list */
	public E first(){
		if(isEmpty()){
			return null;
		}
		
		return tail.getNext().getItem();
	}
	
	/* Returns the current last item (tail node) in the linked list */
	public E last(){
		if(isEmpty()){
			return null;
		}
		
		return tail.getItem();
	}
	
	// update methods
	
	/* Rotates first element to end of the list */
	public void rotate(){
		if(tail != null){				// if empty, do nothing
			tail = tail.getNext();		// old head becomes the new tail
		}
	}
	
	/* Makes given element the first element of the list */
	public void addFirst(E e){
		if(isEmpty()){
			tail = new Node<E>(e);
			tail.setNext(tail);
		} else {
			Node<E> newNode = new Node<E>(e, tail.getNext());
			tail.setNext(newNode);
		}
		size++;
	}
	
	/* Makes given element the last element of the list */
	public void addLast(E e){
		addFirst(e);				// insert new element at front of the list
		tail = tail.getNext();		// new element becomes the tail
	}
	
	/* Removes element first in list */
	public E removeFirst(){
		if(isEmpty()){
			return null;
		}
		
		Node<E> currentFirst = tail.getNext();
		
		if(currentFirst == tail){
			tail = null;
		} else {
			tail.setNext(currentFirst.getNext());
		}
		size--;
		
		return currentFirst.getItem();
	}
	
	/* ---- nested Node class ---- */
	protected static class Node<E>{ 
		private E item;
		private Node<E> next;

		public Node(E item, Node<E> next){
			this.item = item;
			this.next = next;
		}
		
		public Node(E item){
			this(item, null);
		}
		
		// accessor methods
		public E getItem(){
			return item;
		}
		
		public Node<E> getNext(){
			return next;
		}
		
		// update methods
		public void setItem(E e){
			this.item = e;
		}
		
		public void setNext(Node<E> n){
			this.next = n;
		}
	}
}
