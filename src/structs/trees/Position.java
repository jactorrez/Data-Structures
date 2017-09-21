package structs.trees;

public interface Position<T> {
	
	/*
	 * Returns the element stored at this position
	 */
	
	T getElement() throws IllegalStateException;
}
