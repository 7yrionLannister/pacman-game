package dataStructures;

public class Node<E> {
	
	/**It represents the object that is contained in the node nowadays. 
	 */
	private E element;
	/**It represents the associated next node of the actual node. 
	 */
	private Node<E> nextNode;
	
	/**This creates a new Node as from an object of type E.
	 * @param element It represents the object that will be associated with the node.
	 */
	public Node(E element) {
		this.element = element;
	}
	/**This method allows to get the element E.
	 * @return An E object that represents the object that is associated with the actual node.
	 */
	public E getElement() {
		return element;
	}
	/**This method allows to set the element E.
	 * @param element It represents the object that will be associated with actual the node.
	 */
	public void setElement(E element) {
		this.element = element;
	}
	/**This method allows to get the next node of E type.
	 * @return Node<E> that represents the next node of the actual node. 
	 */
	public Node<E> getNextNode() {
		return nextNode;
	}
	/**This method allows to set the next node of E type.
	 * @param nextNode represents the next node of the actual node.
	 */
	public void setNextNode(Node<E> nextNode) {
		this.nextNode = nextNode;
	}
	
	@Override
	public String toString() {
		return element+"";
	}
}
