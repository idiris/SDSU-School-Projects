package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Hash<K, V> implements HashI<K, V> {
    private int currentSize;
    private int maxSize;
    private int tableSize;
    private long modCounter;
    private LinkedList<DictionaryNode<K,V>> [] list;

    public Hash(int n){
        currentSize=0;
        maxSize=n;
        modCounter=0;
        tableSize=(int)(maxSize * 1.3f);
        list = new LinkedList[tableSize];
        for (int i =0;i<tableSize;i++){
            list[i] = new LinkedList<DictionaryNode<K,V>>();
        }
    }
    public class DictionaryNode<K,V> implements Comparable<DictionaryNode<K,V>>{
        K key;
        V value;
        public DictionaryNode(K key, V value){
            this.key=key;
            this.value=value;
        }//wrapper class constructor
       
        public int compareTo(DictionaryNode<K, V> node) {
            return ((Comparable<K>)key).compareTo(node.key);
        }
       
       
    }
    public boolean add(K key, V value) {
        if (!isEmpty())
        return false;
        int index = (key.hashCode()& 0x7FFFFFFF) % tableSize;

        if( list[index].contains(new DictionaryNode<K,V>(key,null)))
            return false;
        list[index].add(new DictionaryNode<K,V> (key,value));
        currentSize++;
        modCounter++;
        return true;
    }

   
    public boolean remove(K key) {
        if(isEmpty())
        return false;
        int index = (key.hashCode()& 0x7FFFFFFF) % tableSize;
        if(!list[index].contains(new DictionaryNode<K,V>(key,null)))
            return false;
        list[index].remove(new DictionaryNode<K,V> (key,null));
        currentSize--;
        modCounter++;
        return true;
    }
    public V getValue(K key) {
        int index = (key.hashCode()& 0x7FFFFFFF) % tableSize;

        DictionaryNode<K,V> temp = list[index].find(new DictionaryNode<K,V> (key,null));
        if(temp==null)
            return null;
        return temp.value;

    }

    public int size() {
        return currentSize;
    }

    public boolean isEmpty() {
        return currentSize==0;
    }

   
    public double loadFactor() {
        return (double)currentSize;
    }

   
    public void resize(int newSize) {
       
    }

   
    public Iterator<K> iterator() {
        return new KeyIteratorHelper();

    }

    public Iterator keys() {
       

        return new KeyIteratorHelper();
    }
   
    public Iterator values() {

        return new ValueIteratorHelper() ;
    }
class KeyIteratorHelper<T> implements Iterator<T>{

    T[] keys;
    int current;
    public KeyIteratorHelper(){
        kets = (T[]) new Object[]
    }
    public boolean hasNext() {

        return false;
    }

    @Override
    public T next() {
        // TODO Auto-generated method stub
        return null;
    }
