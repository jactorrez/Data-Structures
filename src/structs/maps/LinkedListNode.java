package structs.maps;

import structs.priorityqueue.Entry;

public class LinkedListNode<K,V> implements Entry<K,V>{
	private K key;
	private V value;
	private LinkedListNode<K,V> next;
	
	// constructor 
	public LinkedListNode(K k, V v, LinkedListNode<K,V> next){
		this.key = k;
		this.value = v;
		this.next = next;
	}
	
	public LinkedListNode(K k, V v){
		this(k, v, null);
	}
	
	// accessor methods
	public K getKey(){
		return key;
	}
	
	public V getValue(){
		return value;
	}
	
	public LinkedListNode<K,V> getNext(){
		return next;
	}
	// update methods
	public void setKey(K key){
		this.key = key;
	}
	
	public V setValue(V value){
		V oldVal = this.value;
		this.value = value;
		return oldVal;
	}
	
	public void setNext(LinkedListNode<K,V> next){
		this.next = next;
	}
}
