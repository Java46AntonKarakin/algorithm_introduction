package telran.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class ArrayList<T> implements List<T> {
	private static final int DEFAULT_CAPACITY = 16;
	private T[] array;
	private int size;

	@SuppressWarnings("unchecked")
	public ArrayList(int capacity) {
		array = (T[]) new Object[capacity];
	}

	public ArrayList() {
		this(DEFAULT_CAPACITY);
	}

	private class ArrayListIterator implements Iterator<T> {
		int currentIndex = 0;

		@Override
		public boolean hasNext() {
			return currentIndex < size;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return array[currentIndex++];
		}
	}

	@Override
	public boolean add(T obj) {
		if (array.length == size) {
			array = Arrays.copyOf(array, size * 2);
		}
		array[size++] = obj;

		return true;
	}

	@Override
	public boolean remove(Object pattern) {
		boolean flIsRemoved = false;
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null && array[i].equals(pattern)) {
				deleteElementByIndex(i);
				flIsRemoved = true;
				break;
			}
		}
		return flIsRemoved;
	}

	@Override
	public boolean removeIf(Predicate<T> predicate) {
		boolean flIsRemoved = false;
		int index = 0;
		while (index != size && array[index] != null) {
			if (predicate.test(array[index])) {
				deleteElementByIndex(index);
				flIsRemoved = true;
			} else {
				index++;
			}
		}
		return flIsRemoved;
	}

	@Override
	public boolean contains(Object pattern) {

		return indexOf(pattern) >= 0;
	}

	@Override
	public int size() {

		return size;
	}

	@Override
	public Iterator<T> iterator() {

		return new ArrayListIterator();
	}

	@Override
	public boolean add(int index, T obj) {
		if (index < 0 || index > size+1) {
			return false;
		}

		if (size == array.length) {
			array = Arrays.copyOf(array, size * 2);
		}
		System.arraycopy(array, index, array, index + 1, size++ - index);
		array[index] = obj;
		return true;
	}

	@Override
	public T remove(int index) {
		if (index < 0 || index > size) {
			return null;
		}
		
		T res = array[index];
		if (index == 0) {
			System.arraycopy(array, index + 1, array, index, size-- - index);
		} else if (index == size) {
			array[index] = null;
		} else {
			System.arraycopy(array, index, array, index, size-- - index);
		}
		
		return res;
	}

	@Override
	public int indexOf(Object pattern) {
		int indexCounter = 0;
		for (T obj : array) {
			if (pattern.equals(obj)) {
				return indexCounter;
			}
			indexCounter++;
		}
		return -1;
	}

	@Override
	public int lastIndexOf(Object pattern) {
		for (int i = size; i >= 0; i--) {
			if (pattern.equals(array[i])) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public T get(int index) {
		return (index >= 0 && index < size) ? array[index] : null;
	}

	private T[] deleteElementByIndex(int index) {
		System.arraycopy(array, index + 1, array, index, size-- - index);
		array[size] = null;
		return array;
	}
}
