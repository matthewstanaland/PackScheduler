package edu.ncsu.csc216.pack_scheduler.util;

/**
 * A recursive implementation of a singly linked list.
 * 
 * @author Matt Stanaland
 * @param <E> the type of elements stored in the list
 */
public class LinkedListRecursive<E> {
    /** The front node of the list */
    private ListNode front;
    /** The size of the list */
    private int size;

    /**
     * Constructs an empty LinkedListRecursive.
     */
    public LinkedListRecursive() {
        front = null;
        size = 0;
    }

    /**
     * Returns the size of the list.
     * 
     * @return the size of the list
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the list is empty.
     * 
     * @return true if the list is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checks if the list contains the specified element.
     * 
     * @param element the element to search for
     * @return true if the element is found, false otherwise
     */
    public boolean contains(E element) {
        if (front == null) {
            return false;
        }
        return front.contains(element);
    }

    /**
     * Adds the specified element to the end of the list.
     * 
     * @param element the element to add
     * @return true if the element was added successfully
     * @throws NullPointerException     if the element is null
     * @throws IllegalArgumentException if the element is a duplicate
     */
    public boolean add(E element) {
        if (element == null) {
            throw new NullPointerException("Cannot add null element.");
        }
        if (contains(element)) {
            throw new IllegalArgumentException("Duplicate element.");
        }
        if (front == null) { // Empty list
            front = new ListNode(element, null);
            size++;
            return true;
        }
        return addRecursive(front, element);
    }

    private boolean addRecursive(ListNode node, E element) {
        if (node.next == null) { // End of the list
            node.next = new ListNode(element, null);
            size++;
            return true;
        }
        return addRecursive(node.next, element); // Recurse to the next node
    }

    /**
     * Adds the specified element at the given index.
     * 
     * @param idx     the index to insert the element
     * @param element the element to add
     * @throws IndexOutOfBoundsException if the index is out of bounds
     * @throws NullPointerException      if the element is null
     * @throws IllegalArgumentException  if the element is a duplicate
     */
    public void add(int idx, E element) {
        if (element == null) {
            throw new NullPointerException("Cannot add null element.");
        }
        if (idx < 0 || idx > size) {
            throw new IndexOutOfBoundsException("Invalid index.");
        }
        if (contains(element)) {
            throw new IllegalArgumentException("Duplicate element.");
        }
        if (idx == 0) { // Add to the front
            front = new ListNode(element, front);
            size++;
            return;
        }
        // Start recursion
        front.add(idx - 1, element);
        size++;
    }


    /**
     * Returns the element at the specified index.
     * 
     * @param idx the index of the element to retrieve
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public E get(int idx) {
        if (idx < 0 || idx >= size) {
            throw new IndexOutOfBoundsException("Invalid index.");
        }
        return getRecursive(front, idx); // Call the private helper method
    }

    private E getRecursive(ListNode node, int idx) {
        if (idx == 0) { // Base case: index matches the current node
            return node.data;
        }
        return getRecursive(node.next, idx - 1); // Recurse, decrementing index
    }



    /**
     * Removes the element at the specified index.
     * 
     * @param idx the index of the element to remove
     * @return the removed element
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public E remove(int idx) {
        if (idx < 0 || idx >= size) {
            throw new IndexOutOfBoundsException("Invalid index.");
        }

        if (idx == 0) { // Remove front
            E removedData = front.data;
            front = front.next;
            size--;
            return removedData;
        }

        // Start recursion at the front node
        size--;
        return removeRecursive(front, idx - 1);
    }

    private E removeRecursive(ListNode node, int idx) {
        if (node.next == null) {
            throw new NullPointerException("Cannot remove element: next node is null.");
        }

        if (idx == 0) { // Found node before the one to remove
            E removedData = node.next.data;
            node.next = node.next.next;
            return removedData;
        }

        return removeRecursive(node.next, idx - 1); // Recurse, decrementing idx
    }


    /**
     * Removes the specified element from the list.
     * 
     * @param element the element to remove
     * @return true if the element was removed, false otherwise
     */
    public boolean remove(E element) {
        if (element == null || front == null) {
            return false; // Cannot remove null element or from an empty list
        }

        if (front.data.equals(element)) { // Special case: removing the first element
            front = front.next;
            size--;
            return true;
        }

        // Delegate to recursive helper
        return removeRecursive(front, element);
    }
    
    private boolean removeRecursive(ListNode node, E element) {
        if (node.next == null) { // Reached end of the list
            return false; // Element not found
        }

        if (node.next.data.equals(element)) { // Found the element to remove
            node.next = node.next.next; // Skip the node to remove
            size--;
            return true;
        }

        return removeRecursive(node.next, element); // Recurse to the next node
    }

    /**
     * Sets the element at the specified index to the given value.
     * 
     * @param idx     the index of the element to set
     * @param element the new value to set
     * @return the previous value at the index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     * @throws NullPointerException      if the element is null
     * @throws IllegalArgumentException  if the element is a duplicate
     */
    public E set(int idx, E element) {
        if (element == null) {
            throw new NullPointerException("Cannot set null element.");
        }
        if (idx < 0 || idx >= size) {
            throw new IndexOutOfBoundsException("Invalid index.");
        }
        if (contains(element)) {
            throw new IllegalArgumentException("Duplicate element.");
        }
        return front.set(idx, element, 0);
    }

    /**
     * Inner class representing a node in the linked list.
     */
    private class ListNode {
        /** The data in the node */
        public E data;
        /** The next node in the list */
        public ListNode next;

        /**
         * Constructs a ListNode with the given data and next node reference.
         * 
         * @param data the data for the node
         * @param next the reference to the next node
         */
        public ListNode(E data, ListNode next) {
            this.data = data;
            this.next = next;
        }

        public boolean contains(E element) {
            if (data.equals(element)) {
                return true;
            }
            if (next == null) {
                return false;
            }
            return next.contains(element);
        }

        
        private void add(int idx, E element) {
            if (idx == 0) { // Base case: insert element here
                this.next = new ListNode(element, this.next);
                return;
            }
            if (this.next == null) { // Handle case where `next` is null
                throw new IndexOutOfBoundsException("Invalid index for insertion.");
            }
            this.next.add(idx - 1, element); // Recurse, decrementing index
        }


        public E set(int idx, E element, int currentIndex) {
            if (currentIndex == idx) {
                E oldData = data;
                data = element;
                return oldData;
            }
            return next.set(idx, element, currentIndex + 1);
        }
    }
}
