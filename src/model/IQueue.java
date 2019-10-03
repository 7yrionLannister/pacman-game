package model;

public interface IQueue<E> {
	
	/**Returns the element at the front of the queue but does not modify it
	 * @return The front element. If the queue is empty then its front is <b>null</b> and this is the retrieval
	 * */
	E front();
	
	/**Determines whether the queue is empty
	 * @return <b>true</b> if the queue is empty and <b>false</b> otherwise
	 * */
	boolean isEmpty();
	
	/**Adds item to the queue
	 * @param item The item to insert<br>item != <b>null</b>
	 * */
	void enqueue(E item);
	
	/**Removes and returns the front element of the queue
	 * @throws Exception if the queue is empty
	 * */
	E dequeue() throws Exception;
}
