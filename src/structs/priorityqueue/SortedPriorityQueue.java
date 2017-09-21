package structs.priorityqueue;

import java.util.Comparator;

import structs.lists.LinkedPositionalList;
import structs.lists.Position;
import structs.lists.PositionalList;

public class SortedPriorityQueue<K,V> extends AbstractPriorityQueue<K,V>{
	private PositionalList<Entry<K,V>> list = new LinkedPositionalList<>();
	
	// constructors
	/* Creates an empty priority queue based on the natural ordering of its keys */
	public SortedPriorityQueue(){
		super();
	}
	/* Creates an empty priority queue using the given comparator to order keys */
	public SortedPriorityQueue(Comparator<K> comp) {
		super(comp);
	}
	
	/* Inserts a key-value pair and returns the entry created */
	public Entry<K,V> insert(K key, V value){
		checkKey(key);	// key checking method
		Entry<K,V> newEntry = new PQEntry<>(key, value);
		Position<Entry<K,V>> walk = list.last();
		while(walk != null && (compare(newEntry, walk.getElement()) < 0)){
			walk = list.before(walk);
		}
		if(walk == null){
			list.addFirst(newEntry);
		} else {
			list.addAfter(newEntry, walk);
		}
		
		return newEntry;
	}
	
	/* Returns (but does not remove) an entry with minimal key */
	public Entry<K,V> min(){
		if(list.isEmpty())
			return null;
		return list.first().getElement();
	}
	
	/* Removes and returns an entry with minimal key */
	public Entry<K,V> removeMin(){
		if(list.isEmpty())
			return null;
		Entry<K,V> first = list.first().getElement();
		list.remove(list.first());
		
		return first;
	}
	
	/* Returns the current size of the queue */
	public int size(){
		return list.size();
	}
}
