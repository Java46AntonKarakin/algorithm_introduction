package telran.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class LinkedList<T> implements List<T> {
	private static class Node<T> {
		T obj;
		Node<T> next;
		Node<T> prev;

		Node(T obj) {
			this.obj = obj;
		}
	}

	private Node<T> head;
	private Node<T> tail;
	private int size;

	private class LinkedListIterator implements Iterator<T> {

		Node<T> current = head;
		Node<T> previous = head;
		boolean flNext = false;

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public T next() {
			flNext = true;
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			previous = current;
			current = current.next;
			return previous.obj;
		}

		@Override
		public void remove() {
			if (!flNext) {
				throw new IllegalStateException();
			}
			if (head == tail) {
				head = tail = null;
				size--;
			} else if (current == null) {
				removeTailElement();
				flNext = false;
			} else if (current == head.next) {
				removeHeadElement();
				flNext = false;
			} else {
				removeMiddleElement(current.prev);
				flNext = false;
			}
		}
	}

	@Override
	public boolean add(T obj) {
		if (obj == null) {
			throw new NullPointerException();
		}
		Node<T> newNode = new Node<>(obj);
		if (head == null) {
			head = tail = newNode;
		} else {
			newNode.prev = tail;
			tail.next = newNode;
			tail = newNode;
		}
		size++;
		return true;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Iterator<T> iterator() {
		return new LinkedListIterator();
	}

	@Override
	public boolean add(int index, T obj) {
		if (obj == null) {
			throw new NullPointerException();
		}
		boolean res = false;
		if (index >= 0 && index <= size) {
			res = true;
			if (index == size) {
				add(obj);
			} else if (index == 0) {
				addHead(obj);
			} else {
				addIndex(index, obj);
			}
		}
		return res;
	}

	@Override
	public T remove(int index) {
		if (!checkExistingIndex(index) || index > size) {
			return null;
		}
		Node<T> current = head;

		for (int i = 0; i < index; i++) {
			current = current.next;
		}

		if (index == 0) {
			removeHeadElement();
		} else if (index == size - 1) {
			removeTailElement();
		} else {
			current.next.prev = current.prev;
			current.prev.next = current.next;
			size--;
		}
		return current.obj;
	}

	@Override
	public boolean remove(Object pattern) {
		boolean res = false;
		Node<T> tmp = head;

		while (tmp != null) {
			if (removeByPatternOnce(tmp, pattern)) {
				res = true;
				break;
			}
			tmp = tmp.next;
		}
		return res;
	}

	@Override
	public int indexOf(Object pattern) {
		int res = -1;
		Node<T> temp = head;

		if (isFirstObjEqualsPattern(pattern)) {
			return 0;
		}

		for (int i = 0; i < size; i++) {
			if (isNextObjEqualsPattern(temp, pattern)) {
				temp = head.next;
				res = i;
				break;
			}
		}
		return res;
	}

	@Override
	public int lastIndexOf(Object pattern) {
		int commonIndex = -1;
		int indOflastMatchingObj = -1;
		Node<T> temp = head;

		while (temp != null) {
			commonIndex++;
			if (temp.obj.equals(pattern)) {
				indOflastMatchingObj = commonIndex;
			}
			temp = temp.next;
		}
		return indOflastMatchingObj;
	}

	@Override
	public T get(int index) {
		T res = null;
		if (checkExistingIndex(index)) {
			Node<T> node = getNodeByIndex(index);
			res = node.obj;
		}
		return res;
	}

	private boolean checkExistingIndex(int index) {
		return index >= 0 && index < size;
	}

	private Node<T> getNodeByIndex(int index) {

		return index > size / 2 ? getNodeRightToLeft(index) : getNodeLeftToRight(index);
	}

	private Node<T> getNodeLeftToRight(int index) {
		Node<T> current = head;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		return current;
	}

	private Node<T> getNodeRightToLeft(int index) {
		Node<T> current = tail;
		for (int i = size - 1; i > index; i--) {
			current = current.prev;
		}
		return current;
	}

	private boolean isNextObjEqualsPattern(Node<T> current, Object pattern) {
		boolean res = false;
		current = current.next;
		if (current.obj.equals(pattern)) {
			res = true;
		}
		return res;
	}

	public boolean removeByPredicateOnce(Node<T> current, Predicate<T> predicate) {
		boolean res = false;
		if (current.prev == null) {
			if (predicate.test(head.obj)) {
				removeHeadElement();
				res = true;
			}
		} else if (current.next == null) {
			if (predicate.test(tail.obj) && res != true) {
				removeTailElement();
				res = true;
			}
		} else {
			if (predicate.test(current.obj) && res != true) {
				res = true;
				size--;
				current.next.prev = current.prev;
				current.prev.next = current.next;
			}
		}
		return res;
	}

	public boolean removeByPatternOnce(Node<T> current, Object pattern) {
		boolean res = false;
		if (current.prev == null) {
			if (head.obj.equals(pattern)) {
				removeHeadElement();
				res = true;
			}
		} else if (current.next == null) {
			if (tail.obj.equals(pattern) && res != true) {
				removeTailElement();
				res = true;
			}
		} else {
			if (current.obj.equals(pattern) && res != true) {
				removeMiddleElement(current);
				res = true;
			}
		}
		return res;
	}

	public void reverse() {
		headAndTailreplace();
		Node<T> current = head;
		do {
			reverseThisNode(current);
			current = current.next;
		} while (current != null);

	}

	private void reverseThisNode(Node<T> current) {
		Node<T> tmp;
		tmp = current.next;
		current.next = current.prev;
		current.prev = tmp;
	}

	private void headAndTailreplace() {
		Node<T> temp = head;
		head = tail;
		tail = temp;
	}

	private boolean isFirstObjEqualsPattern(Object pattern) {
		if (head == null) {
			return false;
		}
		return head.obj.equals(pattern);
	}

	private void removeTailElement() {
		tail = tail.prev;
		tail.next = null;
		size--;
	}

	private void removeHeadElement() {
		head = head.next;
		head.prev = null;
		size--;
	}

	private void removeMiddleElement(Node<T> current) {
		size--;
		current.next.prev = current.prev;
		current.prev.next = current.next;
	}

	private void addIndex(int index, T obj) {

		Node<T> newNode = new Node<>(obj);
		Node<T> afterNode = getNodeByIndex(index);
		Node<T> beforeNode = afterNode.prev;
		newNode.next = afterNode;
		afterNode.prev = newNode;
		beforeNode.next = newNode;
		newNode.prev = beforeNode;
		size++;
	}

	private void addHead(T obj) {
		size++;
		Node<T> newNode = new Node<>(obj);
		newNode.next = head;
		head.prev = newNode;
		head = newNode;
	}
}
