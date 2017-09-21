package structs.lists;

import java.util.NoSuchElementException;


/**
 * 
 * @author Javier
 * Implementing a singly-linked list
 *
 */

public class LinkedList<T> {
	
	protected LinkedListNode<T> head;
	protected int size; 
	
	public LinkedList(){
		head = null;
		size = 0;
	}
	
	public LinkedList(T item){
		head = new LinkedListNode<T>(item);
		size = 1;
	}
	
	/* ------ Utility Methods ------ */
	
	// Find last element in the list
	private LinkedListNode<T> traverse(){
		
		if(size == 0){
			return null;
		}
		
		LinkedListNode<T> tmp = head;
		
		while(tmp.next != null){
			tmp = tmp.next;
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
	public void addLast(T item){
		
		// List is empty
		if(size == 0){
			addFirst(item);
			return;
		}		
		
		LinkedListNode<T> lastNode = traverse();
		lastNode.next = new LinkedListNode<T>(item);
		size++;
	}
	
	/*
	 * Remove first node
	 */
	
	public void removeFirst(){
		head = head.next;
		size--;
	}
	
	/*
	 * Remove node at given key
	 */
	public void remove(T key){
		// List is empty 
		if(size == 0) 
			throw new RuntimeException("List empty, unable to delete");
		
		// Delete the head node
		if(head.item.equals(key)){
			head = head.next;
			size--;
			return;
		}
		
		LinkedListNode<T> cur = head;
		LinkedListNode<T> prev = null; 
		
		while(cur != null && !cur.item.equals(key)){
			prev = cur;
			cur = cur.next;
		}
		
		if(cur == null)
			throw new RuntimeException("Element not found");
		
		prev.next = cur.next;
		size--;
	}
	
	/*	
	 * Add node at beginning of list
	 */
	public void addFirst(T item){
		if(head == null){
			head = new LinkedListNode<T>(item);
			size++;
			return;
		}
		
		head = new LinkedListNode<T>(item, head);
		size++;
	}
	
	public T getFirst(){
		if(head == null)
			throw new RuntimeException("List is empty, can't delete"); 
		
		return head.item;
	}
	
	/*
	 * Insert item after a given node based on key
	 */
	public void insertAfter(T key, T toInsert){
		LinkedListNode<T> tmp = head;
		
		while(tmp != null && tmp.next.item != key){
			tmp = tmp.next;
		}
		
		if(tmp == null)
			throw new RuntimeException("Item not found");
		
		tmp.next.next = new LinkedListNode<T>(toInsert, tmp.next.next);
		size++;
	}
	
	/*
	 * Insert item after a given node based on key
	 */
	public void InsertBefore(T key, T toInsert){
		LinkedListNode<T> tmp = head;
		
		while(tmp != null && tmp.next.item != key){
			tmp = tmp.next;
		}
		
		if(tmp == null)
			throw new RuntimeException("Item not found");
		
		tmp.next = new LinkedListNode<T>(toInsert, tmp.next);
		size++;
		
	}
	
	 /*
	  * Returns the last element in the list.
	  */
   public T getLast(){
	  if(head == null) throw new NoSuchElementException();
	  
	  return traverse().item;
    }
	   
	/*
	 *  Removes all nodes from the list.
	*/
	public void clear(){
	      head = null;
	}
	
	/*
	 * Delete first node in list
	 */
	public void deleteFront(){
		if(head == null)
			throw new RuntimeException("List is empty, can't delete");
	
		head = head.next;
		size--; 
	}
	
	/*
	 * Returns the node at the given index
	 */
	
	public T findNth(int index){
		if (index < 1 || head == null)
			throw new RuntimeException("List is empty, can't find item");
		
		LinkedListNode<T> tmp = head;
		
		while(index != 1 && tmp != null){
			tmp = tmp.next;
			index--;
		}
		
		if(tmp == null)
			throw new RuntimeException("No item exists at this index");
		
		return tmp.item;	
	}
	
	public String toString(){
		LinkedListNode<T> tmp = head;
		
		StringBuilder result = new StringBuilder();
		
		while(tmp != null){
			result.append(tmp.item + ",");
			tmp = tmp.next;
		}
		
		return result.toString();
	}
	
	
	public static void main(String[] args){
		
		LinkedList<Integer> test = new LinkedList<>();
		
		System.out.println("Before: [" + test.toString() + "]");
		
		test.addLast(4);
//		test.add(5);
//		test.addFirst(200);
//		test.insertAfter(5,70);
//		test.remove(200);
//		test.removeFirst(); 
		
		System.out.println("After: [" + test.toString() + "]");		
	}
}  
