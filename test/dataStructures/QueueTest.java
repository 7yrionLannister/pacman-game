package dataStructures;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dataStructures.Queue;

public class QueueTest {
	private Queue<Integer> queue;
	
	private void setupStage1() {
		queue = new Queue<>();
		for (int i = 0; i < 80; i++) {
			queue.enqueue((int) (Math.random()*80));
		}
	}
	
	private void setupStage2() {
		queue = new Queue<>();
	}
	
	@Test
	public void createQueueTest() {
		queue = new Queue<>();
		assertTrue(queue.isEmpty() && queue.getSize() == 0 && queue.front() == null, "Queue must be initialy empty");
		try {
			queue.dequeue();
			fail("dequeue operation must fail as the queue is empty");
		} catch(Exception e) {
			assertTrue(true);
		}
	}
	
	@Test
	public void enqueueTest() {
		setupStage2();
		int size = queue.getSize();
		for (int i = 0; i < 50; i++) {
			queue.enqueue((int) (Math.random()*80));
			size++;
			assertTrue(queue.getSize() == size, "Size must be increased by one when enqueue is called");
			assertFalse(queue.isEmpty(), "Queue must not be empty as items are being added to it");
		}
	}
	
	@Test
	public void dequeueTest() throws Exception {
		setupStage1();
		int size = queue.getSize();
		while(!queue.isEmpty()) {
			queue.dequeue();
			size--;
			assertTrue(queue.getSize() == size, "Size must be decreased by one when dequeue is called");
		}
	}
}