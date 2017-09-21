package structs.graphs;

import structs.lists.LinkedPositionalList;
import structs.lists.Position;
import structs.maps.Map;
import structs.maps.ProbeHashMap;

public class AdjacencyMapGraph<V,E> implements Graph<V,E>{
	
	// Signals whether graph is directed or undirected
	private boolean isDirected;
	private LinkedPositionalList<Vertex<V>> vertices = new LinkedPositionalList<>();
	private LinkedPositionalList<Edge<E>> edges = new LinkedPositionalList<>();
	
	/* Constructs an empty graph (either undirected or directed) */
	public AdjacencyMapGraph(boolean directed){
		isDirected = directed;
	}
	
	/* Returns the number of vertices of the graph */
	public int numVertices(){
		return vertices.size();
	}
	
	/* Returns the vertices of the graph as an iterable collection */
	public Iterable<Vertex<V>> vertices(){
		return vertices;
	}
	
	/* Returns the number of edges of the graph */
	public int numEdges(){
		return edges.size();
	}
	
	/* Returns the edges of the graph as an iterable collection */
	public Iterable<Edge<E>> edges(){
		return edges;
	}
	
	/* Returns the number of edges for which vertex v is the origin */
	public int outDegree(Vertex<V> v){
		InnerVertex vert = validate(v);
		return vert.getOutgoing().size();
	}
	
	/* Returns an iterable collection of edges for which vertex v is the origin */
	public Iterable<Edge<E>> outgoingEdges(Vertex<V> v){
		InnerVertex vert = validate(v);
		return vert.getOutgoing().values();  // edges are the values in the adjacency map
	}
	
	/* Returns the number of edges for which vertex v is the destination */
	public int inDegree(Vertex<V> v){
		InnerVertex vert = validate(v);
		return vert.getIncoming().size();
	}
	
	/* Returns an iterable collection of edges for which vertex v is the destination */
	public Iterable<Edge<E>> incomingEdges(Vertex<V> v){
		InnerVertex vert = validate(v);
		return vert.getIncoming().values(); 	 // edges are the values in the adjacency map
	}
	
	/* Returns the edge from u to v, or null if they are not adjacent */
	public Edge<E> getEdge(Vertex<V> u, Vertex<V> v){ 
		InnerVertex origin = validate(u);
		return origin.getOutgoing().get(v);
	}
	
	/* Returns the vertices of edge e as an array of length two */
	public Vertex<V>[] endVertices(Edge<E> e){
		InnerEdge edge = validate(e);
		return edge.getEndpoints();
	}
	
	/* Returns the vertex that is opposite vertex v on edge e */
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws IllegalArgumentException{
		InnerEdge edge = validate(e);
		Vertex<V>[] endpoints = edge.getEndpoints();
		
		if (endpoints[0] == v){
			return endpoints[1];
		} else if (endpoints[1] == v){
			return endpoints[0];
		} else {
			throw new IllegalArgumentException("v is not incident to this edge");
		}
	}
	
	/* Inserts and returns a new vertex with the given element */
	public Vertex<V> insertVertex(V element){
		InnerVertex v = new InnerVertex(element, isDirected);
		v.setPosition(vertices.addLast(v));
		return v;
	}
	
