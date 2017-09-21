package structs.core.stack;

import structs.lists.LinkedList;

public class LinkedStack<E> implements Stack<E>{
	// instance variables
	private LinkedList<E> list = new LinkedList<>();	// an empty singly-linked list
	
	public LinkedStack(){} 		// new stack relies on the initially empty list 
	
	public int size(){
		return list.getSize();
	}
	
	public boolean isEmpty(){
		return list.isEmpty();
	}
	
	public void push(E item){
		list.addFirst(item);
	}
	
	public E top(){
		return list.getFirst();
	}
	
	public E pop(){
		E answer = top();
		list.removeFirst();
		return answer;
	}
}
