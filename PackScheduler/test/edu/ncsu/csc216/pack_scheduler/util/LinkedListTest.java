package edu.ncsu.csc216.pack_scheduler.util;



import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

class LinkedListTest {

	@Test
	void testLinkedList() {
		LinkedList<String> list = new LinkedList<String>();
		assertEquals(0, list.size());
	}
	
	/**
	 * Tests the add method of the ArrayList.
	 */
	@Test
	void testAdd() {
		LinkedList<String> list = new LinkedList<String>();
		
		// test adding to an empty list
		list.add(0, "First");
		assertEquals("First", list.get(0));
		assertEquals(1, list.size());
		
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
		LinkedList<String> list = new LinkedList<String>();
		
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
		LinkedList<String> list = new LinkedList<String>();
		
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
	void testIteratorMethods() {
	    LinkedList<String> list = new LinkedList<>();

	    // Add elements to the list
	    list.add(0, "First");
	    list.add(1, "Second");
	    list.add(2, "Third");

	    ListIterator<String> iterator = list.listIterator();

	    // Test hasNext() and next()
	    assertTrue(iterator.hasNext());
	    assertEquals("First", iterator.next());
	    assertTrue(iterator.hasNext());
	    assertEquals("Second", iterator.next());
	    assertTrue(iterator.hasNext());
	    assertEquals("Third", iterator.next());
	    assertThrows(NoSuchElementException.class, () -> iterator.next());

	    // Test hasPrevious() and previous()
	    assertTrue(iterator.hasPrevious());
	    assertEquals("Third", iterator.previous());
	    assertTrue(iterator.hasPrevious());
	    assertEquals("Second", iterator.previous());
	    assertTrue(iterator.hasPrevious());
	    assertEquals("First", iterator.previous());
	    assertThrows(NoSuchElementException.class, () -> iterator.previous());

	    // Test adding and removing elements via iterator
	    iterator.add("NewFirst");
	    assertEquals("NewFirst", list.get(0));

	    iterator.next();
	    iterator.remove();
	    assertEquals("Second", list.get(1));
	}
	
	@Test
	void testAddAllCases() {
	    LinkedList<String> list = new LinkedList<>();

	    // Add elements at different positions
	    list.add(0, "First");
	    list.add(1, "Second");
	    list.add(2, "Third");

	    // Test adding duplicates (should throw exception)
	    assertThrows(IllegalArgumentException.class, () -> list.add(3, "First"));
	    assertThrows(IllegalArgumentException.class, () -> list.add(0, "Second"));

	    // Test adding to the first position
	    list.add(0, "NewFirst");
	    assertEquals("NewFirst", list.get(0));
	    assertEquals("First", list.get(1));

	    // Test adding to the last position
	    list.add(list.size(), "Last");
	    assertEquals("Last", list.get(list.size() - 1));

	    // Validate list order
	    assertEquals("NewFirst", list.get(0));
	    assertEquals("First", list.get(1));
	    assertEquals("Second", list.get(2));
	    assertEquals("Third", list.get(3));
	    assertEquals("Last", list.get(4));
	}

	
}
