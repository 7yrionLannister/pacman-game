package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EmptyStackException;

import org.junit.jupiter.api.Test;


public class StackTest {
	private Stack<Integer> stack;

	private void setupStage1() {
		stack = new Stack<>();
		for (int i = 0; i < 8; i++) {
			stack.push((int) (Math.random()*80));
		}
	}
	
	private void setupStage2() {
		stack = new Stack<>();
	}
	
	@Test
	public void createStackTest() {
		stack = new Stack<>();
		assertTrue(stack.getSize() == 0 && stack.isEmpty() && stack.top() == null, "Stack must be initialy empty");
		try {
			stack.pop();
			fail("Empty stacks must fail when this operation is called and they're empty");
		} catch(EmptyStackException ese) {
			assertTrue(true);
		}
		assertNull(stack.top(), "Top must be null as stack is empty");
	}
	
	@Test
	public void topTest() {
		setupStage1();
		for (int i = 0; i < 80; i++) {
			int newItem = (int) (Math.random()*80);
			stack.push(newItem);
			assertTrue(stack.top() == newItem, "The top of the stack is not the expected");
		}
		while(!stack.isEmpty()) {
			stack.pop();
		}
		assertNull(stack.top(), "Top must be null as stack is empty");
	}
	
	@Test
	public void popTest() {
		setupStage1();
		int size = stack.getSize();
		while(!stack.isEmpty()) {
			stack.pop();
			size--;
			assertTrue(stack.getSize() == size, "Size must be decreased by one when pop is called");
		}
	}
	
	@Test
	public void pushTest() {
		setupStage2();
		int size = stack.getSize();
		for (int i = 0; i < 50; i++) {
			stack.push((int) (Math.random()*80));
			size++;
			assertTrue(stack.getSize() == size, "Size must be increased by one when push is called");
			assertFalse(stack.isEmpty(), "Stack must not be empty as items are being added to it");
		}
	}
}

