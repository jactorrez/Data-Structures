package Graphs;

/* An interface for a graph structure. A graph can be declared as either directed or undirected.
 * In the case of an undirected graph, methods outgoingEdges and incomingEdges return the same collection,
 * and outDegree and inDegree return the same value.
 * 
 * Every vertex stores an element of type V (possibly null)
 * Every edge stores an element of type E (possibly null)
 */
public interface Graph<V,E> {

	/* Returns the number of vertices of the graph */
	int numVertices();
	
	/* Returns the number of edges of the graph */
	int numEdges();
	
	/* Returns the vertices of the graph as an iterable collection */
	Iterable<Vertex<V>> vertices();
	
	/* Returns the edges of the graph as an iterable collection */
	Iterable<Edge<E>> edges();
	
	/* Returns the number of edges leaving vertex v 
	 * For an undirected graph, returns same result returned by outDegree
	 */
	int outDegree(Vertex<V> v) throws IllegalArgumentException;
	
	/* Returns the number of edges for which vertex v is the destination.
	 * For an undirected graph, returns the same result returned by outDegree
	 */
	int inDegree(Vertex<V> v) throws IllegalArgumentException;
	
	/* Returns an iterable collection of edges for which vertex v is the origin.
	 * For an undirected graph, this returns the same result as incoming edges
	 */
	Iterable<Edge<E>> outgoingEdges(Vertex<V> v) throws IllegalArgumentException;
	
	/* Returns an iterable collection of edges for which vertex v is the destination. 
	 * For an undirected graph, this returns the same result as outgoinEdges
	 */
	Iterable<Edge<E>> incomingEdges(Vertex<V> v) throws IllegalArgumentException;
	
	/* Returns the vertices of edge e as an array of length two.
	 * If the graph is directed, the first vertex is the origin, and
	 * the second is the destination. If the graph is undirected, the order is arbitrary 
	 */
	Vertex<V>[] endVertices(Edge<E> e) throws IllegalArgumentException;
	
	/* Returns the vetex opposite to vertex v on edge e */
	Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws IllegalArgumentException;
	
	/* Inserts and returns a new vertex with the given element */
	Vertex<V> insertVertex(V element);
	
	/* Inserts and returns a new edge between vertices u and v, storing given element */
	Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E element) throws IllegalArgumentException;
	
	/* Returns the edge from u to v, or null if they are not adjacent */
	Edge<E> getEdge(Vertex<V> u, Vertex<V> v);
	
	/* Removes a vertex and all its incident edges from the graph */
	void removeVertex(Vertex<V> v) throws IllegalArgumentException;
	
	/* Removes an edge from the graph */
	void removeEdge(Edge<E> e) throws IllegalArgumentException;
}
