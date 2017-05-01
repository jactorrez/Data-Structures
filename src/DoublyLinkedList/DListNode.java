package DoublyLinkedList;

public class DListNode<T> {
	T item;
	DListNode<T> next;
	DListNode<T> prev; 
	
	public DListNode(T item){
		this.item = item;
		this.next = null;
		this.prev = null;
	}
	
	public DListNode(T item, DListNode<T> next, DListNode<T> prev){
		this.item = item;
		this.next = next;
		this.prev = prev;
	}
}
