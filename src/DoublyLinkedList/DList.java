package DoublyLinkedList;

public class DList {
	private DListNode head; // sentinel
	private int size;
	
	public DList(){
		head = new DListNode();
		head.next = head;
		head.prev = head;
		size = 0;
	}
}
