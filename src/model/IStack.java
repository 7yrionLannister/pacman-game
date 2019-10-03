package model;

import java.util.EmptyStackException;

public interface IStack<E> {
	
	/**Returns the element at the top of the stack but does not modify it
	 * @return The top element of the stack. If the stack is empty then its top is <b>null</b> and this is the retrieval
	 * */
	E top();
	
	/**Determines whether this stack is empty
	 * @return <b>true</b> if the stack is empty and <b>false</b> otherwise
	 * */
	boolean isEmpty();
	
	/**Adds an item to the stack
	 * @param item The item to insert to the stack. It will be at the top<br>item != <b>null</b> 
	 * */
	void push(E item);
	
	/**Removes and returns the top element of the stack
	 * @return The top of the stack
	 * @throws EmptyStackException if the stack is empty
	 * */
	E pop();
}
