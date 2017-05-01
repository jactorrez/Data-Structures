package DoublyLinkedList;

public class DList<T>{
	private DListNode<T> head; // Points to sentinel
	private int size;
	
	public DList(){
		head = new DListNode<T>(null, null, null);
		head.next = head.prev = head;
		size = 0;
	}
	
	/* ------ Utility Methods ------ */
	
	// Returns node contained the given value, item
	private DListNode<T> find(T item){
		DListNode<T> tmp = head.next;
		
		while(tmp != head && !tmp.item.equals(item) ){
			tmp = tmp.next;
		}
		
		if(tmp == head)
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
        DListNode<T> last = head.prev;
        DListNode<T> newNode = new DListNode<T>(item);
        newNode.next = head;
        newNode.prev = last;
        head.prev = newNode;
        
        if(head.next == head)
        	head.next = newNode;
        
        size++;
    }
	
	/*
	 * Adds item to beginning of a list
	 */
	public void addFirst(T item) {
		DListNode<T> currentFirst = head.next;
		DListNode<T> newNode = new DListNode<>(item, currentFirst, head);
		currentFirst.prev = newNode;
		head.next = newNode;
		
		if(head.prev == head)
			head.prev = newNode;
		
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
		DListNode<T> last = head.prev;
		DListNode<T> lastPrev = last.prev;
		head.prev = last.prev;
		lastPrev.next = head;
		size--;
	}
	
	/*
	 * Removes first item in list
	 */
	public void removeFirst(){
		DListNode<T> first = head.next;
		DListNode<T> firstNext = first.next;
		
		head.next = firstNext;
		firstNext.prev = head;
		size--;
	}
	
	/*
	 * Retrieves first item in list
	 */
	public T getFirst() { 
	    DListNode<T> first = head.next;
	    return first.item;
	}
	
	/*
	 * Retrives last item in list
	 */
	public T getLast() { 
	    DListNode<T> last = head.prev;
	    return last.item;
	}
	
	/*
	 * Completely clears list
	 */
	public void clear(){
		head.next = head.prev = head;
		size = 0;
	}
	
	/*
	 * Returns string representation of list
	 */
	public String toString() {
		
		DListNode<T> tmp = head.next;
		StringBuilder result = new StringBuilder();
		result.append("[");
		while(tmp != head){
			
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
		
		System.out.println(test.head.next.item);
		
	}
}
