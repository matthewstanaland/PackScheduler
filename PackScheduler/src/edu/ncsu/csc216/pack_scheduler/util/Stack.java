package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * Stack interface 
 * @param <E> Object
 */
public interface Stack<E> {
	/**
	 * Adds an element to the top(end) of the Stack
	 * @param element The element to be added
	 * @throws NullPointerException if element is null
	 * @throws IllegalArgumentException if the capacity of the stack has been reached
	 * @throws IllegalArgumentException if a duplicate element gets added
	 */
	void push(E element);
	/**
	 * Removes an element from the top of the stack
	 * @throws EmptyStackException if the stack is empty
	 * @return element The element removed from the stack
	 */
	E pop();
	/**
	 * Checks if the stack is empty
	 * @return true if the list is empty and false if it's not
	 */
	boolean isEmpty();
	/**
	 * Returns the size of the stack
	 * @return size Size of the stack
	 */
	int size();
	/**
	 * Sets the capacity of the stack
	 * @param capacity The capacity of the stack
	 * @throws IllegalArgumentException if the capacity is less than zero or less than the size of the list
	 */
	void setCapacity(int capacity);
	
	
}
