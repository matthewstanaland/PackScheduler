package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;


/**
 * LinkedStack implements Stack
 * @param <E> Object
 */
public class LinkedStack<E> implements Stack<E> {
	/** The front of the linked list. */
	private LinkedAbstractList<E> list;
	
	/** The number of elements in the linked list. */
	private int size;
	
	/** The maximum allowed number of elements in the linked list. */
	private int capacity;

	/**
	 * Constructs a new LinkedStack object
	 * @param capacity The capacity of the stack
	 */
	public LinkedStack(int capacity) {
		this.list = new LinkedAbstractList<E>(capacity);
		this.size = list.size();
		setCapacity(capacity);
	}
	/**
	 * Adds an element to the top of the stack
	 * @param element The element to be added
	 * @throws IllegalArgumentException if the size equals the capacity
	 */
	@Override
	public void push(E element) {
		if (size == capacity) {
			throw new IllegalArgumentException("Size cannot be bigger than cpacity.");
		}
		
		list.add(size, element);
		size++;
	}
	/**
	 * Removes an element from the top of the stack
	 * @throws EmptyStackException if the stack is empty
	 * @return Returns the value of the removed element
	 */
	@Override
	public E pop() {
		if(size == 0) {
			throw new EmptyStackException();
		}
		E value = list.remove(size - 1);
		size--;
		return value;

	}
	/**
	 * Checks if the stack is empty
	 * @return true if the stack is empty and false if it isn't
	 */
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
		 
	}
	/**
	 * Returns the size of the stack
	 * @return size Size of the stack
	 */
	@Override
	public int size() {
		
		return size;
	}
	
	/**
	 * Sets the capacity of the stack
	 * @param capacity The capacity of the stack
	 * @throws IllegalArgumentException if the capacity is less than zero or less than the size
	 */
	@Override
	public void setCapacity(int capacity) {
		if(capacity < 0 || capacity < size) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
		
	}
}