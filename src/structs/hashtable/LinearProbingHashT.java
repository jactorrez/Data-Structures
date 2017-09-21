package structs.hashtable;

public class LinearProbingHashT {
	protected int N = 0;    // number of entries in the hash table
	protected int capacity; // length of the hash table 
	public String[] entries; 
	
	public LinearProbingHashT(int cap){
		capacity = isPrime(cap) ? cap : getNextPrime(cap);
		entries = new String[capacity];
	}
	
	// public methods
	public void put(String key){
		if(N >= capacity/2){
			resize(2*capacity);
		}
		
		int i;
		int stepDistance = 7 - (Integer.parseInt(key) % 7);
		for(i = hash(key); entries[i] != null; i += stepDistance){
			if(entries[i].equals(key)){
				return;
			}
		}
		entries[i] = key;
		N++;
	}
	
	public String get(String key){
		int stepDistance = 7 - (Integer.parseInt(key) % 7);
		
		for(int i = hash(key); entries[i] != null; i += stepDistance){
			
			if(entries[i].equals(key)){
				return entries[i];
			}
		}
		return null;
	}
		
	public void resize(int newCap){
		int newCapacity = getNextPrime(newCap);
		createNewArray(newCapacity);
	}
	
	public void delete(String key){
		int keyIndex = findIndex(key);
		if(keyIndex != -1){
			entries[keyIndex] = null;
			N--;
			return;
		} else {
			return;
		}
	}

	//private utilities 
	private int hash(String key){
		return Integer.parseInt(key) % capacity;
	}
	
	private int findIndex(String key){
		int stepDistance = 7 - (Integer.parseInt(key) % 7);
		
		for(int i = hash(key); entries[i] != null; i += stepDistance){
			if(entries[i].equals(key)){
				return i;
			}
		}
		return -1;
	}
	
	private boolean isPrime(int number){
		if(number % 2 == 0){
			return false;
		}
		
		for(int i = 3; i * i < number; i+=2){
			if(number % i == 0){
				return false;
			}
		}
		return true;
	}
	
	private int getNextPrime(int numberToCheck){
		for(int i = numberToCheck; true; i++){
			if(isPrime(i)){
				return i; 
			}
		}
	}

	private void createNewArray(int cap){
		LinearProbingHashT t = new LinearProbingHashT(cap);
		
		for(int i = 0; i < capacity; i++){
			if(entries[i] != null){
				t.put(entries[i]);
			}
		}
		
		entries = t.entries.clone();
		capacity = cap;
		N = t.N;
	}
	
	public static void main(String[] args){
//		LinearProbingHashT hashTest = new LinearProbingHashT(10);
//		System.out.println("------------- Before Resize -------------");
//		hashTest.put("40");
//		hashTest.put("80");
//		System.out.println("------------- After Resize -------------");
//		hashTest.resize(40);
//		hashTest.get("80");
//		hashTest.delete("80");
//		hashTest.get("80");
//		System.out.println("Total items in array: " + hashTest.N);
//		System.out.println("Total capacity: " + hashTest.capacity);
	}
}
