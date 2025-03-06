package edu.ncsu.csc217.collections.list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

/**
 * Tests for the SortedList Library
 * @author Ruairi Gallagher
 * @author Dreese Abdelilah
 * @author Kergan Sanderson
 */
public class SortedListTest {
	
	/** String array containing a list of 11 Names */
	private static final String[] NAMES = {"Ruairi", "Dreese", "Kergan", "John", "James", "George", "Adam", "Jack", "Abe", "Richard", "Bill"};
	/** String array containing a list of 11 names in alphabetical order */
	private static final String[] SORTED_NAMES = {"Abe", "Adam", "Bill", "Dreese", "George", "Jack", "James", "John", "Kergan", "Richard", "Ruairi"};
	
	/**
	 *  Tests the construction of a new, empty, SortedList
	 */
	@Test
	public void testSortedList() {
		SortedList<String> list = new SortedList<String>();
		assertEquals(0, list.size());
		assertFalse(list.contains("apple"));
		
		for(int i = 0; i < NAMES.length; i++) {
			list.add(NAMES[i]);		
		}
		assertEquals(11, list.size());
		for(int i = 0; i < 11; i++) {
			assertEquals(SORTED_NAMES[i], list.get(i));
		}
		
	}
	
	/**
	 * Tests adding an element as the first element, the last element, the middle element, 
	 * the front element of a SortedList. Tests adding a null element to a SortedList, and a 
	 * duplicate element to SortedList.
	 */
	@Test
	public void testAdd() {
		SortedList<String> list = new SortedList<String>();
		
		list.add("banana");
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));
		
		//TODO Test adding to the front, middle and back of the list
		list.add("apple");
		list.add("apricot");
		list.add("dragonfruit");
		assertEquals(4, list.size());
		assertEquals("apple", list.get(0));
		assertEquals("apricot", list.get(1));
		assertEquals("banana", list.get(2));
		assertEquals("dragonfruit", list.get(3));
		assertThrows(NullPointerException.class, () -> list.add(null));
		assertThrows(IllegalArgumentException.class, () -> list.add("apple"));
	}
	
	/**
	 * Tests the SortedList get() method error cases.
	 */
	@Test
	public void testGet() {
		SortedList<String> list = new SortedList<String>();
		
		//Since get() is used throughout the tests to check the
		//contents of the list, we don't need to test main flow functionality
		//here.  Instead this test method should focus on the error 
		//and boundary cases.
		
		// Test getting an element from an empty list
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
		
		// Add some elements to the list
	    list.add("dragonfruit");
		list.add("apple");
		list.add("banana");
		
		// Test getting an element at an index < 0
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
		
		// Test getting an element at size
		assertThrows(IndexOutOfBoundsException.class, () -> list.get(list.size()));
		
	}
	
	/**
	 * Tests the SortedList remove() method. Tests for removing errors of removing from an empty list and removing out of
	 * bound indices. Tests removing first, middle, last, and final elements from the list.
	 */
	@Test
	public void testRemove() {
		SortedList<String> list = new SortedList<String>();
		
		// Test removing from an empty list
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));
		
		// Add some elements to the list - at least 4
        list.add("apple");
        list.add("durian");
        list.add("banana");
        list.add("theremin");
		
		// Test removing an element at an index < 0
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
		
		// Test removing an element at size
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(list.size()));
		
		// Test removing a middle element
        list.remove(1);
        assertEquals(3, list.size());
        assertEquals("apple", list.get(0));
        assertEquals("durian", list.get(1));
        assertEquals("theremin", list.get(2));
		
		// Test removing the last element
        list.remove(list.size() - 1);
        assertEquals(2, list.size());
        assertEquals("apple", list.get(0));
        assertEquals("durian", list.get(1));
		
		// Test removing the first element
        list.remove(0);
        assertEquals(1, list.size());
        assertEquals("durian", list.get(0));
		
		// Test removing the last element
        list.remove(list.size() - 1);
        assertEquals(0, list.size());
	}
	
	/**
	 * Tests the SortedList indexOf() method. Tests getting from an empty list. Also tests adding items to the list and 
	 * retrieving their indices. Tests retrieving index of null object.
	 */
	@Test
	public void testIndexOf() {
		SortedList<String> list = new SortedList<String>();
		
		// Test indexOf on an empty list
		assertEquals(-1, list.indexOf("banana"));
		
		// Add some elements
		list.add("theremin");
		list.add("xylophone");
		list.add("trombone");
		list.add("spoons");
		list.add("trumpet");
		
		// Test various calls to indexOf for elements in the list
		//and not in the list
	    assertEquals(0, list.indexOf("spoons"));
		assertEquals(1, list.indexOf("theremin"));
	    assertEquals(2, list.indexOf("trombone"));
	    assertEquals(3, list.indexOf("trumpet"));
	    assertEquals(4, list.indexOf("xylophone"));
	    
	    assertEquals(-1, list.indexOf("apple"));
		
		// Test checking the index of null
	    assertThrows(NullPointerException.class, () -> list.indexOf(null));
		
	}
	
	/**
	 * Tests the SortedList indexOf() method. Adds items to a list, clears the list, and checks that the list is empty.
	 */
	@Test
	public void testClear() {
		SortedList<String> list = new SortedList<String>();

		// Add some elements
		list.add("theremin");
        list.add("xylophone");
        list.add("trombone");
        list.add("spoons");
        list.add("trumpet");
		
		// Clear the list
        list.clear();
		
		// Test that the list is empty
        assertEquals(0, list.size());
	}

	/**
	 * Tests the SortedList isEmpty method by ensuring that the list
	 * is empty when created, and no longer is after elements are added.
	 */
	@Test
	public void testIsEmpty() {
		SortedList<String> list = new SortedList<String>();
		assertEquals(0, list.size());
		assertTrue(list.isEmpty());
		list.add("apple");
		assertEquals(1, list.size());
		assertFalse(list.isEmpty());
		assertEquals("apple", list.get(0));
	}
	
	/**
	 * Tests the SortedList contains method by first verifying that a
	 * newly created list is empty, then adding elements to the list. Then
	 * the contains method is used to check whether the elements are in the list.
	 */
	@Test
	public void testContains() {
		SortedList<String> list = new SortedList<String>();
		
		//TODO Test the empty list case
		assertTrue(list.isEmpty());
		
		//TODO Add some elements
		list.add("apple");
		list.add("banana");
		list.add("orange");
		list.add("pear");
		list.add("cranberry");
		
		assertFalse(list.isEmpty());
		assertTrue(list.contains("apple"));
		assertTrue(list.contains("banana"));
		assertFalse(list.contains("grapefruit"));
		assertFalse(list.contains("pineapple"));
	}
	
	/**
	 * Tests Equals, compares the specified object with the list for equality
	 */
	@Test
	public void testEquals() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();
		
		//Make two lists the same and one list different
		list1.add("apple");
		list2.add("apple");
		list3.add("pear");
		//Test for equality and non-equality
		assertEquals(list1, list2);
		assertNotEquals(list1, list3);
	}
	
	/**
	 * Tests hashCode method that returns the hashCode for this list
	 */
	@Test
	public void testHashCode() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();
		
		//Make two lists the same and one list different
		list1.add("grape");
		list2.add("grape");
		list3.add("pear");
		//Test for the same and different hashCodes
		assertEquals(list1, list2);
		assertNotEquals(list1, list3);
	}

}
 