package structs.maps;

import java.util.ArrayList;
import java.util.Random;
import structs.priorityqueue.Entry;

public abstract class AbstractHashMap<K,V> extends AbstractMap<K,V>{
	// instance variables
	protected int n = 0;			// number of entries in the dictionary
	protected int capacity;			// length of the table
	protected int prime;			// prime factor
	private long scale, shift;		// the shift and scaling factors 
	protected final double LOAD_FACTOR = 0.75;
	
	// constructors
	public AbstractHashMap(int cap, int p){
		prime = p;
		capacity = (int) Math.floor(cap / LOAD_FACTOR);
		Random rand = new Random();
		scale = rand.nextInt(prime-1) + 1;
		shift = rand.nextInt(prime);
		createTable();
	}
	
	public AbstractHashMap(int cap){
		this(cap, 10935121);		// default prime
	}
	
	public AbstractHashMap(){
		this(17);					// default capacity
	}
	
	// public methods
	public int size(){
		return n;
	}
	
	public V get(K key){
		return bucketGet(hashValue(key), key);
	}
	
	public V remove(K key){
		return bucketRemove(hashValue(key), key);
	}
	
	public V put(K key, V value){
		V answer = bucketPut(hashValue(key), key, value);
		if(n > (capacity * LOAD_FACTOR))	// keep load factor <= 0.75
			resize(2 * (capacity - 1));		// or resize
		return answer;
	}
	
	// private utilities 
	private int hashValue(K key){
		return (int) ((Math.abs(key.hashCode() * scale + shift) % prime) % capacity);
	}
	
	private void resize(int newCap){
		ArrayList<Entry<K,V>> buffer = new ArrayList<>(n);
		for(Entry<K,V> e : entrySet()){
			buffer.add(e);
		}
		
		capacity = newCap;
		createTable();						// based on updated capacity
		n = 0;								// will be recomputed while inserting entries
		
		for(Entry<K,V> e : buffer){
			put(e.getKey(), e.getValue());
		}
	}
	
	// protected abstract methods to be implemented by subclasses
	protected abstract void createTable();
	protected abstract V bucketGet(int h, K k);
	protected abstract V bucketPut(int h, K k, V v);
	protected abstract V bucketRemove(int h, K k);
}
