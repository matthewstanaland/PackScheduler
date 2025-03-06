package edu.ncsu.csc216.pack_scheduler.util;

/**
 * A custom implementation of the Java ArrayList. Uses list functionality with an underlying
 * array for object storage.
 * @param <E> generic type of the ArrayList
 * 
 * @author Kergan Sanderson
 * @author Artie Yakovlev
 * @author Cameron Edwards
 */
public class ArrayList<E> extends java.util.AbstractList<E> {
	
	/** Default initialization size for the underlying array. */
	public static final int INIT_SIZE = 10;
	
	
	/** The array of elements in the ArrayList*/
	private E[] list;
	
	/** The current number of elements in the ArrayList. */
	private int size;
	
	/**
	 * Constructs a new ArrayList. Creates an empty array and sets the initial size to 0;
	 */
	@SuppressWarnings("unchecked")
	public ArrayList() {
		list = (E[]) new Object[INIT_SIZE];
		size = 0;
	}
	
	/**
	 * Adds an element at the provided index in the array
	 * @throws IndexOutOfBoundsException if the index is out of bounds
	 * @throws NullPointerException if the element is null
	 * @throws IllegalArgumentException if a duplicate exists in the list
	 */
	@Override
	public void add(int index, E element) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		if (element == null) {
			throw new NullPointerException();
		}
		for (int i = 0; i < size; i++) {
			if (list[i].equals(element)) {
				throw new IllegalArgumentException();
			}
		}
		if (size == list.length) {
			growArray();
		}
		for (int i = size - 1; i >= index; i--) {
			list[i + 1] = list[i];
		}
		list[index] = element;
		size++;
	}
	
	/**
	 * Creates a new array that is double the capacity of the provided array
	 */
	@SuppressWarnings("unchecked")
	private void growArray() {
		E[] temp = (E[]) new Object[list.length * 2];
		for (int i = 0; i < list.length; i++) {
			temp[i] = list[i];
		}
		list = temp;
	}

	/**
	 * Return the number of elements in the ArrayList
	 * @return the size of the ArrayList
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Returns the item at the given index. If index if less than 0 or greater than or equal to
	 * the ArrayList's size, an IndexOutOfBoundsException is thrown.
	 * @param index the index of the desired item to return
	 * @return the item in the ArrayList at the given index
	 * @throws IndexOutOfBoundsException if index is invalid
	 */
	@Override
	public E get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		return list[index];
	}
	
	/**
	 * Removes the element from the array at the provided index. If index is less than 0 or greater than the
	 * highest index in the arrayList, an IndexOutOfBoundsException is thrown.
	 * @throws IndexOutOfBoundsException if index is invalid
	 * @return the element in the array that was removed
	 */
	@Override
	public E remove(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		E element = list[index];
		
		for (int i = index; i <= (size - 2); i++) {
			list[i] = list[i + 1];
		}
		list[size - 1] = null;
		size--;
		return element;
	}
	
	/**
	 * Replaces an item at an index with the given element. If index is less than 0 or greater than the
	 * highest index in the arrayList, an IndexOutOfBoundsException is thrown. If element is null, a
	 * NullPointerException is thrown. If element is a duplicate of an element already in the list, an
	 * IllegalArgumentException is thrown.
	 * @param index the index to replace the item at
	 * @param element the item used to replace the current indexed item
	 * @return the value in the array that was replaced by element
	 * @throws IndexOutOfBoundsException if index is invalid
	 * @throws NullPointerException if element is null
	 * @throws IllegalArgumentException if element is a duplicate
	 */
	@Override
	public E set(int index, E element) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		if (element == null) {
			throw new NullPointerException();
		}
		for (int i = 0; i < size; i++) {
			if (list[i].equals(element)) {
				throw new IllegalArgumentException();
			}
		}
		
		E value = list[index];
		list[index] = element;
		
		return value;
	}

}
