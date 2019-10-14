package dataStructures;

import java.util.ArrayList;

public class AdjacencyListVertex<E> implements Comparable<AdjacencyListVertex<E>> {
	public enum State {
		WHITE, GRAY, BLACK
	};
	
	private E element;
	
	private State color;
	
	private ArrayList<AdjacencyListEdge<E>> edges;
	
	private AdjacencyListVertex<E> predecessor;
	
	//BFS Attributes
	private int distance;
	
	//DFS Attributes
	private int discovered;
	
	private int finished;
	
	//Constructor
	public AdjacencyListVertex(E element) {
		this.element = element;
		color = State.WHITE;
		edges = new ArrayList<>();
		predecessor = null;
	}
	
	@Override
	public int compareTo(AdjacencyListVertex<E> other) {
		return this.distance-other.distance;
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
	
	public int getDistance() {
		return distance;
	}
	
	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	public AdjacencyListVertex<E> getPredecessor() {
		return predecessor;
	}
	
	public void setPredecessor(AdjacencyListVertex<E> predecessor) {
		this.predecessor = predecessor;
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

	public ArrayList<AdjacencyListEdge<E>> getEdges() {
		return edges;
	}

	public void setEdges(ArrayList<AdjacencyListEdge<E>> edges) {
		this.edges = edges;
	}
	
	@Override
	public boolean equals(Object another) {
		AdjacencyListVertex<E> a = (AdjacencyListVertex<E>)another; //throws exception if another is not an AdjacencyListVertex<E>
		return a.element.equals(element);
	}
}