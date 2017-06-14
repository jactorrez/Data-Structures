package Map;

import java.util.ArrayList;

import PriorityQueue.Entry;

public class ChainHashMap<K,V> extends AbstractHashMap<K,V>{

	// a fixed capacity array of UnsortedTableMap that serve as buckets
	private EntryLinkedList<K,V>[] table; 	// initialized within createTable
	
	// constructors
	public ChainHashMap(){
		super();
	}
	
	public ChainHashMap(int cap){
		super(cap);
	}
	
	public ChainHashMap(int cap, int p){
		super(cap, p);
	}
	
	/* Creates an empty table having length equal to current capacity */
	protected void createTable(){
		table = (EntryLinkedList<K,V>[]) new EntryLinkedList[capacity];
	}
	
	/* Returns value associated with given key in bucket with hash value h, or else null */
	protected V bucketGet(int h, K key){
		EntryLinkedList<K,V> bucket = table[h];
		if(bucket == null){
			return null;
		}
	
		return bucket.find(key).getValue();
	}
	
	/* Associates key k with value in bucket with hash value h; returns old value */
	protected V bucketPut(int h, K k, V v){
		EntryLinkedList<K,V> bucket = table[h];
		if(bucket == null)
			bucket = table[h] = new EntryLinkedList<>();
		
		int oldSize = bucket.getSize();
		V answer = bucket.add(k, v).getValue();
		n += (bucket.getSize() - oldSize);				// size may have increased
		return answer;
	}
	
	/* Removes entry having key k from bucket with hash value h (if any) */
	protected V bucketRemove(int h, K key){
		EntryLinkedList<K,V> bucket = table[h];
		if(bucket == null)
			return null;
		int oldSize = bucket.getSize();
		V answer = bucket.remove(key).getValue();
		n -= (oldSize - bucket.getSize());				  // size may have decreased
		return answer; 
	}
	
	/* Returns an iterable collection of all key-value entries of the map */
	public Iterable<Entry<K,V>> entrySet(){
		ArrayList<Entry<K,V>> buffer = new ArrayList<>();
		for(int h = 0; h < capacity; h++){
			if(table[h] != null){
				for(Entry<K,V> entry : table[h].entrySet()){
					buffer.add(entry);
				}
			}
		}
		return buffer;
	}
	
	
	
}
