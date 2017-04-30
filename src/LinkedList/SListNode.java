package LinkedList;

public class SListNode {
	public int item;
	public SListNode next;

	public SListNode(int item, SListNode next){
		this.item = item;
		this.next = next;
	}
	
	public SListNode(int item){
		this(item, null);
	}
}
