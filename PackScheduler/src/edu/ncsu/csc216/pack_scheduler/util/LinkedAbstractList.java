package edu.ncsu.csc216.pack_scheduler.util;

/**
 * An abstract list that has a capacity, excludes null elements, and excludes duplicate elements.
 * Uses a linked list as the main data structure.
 * @param <E> the type of object stored in the list.
 * 
 * @author Cameron Edwards
 * @author Kergan Sanderson
 * @author Artie Yakovlev
 */
public class LinkedAbstractList<E> extends java.util.AbstractList<E> {
	
	/** The front of the linked list. */
	private ListNode front;
	
	/** The number of elements in the linked list. */
	private int size;
	
	/** The maximum allowed number of elements in the linked list. */
	private int capacity;
	
	/** The back of the linked list. */
	private ListNode back;
	
	
	/**
	 * Constructs a LinkedAbstractList given the capacity of the list.
	 * @param capacity the maximum number of items that will be allowed in the list.
	 */
	public LinkedAbstractList(int capacity) {
		this.front = null;
		this.size = 0;
		this.back = null;
		setCapacity(capacity);
	}
	
	/**
	 * Sets the capacity of the list to the given value.
	 * @param capacity the new value of the capacity of the list
	 * @throws IllegalArgumentException if the capacity is less than zero or less than size
	 */
	public void setCapacity(int capacity) {
		if (capacity >= 0) {
			this.capacity = capacity;
		}
		else {
			throw new IllegalArgumentException("Capacity cannot be less than zero.");
		}
		
		if (capacity < size) {
			throw new IllegalArgumentException("Capacity cannot be less than size of the list.");
		}
	}
	
	/**
	 * Gets the capacity of the list.
	 * @return the maximum number of items allowed in the list
	 */
	public int getCapacity() {
		return capacity;
	}
	
	/**
	 * Adds an item to the list at the given index. If index is less than 0 or greater than size, an
	 * IndexOutOfBoundsException is thrown. If element is null, a NullPointerException is thrown. If
	 * the list is full or element is a duplicate of an element in the list already, an
	 * IllegalArgumentException is thrown.
	 * @param index the index to add the element at
	 * @param element the item to be added to the list
	 * @throws IndexOutOfBoundsException if index is invalid
	 * @throws NullPointerException if element is null
	 * @throws IllegalArgumentException if the list is full
	 * @throws IllegalArgumentException if element is a duplicate of an element in the list
	 */
	@Override
	public void add(int index, E element) {
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException("Index is out of bounds.");
		}
		
		if (element == null) {
			throw new NullPointerException("Element cannot be null.");
		}
		
		if (size() == capacity) {
			throw new IllegalArgumentException("Size cannot be bigger than cpacity.");
		}
		
		ListNode current = front;
		
		for (int i = 0; i < size(); i++) {
			if(current.data.equals(element)) {
				throw new IllegalArgumentException("Element is already in list");
			}
			current = current.next;
		}
		
		if (index == 0) {
			front = new ListNode(element, front);
			if (size == 0) {
				back = front;
			}
			
		} else if (index == size) {
			back.next = new ListNode(element, null);
			back = back.next;
		} else {
			current = front;
			for (int i = 0; i < index - 1; i++) {
				current = current.next;
		}
			current.next = new ListNode(element, current.next);
		}
		size++;
	}
	
	/**
	 * Removes the element in the list at the given index. If index is less than 0 or greater than the size of
	 * the list, an IndexOutOfBoundsException is thrown.
	 * @param index the index of the element to be removed
	 * @return the value that was removed from the list
	 * @throws IndexOutOfBoundsException if index is invalid
	 */
	@Override
	public E remove(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException("Index is out of bounds");
		}
		E value = null;
		if (index == 0) {
			value = front.data;
			front = front.next;
			if (size == 1) {
				back = null;
			}
		} else {
			ListNode current = front;
			for (int i = 0; i < index - 1; i++) {
				current = current.next;
			}
			value = current.next.data;
			current.next = current.next.next;
			if (index == size - 1) {
				back = current;
			}
		}
		size--;
		return value;
	}
	
	/**
	 * Sets an element at an index in the list to a new value. If element is null, a NullPointerException is thrown.
	 * If element is a duplicate of an item in the list, an IllegalArgumentException is thrown. If index is invalid,
	 * an IndexOutOfBoundsException is thrown.
	 * @param index the index of the element in the list to set
	 * @param element the new element to set at index
	 * @return the element that was overwritten in the list
	 * @throws NullPointerException if element is null
	 * @throws IllegalArgumentException if element is a duplicate of something else in the list
	 * @throws IndexOutOfBoundsException if index is invalid
	 */
	@Override
	public E set(int index, E element) {
		E temp = null;
		if (element == null) {
			throw new NullPointerException("Element cannot be null");
		}
		
		ListNode current = front;
		
		for (int i = 0; i < size(); i++) {
			if(current.data.equals(element)) {
				throw new IllegalArgumentException("Element is already in list");
			}
			current = current.next;
		}
		
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException("Index is out of bounds");
		}
		
		if (index == 0) {
			temp = front.data;
			front = front.next;
			front = new ListNode(element, front);
		}
		else {
			current = front;
			for (int i = 0; i < index - 1; i++) {
				current = current.next;
		}
			temp = current.next.data;
			current.next = current.next.next;
			current.next = new ListNode(element, current.next);
		}
		return temp;
		
	}
	
	/**
	 * Gets the value of an index in the list. If the index is out of the bounds of the list, an
	 * IndexOutOfBoundsException is thrown.
	 * @param index the index to retrieve from the list
	 * @return the element at index in the list
	 * @throws IndexOutOfBoundsException if index is invalid
	 */
	@Override
	public E get(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException("Index is out of bounds");
		}
		ListNode current = front;
		for (int i = 0; i <= index - 1; i++) {
			current = current.next;
	}
		E temp = current.data;
		return temp;
	}
	
	/**
	 * Gets the size of the list.
	 * @return the number of elements in the list
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * A node in the LinkedAbstractList. Each node contains data and a reference to the next node.
	 * 
	 * @author Kergan Sanderson
	 * @author Artie Yakovlev
	 * @author Cameron Edwards
	 */
	private class ListNode {
		
		/** The data value stored by this list node. */
		private E data;
		
		/** A reference to the next list node in the linked list. */
		private ListNode next;
		
		/**
		 * Constructs a list node by itself without connecting it to other nodes in a list.
		 * @param data the data to contain in this list node
		 */
		public ListNode(E data) {
			this.data = data;
		}
		
		/**
		 * Constructs a list node with data and a connection to the next list node in the linked list.
		 * @param data the data to contain in this list node
		 * @param next the reference to the next list node in the linked list
		 */
		public ListNode(E data, ListNode next) {
			this(data);
			this.next = next;
		}
 	}



}
