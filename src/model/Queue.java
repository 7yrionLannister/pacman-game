package model;

public class Queue<E> implements IQueue<E> {
	private Node<E> front;
	private Node<E> back;
	private int size;

	/**Creates an empty queue
	 * */
	public Queue() {
		size = 0;
	}

	/**Returns the element at the front of the queue but does not modify it
	 * @return The front element. If the queue is empty then its front is <b>null</b> and this is the retrieval
	 * */
	@Override
	public E front() {
		if(front != null) {
			return front.getElement();
		}
		return null;
	}

	/**Determines whether the queue is empty
	 * @return <b>true</b> if the queue is empty and <b>false</b> otherwise
	 * */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**Adds item to the queue
	 * @param item The item to insert<br>item != <b>null</b>
	 * */
	@Override
	public void enqueue(E item) {
		if(item == null) {
			throw new IllegalArgumentException("item must not be null");
		}
		Node<E> newNode = new Node<>(item);
		if(isEmpty()) {
			front = newNode;
			back = front;
		} else {
			back.setNextNode(newNode);
			back = newNode;
		}
		size++;
	}

	/**Removes and returns the front element of the queue
	 * @throws Exception if the queue is empty
	 * */
	@Override
	public E dequeue() throws Exception {
		if(isEmpty()) {
			throw new Exception("Queue is empty");
		}
		E e = front.getElement();
		front = front.getNextNode();
		if(front == null) {
			back = front;
		}
		size--;
		return e;
	}

	/**Returns the size of the queue
	 * @return The total number of elements stored in this queue
	 * */
	public int getSize() {
		return size;
	}
	
	@Override
	public String toString() {
		Node<E> node = front;
		String rt = "";
		while(node != null) {
			rt += node+"\n";
			node = node.getNextNode();
		}
		return rt;
	}
}
