package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LinkedAbstractListTest {

	/**
	 * Test the LinkedAbstractList constructor.
	 */
	@Test
	void testLinkedAbstractList() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(5);
		assertEquals(0, list.size());
		assertEquals(5, list.getCapacity());
	}
	
	/**
	 * Test the addToList method.
	 */
	@Test
	void testAddToList() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(5);
		assertEquals(0, list.size());
		// Test adding an element to the beginning of a list //
		list.add(0, "first");
		assertEquals(1, list.size());
		assertEquals("first", list.get(0));
		// Test adding an element to the end of the list //
		list.add(0, "second");
		assertEquals(2, list.size());
		assertEquals("second", list.get(0));
		assertEquals("first", list.get(1));
		// Test adding an element to the middle of a list //
		list.add(1, "third");
		assertEquals(3, list.size());
		assertEquals("second", list.get(0));
		assertEquals("third", list.get(1));
		assertEquals("first", list.get(2));
		// Test adding an index out of bounds
		assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, "fourth"));
		assertThrows(IndexOutOfBoundsException.class, () -> list.add(4, "fourth"));
		// Test adding a duplicate element //
		assertThrows(IllegalArgumentException.class, () -> list.add(0, "second"));
		// Test adding a null element //
		assertThrows(NullPointerException.class, () -> list.add(0, null));
		list.add(0, "fourth");
		list.add(0, "fifth");
		// Test trying to add an element to the list if the capacity is met //
		assertThrows(IllegalArgumentException.class, () -> list.add(0, "sixth"));
	}
	
	/**
	 * Test the removeFromList method.
	 */
	@Test
	void testRemoveFromList() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(5);
		list.add(0, "first");
		list.add(1, "second");
		list.add(2, "third");
		list.add(3, "fourth");
		assertEquals(4, list.size());
		// Tests removing an element from the start of the list //
		list.remove(0);
		assertEquals(3, list.size());
		assertEquals("second", list.get(0));
		assertEquals("third", list.get(1));
		assertEquals("fourth", list.get(2));
		// Tests removing an element from the middle of the list //
		list.remove(1);
		assertEquals(2, list.size());
		assertEquals("second", list.get(0));
		assertEquals("fourth", list.get(1));
		// Tests removing an element from the end of the list //
		list.remove(1);
		assertEquals(1, list.size());
		assertEquals("second", list.get(0));
		// Tests removing an element from an invalid index //
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(2));
	}
	
	/**
	 * Test setElementInList method.
	 */
	@Test
	void testSetElementInList() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(5);
		list.add(0, "first");
		list.add(1, "second");
		list.add(2, "third");
		list.add(3, "fourth");
		assertEquals(4, list.size());
		// Test setting an element at the beginning of the list //
		assertEquals("first", list.set(0, "newFirst"));
		assertEquals("newFirst", list.get(0));
		// Test setting an element at the end of the list //
		assertEquals("fourth", list.set(3, "newFourth"));
		assertEquals("newFourth", list.get(3));
		// Test setting an element in the middle of the list //
		assertEquals("third", list.set(2, "newThird"));
		assertEquals("newThird", list.get(2));
		// Test adding an index out of bounds
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, "error"));
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(4, "error"));
		// Test adding a duplicate element //
		assertThrows(IllegalArgumentException.class, () -> list.set(0, "newFourth"));
		// Test adding a null element //
		assertThrows(NullPointerException.class, () -> list.set(0, null));
	}
	
	/**
	 * Test invalid get method outcomes.
	 */
	@Test
	void testInvalidGet() {
		LinkedAbstractList<String> list = new LinkedAbstractList<String>(5);
		list.add(0, "first");
		list.add(1, "second");
		list.add(2, "third");
		list.add(3, "fourth");
		// Test adding an index out of bounds
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, "error"));
		assertThrows(IndexOutOfBoundsException.class, () -> list.set(4, "error"));
	}
}
