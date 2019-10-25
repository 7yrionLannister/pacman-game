package dataStructures;

public class AdjacencyListEdge<E> {
	private Vertex<E> src;
	private Vertex<E> dst;
	private int weight;
	
	/**This creates a Adjacency List Edge assigning it two vertices and a weight.
	 * @param src A Vertex<E> that represents the source where dst is associated. 
	 * @param dst A Vertex<E> that represents the vertex which src is connected with.
	 * @param weight A Integer that represents the connection weight between src and dst.
	 */
	public AdjacencyListEdge(Vertex<E> src, Vertex<E> dst, int weight) {
		this.src = src;
		this.dst = dst;
		this.weight = weight;
	}
	/**It allows to get the source vertex which dst is connected with.
	 * @return A Vertex<E> that represents the source vertex which dst is connected with.
	 */
	public Vertex<E> getSrc() {
		return src;
	}
	/**It allows to set the source vertex which dst is connected with.
	 * @param src is a Vertex<E> that represents the source vertex which dst is connected with.
	 */
	public void setSrc(Vertex<E> src) {
		this.src = src;
	}
	/**It allows to get the vertex which src is connected with.
	 * @return A Vertex<E> that represents the vertex which src is connected with.
	 */
	public Vertex<E> getDst() {
		return dst;
	}
	/**It allows to set the vertex which src is connected with.
	 * @param dst is a Vertex<E> that represents the vertex which src is connected with.
	 */
	public void setDst(Vertex<E> dst) {
		this.dst = dst;
	}
	/**It allows to get an integer that represents the connection weight between src and dst.
	 * @return An Integer that represents the connection weight between src and dst.
	 */
	public int getWeight() {
		return weight;
	}
	/**It allows to set an integer that represents the connection weight between src and dst.
	 * @param weight is an integer that represents the connection weight between src and dst.
	 */
	public void setWeight(int weight) {
		this.weight = weight;
	}
	/***This method compares if another Adjacency List Edge of E type is equals to the actual Adjacency List Edge.
	 * @throws ClassCastException if another is not an AdjacencyListEdge<E>.
	 */
	@Override
	public boolean equals(Object another) {
		AdjacencyListEdge<E> a = (AdjacencyListEdge<E>)another; 
		return a.src.equals(src) && a.dst.equals(dst);
	}
}
