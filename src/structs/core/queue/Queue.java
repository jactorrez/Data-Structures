package structs.core.queue;

public interface Queue<T>{

	/*
	 * Returns the number of elements in the queue 
	 */
	int size();
	
	/*
	 * Test whether the queue is empty 
	 */
	boolean isEmpty();
	
	/*
	 * Inserts an element at the rear of a queue
	 */
	void enqueue(T t);
	
	/*
	 * Returns but does not remove the first element of a queue (null if empty)
	 */
	T first();
	
	/*
	 * Removes and returns the first element of the queue (null if empty)
	 */
	T dequeue();
}
