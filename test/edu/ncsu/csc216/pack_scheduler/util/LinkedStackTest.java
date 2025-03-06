/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EmptyStackException;

import org.junit.jupiter.api.Test;

/**
 * Tests the LinkedStack class
 */
class LinkedStackTest {
	/**
	 * Tests the constructor for the LinkedStack. Ensures that the constructor creates an empty
	 * LinkedStack of size 0;
	 */
	
	@Test
	void testLinkedStack() {
		LinkedStack<String> list = new LinkedStack<String>(5);
		assertEquals(0, list.size());
	}
	
	/**
	 * Tests the push method of the LinkedStack.
	 */
	@Test
	void testPushAndPop() {
		LinkedStack<String> list = new LinkedStack<String>(4);
		
		list.push("First");
		assertEquals("First", list.pop());
		assertTrue(list.isEmpty());
		
		list.push("First");
		list.push("Second");
		list.push("Third");
		list.push("Fourth");
		assertEquals("Fourth", list.pop());
		assertEquals("Third", list.pop());
		assertEquals("Second", list.pop());
		assertEquals("First", list.pop());
		assertTrue(list.isEmpty());
		
		
		list.push("First");
		list.push("Second");
		list.push("Third");
		list.push("Fourth");
		assertThrows(IllegalArgumentException.class, () -> list.push("Fifth"));
		assertThrows(IllegalArgumentException.class, () -> list.push("Fourth"));
		
	}
	
	@Test
	void testPushFromEmpty() {
		LinkedStack<String> list = new LinkedStack<String>(4);
		
		assertThrows(EmptyStackException.class, () -> list.pop());
	}


}
