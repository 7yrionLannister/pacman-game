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
	
	/**This creates a new vertex as from an object of type E with its state in white and its predecessor in null.
	 * @param element It represents the object that will be associated with the vertex.
	 */
	public Vertex(E element) {
		this.element = element;
		color = State.WHITE;
		setPredecessor(null);
	}
	/**It allows to get the actual vertex distance.
	 * @return An Integer that represents the actual vertex distance.
	 */
	public int getDistance() {
		return distance;
	}
	/**It allows to set the actual vertex distance.
	 * @param distance is an integer represents the actual vertex distance.
	 */
	public void setDistance(int distance) {
		this.distance = distance;
	}
	/**It allows to get the related E object of the actual vertex.
	 * @return An E object that represents the related E object of the actual vertex.
	 */
	public E getElement(){
		return element;
	}
	/**It allows to set the related E object of the actual vertex.
	 * @param element is an E object that represents the related E object of the actual vertex.
	 */
	public void setElement(E element){
		this.element = element;
	}
	/**It allows to get the state of the actual vertex defined by a color.
	 * @return A State object that represents the state of the actual vertex.
	 */
	public State getColor() {
		return color;
	}
	/**It allows to set the state of the actual vertex defining a color.
	 * @param color is a State object that represents the state of the actual vertex.
	 */
	public void setColor(State color) {
		this.color = color;
	}
	/**It allows to get an integer that represents if the actual vertex stage is finished when BFS is applied.
	 * @return An Integer that represents if the actual vertex stage is finished when BFS is applied.
	 */
	public int getFinished() {
		return finished;
	}
	/**It allows to set an integer that represents if the actual vertex stage is finished when BFS is applied.
	 * @param finished is an integer that represents if the actual vertex stage is finished when BFS is applied.
	 */
	public void setFinished(int finished) {
		this.finished = finished;
	}
	/**It allows to get an integer that represents if the actual vertex stage has been discovered when BFS is being applied.
	 * @return An integer that represents if the actual vertex stage has been discovered when BFS is being applied.
	 */
	public int getDiscovered() {
		return discovered;
	}
	/**It allows to set an integer that represents if the actual vertex stage has been discovered when BFS is being applied.
	 * @param discovered is an integer that represents if the actual vertex stage has been discovered when BFS is being applied.
	 */
	public void setDiscovered(int discovered) {
		this.discovered = discovered;
	}
	/**This method compares if another vertex of E type is equals to the actual vertex.
	 * @throws ClassCastException if another is not a Vertex<E>.
	 */
	@Override
	public boolean equals(Object another) {
		Vertex<E> a = (Vertex<E>)another; 
		return a.element.equals(element);
	}
	/**This method compares the actual vertex distance with another one that arrives as parameter.
	 */
	@Override
	public int compareTo(Vertex<E> other) {
		return this.distance-other.distance;
	}
	/***It allows to get the actual vertex predecessor. 
	 * @return A Vertex<E> that represents the actual vertex predecessor. 
	 */
	public Vertex<E> getPredecessor() {
		return predecessor;
	}
	/**It allows to set the actual vertex predecessor. 
	 * @param predecessor is a Vertex<E> that represents the actual vertex predecessor.
	 */
	public void setPredecessor(Vertex<E> predecessor) {
		this.predecessor = predecessor;
	}
}
