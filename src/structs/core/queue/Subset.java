package structs.core.queue;

import java.util.Scanner;
import structs.core.queue.RandomizedQueue;

public class Subset {
	
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		
		// prompt for the user's sequence
		System.out.print("Enter your string: ");
		
		// get sequence as a string, then convert to array to create randomized queue
		String userSeq = scanner.nextLine();
		String[] sequence = userSeq.split(" ");
		
		RandomizedQueue<String> userQ = new RandomizedQueue<>(sequence);
		
		System.out.print("Enter how many to show: ");
		
		int k = scanner.nextInt();
		
		for(int i = 0; i < k; i++){
			System.out.println(userQ.dequeue());
		}
		
		scanner.close();
	}
}
