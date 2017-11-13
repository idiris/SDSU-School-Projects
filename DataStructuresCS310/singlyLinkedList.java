package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Mohamed Sharif-Idiris , Masc0987
 *
 * Masc0987
 */
public class LinkedList<E> implements ListI<E> {
    private class Node<E>{
        Node<E> next;
        E data;
        public Node(E data){
            this.data=data;
            next=null;
        }

    }
    private Node<E> head, tail;
    private int currentSize;
    public LinkedList(){
        head = tail = null;
        currentSize=0;
    }
    public void addFirst(E obj) {
        Node<E> newNode = new Node<E>(obj);
        if(isEmpty()){
            head = tail = newNode;
            currentSize++;
        }
        else{
            newNode.next = head;
            head = newNode;
            currentSize++;   
        }
    }
    public void addLast(E obj) {   
        Node<E> newNode = new Node<E>(obj);
        if(isEmpty()){
            head =tail= newNode;
            currentSize++;
        }
        else{
            tail.next=newNode;
            tail=newNode;
            currentSize++;
        }

    }
    public E removeFirst() {
        if (head == null)
            return null;
        E tmp = head.data;
        head = head.next;
        if (head == null)
            tail = null;
        currentSize--;
        return tmp;
    }
    public E removeLast() {
        Node <E>current = head;
        Node<E> previous = null;
        if(head==null)
            return null;
        if (head==tail)
            return removeFirst();
        E tmp = tail.data;
        while (current.next!= null){
            previous=current;
            current=current.next;
        }

        previous.next=null;
        tail=previous;
        currentSize--;
        return tmp;

    }
    public E peekFirst() {
        return head.data;
    }

    public E peekLast() {       
        return tail.data;
    }

    public void makeEmpty() {
        head=null;
        currentSize=0;
    }
    public boolean isEmpty() {
        return currentSize==0; // check to see if currentSize is empty
    }

    public boolean isFull() {
        return false; // its a linkedlist and will expand so will remain false;
    }
    public int size() {       
        return currentSize;
    }// size is the currentSize counter

    public boolean contains(E obj) {
        Node<E> newNode = new Node<E>(obj); // create a new Node Object
        Node<E> tmp = head; //
        while (tmp != null) {
            if (((Comparable<E>) obj).compareTo(tmp.data) == 0)
                return true;// it has found the object that it was comparing to
            tmp = tmp.next; // it has not found it and should go to the next node and continue test
        }

        return false; // the object being located was not found and therefore not in the node.

    }

    public void reverse() {       
        Node<E> current= head;
        Node<E> previous=null;
        Node<E> tmp = null;
        while(current!=null){
            tmp = current.next;
            current.next = previous;
            previous = current;
            current = tmp;
        }
        head = previous;

        //previous=current;
        //current=current.next;
    }
    //check to see if the list has more than one element
    //have 2 checkers traversing the list
    //
    public Iterator<E> iterator() {
        return new IteratorHelper();
    }

    class IteratorHelper implements Iterator<E> {
        Node<E> current;

        public IteratorHelper() {
            current = head;
        }
        public boolean hasNext() {

            return current != null;
        }

        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E temp = current.data;
            current = current.next;
            return temp;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}



