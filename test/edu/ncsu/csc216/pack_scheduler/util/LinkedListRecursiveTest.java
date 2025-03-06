package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for LinkedListRecursive.
 * 
 * @author Matt Stanaland
 */
public class LinkedListRecursiveTest {

    /**
     * Test for size() and isEmpty().
     */
    @Test
    public void testSizeAndIsEmpty() {
        LinkedListRecursive<String> list = new LinkedListRecursive<>();
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());

        list.add("First");
        assertEquals(1, list.size());
        assertFalse(list.isEmpty());
    }

    /**
     * Test for add(E).
     */
    @Test
    public void testAddElement() {
        LinkedListRecursive<String> list = new LinkedListRecursive<>();
        assertTrue(list.add("First"));
        assertTrue(list.add("Second"));
        assertEquals(2, list.size());

        // Adding a duplicate
        assertThrows(IllegalArgumentException.class, () -> list.add("First"));

        // Adding a null element
        assertThrows(NullPointerException.class, () -> list.add(null));
    }

    /**
     * Test for add(int, E).
     */
    @Test
    public void testAddAtIndex() {
        LinkedListRecursive<String> list = new LinkedListRecursive<>();

        // Add to the front
        list.add(0, "A");
        assertEquals("A", list.get(0));

        // Add to the end
        list.add(1, "B");
        assertEquals("B", list.get(1));

        // Add to the middle
        list.add(1, "New");
        assertEquals("A", list.get(0));
        assertEquals("New", list.get(1));
        assertEquals("B", list.get(2));

        // Add to an invalid index
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, "Invalid"));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(4, "Invalid"));

        // Add null element
        assertThrows(NullPointerException.class, () -> list.add(1, null));

        // Add duplicate element
        assertThrows(IllegalArgumentException.class, () -> list.add(1, "New"));
    }


    /**
     * Test for get(int).
     */
    @Test
    public void testGet() {
        LinkedListRecursive<String> list = new LinkedListRecursive<>();

        // Test getting from an empty list
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));

        // Add elements to the list
        list.add("First");
        list.add("Second");
        list.add("Third");

        // Test valid indices
        assertEquals("First", list.get(0)); // Start of the list
        assertEquals("Second", list.get(1)); // Middle of the list
        assertEquals("Third", list.get(2)); // End of the list

        // Test invalid indices
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1)); // Negative index
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(3)); // Out of bounds

        // Add more elements and test further
        list.add("Fourth");
        list.add("Fifth");

        assertEquals("Fourth", list.get(3)); // Test newly added element
        assertEquals("Fifth", list.get(4)); // Test end of list again
    }




    /**
     * Test for contains(E).
     */
    @Test
    public void testContains() {
        LinkedListRecursive<String> list = new LinkedListRecursive<>();
        assertFalse(list.contains("First"));

        list.add("First");
        list.add("Second");

        assertTrue(list.contains("First"));
        assertTrue(list.contains("Second"));
        assertFalse(list.contains("Third"));

        // Null element
        assertFalse(list.contains(null));
    }

    /**
     * Test for remove(int).
     */
    @Test
    public void testRemoveByIndex() {
        LinkedListRecursive<String> list = new LinkedListRecursive<>();
        list.add("First");
        list.add("Second");
        list.add("Third");

        // Remove middle element
        assertEquals("Second", list.remove(1));
        assertEquals(2, list.size());
        assertEquals("First", list.get(0));
        assertEquals("Third", list.get(1));

        // Remove first element
        assertEquals("First", list.remove(0));
        assertEquals(1, list.size());
        assertEquals("Third", list.get(0));

        // Remove last element
        assertEquals("Third", list.remove(0));
        assertEquals(0, list.size());

        // Invalid indices
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0)); // Empty list
    }


    /**
     * Test for remove(E).
     */
    @Test
    public void testRemoveByElement() {
        LinkedListRecursive<String> list = new LinkedListRecursive<>();
        list.add("First");
        list.add("Second");
        list.add("Third");

        // Remove middle element
        assertTrue(list.remove("Second"));
        assertEquals(2, list.size());
        assertFalse(list.contains("Second"));

        // Remove first element
        assertTrue(list.remove("First"));
        assertEquals(1, list.size());
        assertFalse(list.contains("First"));

        // Remove last element
        assertTrue(list.remove("Third"));
        assertEquals(0, list.size());
        assertFalse(list.contains("Third"));

        // Attempt to remove non-existent element
        assertFalse(list.remove("NonExistent"));
        assertEquals(0, list.size());

        // Attempt to remove null element
        assertFalse(list.remove(null));
        assertEquals(0, list.size());
    }


    /**
     * Test for set(int, E).
     */
    @Test
    public void testSet() {
        LinkedListRecursive<String> list = new LinkedListRecursive<>();
        list.add("First");
        list.add("Second");
        list.add("Third");

        // Valid set
        assertEquals("Second", list.set(1, "NewSecond"));
        assertEquals("First", list.get(0));
        assertEquals("NewSecond", list.get(1));
        assertEquals("Third", list.get(2));

        // Invalid indices
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, "Invalid"));
        assertThrows(IndexOutOfBoundsException.class, () -> list.set(3, "Invalid"));

        // Null element
        assertThrows(NullPointerException.class, () -> list.set(1, null));

        // Duplicate element
        assertThrows(IllegalArgumentException.class, () -> list.set(1, "First"));
    }
}
