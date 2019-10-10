package dataStructures;

public class Vertex<T> implements Comparable<Vertex<T>> {
	
	public static final String GREY = "Grey";
	/**
	 * 
	 */
	public static final String WHITE = "White";
	/**
	 * 
	 */
	public static final String BLACK = "Black";
	// -----------------------------------------------------------------
    // Attributes
    // -----------------------------------------------------------------
	private String color;
	/**
	 * 
	 */
	private int distance;
	/**
	 * 
	 */
	private int discovered;
	/**
	 * 
	 */
	private int ended;
	/**
	 * 
	 */
	private Vertex<T> predecessor;
	/**
	 * 
	 */
	private T data;
	/**
	 * 
	 */
	private ArrayList<Triple<T>> triples;
	/**
	 * 
	 */
	
	
	// -----------------------------------------------------------------
    // Builder
    // -----------------------------------------------------------------
	/**
	 * 
	 * @param data
	 */
	public Vertex(T data){
		this.data = data;
		triples = new ArrayList<Triple<T>>();
		distance = Integer.MAX_VALUE;
	}
	
	public void addTriple(int weight, Vertex<T> vertex){
		Triple<T> triple = new Triple<T>(weight, vertex);
//		if(!(hashTriples.containsKey(nombre))){
			triples.add(triple);
				
//		}
	}
	
	@Override
	public int compareTo(Vertex<T> other) {
		
		return this.distance-other.distance;
	}
	
	public T getdata(){
		return data;
	}
	
	public void setdata(T data){
		this.data = data;
	}
	
	public int getDiscovered() {
		return discovered;
	}
	
	public void setDiscovered(int discovered) {
		this.discovered = discovered;
	}
	
	public int getEnded() {
		return ended;
	}
	
	public void setEnded(int ended) {
		this.ended = ended;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public int getDistance() {
		return distance;
	}
	
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public Vertex<T> getPredecessor() {
		return predecessor;
	}
	
	public void setPredecessor(Vertex<T> predecessor) {
		this.predecessor = predecessor;
	}
	
	public ArrayList<Triple<T>> getTriples() {
		return triples;
	}
	
	public void setTriples(ArrayList<Triple<T>> triples) {
		this.triples = triples;
	}
	
	
}