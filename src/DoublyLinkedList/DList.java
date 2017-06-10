package DoublyLinkedList;

public class DList<T>{
	private DListNode<T> sentinel; // Points to sentinel
	private int size;
	
	public DList(){
		sentinel = new DListNode<T>(null, null, null);
		sentinel.next = sentinel.prev = sentinel;
		size = 0;
	}
	
	/* ------ Utility Methods ------ */
	
	// Returns node contained the given value, item
	private DListNode<T> find(T item){
		DListNode<T> tmp = sentinel.next;
		
		while(tmp != sentinel && !tmp.item.equals(item) ){
			tmp = tmp.next;
		}
		
		if(tmp == sentinel)
			throw new RuntimeException("Item not found");
		
		return tmp;
	}
	/* ------------------------ */	
		
	/*
	 * Return the size of the list
	 */
	public int getSize(){
		return size;
	}
	
	/*
	 * Return whether list is empty or not
	 */
	public boolean isEmpty(){
		return size == 0;
	}
	
	/*
	 * Returns whether or not a given list contains a given value, item
	 */
	public boolean contains(T item){
		return find(item) != null;
	}
	
	/*
	 * Adds item to end of a list
	 */
	public void add(T item) {
        DListNode<T> last = sentinel.prev;
        DListNode<T> newNode = new DListNode<T>(item);
        newNode.next = sentinel;
        newNode.prev = last;
        sentinel.prev = newNode;
        
        if(sentinel.next == sentinel)
        	sentinel.next = newNode;
        
        size++;
    }
	
	/*
	 * Adds item to beginning of a list
	 */
	public void addFirst(T item) {
		DListNode<T> currentFirst = sentinel.next;
		DListNode<T> newNode = new DListNode<>(item, currentFirst, sentinel);
		currentFirst.prev = newNode;
		sentinel.next = newNode;
		
		if(sentinel.prev == sentinel)
			sentinel.prev = newNode;
		
		size++;
	}
	
	/*
	 * Removes node which contains the value, item 
	 */
	public void remove(T item){
		DListNode<T> foundNode = find(item);
		DListNode<T> foundPrev = foundNode.prev;
		DListNode<T> foundNext = foundNode.next;
		foundPrev.next = foundNode.next;
		foundNext.prev = foundPrev;
		size--;
	}
	
	/*
	 * Removes last item in list
	 */
	public void removeLast(){
		DListNode<T> last = sentinel.prev;
		DListNode<T> lastPrev = last.prev;
		sentinel.prev = last.prev;
		lastPrev.next = sentinel;
		size--;
	}
	
	/*
	 * Removes first item in list
	 */
	public void removeFirst(){
		DListNode<T> first = sentinel.next;
		DListNode<T> firstNext = first.next;
		
		sentinel.next = firstNext;
		firstNext.prev = sentinel;
		size--;
	}
	
	/*
	 * Retrieves first item in list
	 */
	public T getFirst() { 
	    DListNode<T> first = sentinel.next;
	    return first.item;
	}
	
	/*
	 * Retrives last item in list
	 */
	public T getLast() { 
	    DListNode<T> last = sentinel.prev;
	    return last.item;
	}
	
	/*
	 * Completely clears list
	 */
	public void clear(){
		sentinel.next = sentinel.prev = sentinel;
		size = 0;
	}
	
	/*
	 * Returns string representation of list
	 */
	public String toString() {
		
		DListNode<T> tmp = sentinel.next;
		StringBuilder result = new StringBuilder();
		result.append("[");
		while(tmp != sentinel){
			
			result.append(tmp.item + ",");
			
			tmp = tmp.next;
		}
		result.append("]");
		
		return result.toString();
	}
	
	public static void main(String[] args){
		DList<Integer> test = new DList<>();
		
		test.add(3);
		test.addFirst(4);
		
		System.out.println(test.sentinel.next.item);	
	}
}
