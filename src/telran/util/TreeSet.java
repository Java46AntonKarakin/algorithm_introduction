package telran.util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import javax.naming.OperationNotSupportedException;

public class TreeSet<T> implements SortedSet<T> {
	private static class Node<T> {
		T obj;
		Node<T> parent;
		Node<T> left;
		Node<T> right;

		Node(T obj) {
			this.obj = obj;
		}
	}

	private Node<T> root;
	int size;
	Comparator<T> comp;

	private Node<T> getLeastNodeFrom(Node<T> node) {
		while (node.left != null) {
			node = node.left;
		}
		return node;
	}

	private Node<T> getMostNodeFrom(Node<T> node) {
		while (node.right != null) {
			node = node.right;
		}
		return node;
	}

	private class TreeSetIterator implements Iterator<T> {
		Node<T> current = root == null ? null : getLeastNodeFrom(root);

		@Override
		public boolean hasNext() {

			return current != null;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			T res = current.obj;
			updateCurrent();
			return res;
		}

		private void updateCurrent() {
			current = current.right != null ? getLeastNodeFrom(current.right) : getGreaterParent(current);
		}

		private Node<T> getGreaterParent(Node<T> node) {

			while (node.parent != null && node.parent.left != node) {
				node = node.parent;
			}
			return node.parent;
		}

		@Override
		public void remove() {
			// TODO
			throw new UnsupportedOperationException();
		}

	}

	public TreeSet(Comparator<T> comp) {
		this.comp = comp;
	}

	@SuppressWarnings("unchecked")
	public TreeSet() {
		this((Comparator<T>) Comparator.naturalOrder());
	}

	@Override
	public boolean add(T obj) {
		Node<T> parent = getNodeOrParent(obj);
		boolean res = false;
		int compRes = 0;
		if (parent == null || (compRes = comp.compare(obj, parent.obj)) != 0) {
			// obj doesn't exist
			Node<T> newNode = new Node<>(obj);
			if (parent == null) {
				// added first element that is the root
				root = newNode;
			} else if (compRes > 0) {
				parent.right = newNode;
			} else {
				parent.left = newNode;
			}
			res = true;
			newNode.parent = parent;
			size++;
		}
		return res;
	}

	private Node<T> getNodeOrParent(T obj) {
		Node<T> current = root;
		Node<T> parent = null;
		int compRes = 0;
		while (current != null) {
			parent = current;
			compRes = comp.compare(obj, current.obj);
			if (compRes == 0) {
				break;
			}
			current = compRes > 0 ? current.right : current.left;
		}
		return parent;
	}

	@Override
	public boolean remove(Object pattern) {
		Node <T> nodeToRemove = findNodeEqualsPredicate(pattern);
		if (nodeToRemove == null) {
			return false;
		}
		if (nodeToRemove.left == null) {
			removeNoLeft(nodeToRemove);
		} else if (nodeToRemove.right == null){
			removeNoRight(nodeToRemove);
		} else {
			removeFullLoad(nodeToRemove);
		}
		return true;
	}

	private void removeFullLoad(Node<T> node) {
		// TODO Auto-generated method stub
		
	}

	private void removeNoRight(Node<T> node) {
		// TODO Auto-generated method stub
		
	}

	private void removeNoLeft(Node<T> node) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(Object pattern) {
		return findNodeEqualsPredicate(pattern) != null ? true : false;
	}

	private Node<T> findNodeEqualsPredicate(Object pattern) {
		Node<T> res = getLeastNodeFrom(root);
		Node<T> mostNode = getMostNodeFrom(root);
		while (!res.obj.equals(pattern)) {
			res = getGreaterNode(res);
			if (res == mostNode) {
				res = null;
				break;
			}
		}
		return res;
	}

	@Override
	public int size() {

		return size;
	}

	@Override
	public Iterator<T> iterator() {

		return new TreeSetIterator();
	}

	@Override
	public T first() {
		if (root == null) {
			return null;
		}
		return getLeastNodeFrom(root).obj;
	}

	@Override
	public T last() {
		if (root == null) {
			return null;
		}
		return getMostNodeFrom(root).obj;
	}
	
	private Node<T> getGreaterNode(Node<T> res) {
		res = res.right != null ? getLeastNodeFrom(res.right) : getGreaterParent(res);
		return res;
	}

	private Node<T> getGreaterParent(Node<T> node) {

		while (node.parent != null && node.parent.left != node) {
			node = node.parent;
		}
		return node.parent;
	}
}
