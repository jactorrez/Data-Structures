 package structs.priorityqueue;

import java.util.Comparator;

public abstract class AbstractPriorityQueue<K,V> implements PriorityQueue<K,V>{
	
	// instance variables
	private Comparator<K> comp;
	
	// constructors 
	protected AbstractPriorityQueue(Comparator<K> c){
		comp = c;
	}
	
	protected AbstractPriorityQueue(){
		this(new DefaultComparator<K>());
	}
	
	/*
	 * Method for comparing two entries according to key
	 */
	public int compare(Entry<K,V> a, Entry<K,V> b){
		return comp.compare(a.getKey(), b.getKey());
	}
	
	/*
	 * Determines whether a key is valid 
	 */
	protected boolean checkKey(K key) throws IllegalArgumentException{
		try{
			return (comp.compare(key, key) == 0); // see if key can be compared to itself
		} catch (ClassCastException e){
			throw new IllegalArgumentException("Incompatible key");
		}
	}
	
	/*
	 * Check whether the priority queue is empty
	 */
	
	public boolean isEmpty(){
		return size() == 0;
	}
	
	/*
	 * -----------  Nested PQEntry class -----------
	 */
	protected static class PQEntry<K,V> implements Entry<K,V>{
		private K k;
		private V v;
		
		public PQEntry(K key, V value){
			k = key;	// key of entry
			v = value;	// value of entry
		}
		
		// methods of the Entry interface
		
		public K getKey(){
			return k;
		}
		
		public V getValue(){
			return v;
		}
		
		// utilities not exposed as part of the Entry interfaces
		protected void setKey(K key){
			k = key;
		}
		
		protected void setValue(V value){
			v = value;
		}
	}
	
}