	/* Inserts and returns a new edge between u and v, storing given element */
	public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E element) throws IllegalArgumentException{
		if(getEdge(u,v) == null){
			InnerEdge e = new InnerEdge(u, v, element);
			e.setPosition(edges.addLast(e));
			InnerVertex origin = validate(u);
			InnerVertex dest = validate(v);
			origin.getOutgoing().put(v, e);
			dest.getIncoming().put(u, e);
			return e;
		} else{
			throw new IllegalArgumentException("Edge from u to v exists");
		}
	}
	
	/* Removes a vertex and all its incident edges from the graph */
	public void removeVertex(Vertex<V> v){
		InnerVertex vert = validate(v);
		// remove all incident edges from the graph
		for(Edge<E> e : vert.getOutgoing().values()){
			removeEdge(e);
		}
		
		for(Edge<E> e : vert.getIncoming().values()){
			removeEdge(e);
		}
		
		// remove this vertex from the list of vertices
		vertices.remove(vert.getPosition());
	}
	
    /** Removes an edge from the graph. */
    public void removeEdge(Edge<E> e) throws IllegalArgumentException {
      InnerEdge edge = validate(e);
      // remove this edge from vertices' adjacencies
      InnerVertex[] verts = (InnerVertex[]) edge.getEndpoints();
      verts[0].getOutgoing().remove(verts[1]);
      verts[1].getIncoming().remove(verts[0]);
      // remove this edge from the list of edges
      edges.remove(edge.getPosition());
      edge.setPosition(null);             // invalidates the edge
    }
	
    private InnerVertex validate(Vertex<V> v) {
      if (!(v instanceof AdjacencyMapGraph.InnerVertex)) throw new IllegalArgumentException("Invalid vertex");
      InnerVertex vert = (InnerVertex) v;     // safe cast
      if (!vert.validate(this)) throw new IllegalArgumentException("Invalid vertex");
      return vert;
    }
    
    private InnerEdge validate(Edge<E> e) {
      if (!(e instanceof AdjacencyMapGraph.InnerEdge)) throw new IllegalArgumentException("Invalid edge");
      InnerEdge edge = (InnerEdge) e;     // safe cast
      if (!edge.validate(this)) throw new IllegalArgumentException("Invalid edge");
      return edge;
    }
	// ------ Nested classes -------
    
    /* A vertex of an adjacency map graph representation */
    private class InnerVertex implements Vertex<V> {
    	private V element; 
    	private Position<Vertex<V>> pos;
    	private Map<Vertex<V>, Edge<E>> outgoing, incoming;
    	
    	/* Constructs a new InnerVertex instance storing the given element */
    	public InnerVertex(V elem, boolean graphIsDirected){
    		element = elem;
    		outgoing = new ProbeHashMap<>();
    		if(graphIsDirected){
    			incoming = new ProbeHashMap<>();
    		} else{
    			incoming = outgoing;	// if undirected, alias outgoing map 
    		}
    	}	
    	
    	/* Returns the element associated with the vertex */
    	public V getElement(){
    		return element;
    	}
    	
    	/* Stores the position of this vertex within the graph's vertex list */
    	public void setPosition(Position<Vertex<V>> p){
    		pos = p;
    	}
    	
    	/* Returns the position of this vertex within the graph's vertex list */
    	public Position<Vertex<V>> getPosition(){
    		return pos;
    	}
    	
    	/* Returns reference to the underlying map of outgoing edges */
    	public Map<Vertex<V>, Edge<E>> getOutgoing(){
    		return outgoing;
    	}
    	
    	/* Returns reference to the underlying map of incoming edges */
    	public Map<Vertex<V>, Edge<E>> getIncoming(){
    		return incoming;
    	}
    	
    	 /* Validates that this vertex instance belongs to the given graph. */
        public boolean validate(Graph<V,E> graph) {
          return (AdjacencyMapGraph.this == graph && pos != null);
        }
    }
    // ------ end of vertex class ------- 
    
    /* An edge between two vertices */
    private class InnerEdge implements Edge<E> {
    	private E element;
    	private Position<Edge<E>> pos;
    	private Vertex<V>[] endpoints;
    	
    	/* Construct InnerEdge instance connecting u to v, storing the given element */
    	@SuppressWarnings("unchecked")
		public InnerEdge(Vertex<V> u, Vertex<V> v, E elem){
    		element = elem;
    		endpoints = (Vertex<V>[]) new Vertex[]{u,v};	// array of length 2
    	}
    	
    	/* Returns the element associated with the edge */
    	public E getElement(){
    		return element;
    	}
    	
    	/* Returns reference to the endpoint array */
    	public Vertex<V>[] getEndpoints(){
    		return endpoints;
    	}
    	
    	/* Stores the position of this edge within the graph's vertex list */
    	public void setPosition(Position<Edge<E>> p){
    		pos = p;
    	}
    	
    	/* Retrieves the position of this edge within the graph's vertex list */
    	public Position<Edge<E>> getPosition(){
    		return pos;
    	}
    	
    	/* Validates that this edge instance belongs to the given graph. */
        public boolean validate(Graph<V,E> graph) {
          return AdjacencyMapGraph.this == graph && pos != null;
        }
    }
}
