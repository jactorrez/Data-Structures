package PriorityQueue;

import java.util.ArrayList;
import java.util.Comparator;

/* An implementation of a priority queue using an array-based min-heap */
public class HeapPriorityQueue<K,V> extends AbstractPriorityQueue<K,V>{
	
	/* primary collection of priority queue entries */
	public ArrayList<Entry<K,V>> heap;
	
	/* Creates an empty priority queue based on the natural ordering of its keys */
	public HeapPriorityQueue(){
		super();
		heap = new ArrayList<Entry<K,V>>();
	}
	
	public HeapPriorityQueue(ArrayList<Entry<K,V>> arr){
		super();
		heap = arr;
		buildHeap(heap);
	}
	
	/* Creates an empty priority queue using the given comparator to order keys */
	public HeapPriorityQueue(Comparator<K> comp){
		super(comp);
		heap = new ArrayList<Entry<K,V>>();
	}
	
	/* Creates a binary heap given a filled array */
	public void buildHeap(ArrayList<Entry<K,V>> a){
		int heapSize = heap.size();
		for(int i = heapSize / 2; i >= 0; i--){
			downheap(i);
		}
		
	}
	
	// protected utilities
	
	/* Returns parent of item in index i */
	public int parent(int i){		
		return (i - 1) / 2;
	}
	
	/* Returns left child of item in index i */
	public int left(int i){
		return 2*i + 1;
	}
	
	/* Returns right child of item in index i */
	public int right(int i){
		return 2*i + 2;
	}
	
	/* Tests whether item at index i has a left child */
	public boolean hasLeft(int i){
		return left(i) < heap.size();
	}
	
	/* Tests whether item at index i has a right child */
	public boolean hasRight(int i){
		return right(i) < heap.size();
	}
	
	/* Exchanges the entries at indices i and j of the array list */
	public void swap(int i, int j){
		Entry<K,V> temp = heap.get(i);
		heap.set(i, heap.get(j));
		heap.set(j, temp);
	}
	
	/* Moves the entry at index i higher, if necessary, to restore the heap property */
	protected void upheap(int i){
		while(i > 0){
			int p = parent(i);
			if(compare(heap.get(i), heap.get(p)) >= 0){
				break;					// heap property verified
			}
			swap(i, p);
			i = p;						// continue from the parent's location
		}
	}
	
	/* Moves the entry at index i lower, if necessary, to restore the heap property */
	public void downheap(int i){
		while(hasLeft(i)){
			int leftIndex = left(i);
			int smallChildIndex = leftIndex;
			if(hasRight(i)){
				int rightIndex = right(i);
				if(compare(heap.get(leftIndex), heap.get(rightIndex)) > 0){
					smallChildIndex = rightIndex;
				}
			}
			
			if(compare(heap.get(smallChildIndex), heap.get(i)) >= 0){
				break;				  // heap property restored
			} 
			
			swap(i, smallChildIndex);
			i = smallChildIndex;
		}
	}
	
	// public methods
	/* Returns the number of items in the priority queue */
	public int size(){
		return heap.size();
	}
	
	/* Returns (but does not remove) an entry with minimal key (if any) */
	public Entry<K,V> min(){
		if(heap.isEmpty())
			return null;
		
		return heap.get(0);
	}
	
	/* Inserts a key-value pair and returns the entry created */
	public Entry<K,V> insert(K key, V value) throws IllegalArgumentException{
		checkKey(key);
		Entry<K,V> newest = new PQEntry<>(key, value);
		heap.add(newest);			// add to the end of the list
		upheap(heap.size() - 1);	// upheap newly added entry
		return newest;
	}
	
	/* Removes and returns an entry with minimal key (if any) */
	public Entry<K,V> removeMin(){
		if(heap.isEmpty()) 
			return null;
		
		Entry<K,V> answer = heap.get(0);
		swap(0, heap.size() - 1);		// put minimum item at the end
		heap.remove(heap.size() - 1);	// and remove it from the list
		downheap(0);					// then fix new root
		return answer;
	}
}
