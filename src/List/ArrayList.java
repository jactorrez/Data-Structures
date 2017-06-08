package List;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayList<E> implements List<E>, Iterable<E> {
	// instance variables
	public static final int CAPACITY = 16; 		// default array capacity
	private E[] data;							// generic array used for storage
	private int size = 0;						// current number of elements
	
	// constructors 
	public ArrayList(int capacity){				// constructs list with given capacity
		data = (E[]) new Object[capacity];		// safe cast
	}
	
	public ArrayList(){							// constructs list with default capacity
		this(CAPACITY);
	}
	
	// public methods
	
	/* Returns the number of elements in the array list */
	public int size(){
		return size;
	}
	
	/* Returns whether or not the current array list is empty */
	public boolean isEmpty(){
		return (size == 0);
	}
	
	/* Returns but does not remove the element at index i */
	public E get(int i) throws IndexOutOfBoundsException{
		checkIndex(i, size);
		return data[i];
	}
	
	/* Replaces the element at index i with e, and returns the replaced element */
	public E set(int i, E e) throws IndexOutOfBoundsException{
		checkIndex(i, size);
		E temp = data[i];
		data[i] = e;
		return temp; 
	}
	
	/* Inserts element e to be at index i, shifting all subsequent elements later */
	public void add(int i, E e) throws IndexOutOfBoundsException{
		
		checkIndex(i, size + 1);
		
		int lastElIndex = size - 1;
		
		if(size == data.length)
			resize(2 * data.length);
		
		while(lastElIndex >= i){
			data[lastElIndex + 1] = data[lastElIndex];
			lastElIndex--;
		}
		
		data[i] = e;	
		size++;
	}
	
	/* Removes/returns the element at index i, shifting subsequent elements earlier */
	public E remove(int i) throws IndexOutOfBoundsException, IllegalStateException{
		checkIndex(i, size);
		
		E temp = data[i];
		
		while(data[i + 1] != null){
			data[i] = data[i + 1];
			i++;
		}
		data[i] = null;
		
		size--;
		return temp;
	}
	
	// utility methods
	
	/* Checks whether the given index is in the range [0, n-1] */
	protected void checkIndex(int i, int size) throws IndexOutOfBoundsException{
		if(i < 0 || i >= size){
			throw new IndexOutOfBoundsException("Illegal index: " +i);
		}
	}
	
	/* Resizes internal array to have given capacity >= size */
	protected void resize(int capacity){
		E[] temp = (E[]) new Object[capacity];	 // safe cast;
		
		for(int i = 0, length = size; i < length; i++){
			temp[i] = data[i];
		}
		
		data = temp;		// start using the new array
	}
	
	public Iterator<E> iterator(){
		return new ArrayIterator();
	}
	
	// nested ArrayIterator class to make containing class iterable
	
	/* A non-static inner class. Each instance contains an implicit reference to
	 * the containing list, allowing it to access the lists' members 
	 */
	private class ArrayIterator implements Iterator<E>{
		private int j = 0;
		private boolean removable = false;
		
		/* Tests whether the iterator has a next object */
		public boolean hasNext(){
			return j < size;
		}
		
		/* Returns the next object in the iterator */
		public E next() throws NoSuchElementException{
			if(j == size)
				throw new NoSuchElementException("No elements left"); 
			removable = true;		// element can be subsequently removed
			return data[j++];
		}
		
		/* Removes the element returned by most recent call to next */
		public void remove() throws IllegalStateException{
			if(!removable)
				throw new IllegalStateException("Nothing to remove");
			
			ArrayList.this.remove(--j);
			removable = false;
		}
		
		
	}
	
}
