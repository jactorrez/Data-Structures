package LinkedList;
/**
 * 
 * @author Javier
 * Implementing a singly-linked list
 *
 */

public class SList {
	
	private SListNode head;
	private SListNode tail;
	private int size; 
	
	public SList(){
		head = null;
		tail = null;
		size = 0;
	}
	
	public SList(SListNode firstNode){
		head = firstNode;
		tail = head;
		size = 1;
	}

	private SListNode traverse(){
		
		if(size == 0){
			return null;
		}
		
		SListNode tmp = head;
		
		while(tmp.next != null){
			tmp = tmp.next;
		}
		
		return tmp;
	}
	// Inserting item at nth position 
	
	public void addFirst(int item){
		if(head == null){
			head = new SListNode(item, null);
			tail = head;
			return;
		}
		
		head = new SListNode(item, head);
		
	}
	
	public void addLast(int item){
		SListNode lastNode = traverse();
		
		lastNode.next = new SListNode(item);
		tail = lastNode.next;
	}
	
	// Inserting item at the front of a list
	public void insertAfter(int key, int toInsert){
		SListNode tmp = head;
		
		while(tmp != null && tmp.next.item != key){
			tmp = tmp.next;
		}
		
		if(tmp != null)
			tmp.next.next = new SListNode(toInsert, tmp.next.next);
	}
	
	public void InsertBefore(int key, int toInsert){
		SListNode tmp = head;
		
		while(tmp != null && tmp.next.item != key){
			tmp = tmp.next;
		}
		
		tmp.next = new SListNode(toInsert, tmp.next);
		
	}
	
	// Inserting item at the end of a list
	public void deleteFront(){
		if(head != null){
			head = head.next;
			size--; 
		}
	}
	
	public static void main(String[] args){
		SListNode testNode = new SListNode(4);
		
		SList test = new SList(testNode);
		
		System.out.println("first node: " + test.head.item);
		
		test.addFirst(6);
		
		System.out.println("new first node: " + test.head.item);
		
		test.insertAfter(4, 99);
		
		System.out.println("insert after: " + test.head.next.next.item);
		
		test.addLast(499);
		
		System.out.println("insert last: " + test.tail.item);
	}
}  
