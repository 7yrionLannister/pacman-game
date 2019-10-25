package dataStructures;

public class AdjacencyListEdge<E> {
	private Vertex<E> src;
	private Vertex<E> dst;
	private int weight;
	
	/**
	 * @param src
	 * @param dst
	 * @param weight
	 */
	public AdjacencyListEdge(Vertex<E> src, Vertex<E> dst, int weight) {
		this.src = src;
		this.dst = dst;
		this.weight = weight;
	}
	/**
	 * @return
	 */
	public Vertex<E> getSrc() {
		return src;
	}
	/**
	 * @param src
	 */
	public void setSrc(Vertex<E> src) {
		this.src = src;
	}
	/**
	 * @return
	 */
	public Vertex<E> getDst() {
		return dst;
	}
	/**
	 * @param dst
	 */
	public void setDst(Vertex<E> dst) {
		this.dst = dst;
	}
	/**
	 * @return
	 */
	public int getWeight() {
		return weight;
	}
	/**
	 * @param weight
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
	/**
	 */
	@Override
	public boolean equals(Object another) {
		AdjacencyListEdge<E> a = (AdjacencyListEdge<E>)another; //throws exception if another is not an AdjacencyListEdge<E> 
		return a.src.equals(src) && a.dst.equals(dst);
	}
}
