package dataStructures;

public class Edge<E> implements Comparable<Edge<E>>{
	
	/**It represents the source vertex from the dst vertex is going to be linked. 
	 */
	private E src;
	/**It represents the vertex which src is going to be linked with.
	 */
	private E dst;
	/**It represents the cost of arriving from src to dst.
	 */
	private int weight;
	
	/**This creates a Adjacency List Edge assigning it two vertices and a weight.
	 * @param src A E that represents the source where dst is associated. 
	 * @param dst A E that represents the vertex which src is connected with.
	 * @param weight A Integer that represents the connection weight between src and dst.
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
	/**It allows to set the source vertex which dst is connected with.
	 * @param src is a E that represents the source vertex which dst is connected with.
	 */
	public void setSrc(E src) {
		this.src = src;
	}
	/**It allows to get the vertex which src is connected with.
	 * @return A E that represents the vertex which src is connected with.
	 */
	public E getDst() {
		return dst;
	}
	/**It allows to set the vertex which src is connected with.
	 * @param dst is a E that represents the vertex which src is connected with.
	 */
	public void setDst(E dst) {
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
	 * @return A boolean that indicates if another Adjacency List Edge of E type is equals to the actual Adjacency List Edge.
	 * @param another An AdjacencyListEdge<E> that is going to be compared with the actual one.
	 * @throws ClassCastException if another is not an AdjacencyListEdge<E>.
	 */
	@Override
	public boolean equals(Object another) {
		Edge<E> a = (Edge<E>)another; 
		return a.src.equals(src) && a.dst.equals(dst);
	}
	@Override
	public int compareTo(Edge<E> edge) {
		return weight - edge.weight;
	}
}
