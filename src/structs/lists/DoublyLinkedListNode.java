package structs.lists;

public class DoublyLinkedListNode<T> {
	T item;
	DoublyLinkedListNode<T> next;
	DoublyLinkedListNode<T> prev; 
	
	public DoublyLinkedListNode(T item){
		this.item = item;
		this.next = null;
		this.prev = null;
	}
	
	public DoublyLinkedListNode(T item, DoublyLinkedListNode<T> next, DoublyLinkedListNode<T> prev){
		this.item = item;
		this.next = next;
		this.prev = prev;
	}
}
