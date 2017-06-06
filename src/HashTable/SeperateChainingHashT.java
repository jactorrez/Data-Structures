package HashTable;

import LinkedList.SList;
import LinkedList.SListNode;

public class SeperateChainingHashT {
	
	WordList[] entries; // array of WordList objects
	int capacity;	// hash table size
	
	public SeperateChainingHashT(int cap){
		capacity = isPrime(cap) ? cap : getNextPrime(cap);
		entries = new WordList[capacity];
		
		// instantiate each bucket in entries with WordList containers
		for(int i = 0; i < capacity; i++){
			entries[i] = new WordList();
		}
	}
	
	/*
	 * Get word
	 */
	public Word get(String word){
		return entries[stringHash(word)].find(word);
	}
	
	/*
	 * Add new word
	 */
	public void put(Word newWord){
		String word = newWord.word; 
		entries[stringHash(word)].addLast(newWord);
	}
	
	/*
	 * Remove word
	 */
	public void remove(String word){
		entries[stringHash(word)].removeWord(word);
	}
	
	/*
	 * Display all lists
	 */
	
	public void display(){
		for(WordList list : entries){
			System.out.println("Displaying one list");
			list.displayWordList();
			System.out.println();
		}
	}
	
	/* ------ Utility Methods ------ */
	
	/*
	 * Hash function for strings
	 */
	private int stringHash(String word){
		return (word.hashCode() % capacity);
	}
	
	/*
	 * Check if number is prime
	 */
	
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
	
	/*
	 * Calculate next possible prime number given initial number
	 */
	
	private int getNextPrime(int number){
		for(int i = number; true; i++){
			if(isPrime(i)){
				return i; 
			}
		}
	}
	/* ------------------------ */
	
	public static void main(String[] args){
		SeperateChainingHashT testHashTable = new SeperateChainingHashT(31); 
		
		String[][] words = {
				{ "ace", "Very good" },
				{ "act", "Take action" },
				{ "add", "Join (something) to something else" },
				{ "age", "Grow old" },
				{ "ago", "Before the present" },
				{ "aid", "Help, assist, or support" },
				{ "aim", "Point or direct" },
				{ "air", "Invisible gaseous substance" },
				{ "all", "Used to refer to the whole quantity" },
				{ "amp",
						"Unit of measure for the strength of an electrical current" },
				{ "and", "Used to connect words" }, { "ant", "A small insect" },
				{ "any", "Used to refer to one or some of a thing" },
				{ "ape", "A large primate" },
				{ "apt", "Appropriate or suitable in the circumstances" },
				{ "arc", "A part of the circumference of a curve" },
				{ "are", "Unit of measure, equal to 100 square meters" },
				{ "ark", "The ship built by Noah" },
				{ "arm", "Two upper limbs of the human body" },
				{ "art", "Expression or application of human creative skill" },
				{ "ash", "Powdery residue left after the burning" },
				{ "ask", "Say something in order to obtain information" },
				{ "asp", "Small southern European viper" },
				{ "ass", "Hoofed mammal" },
				{ "ate", "To put (food) into the mouth and swallow it" },
				{ "atm", "Unit of pressure" },
				{ "awe", "A feeling of reverential respect" },
				{ "axe", "Edge tool with a heavy bladed head" },
				{ "aye", "An affirmative answer" } };
		
		for(int i = 0; i < words.length; i ++){
			String word = words[i][0];
			String definition = words[i][1];
			Word wordToAdd = new Word(word, definition);
			
			testHashTable.put(wordToAdd);
		}
		
		//testHashTable.display();
	}
}

class Word {
	public String word;
	public String defintion;
	
	public Word(String word, String definition){
		this.word = word;
		this.defintion = definition;
	}
	
	public String toString(){
		return word + " : " + defintion;
	}
}

class WordList extends SList<Word>{
	
	public WordList(){
		super();
	}
	
	/*
	 * Find word that matches given word
	 */
	public Word find(String word){
		if (word.equals("")|| head == null)
			throw new RuntimeException("Enter a correct word to search or add a word to the list");
		
		SListNode<Word> current = head;
		
		while(current != null){
			if(current.item.word.equals(word)){
				break;
			}
			current = current.next;
		}
		
		if(current == null)
			throw new RuntimeException("No item exists with this value");
		
		return current.item;
	}
	
	/*
	 * Remove word that matches given word
	 */
	public void removeWord(String word){
		// List is empty 
		if(size == 0) 
			throw new RuntimeException("List empty, unable to delete");
		
		// Delete the head node
		if(head.item.word.equals(word)){
			head = head.next;
			size--;
			return;
		}
		
		SListNode<Word> cur = head;
		SListNode<Word> prev = null; 
		
		while(cur != null && !cur.item.word.equals(word)){
			prev = cur;
			cur = cur.next;
		}
		
		if(cur == null)
			throw new RuntimeException("Word not found");
		
		prev.next = cur.next;
		size--;
	}
	
	/*
	 * Display list 
	 */
	public void displayWordList(){
		System.out.println("The size of this list is " + size);
		if(size == 0){
			return;
		}
		
		SListNode<Word> current = head;
		
		while(current != null){
			System.out.println(current.item);
			current = current.next;
		}
	}
}

