package Queue;
import LinkedList.SList;

public class LinkedQueue<E> implements Queue<E>{
	// instance variables
	private SList<E> list = new SList<>();
	
	// constructors
	public LinkedQueue(){}
	
	/*
	 * Returns the number of elements in the queue
	 */
	public int size(){
		return list.getSize();
	}
	
	/*
	 * Tests whether the queue is empty
	 */
	public boolean isEmpty(){
		return list.isEmpty();
	}
	
	/*
	 * Inserts an element at the rear of the queue
	 */
	public void enqueue(E Element){
		list.addLast(Element);
	}
	
	/*
	 * Returns the first element of the queue without removing (null if empty)
	 */
	public E first(){
		return list.getFirst();
	}
	
	/*
	 * Removes and returns the first element of the queue (null if empty)
	 */
	public E dequeue(){
		E first = list.getFirst();
		list.removeFirst();
		return first;
	}
}
