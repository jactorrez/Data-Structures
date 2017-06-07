package PriorityQueue;

/*
 * Interface for key-value pair
 */
public interface Entry<K,V> {
	K getKey();		// returns the key stored in this entry
	V getValue();	// returns the value stores in this entry
}
