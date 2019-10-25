package dataStructures;

public class Vertex<E> implements Comparable<Vertex<E>> {
	//TODO poner las cosas que los vertices de list y matrix tengan en comun aqui
	private E element;

	private State color;
	
	private Vertex<E> predecessor;

	//BFS and Dijkstra Attributes
	private int distance;

	//DFS Attributes
	private int discovered;

	private int finished;
	
	/**
	 * 
	 * @param element
	 */
	public Vertex(E element) {
		this.element = element;
		color = State.WHITE;
		setPredecessor(null);
	}
	/**
	 * 
	 * @return
	 */
	public int getDistance() {
		return distance;
	}
	/**
	 * 
	 * @param distance
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}
	/**
	 * 
	 * @return
	 */
	public E getElement(){
		return element;
	}
	/**
	 * 
	 * @param element
	 */
	public void setElement(E element){
		this.element = element;
	}
	/**
	 * 
	 * @return
	 */
	public State getColor() {
		return color;
	}
	/**
	 * 
	 * @param color
	 */
	public void setColor(State color) {
		this.color = color;
	}
	/**
	 * 
	 * @return
	 */
	public int getFinished() {
		return finished;
	}
	/**
	 * 
	 * @param finished
	 */
	public void setFinished(int finished) {
		this.finished = finished;
	}
	/**
	 * 
	 * @return
	 */
	public int getDiscovered() {
		return discovered;
	}
	/**
	 * 
	 * @param discovered
	 */
	public void setDiscovered(int discovered) {
		this.discovered = discovered;
	}
	/**
	 * 
	 */
	@Override
	public boolean equals(Object another) {
		Vertex<E> a = (Vertex<E>)another; //throws exception if another is not a Vertex<E>
		return a.element.equals(element);
	}
	/**
	 * 
	 */
	@Override
	public int compareTo(Vertex<E> other) {
		return this.distance-other.distance;
	}
	/**
	 * 
	 * @return
	 */
	public Vertex<E> getPredecessor() {
		return predecessor;
	}
	/**
	 * 
	 * @param predecessor
	 */
	public void setPredecessor(Vertex<E> predecessor) {
		this.predecessor = predecessor;
	}
}
