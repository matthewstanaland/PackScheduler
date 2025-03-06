package edu.ncsu.csc216.pack_scheduler.util;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests the custom ArrayList class.
 * 
 * @author Kergan Sanderson
 * @author Artie Yakovlev
 * @author Cameron Edwards
 */
class ArrayListTest {

	/**
	 * Tests the constructor for the ArrayList. Ensures that the constructor creates an empty
	 * ArrayList of size 0;
	 */
	@Test
	void testArrayList() {
		ArrayList<String> list = new ArrayList<String>();
		assertEquals(0, list.size());
	}
	
	/**
	 * Tests the add method of the ArrayList.
	 */
	@Test
	void testAdd() {
		ArrayList<String> list = new ArrayList<String>();
		
		// test adding to an empty list
		list.add(0, "First");
		assertEquals("First", list.get(0));
		
		// test adding to the front of the list
		list.add(0, "Second");
		assertEquals("Second", list.get(0));
		assertEquals("First", list.get(1));
		
		// test adding to the end of the list
		list.add(2, "Third");
		assertEquals("Second", list.get(0));
		assertEquals("First", list.get(1));
		assertEquals("Third", list.get(2));
		
		// test adding to the middle of the list
		list.add(2, "Fourth");
		assertEquals("Second", list.get(0));
		assertEquals("First", list.get(1));
		assertEquals("Fourth", list.get(2));
		assertEquals("Third", list.get(3));
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, "Low"));
		assertThrows(IndexOutOfBoundsException.class, () -> list.add(5, "High"));
		assertThrows(NullPointerException.class, () -> list.add(2, null));
		assertThrows(IllegalArgumentException.class, () -> list.add(3, "First"));
	}
	
	@Test
	void testRemove() {
		ArrayList<String> list = new ArrayList<String>();
		
		list.add(0, "First");
		list.add(1, "Second");
		list.add(2, "Third");
		list.add(3, "Fourth");
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(4));
		
		list.remove(2);
		assertEquals(3, list.size());
		assertEquals("First", list.get(0));
		assertEquals("Second", list.get(1));
		assertEquals("Fourth", list.get(2));
		
		list.remove(0);
		assertEquals(2, list.size());
		assertEquals("Second", list.get(0));
		assertEquals("Fourth", list.get(1));
		
		list.remove(list.size() - 1);
		assertEquals(1, list.size());
		assertEquals("Second", list.get(0));
		
		list.remove(list.size() - 1);
		assertEquals(0, list.size());
	}
	
	@Test
	void testSet() {
		ArrayList<String> list = new ArrayList<String>();
		
		list.add(0, "First");
		list.add(1, "Second");
		list.add(2, "Third");
		list.add(3, "Fourth");
		
		list.set(1, "s");
		assertEquals("s", list.get(1));
		
		list.set(2, "tri");
		assertEquals("tri", list.get(2));
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, "Low"));
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(4, "High"));
		assertThrows(NullPointerException.class, () -> list.set(2, null));
		assertThrows(IllegalArgumentException.class, () -> list.set(3, "First"));
	}
	
	@Test
	void testGet() {
		ArrayList<String> list = new ArrayList<String>();
		
		list.add(0, "First");
		list.add(1, "Second");
		list.add(2, "Third");
		list.add(3, "Fourth");
		
		assertEquals("First", list.get(0));
		assertEquals("Second", list.get(1));
		assertEquals("Third", list.get(2));
		assertEquals("Fourth", list.get(3));
		
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(5));
	}

}
