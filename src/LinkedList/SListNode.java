package LinkedList;

public class SListNode<T> {
	public T item;
	public SListNode<T> next;

	public SListNode(T item, SListNode<T> next){
		this.item = item;
		this.next = next;
	}
	
	public SListNode(T item){
		this(item, null);
	}
}
