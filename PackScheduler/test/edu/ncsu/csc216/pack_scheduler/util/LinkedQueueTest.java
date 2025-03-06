/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Tests the LinkedQueue class
 */
public class LinkedQueueTest {

	/**
	 * test the LinkedQueue class
	 */
	@Test
	public void testLinkedQueue(){
		//construct LinkedQueue
		LinkedQueue<String> list = new LinkedQueue<String>(4); 
		assertEquals(0, list.size());
	}
	/**
	 * Tests the functionality of enqueue and dequeue within LinkedQueue
	 */
	@Test
	public void testEnqueueAndDequeue() {
		LinkedQueue<String> list = new LinkedQueue<String>(10); 
		list.enqueue("First");
		assertEquals(1, list.size());
		list.enqueue("Second");
		list.enqueue("Third");
		list.enqueue("Fourth");
		assertEquals(4, list.size());
		// The first element to get dequeued should be First //
		assertEquals("First", list.dequeue());
		// The second element should be Second //
		assertEquals("Second", list.dequeue());
		assertEquals(2, list.size());
		assertEquals("Third", list.dequeue());
		assertEquals("Fourth", list.dequeue());
		assertThrows(NoSuchElementException.class, () -> list.dequeue());
		LinkedQueue<String> newList = new LinkedQueue<String>(1); 
		newList.enqueue("First");
		assertThrows(IllegalArgumentException.class, () -> newList.enqueue("Second"));
						
	}

}
