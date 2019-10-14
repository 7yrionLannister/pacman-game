package dataStructures;

public class AdjacencyListEdge<E> {
	private AdjacencyListVertex<E> src;
	private AdjacencyListVertex<E> dst;
	private int weight;
	
	public AdjacencyListEdge(AdjacencyListVertex<E> src, AdjacencyListVertex<E> dst, int weight) {
		this.src = src;
		this.dst = dst;
		this.weight = weight;
	}
	
	public AdjacencyListVertex<E> getSrc() {
		return src;
	}
	
	public void setSrc(AdjacencyListVertex<E> src) {
		this.src = src;
	}
	
	public AdjacencyListVertex<E> getDst() {
		return dst;
	}
	
	public void setDst(AdjacencyListVertex<E> dst) {
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
