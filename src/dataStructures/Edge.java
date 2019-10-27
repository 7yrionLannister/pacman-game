package dataStructures;

public class Edge<E> {
	
	/**
	 */
	private E src;
	/**
	 */
	private E dst;
	/**
	 */
	private int weight;
	
	/**This creates an Edge assigning it two vertices and a weight.
	 * @param src An E that represents the source where dst is associated. 
	 * @param dst An E that represents the vertex which src is connected with.
	 * @param weight An Integer that represents the connection weight between src and dst.
	 */
	public Edge(E src, E dst, int weight) {
		this.src = src;
		this.dst = dst;
		this.weight = weight;
	}
	
	/**It allows to get the source vertex which dst is connected with.
	 * @return A E that represents the source vertex which dst is connected with.
	 */
	public E getSrc() {
		return src;
	}
	
	/**It allows to get the vertex which src is connected with.
	 * @return A E that represents the vertex which src is connected with.
	 */
	public E getDst() {
		return dst;
	}
	
	/**It allows to get an integer that represents the connection weight between src and dst.
	 * @return An Integer that represents the connection weight between src and dst.
	 */
	public int getWeight() {
		return weight;
	}
	
	/***This method compares if another Adjacency List Edge of E type is equals to the actual Adjacency List Edge.
	 * @return A boolean 
	 * @param another
	 * @throws ClassCastException if another is not an AdjacencyListEdge<E>.
	 */
	@Override
	public boolean equals(Object another) {
		Edge<E> a = (Edge<E>)another;
		return a.src.equals(src) && a.dst.equals(dst);
	}
}
