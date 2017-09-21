package structs.lists;

public class LinkedListNode<T> {
	public T item;
	public LinkedListNode<T> next;

	public LinkedListNode(T item, LinkedListNode<T> next){
		this.item = item;
		this.next = next;
	}
	
	public LinkedListNode(T item){
		this(item, null);
	}
}
