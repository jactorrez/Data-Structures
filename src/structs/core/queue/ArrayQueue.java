package structs.core.queue;

public class ArrayQueue<E> implements Queue<E> {
	
	// instance variables 
	private E[] data;	// array used for storage
	private int f = 0;	// index of front element
	private int sz = 0; // current number of elements
	
	public ArrayQueue(int capacity){
		data = (E[]) new Object[capacity];
	}
	
	public ArrayQueue(){
		this(1000);
	}
	
	/*
	 * Returns the number of elements in the queue
	 */
	public int size(){
		return sz;
	}
	
	/*
	 * Tests whether the queue is empty
	 */
	public boolean isEmpty(){
		return (sz == 0);
	}
	
	/*
	 * Inserts an element at the rear of the queue
	 */
	public void enqueue(E e) throws IllegalStateException {
		if(sz == data.length){
			throw new IllegalStateException("Queue is full");
		}
		
		int avail = (f + sz) % data.length;
		data[avail] = e;
		sz++;
	}
	
	/*
	 * Returns but does not remove, the first element of the queue (null if empty)
	 */
	public E first(){
		if(isEmpty())
			return null;
		
		return data[f];
	}
	
	/*
	 * Removes and returns the first element of the enqueue (null if empty)
	 */
	public E dequeue(){
		if(isEmpty())
			return null;
		
		E first = data[f];
		data[f] = null;
		f = (f + 1) % sz;
		sz--;
		
		return first;
	}
}
