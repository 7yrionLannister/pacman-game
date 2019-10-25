package dataStructures;

public class Node<E> {
	private E element;
	private Node<E> nextNode;
	/**
	 * 
	 * @param element
	 */
	public Node(E element) {
		this.element = element;
	}
	/**
	 * 
	 * @return
	 */
	public E getElement() {
		return element;
	}
	/**
	 * 
	 * @param element
	 */
	public void setElement(E element) {
		this.element = element;
	}
	/**
	 * 
	 * @return
	 */
	public Node<E> getNextNode() {
		return nextNode;
	}
	/**
	 * 
	 * @param nextNode
	 */
	public void setNextNode(Node<E> nextNode) {
		this.nextNode = nextNode;
	}
	/**
	 * 
	 */
	@Override
	public String toString() {
		return element+"";
	}
}
