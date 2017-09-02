package Queue;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;

public class RandomizedQueue<Item> implements Iterable<Item> {
	
	private int size = 0; 
	private QueueNode head;
	private QueueNode tail;
	
	public RandomizedQueue(){
		head = tail = null;
	}
	
	public RandomizedQueue(Item[] items){
		
		for(int i = 0; i < items.length; i++){
			this.enqueue(items[i]);
		}
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
		
		int n = getRandomIndex();
		
		QueueNode nthNode = findNth(n);
				
		if(size == 2){
			head = tail;
			tail.prev = null;
		} else if(size == 1){
			head = tail = null;
		} else if(nthNode == head){
			head = head.next; 
		} else if(nthNode == tail){
			tail = tail.prev;
			tail.next = null;
		} else {
			QueueNode prev = nthNode.prev;
			QueueNode succ = nthNode.next;
			
			prev.next = succ;
			succ.prev = prev;
		}
		
		size--;
		
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
		return new RandomizedQueueIterator();
	}
	
	private class RandomizedQueueIterator implements Iterator<Item>{
		
		private LinkedList<Item> randomList;
		private boolean[] visited = new boolean[size() + 1];

		private int filled = 0;
		
		public RandomizedQueueIterator(){
			visited[0] = true;
			randomList = new LinkedList<>();
			
			for(int i = 1; i < visited.length; i++){
				visited[i] = false;
			}
			
			while(filled != size()){
				int randIndex = getRandomIndex();
				
				if(!visited[randIndex]){
					randomList.push(findNth(randIndex).data);
					visited[randIndex] = true;
					filled++;
				}	
			}		
		}
		
		@Override 
		public boolean hasNext(){
			return (!randomList.isEmpty());
		}
		
		@Override
		public Item next(){
			if(!hasNext()){
				throw new NoSuchElementException("No items left to iterate over");
			}
			
			return randomList.removeFirst();
		}
		
		@Override
		public void remove(){
			throw new UnsupportedOperationException("Removal during iteration is not supported");
		}
		
	}
	
	private int getRandomIndex(){
		
		Random rand = new Random();
		int n = rand.nextInt(size) + 1;
		
		return n;	
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
		RandomizedQueue<Integer> test = new RandomizedQueue<>();
		
		System.out.println("Filling queue...");

		for(int i = 1; i <= 10; i++){
			test.enqueue(i);
		}
		
		System.out.println("Testing iterator...");
		Iterator<Integer> qIterator = test.iterator();
		while(qIterator.hasNext()){
			System.out.println(qIterator.next());
		}
		
		System.out.println("Testing randomized dequeue...");
		
		for(int i = 1; i <= 10; i++){
			System.out.println(test.dequeue());
		}		
	}
}
