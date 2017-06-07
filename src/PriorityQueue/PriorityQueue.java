package PriorityQueue;

/*
 * Interface for PriorityQueue wherein objects with key of type K store values of type V
 */
public interface PriorityQueue<K,V>{
	int size();		// returns the amount of items in the priority queue
	boolean isEmpty();	// returns whether or not the priority queue is empty
	Entry<K,V> insert(K key, V value) throws IllegalArgumentException; // inserts entry with key k and value v to priority queue
	Entry<K,V> min(); // returns the minimum value of the priority queue
	Entry<K,V> removeMin(); // removes the minimum value of the priority queue
}
