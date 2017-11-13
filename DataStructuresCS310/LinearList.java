package data_structures;
// Doubly Linked List Program 
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinearList<E> implements LinearListADT<E> {
	private class Node<E> {
		E data;
		Node<E> next;
		Node<E> previous;

		public Node(E data) {
			this.data = data;
			next = null;
			previous = null;
		}

	}//node

	private Node<E> head, tail;
	private int currentSize;
	private long modificationCounter; 
	public LinearList() {
		head = tail = null;
		currentSize = 0;
		modificationCounter=0;
	}

	// Adds the Object obj to the beginning of list and returns true if the list
	// is not full.
	// returns false and aborts the insertion if the list is full.

	public boolean addFirst(E obj) {
		Node<E> newNode = new Node<E>(obj);
		if (isEmpty()) {
			head = tail = newNode;
			currentSize++;
			return true;
		}
		newNode.next = head;
		head = newNode;
		currentSize++;
		modificationCounter++;
		return true;
	}

	public boolean addLast(E obj) {
		Node<E> newNode = new Node<E>(obj);
		if (isEmpty()) {
			head = tail = newNode;
		} else {
			tail.next = newNode;
			newNode.previous = tail;
			tail = newNode;
		}
		currentSize++;
		modificationCounter++;
		return true;
	}

	public E removeFirst() {
		if (head == null)
			return null;
		E tmp = head.data;
		head = head.next;
		if (head == null)
			tail = null;
		else
			head.previous = null;
		currentSize--;
		modificationCounter++;
		return tmp;
	}

	public E removeLast() {
		if (tail == null)
			return null;
		E tmp = tail.data;
		tail = tail.previous;
		if (tail == null)
			head = null;
		else
			tail.next = null;
		currentSize--;
		modificationCounter++;
		return tmp;
	}

	public E remove(E obj) {

		if (currentSize == 0)
			return null;

		Node<E> current = head;

		while (current != null
				&& ((Comparable<E>) obj).compareTo(current.data) != 0) {
			current = current.next;
			// current.previous=previous.previous;
			if (current == null)
				return null;
		}
		if (currentSize == 1)
			clear();
		else if (current == head)
			removeFirst();
		else if (current == tail)
			removeLast();
		else
			current.previous.next = current.next;
		currentSize--;
		modificationCounter++;
		return obj;
	}

	public E peekFirst() {
		return head.data;
	}

	public E peekLast() {
		return tail.data;
	}

	public boolean contains(E obj) {
		Node<E> newNode = new Node<E>(obj);
		Node<E> tmp = head;
		while (tmp != null) {
			if (((Comparable<E>) obj).compareTo(tmp.data) == 0)
				return true;
			tmp = tmp.next;
		}
		return false;
	}

	public E find(E obj) {
		if (contains(obj))
			return obj;
		else
			return null;
		// Node<E> currentNode = head;
		/*
		* Node<E> tmp =head; while(tmp!=null){
		* if(((Comparable<E>)obj).compareTo(tmp.data)==0) return true; tmp =
		* tmp.next;
		* 
		* 
		* } return ;
		*/}

	public void clear() {
		head = tail = null;
		modificationCounter=0;
	}

	public boolean isEmpty() {
		return currentSize == 0;
	}

	public boolean isFull() {
		return false;
	}

	public int size() {
		return currentSize;
	}

	@Override
	public Iterator<E> iterator() {
		return new IteratorHelper();
	}

	class IteratorHelper implements Iterator<E> {
		Node<E> current;
		long modCounter;

		public IteratorHelper() {
			current = head;
			modCounter = modificationCounter;
		}

		public boolean hasNext() {
			if(modCounter != modificationCounter)
				throw new ConcurrentModificationException();

