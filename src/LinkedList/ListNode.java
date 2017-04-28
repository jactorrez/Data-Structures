package LinkedList;

public class ListNode {
	int item;
	ListNode next; 
	
	public ListNode(int item, ListNode next){
		this.item = item;
		this.next = next; 
	}
	
	// Constructor to create last node with null pointer
	public ListNode(int item){
		this(item, null); 
	}
	
	// Inserts a new item after "this"
	public void insertAfter(int item){
		next = new ListNode(item, next);
	}
	
	// Finds node at nth position 
	public ListNode nth(int position){
		if (position == 1){
			return this;
		} else if ((position < 1) || (next == null)){
			return null;
		} else {
			return next.nth(position - 1);
		}	
	}
	
	public static void main(String[] args){
		ListNode l1 = new ListNode(4, new ListNode(7, 
								      new ListNode(6)));

	}
}
