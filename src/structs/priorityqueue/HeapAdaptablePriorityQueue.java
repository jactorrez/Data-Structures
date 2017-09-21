package structs.priorityqueue;

import java.util.Comparator;

/* An implementation of an adaptable priority queue using an array-based heap */
public class HeapAdaptablePriorityQueue<K,V> extends HeapPriorityQueue<K,V>{
	
	/* ----- nested AdaptablePQEntry class ----- */
	// Extends PQEntry to make entry location-aware 
	protected static class AdaptablePQEntry<K,V> extends PQEntry<K,V>{
		private int index;
		public AdaptablePQEntry(K key, V value, int i){
			super(key, value);
			index = i;
		}
		
		public int getIndex(){
			return index;
		}
		
		public void setIndex(int i){
			index = i;
		}
	}
	
	/* Creates an empty adaptable priority queue using natural ordering of keys */
	public HeapAdaptablePriorityQueue(){
		super();
	}
	
	/* Creates an empty adaptable priority queue using the given comparator */
	public HeapAdaptablePriorityQueue(Comparator<K> comp){
		super(comp);
	}
	
	// protected utilities 
	
	protected AdaptablePQEntry<K,V> validate(Entry<K,V> entry) throws IllegalArgumentException{
		if(!(entry instanceof AdaptablePQEntry))
			throw new IllegalArgumentException("Invalid array");
		AdaptablePQEntry<K,V> locator = (AdaptablePQEntry<K,V>) entry; // safe
		int j = locator.getIndex();
		if(j > heap.size() || heap.get(j) != locator)
			throw new IllegalArgumentException("Invalid entry");
		
		return locator;
	}
	
	/* Exchanges the entries at indices i and j of the array list */
	public void swap(int i, int j){
		super.swap(i, j);
		((AdaptablePQEntry<K,V>) heap.get(i)).setIndex(i);
		((AdaptablePQEntry<K,V>) heap.get(j)).setIndex(j);
	}
	
	/* Restores the heap property by moving the entry at index j upward/downward */
	protected void bubble(int j){
		if(j > 0 && compare(heap.get(j), heap.get(parent(j))) < 0){
			upheap(j);
		} else{
			downheap(j);
		}
	}
	
	/* Inserts a key-value pair and returns the entry created */
	public Entry<K,V> insert(K key, V value) throws IllegalArgumentException{
		checkKey(key);
		Entry<K,V> newest = new AdaptablePQEntry<>(key, value, heap.size());
		heap.add(newest);
		upheap(heap.size()-1);
		return newest;
	}
	
	/* Removes the given entry from the priority queue */
	public void remove(Entry<K,V> entry) throws IllegalArgumentException{
		AdaptablePQEntry<K,V> locator = validate(entry);
		int j = locator.getIndex();
		if(j == heap.size() - 1){
			heap.remove(heap.size() - 1);
		} else {
			swap(j, heap.size() - 1);
			heap.remove(heap.size() - 1);
			bubble(j);
		}
	}
	
	/* Replaces the key of an entry */
	public void replaceKey(Entry<K,V> entry, K key) throws IllegalArgumentException{
		AdaptablePQEntry<K,V> locator = validate(entry);
		checkKey(key);
		locator.setKey(key);
		bubble(locator.getIndex());
	}
	
	/* Replaces the value of an entry */
	public void replaceValue(Entry<K,V> entry, V value) throws IllegalArgumentException{
		AdaptablePQEntry<K,V> locator = validate(entry);
		locator.setValue(value);
	}
	
}
