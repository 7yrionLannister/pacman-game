package dataStructures;

public class AdjacencyListEdge<E> {
	private Vertex<E> src;
	private Vertex<E> dst;
	private int weight;
	
	public AdjacencyListEdge(Vertex<E> src, Vertex<E> dst, int weight) {
		this.src = src;
		this.dst = dst;
		this.weight = weight;
	}
	
	public Vertex<E> getSrc() {
		return src;
	}
	
	public void setSrc(Vertex<E> src) {
		this.src = src;
	}
	
	public Vertex<E> getDst() {
		return dst;
	}
	
	public void setDst(Vertex<E> dst) {
		this.dst = dst;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	@Override
	public boolean equals(Object another) {
		AdjacencyListEdge<E> a = (AdjacencyListEdge<E>)another; //throws exception if another is not an AdjacencyListEdge<E> 
		return a.src.equals(src) && a.dst.equals(dst);
	}
}
