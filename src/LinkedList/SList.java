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
	
	public void insertFront(Object item){
		head = new SListNode(item, head);
		size++;
	}

}  
