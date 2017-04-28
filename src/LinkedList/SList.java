package LinkedList;
/**
 * 
 * @author Javier
 * Implementing a singly-linked list
 *
 */

public class SList {
	
	private SListNode head;
	private int size; 
	
	public SList(){
		head = null;
		size = 0;
	}
	
	// Inserting item at nth position 
	
	// Inserting item at the front of a list
	public void insertFront(Object item){
		head = new SListNode(item, head);
		size++;
	}
	
	// Inserting item at the end of a list
	public void deleteFront(){
		if(head != null){
			head = head.next;
			size--; 
		}
	}
}  
