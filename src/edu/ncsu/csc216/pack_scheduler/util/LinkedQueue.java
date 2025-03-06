/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * LinkedQueue implements Queue
 * @param <E> Object
 */
public class LinkedQueue<E> implements Queue<E> {
	/** The front of the linked list. */
	private LinkedAbstractList<E> list;
	
	/** The number of elements in the linked list. */
	private int size;
	
	/** The maximum allowed number of elements in the linked list. */
	private int capacity;
	
	/**
	 * Constructs a new LinkedQueue
	 * @param capacity The capacity of the queue
	 */
	public LinkedQueue(int capacity) {
		this.list = new LinkedAbstractList<E>(capacity);
		this.size = list.size();
		setCapacity(capacity);
	}
	/**
	 * Adds an element to the back of the queue
	 * @param element The element to be added to the queue
	 * @throws IllegalArgumentException if the size is equal to the capacity
	 */
	@Override
	public void enqueue(E element) {
		if (size == capacity) {
			throw new IllegalArgumentException("Size cannot be bigger than capacity.");
		}
		
		list.add(size(), element);
		size++;
		
	}
	/**
	 * Removes an element from the front of the queue
	 * @throws NoSuchElementException if the queue is empty
	 * @return Returns the element removed from the front of the queue
	 */
	@Override
	public E dequeue() {
		if(isEmpty()) {
			throw new NoSuchElementException();
		}
		E removedElement = list.remove(0);
		size--;
		return removedElement;
	}
	/**
	 * Checks if the queue is empty
	 * @return true if the queue is empty and false if it's not
	 */
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}
	/**
	 * Returns the size of the queue
	 * @return size The size of the queue
	 */
	@Override
	public int size() {
		return size;
	}
	/**
	 * Sets the capacity of the queue
	 * @param capacity The capacity of the queue
	 * @throws IllegalArgumentException if the capacity is less than zero or less than the size
	 */
	@Override
	public void setCapacity(int capacity) {
		if(capacity < 0 || capacity < list.size()) {
			throw new IllegalArgumentException();
		}
		
		this.capacity = capacity;
		
	}

}
