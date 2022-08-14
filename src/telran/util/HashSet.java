package telran.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashSet<T> implements Set<T> {
	private static final double DEFAULT_FACTOR = 0.75;
	private static final int DEFAULT_HASH_TABLE_CAPACITY = 16;
	private List<T>[] hashTable;
	private int size;
	private double factor;

	@SuppressWarnings("unchecked")
	public HashSet(int hashTableCapacity, double factor) {
		this.factor = factor;
		hashTable = new List[hashTableCapacity];
	}

	public HashSet(int hashTableCapacity) {
		this(hashTableCapacity, DEFAULT_FACTOR);
	}

	public HashSet() {
		this(DEFAULT_HASH_TABLE_CAPACITY, DEFAULT_FACTOR);
	}

	private class HashSetIterator implements Iterator<T> {
		boolean isThisListDone = false; // true if current list has been completely iterated
		int indexHashTable = 0;
		int indexList = 0;
//		Integer curListSize = hashTable[currentPositionInHashTable].size();
		T res;
		int hashTableLength = hashTable.length;

		@Override
		public boolean hasNext() {
			return indexHashTable < hashTableLength;
		}

		@Override
		public T next() {
			checkhasNext();
			

			while (!isIterable()) {
				if (isThisListDone) {
					isThisListDone = false;
				}
				indexHashTable++;
			}

			checkhasNext();

			getNextObj();
			return res;
		}


		@Override
		public void remove() {
			
		}

		private T getNextObj() {
			res = hashTable[indexHashTable].get(indexList++);
			if (indexList >= hashTable[indexHashTable].size()) {
				isThisListDone = true;
				indexList = 0;
				indexHashTable++;
			} else {
				indexList++;
			}
			return res;
		}

		private boolean isIterable() {
			boolean res = true;
			if (hashTable[indexHashTable] == null || indexHashTable > hashTableLength) {
				res = false;
			}
			return res;
		}
		
		private void checkhasNext() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
		}

	}

	@Override

	public boolean add(T obj) {
		// set can not have two equal objects
		// that's why the method returns false at adding an object that already exists
		boolean res = false;
		if (!contains(obj)) {
			res = true;
			if (size >= hashTable.length * factor) {
				recreateHashTable();
			}
			int hashTableInd = getHashTableIndex(obj.hashCode());
			if (hashTable[hashTableInd] == null) {
				hashTable[hashTableInd] = new LinkedList<T>();
			}
			hashTable[hashTableInd].add(obj);
			size++;
		}
		return res;
	}

	private void recreateHashTable() {
		HashSet<T> tmp = new HashSet<>(hashTable.length * 2); // tmp hashset has table with twice capacity
		for (List<T> list : hashTable) {
			if (list != null) {
				for (T obj : list) {
					tmp.add(obj);
				}
			}
		}
		hashTable = tmp.hashTable;
	}

	private int getHashTableIndex(int hashCode) {
		int res = Math.abs(hashCode) % hashTable.length;
		return res;
	}

	@Override
	public boolean remove(Object pattern) {
		int index = getHashTableIndex(pattern.hashCode());
		boolean res = false;
		if (hashTable[index] != null) {
			res = hashTable[index].remove(pattern);
			if (res) {
				size--;
			}
		}
		return res;
	}

	@Override
	public boolean contains(Object pattern) {
		int index = getHashTableIndex(pattern.hashCode());
		boolean res = false;
		if (hashTable[index] != null) {
			res = hashTable[index].contains(pattern);
		}
		return res;
	}

	@Override
	public int size() {

		return size;
	}

	@Override
	public Iterator<T> iterator() {

		return new HashSetIterator();
	}

}
