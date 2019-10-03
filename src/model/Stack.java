package model;

import java.util.EmptyStackException;

public class Stack<E> implements IStack<E> {
	private Node<E> top;
	private int size;

	/**Creates an empty stack
	 * */
	public Stack() {
		size = 0;
	}

	/**Returns the element at the top of the stack but does not modify it
	 * @return The top element of the stack. If the stack is empty then its top is <b>null</b> and this is the retrieval
	 * */
	@Override
	public E top() {
		if(top != null) {
			return top.getElement();
		}
		return null;
	}

	/**Determines whether this stack is empty
	 * @return <b>true</b> if the stack is empty and <b>false</b> otherwise
	 * */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**Adds an item to the stack
	 * @param item The item to insert to the stack. It will be at the top<br>item != <b>null</b> 
	 * */
	@Override
	public void push(E item) {
		Node<E> newNode = new Node<>(item);
		newNode.setNextNode(top);
		top = newNode;
		size++;
	}

	/**Removes and returns the top element of the stack
	 * @return The top of the stack
	 * @throws EmptyStackException if the stack is empty
	 * */
	@Override
	public E pop() {
		if(isEmpty()) {
			throw new EmptyStackException();
		}
		E item = top.getElement();
		top = top.getNextNode();
		size--;
		return item;
	}

	/**Returns the size of the stack.
	 * @return The total number of elements in the stack.
	 * */
	public int getSize() {
		return size;
	}
	
	@Override
	public String toString() {
		String rt = "";
		Node<E> node = top;
		while(node != null) {
			rt += node+"\n";
			node = node.getNextNode();
		}
		return rt;
	}
}
