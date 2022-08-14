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
		int prevIndexHashTable = 0;
		int prevIndexList = 0;
		boolean removeable = false;
		boolean isCurPositionChecked = false;
		T current;

		@Override
		public boolean hasNext() {
			isCurPositionChecked = findList();
			return isCurPositionChecked;
		}

		@Override
		public T next() {
			if (!isCurPositionChecked) {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
			}
			isCurPositionChecked = false;
			getNextObj();
			moveToNextNextObj();
			removeable = true;
			return current;
		}

		@Override
		public void remove() {
			if (!removeable) {
				throw new IllegalStateException();
			}
			hashTable[prevIndexHashTable].remove(prevIndexList);
			if (hashTable[prevIndexHashTable].size() == 0) {
				hashTable[prevIndexHashTable] = null;
			}
			removeable = false;
			size--;
		}

		private boolean findList() { 
			boolean res = isIterable();
			while (!res) {
				if (isThisListDone) {
					isThisListDone = false;
				}
				if (!(indexHashTable < hashTable.length)) {
					res = false;
					break;
				}
				indexHashTable++;
				res = isIterable();
			}
			return res;
		}

		private void getNextObj() {
			current = hashTable[indexHashTable].get(indexList++);
			prevIndexHashTable = indexHashTable;
			prevIndexList = indexList - 1;
		}

		private void moveToNextNextObj() {

			if (indexList >= hashTable[indexHashTable].size()) {
				isThisListDone = true;
				indexList = 0;
				indexHashTable++;
			} else {
				indexList++;
			}
		}

		private boolean isIterable() {
			boolean res = indexHashTable < hashTable.length;
			return res ? hashTable[indexHashTable] != null : false;
		}
	}

	@Override

	public boolean add(T obj) {
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
		HashSet<T> tmp = new HashSet<>(hashTable.length * 2);
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
