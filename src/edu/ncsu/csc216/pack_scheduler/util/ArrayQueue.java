/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;


/**
 * ArrayQueue implements queue and adds elements to the back of the queue, or removes and returns the elements at the front of the queue. 
 * it has the methods enqueue for adding the elements to the back of the queue 
 * and dequeue for removing and return the elements at the front of the Queue
 * @param <E> ArrayQueu with type E that implements the queue
 */
public class ArrayQueue<E> implements Queue<E> {
	/**
	 * arrayQueue list
	 */
	ArrayList<E> list;
	/**
	 * field for the size of the capacity of the queue
	 */
	private int size;
	/**
	 * field for the capacity of the queue
	 */
	private int capacity;
	
	/**
	 * constructs a new empty arrayList
	 * sets size to zero
	 * 
	 * @param capacity capacity for the array queue
	 */
	public ArrayQueue(int capacity) {
		list = new ArrayList<E>();
		size = 0;
		setCapacity(capacity);
	}

	/**
	 * Add element to the back of the queue
	 * @param element is added to the end of the que
	 * @throws IllegalArgumentException if the size equals the capacity
	 */
	@Override
	public void enqueue(E element) {
		if(size == capacity) {
			throw new IllegalArgumentException();
		}
		
		list.add(list.size(), element);
		size++;
	}

	/**
	 * Removes and returns the elemnt at the front of the queue
	 * if queue is empty throw NSEE
	 * @throws NoSuchElementException if the queue is empty
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
	 * contains method that checks if the list contains the student
	 * @param element element that is checked against the list
	 * @return true or false depending on the if the element is equal to the specified element from the list
	 */
	public boolean contains(E element) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals(element)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * returns true if the queue is empty and false other wise
	 */
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	/**
	 * returns the number of elements in the queue
	 */
	@Override
	public int size() {
		
		return size;
	}

	/**
	 * sets the Queues capacity and if the parameter is negative 
	 * or if it is less than the number of elements in the queue an IAE is thrown
	 * @param capacity is the capacity being set
	 * @throws IllegalArgumentException if capacity is less than number of 
	 * elements in the queue or if parameter is negative
	 */
	@Override
	public void setCapacity(int capacity) {
		if(capacity < 0 || capacity < list.size()) {
			throw new IllegalArgumentException();
		}
		
		this.capacity = capacity;
		
	}

}
