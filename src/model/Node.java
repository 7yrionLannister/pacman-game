package model;

public class Node<E> {
	private E element;
	private Node<E> nextNode;
	
	public Node(E element) {
		this.element = element;
	}
	
	public E getElement() {
		return element;
	}
	
	public void setElement(E element) {
		this.element = element;
	}
	
	public Node<E> getNextNode() {
		return nextNode;
	}
	
	public void setNextNode(Node<E> nextNode) {
		this.nextNode = nextNode;
	}
	
	@Override
	public String toString() {
		return element+"";
	}
}
