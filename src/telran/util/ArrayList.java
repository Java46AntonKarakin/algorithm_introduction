package telran.util;

import java.util.Arrays;
import java.util.Iterator;
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

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public T next() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		//array reallocation isn't done
		//that is new array won't be created - essence of remove
		//to use System.arraycopy
		// size--
		return false;
	}

	@Override
	public boolean removeIf(Predicate<T> predicate) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Object pattern) {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		//if size == array.length you should do reallocation see the method add
				//if size < array.length new array won't be created - essence of the algorithm
		return false;
	}

	@Override
	public T remove(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int indexOf(Object pattern) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int lastIndexOf(Object pattern) {
		// TODO Auto-generated method stub
		
		return 0;
	}
	@Override
	public T get(int index) {
		// TODO Auto-generated method stub
		return null;
	}

}
