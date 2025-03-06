package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;
/**
 * ArrayStack implements Stack and adds element to the end of the ArrayList stack
 * @param <E> Object
 */
public class ArrayStack<E> implements Stack<E> {
	/** The ArrayList to be used in the ArrayStack */
	private ArrayList<E> list;
	/** The size of the ArrayStack */
	private int size;
	/** The capacity of the ArrayStack */
	private int capacity;
	/**
	 * Constructs a new ArrayStack
	 * @param capacity The capacity of the stack
	 */
	public ArrayStack(int capacity) {
		setCapacity(capacity);
		list = new ArrayList<E>();
		size = 0;
	}
	/**
	 * Adds an element to the top(end) of the ArrayStack
	 * @param element The element to be added
	 * @throws NullPointerException if element is null
	 * @throws IllegalArgumentException if the capacity of the stack has been reached
	 * @throws IllegalArgumentException if a duplicate element gets added
	 */
	@Override
	public void push(E element) {
		if (element == null) {
			throw new NullPointerException();
		}
		if (size == capacity) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < size; i++) {
			if (list.get(i).equals(element)) {
				throw new IllegalArgumentException();
			}
		}
		
		list.add(size, element);
		size++;
	}
	/**
	 * Removes an element from the top of the stack
	 * @throws EmptyStackException if the stack is empty
	 * @return element The element removed from the stack
	 */
	@Override
	public E pop() {
		if(size == 0) {
			throw new EmptyStackException();
		}
		E element = list.get(size - 1);
		
		list.remove(size - 1);
		size--;
		return element;
		
	}
	/**
	 * Checks if the stack is empty
	 * @return true if the list is empty and false if it's not
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
	 * @throws IllegalArgumentException if the capacity is less than zero or less than the size of the list
	 */
	@Override
	public void setCapacity(int capacity) {
		if(capacity < 0 || capacity < size) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
		
	}

}
