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

	public Vertex(E element) {
		this.element = element;
		color = State.WHITE;
		setPredecessor(null);
	}

	public int getDistance() {
		return distance;
	}
	
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public E getElement(){
		return element;
	}

	public void setElement(E element){
		this.element = element;
	}

	public State getColor() {
		return color;
	}

	public void setColor(State color) {
		this.color = color;
	}

	public int getFinished() {
		return finished;
	}

	public void setFinished(int finished) {
		this.finished = finished;
	}

	public int getDiscovered() {
		return discovered;
	}

	public void setDiscovered(int discovered) {
		this.discovered = discovered;
	}
	
	@Override
	public boolean equals(Object another) {
		Vertex<E> a = (Vertex<E>)another; //throws exception if another is not a Vertex<E>
		return a.element.equals(element);
	}

	@Override
	public int compareTo(Vertex<E> other) {
		return this.distance-other.distance;
	}

	public Vertex<E> getPredecessor() {
		return predecessor;
	}

	public void setPredecessor(Vertex<E> predecessor) {
		this.predecessor = predecessor;
	}
}
