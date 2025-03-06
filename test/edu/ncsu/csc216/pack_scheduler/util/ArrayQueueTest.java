package edu.ncsu.csc216.pack_scheduler.util;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * ArrayQueue Tests 
 */
public class ArrayQueueTest {

	/**
	 * test the array Queue class
	 */
	@Test
	public void testArrayQueue(){
		//construct ArrayQueue
		ArrayQueue<String> list = new ArrayQueue<String>(0); 
		assertEquals(0, list.size());
		
		
ArrayQueue<String> list1 = new ArrayQueue<String>(4);
		
		list1.enqueue("First");
		assertEquals(1, list1.size());
		list1.dequeue();
		assertTrue(list1.isEmpty());
		
		list1.enqueue("First");
		list1.enqueue("Second");
		list1.enqueue("Third");
		list1.enqueue("Fourth");
		assertEquals("First", list1.dequeue());
		assertEquals("Second", list1.dequeue());
		assertEquals("Third", list1.dequeue());
		assertEquals("Fourth", list1.dequeue());
		assertTrue(list1.isEmpty());
		
		assertThrows(NoSuchElementException.class, () -> list.dequeue());
		
	}
}
