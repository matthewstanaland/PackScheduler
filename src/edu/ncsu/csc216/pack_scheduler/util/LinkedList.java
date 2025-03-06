/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * This LinkedList will inhereit from AbstractSequentialList and will implement the standard list methods
 * in terms of an Iterator
 * @param <E> Object
 */
public class LinkedList<E> extends AbstractSequentialList<E> {
	/** The front of the LinkedList */
	private ListNode front;
	/** The back of the LinkedList */
	private ListNode back;
	/** The size of the LinkedList */
	private int size;
	/** Constructs a new LinkedList object */
	public LinkedList() {
		this.front = new ListNode(null);
		this.back = new ListNode(null);
		front.next = back;
		back.prev = front;
		this.size = 0;
	}
	/**
	 * Returns the size of the LinkedList
	 * @return size
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Checks if the element set from LinkedListIterator is a duplicate, then sets it to the LinkedList
	 * @param index the index of the element to be replaced
	 * @param element the new element
	 * @throws IllegalArgumentException if the element is a duplicate
	 */
	@Override
	public E set(int index, E element) {
		if (contains(element)) {
			throw new IllegalArgumentException();
		}
		return super.set(index, element);
	}
	
	/**
	 * Checks if the element to be added from the LinkedListIterator is a duplicate, then adds it to the LinkedList
	 * @param index the index where the element is to be added
	 * @param element the new element
	 * @throws IllegalArgumentException if the element is a duplicate
	 */
	@Override
	public void add(int index, E element) {
		if (contains(element)) {
			throw new IllegalArgumentException();
		}
		super.add(index, element);
		size++;
	}
	
	/**
	 * Returns a new LinkedListIterator object positioned at the provided index
	 * @param index The index where the iterator is placed
	 * @return iterator new LinkedListIterator
	 * @throws IndexOutOfBoundsException if the index is out of bounds
	 */
	@Override
	public ListIterator<E> listIterator(int index) {
		if (index < 0 || index > size()) {
			throw new IndexOutOfBoundsException();
		}
		LinkedListIterator iterator = new LinkedListIterator(index);
		return iterator;
	}
	/**
	 * Inner class containing the ListNodes in LinkedListIterator
	 */
	private class ListNode {
		/** The data within each ListNode */
		private E data;
		/** The next ListNode */
		private ListNode next;
		/** The previous ListNode */
		private ListNode prev;
		
		/**
		 * Constructs a new ListNode
		 * @param data The data contained in the ListNode
		 */
		public ListNode(E data) {
			this.data = data;
		}
		/**
		 * Constructs a new ListNode with the included previous and next calls
		 * @param data The data contained in the ListNode
		 * @param prev The ListNode placed before this ListNode
		 * @param next The ListNode placed after this ListNode
		 */
		public ListNode(E data, ListNode prev, ListNode next) {
			this.data = data;
			this.next = next;
			this.prev = prev;
		}
		
	}
	
	/**
	 * The iterator created to traverse through the LinkedList
	 */
	private class LinkedListIterator implements ListIterator<E> {
		/** The previous ListNode in the iterator */
		private ListNode previous;
		/** The next ListNode in the iterator */
		private ListNode next;
		/** The index of the previous ListNode */
		private int previousIndex;
		/** The index of the next ListNode */
		private int nextIndex;
		/** The last retrieved ListNode in the iterator */
		private ListNode lastRetrieved;
		
		/**
		 * Constructs a new LinkedListIterator object 
		 * @param index The index where the iterator will be placed
		 * @throws IndexOutOfBoundsException if the index is out of bounds
		 */
		public LinkedListIterator(int index) {
			if (index < 0 || index > size()) {
				throw new IndexOutOfBoundsException();
			}
			this.previous = front;
			for (int i = 0; i < index; i++) {
				previous = previous.next;
			}
			this.next = previous.next;
			this.previousIndex = index - 1;
			this.nextIndex = index;
			this.lastRetrieved = null;
		}
		
		/**
		 * Checks if the next node exists
		 * @return true if next exists and false if not
		 */
		@Override
		public boolean hasNext() {
			return next != back;
		}
		
		/**
		 * Checks if the previous node exists
		 * @return true if previous exists and false if not
		 */
		@Override
		public boolean hasPrevious() {
			return previous != front;
		}
		/**
		 * Returns the element called by the next reference in the iterator. Updates the
		 * previous and next indices to reflect the new position in the iterator
		 * @return The value of the next ListNode
		 * @throws NoSuchElementException if the next reference is null or the size of the list is empty
		 */
		@Override
		public E next() {
		    if (next == back) { // Check if we’ve reached the end of the list
		        throw new NoSuchElementException();
		    }
		    lastRetrieved = next;
		    previous = next;
		    next = next.next;
		    previousIndex++;
		    nextIndex++;
		    return lastRetrieved.data;
		}

		/**
		 * Returns the element called by the previous reference in the iterator. Updates the
		 * previous and next indices to reflect the new position in the iterator
		 * @return The value of the previous ListNode
		 * @throws NoSuchElementException if the previous reference is null or the size of the list is empty
		 */
		@Override
		public E previous() {
		    if (previous == front) { // Check if we’ve reached the start of the list
		        throw new NoSuchElementException();
		    }
		    lastRetrieved = previous;
		    next = previous;
		    previous = previous.prev;
		    previousIndex--;
		    nextIndex--;
		    return lastRetrieved.data;
		}
		
		/**
		 * Returns the index of the next ListNode
		 * @return nextIndex 
		 */
		@Override
		public int nextIndex() {
			return nextIndex;
		}
		
		/**
		 * Returns the index of the previous ListNode
		 * @return previousIndex
		 */
		@Override
		public int previousIndex() {
			return previousIndex;
		}
		
		/**
		 * Removes the element at the ListNode in the iterator. Updates the indices of the previous or next ListNodes
		 * if necessary
		 * @throws IllegalStateException if lastRetrieved is null
		 */
		@Override
		public void remove() {
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}
			lastRetrieved.prev.next = lastRetrieved.next;
			lastRetrieved.next.prev = lastRetrieved.prev;
			if(lastRetrieved == previous) {
				previous = lastRetrieved.prev;
				previousIndex--;
			} else {
				next = lastRetrieved.next;
				nextIndex--;
			}
			lastRetrieved = null;
			size--;
			
		}
		
		/**
		 * Sets the element in the position of the iterator to the provided element.
		 * @param e The element to be set
		 * @throws IllegalStateException if lastRetrieved is null
		 * @throws NullPointerException if e is null
		 */
		@Override
		public void set(E e) {
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}
			if (e == null) {
				throw new NullPointerException();
			}
			lastRetrieved.data = e;
			
		}
		
		/**
		 * Adds an element to the specified spot in the iterator. Updates the next and previous
		 * indices to reflect this addition
		 * @param e The element to be added
		 * @throws NullPointerException if e is null
		 */
		@Override
		public void add(E e) {
			if (e == null) {
				throw new NullPointerException();
			}
			ListNode node = new ListNode(e, previous, next);
			previous.next = node;
			next.prev = node;
			previousIndex++;
			nextIndex++;
			lastRetrieved = null;
		}
		
	}

}
