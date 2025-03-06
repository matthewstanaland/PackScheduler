/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * The Queue interface to be used in ArrayQueue and LinkedQueue
 * @param <E> Object
 */
public interface Queue<E> {
	/**
	 * Adds an element to the back of the queue
	 * @param element The element to be added to the queue
	 * @throws IllegalArgumentException if the size is equal to the capacity
	 */
	void enqueue(E element);
	/**
	 * Removes an element from the front of the queue
	 * @throws NoSuchElementException if the queue is empty
	 * @return Returns the element removed from the front of the queue
	 */
	E dequeue();
	/**
	 * Checks if the queue is empty
	 * @return true if the queue is empty and false if it's not
	 */
	boolean isEmpty();
	/**
	 * Returns the size of the queue
	 * @return size The size of the queue
	 */
	int size();
	/**
	 * Sets the capacity of the queue
	 * @param capacity The capacity of the queue
	 * @throws IllegalArgumentException if the capacity is less than zero or less than the size
	 */
	void setCapacity(int capacity);
}
