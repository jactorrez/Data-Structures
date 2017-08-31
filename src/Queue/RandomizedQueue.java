package Queue;

import java.util.NoSuchElementException;
import java.util.Random;

public class RandomizedQueue<Item> implements Iterable<Item> {
	
	private int size; 
	private QueueNode head;
	private QueueNode tail;
	
	public RandomizedQueue(){
		head = tail =null;
		size = 0;
	}
	
	public boolean isEmpty(){
		return (size == 0);
	}
	
	public int size(){
		return size;
	}
	
	public void enqueue(Item data){
		if(data == null){
			throw new NullPointerException("Attempted to add null item");
		}
		if(head == null){
			head = tail = new QueueNode(data);
			size++;
			return;
		}
		
		QueueNode prevLast = tail;
		tail = tail.next = new QueueNode(data);
		tail.prev = prevLast;
		size++;
	}
	
	// deletes and returns a random item
	public Item dequeue(){
		if(size == 0){
			throw new NoSuchElementException("Attempted to dequeue from an empty queue");
		}
		
		Random rand = new Random();
		int n = rand.nextInt(size + 1);
		
		QueueNode nthNode = findNth(n);
		
		if(nthNode == head){
			head = head.next; 
			head.prev = null;
		} else if(nthNode == tail){
			tail = tail.prev;
			tail.next = null;
		} else {
			QueueNode prev = nthNode.prev;
			QueueNode succ = nthNode.next;
			
			prev.next = succ;
			succ.prev = prev;
		}
		
		return nthNode.data;
	}
	
	// returns but does not delete a random item
	public Item sample(){
		if(size == 0){
			throw new NoSuchElementException("Attempted to dequeue from an empty queue");
		}
		
		Random rand = new Random();
		int n = rand.nextInt(size + 1);
		
		QueueNode nthNode = findNth(n);
		
		return nthNode.data;
	}
	
	public Iterator<Item> iterator(){
		
	}
	
	private QueueNode findNth(int n){
		
		QueueNode currentNode = head;
		
		while(n != 1){
			currentNode = currentNode.next;
			n--;
		}
		
		return currentNode;
	}
	
	private class QueueNode{
		public Item data;
		public QueueNode next;
		public QueueNode prev;
		
		public QueueNode(Item data, QueueNode next, QueueNode prev){
			this.data = data;
			this.next = next;
			this.prev = prev;
		}
		
		public QueueNode(Item data){
			this(data, null, null);
		}
	}
	
	public static void main(String[] args){
		
	}

}
