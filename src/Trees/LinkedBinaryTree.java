package Trees;

/*
 * Concrete implementation of a binary tree using a node-based, linked structure
 */
public class LinkedBinaryTree<T> extends AbstractBinaryTree<T> implements BinaryTree<T>{
	
	// instance variables
	protected Node<T> root = null;
	private int size = 0;
	
	public LinkedBinaryTree(){
		// constructs an empty binary tree
	}
	// Function to create a new node storing element e 
	protected Node<T> createNode(T e, Node<T> parentNode, Node<T> leftNode, Node<T> rightNode){
		return new Node(e, parentNode, leftNode, rightNode);
	}
	// nested Node class
	protected static class Node<E> implements Position<E>{
		private E element;		// element stored in current node
		private Node<E> parent;	// a reference to the parent node (if any)
		private Node<E> left; 	// reference to the left child node (if any)
		private Node<E> right;  // reference to the right child node (if any)
		
		public Node(E e, Node<E> above, Node<E> leftChild, Node<E> rightChild){
			element = e;
			parent = above;
			left = leftChild;
			right = rightChild;
		}
		
		// accessor methods 
		public E getElement(){
			return element;
		}
		
		public Node<E> getParent(){
			return parent;
		}
		
		public Node<E> getLeft(){
			return left;
		}
		
		public Node<E> getRight(){
			return right;
		}
		
		// update methods
		public void setElement(E e){
			element = e;
		}
		
		public void setParent(Node<E> parentNode){
			parent = parentNode;
		}
		
		public void setLeft(Node<E> leftNode){
			left = leftNode;
		}
		
		public void setRight(Node<E> rightNode){
			right = rightNode;
		}
	}
	
	// Non-public utility 
	protected Node<T> validate(Position<T> p) throws IllegalArgumentException{
		if(!(p instanceof Node))
			throw new IllegalArgumentException("Not valid position type");
		Node<T> node = (Node<T>) p;
		if(node.getParent() == node)
			throw new IllegalArgumentException("p is no longer in the tree");
		return node;
	}
	
	// accessor methods (not already implemented in AbstractBinaryTree)
	// Returns the number of nodes in the tree
	public int size(){
		return size;
	}
	
	// Returns the root Position in the tree (null if tree is empty)
	public Position<T> root() throws IllegalArgumentException{
		return root;
	}
	
	// Returns the Position of p's parent (or null if p is root)
	public Position<T> parent(Position<T> p) throws IllegalArgumentException{
		Node<T> node = validate(p);
		return node.getParent();
	}
	
	// Return the Position of p's left child (or null if no child exists)
	public Position<T> left(Position<T> p) throws IllegalArgumentException{
		Node<T> node = validate(p);
		return node.getLeft();
	}
	
	public Position<T> right(Position<T> p) throws IllegalArgumentException{
		Node<T> node = validate(p);
		return node.getRight();
	}
	
	// additional update methods supported by this class
	
	// Places element at the root of an empty tree and returns its new Position
	public Position<T> addRoot(T e) throws IllegalArgumentException{
		if(!isEmpty())
			throw new IllegalArgumentException("The tree is not empty");
		
		root = new Node<>(e, null, null, null);
		size = 1;
		return root;
	}
	
	/*
	 * Creates a new node storing element e and makes it node p's left child
	 */
	public Position<T> addLeft(Position<T> p, T e) throws IllegalArgumentException{
		Node<T> parent = validate(p);
		
		if(parent.getLeft() != null){
			throw new IllegalArgumentException("The node already has a left child");
		}
		Node<T> child = new Node<>(e, parent, null, null);
		parent.setLeft(child);
		size++;
		return child;
	}
	
	/*
	 * Creates a new node storing element e and makes it node p's right child
	 */
	public Position<T> addRight(Position<T> p, T e) throws IllegalArgumentException {
		Node<T> parent = validate(p);
		
		if(parent.getRight() != null){
			throw new IllegalArgumentException("The node already has a right child");
		}
		Node<T> child = new Node<>(e, parent, null, null);
		parent.setRight(child);
		size++;
		return child;
	}
	
	/*
	 * Replaces the element referenced by node p to element e
	 */
	public T set(Position<T> p, T e) throws IllegalArgumentException{
		Node<T> node = validate(p);
		T oldElement = node.getElement();
		node.setElement(e);
		return oldElement;
	}
	
	/*
	 * Attaches trees t1 and t2 as left and right subtrees respectively of external p
	 */
	public void attach(Position<T> p, LinkedBinaryTree<T> t1, LinkedBinaryTree<T> t2) throws IllegalArgumentException{
		Node<T> node = validate(p);
		
		if(isInternal(p)){
			throw new IllegalArgumentException("The node p must not have either a left or right child");
		}
		
		if(!t1.isEmpty()){				// attach t1 as left subtree
			t1.root.setParent(node);
			node.setLeft(t1.root);
			t1.root = null;
			t1.size = 0;
		}
		
		if(!t2.isEmpty()){				// attach t2 as right subtree
			t2.root.setParent(node);
			node.setRight(t2.root);		
			t2.root = null;
			t2.size = 0;
		}
		
		size += t1.size() + t2.size();
	}
	
	/*
	 * Removes node at position p and replaces it with its child (if any)
	 */
	
	public T remove(Position<T> p) throws IllegalArgumentException{
		Node<T> node = validate(p);	
		Node<T> parent = node.getParent();
		
		if(numChildren(p) == 2){
			throw new IllegalArgumentException("P has two children");
		}
		
		Node<T> child = (node.getLeft() != null ? node.getLeft() : node.getRight());
		
		if(child != null){
			child.setParent(parent);
		} 
		
		if(node == root){
			root = child;
			
		} else {
			if(node == parent.getLeft()){
				parent.setLeft(child);
			} else {
				parent.setRight(child);
			}
		}
		
		size--;
		T temp = node.getElement();
		node.setParent(node);
		node.setElement(null);
		node.setLeft(null);
		node.setRight(null);
		
		return temp;
	}
	
}	

