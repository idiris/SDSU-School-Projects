package data_structures;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BalancedSearchTree<E>{
    private class Node<E>{
        E obj;
        private Node<E> leftChild;
        private Node<E> rightChild;
        private Node<E> parent;
        public Node(E o){
            obj = o;
            parent=leftChild=rightChild=null;
        }   
    }//end class Node
    Node<E> root;
    private int currentSize;
    public BalancedSearchTree(){
        root=null;
        currentSize=0;
    }
    private void insert(Node<E> newnode, Node<E> currentnode){
        if (((Comparable<E>)newnode.obj).compareTo((E)currentnode.obj)<0){
            if(currentnode.leftChild == null){
                currentnode.leftChild = newnode;
                newnode.parent = currentnode;
            }
            else    
                insert(newnode,currentnode.leftChild);
        }
        else {
            if(currentnode.rightChild == null){
                currentnode.rightChild = newnode;
                newnode.parent = currentnode;
            }
            else    
                insert(newnode,currentnode.rightChild);
        }
        checkBalance(newnode);
    }
    private void checkBalance(Node<E> node){
        if(node==null)
            return;
        if(
                (height(node.leftChild)- height(node.rightChild)>1) ||
                (height(node.leftChild) - height(node.rightChild)< -1 )
                ){
            rebalance(node);
        }
        checkBalance(node.parent);
    }
    private void rebalance(Node<E> node) {
        if(node==null)
            return;
        if (height(node.leftChild)- height(node.rightChild)>1){
            if (height(node.leftChild.leftChild) > height(node.leftChild.rightChild)){
                node = rightRotate(node);
            }
            else{
                node = LeftRightRotate(node);
            }
        }
        else {
            if (height(node.rightChild)- height(node.leftChild)>1)
                if (height(node.rightChild.rightChild) > height(node.rightChild.leftChild)){
                    node = leftRotate(node);
                }
                else{
                    node = RightLeftRotate(node);
                }
        }
        if (node.parent == null)
            root=node;
        else{
            if(((Comparable<E>)node.obj).compareTo(node.parent.obj)>0)
                node.parent.rightChild=node;
            else
                node.parent.leftChild=node;
        }
    }
    private Node<E> LeftRightRotate(Node<E> node) {
        node.leftChild = rightRotate(node.leftChild);
        return leftRotate(node);
    }
    private Node<E> RightLeftRotate(Node<E> node) {
        node.rightChild=leftRotate(node.rightChild);
        return rightRotate(node);
    }
    private Node<E> leftRotate(Node<E> grandparent) {
        Node<E> node = grandparent.rightChild;
        grandparent.rightChild = node.leftChild;
        node.leftChild=grandparent;
        if(grandparent.parent==null){
            node.parent=null;
            root=node;
        }
        else {
            node.parent=grandparent.parent;
        }
        grandparent.parent=node;
        return node;
    }
    private Node<E> rightRotate(Node<E> grandparent) {
        Node<E> node = grandparent.leftChild;
        grandparent.leftChild = node.rightChild;
        node.rightChild=grandparent;
        if(grandparent.parent==null){
            node.parent=null;
            root=node;
        }
        else {
            node.parent=grandparent.parent;
        }
        grandparent.parent=node;
        return node;
    }
    public boolean add(E obj) {
        Node<E> newNode= new Node<E>(obj);
        if(root==null)
            root = newNode;
        else
            insert(newNode,root);
        currentSize++;
        return true;
    }
    public boolean delete(E obj) {
        Node<E> parent = null;
        Node<E> current = root;
        boolean isLeftChild = false;
        if(root==null)
            return false;
        boolean found = false;
        while(current != null){
            if(((Comparable<E>)obj).compareTo(current.obj)<0){
                isLeftChild = true;
                parent = current;
                current = current.leftChild;
            }
            else if(((Comparable<E>)obj).compareTo(current.obj)>0) {
                isLeftChild = false;
                parent = current;
                current = current.rightChild;
            }
            else {
                found = true;
                break;
            }
        }
        if (!found) {
            return false;
        }
        //if node to be deleted has no children
        if(current.leftChild==null && current.rightChild==null){
            if(current==root){
                root = null;
                currentSize--;
                return true;
            }
            if(isLeftChild){
                parent.leftChild = null;
                currentSize--;
                return true;
            }
            else{
                parent.rightChild = null;
                currentSize--;
                return true;
            }
            //checkBalance(root);
        }
        // if node to be deleted has one child    
        else if(current.rightChild==null){
            if(current==root){
                root = current.leftChild;
                currentSize--;
                checkBalance(root);
                return true;
            }
            else if(isLeftChild){
                parent.leftChild= current.leftChild;
                currentSize--;
                checkBalance(root);
                return true;
            }
            else{
                parent.rightChild = current.leftChild;
                currentSize--;
                checkBalance(root);
                return true;
            }
        }
        else if(current.leftChild==null){
            if(current==root){
                root = current.rightChild;
                currentSize--;
                checkBalance(root);
                return true;
            }
            else if(isLeftChild){
                parent.leftChild = current.rightChild;
                currentSize--;
                checkBalance(root);
                return true;
            }
            else{
                parent.rightChild = current.rightChild;
                currentSize--;
                checkBalance(root);
                return true;
            }
        }   
        //Node to be deleted has two children
        else if(current.leftChild!=null && current.rightChild!=null){
            Node<E> successor = getSuccessor(current);
            if(current==root){
                root = successor;
                checkBalance(root);
                currentSize--;
            }else if(isLeftChild){
                parent.leftChild = successor;
                checkBalance(root);
                currentSize--;
            }else{
                parent.rightChild = successor;
                checkBalance(root);
                currentSize--;
            }           
            successor.leftChild = current.leftChild;
            checkBalance(root);
        }       
        return true;
    }
    public Node<E> getSuccessor(Node<E> deleleNode){
        Node<E> successsor =deleleNode;
        Node<E> successsorParent =deleleNode;
        Node<E> current = deleleNode.rightChild;
        while(current!=null){
            successsorParent = successsor;
            successsor = current;
            current = current.leftChild;
        }
        if(successsor!=deleleNode.rightChild){
            successsorParent.leftChild = successsor.rightChild;
            successsor.rightChild = deleleNode.rightChild;
        }
        return successsor;
    }    // delete an object from the tree
    private E find(E obj, Node<E> n) {
        if(n==null)return null;
        if(((Comparable<E>)obj).compareTo(n.obj)<0)
            return find(obj,n.leftChild);
        if(((Comparable<E>)obj).compareTo(n.obj)>0)
            return find(obj,n.rightChild);
        return(E)n.obj;
    }
    public E get(E obj){
        return find(obj,root);
        // find and retrieve an object in the tree
    }
    public int size(){
        return currentSize;
        // return the current size of the tree
    }
    public int height(Node<E>node ){
        if(node==null)
            return 0;
        int leftheight= height(node.leftChild)+1;
        int rightheight = height(node.rightChild)+1;
        if(leftheight > rightheight)
            return leftheight;
        else
            return rightheight;
        // return the current height of the tree
    }
    public int heightBelow(Node<E> node){
        return height(node);
        // return the height of the tree below the node passed in. The height is the longest path from this node to a leaf
    }
    public boolean isEmpty(){
        return currentSize==0;
        // is the tree empty?
    }
    public boolean isFull(){
        return false;
        // is the tree full?
    }
    public Iterator<E> allElements(){
        return new ObjIteratorHelper();
        // iterate over the tree
    }
    abstract class IteratorHelper implements Iterator<E> {
        protected Node<E>[] array;
        protected int iterPointer = 0;
        public IteratorHelper() {
            array = new Node[currentSize];
            iterPointer = 0;
            inOrder( root);
        }
        public void inOrder(Node<E> n) {
            if (n != null) {
                inOrder(n.leftChild);
                array[iterPointer++] = n;
                inOrder(n.rightChild);
            }
        }
        public boolean hasNext() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return iterPointer < currentSize;
        }
        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return (E) array[iterPointer++].obj;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    class ObjIteratorHelper extends IteratorHelper {

        public ObjIteratorHelper() {
            super();
        }

        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return (E) array[iterPointer++].obj;
        }

    }   
    private E findnexthelper(E obj, Node<E> n){
        if(n==null) return null;
        if(((Comparable<E>)obj).compareTo(n.obj)<0)
            return findnexthelper(obj,n.leftChild);
        if(((Comparable<E>)obj).compareTo(n.obj)>0)
            return findnexthelper(obj,n.rightChild);
        Node<E> successor = null;
        Node<E> successorParent = null;
        Node<E> current = n.rightChild;
        while(current!=null){
            successorParent = successor;
            successor = current;
            current = current.leftChild;
        }
        if(successor!=n.rightChild){
            successorParent.leftChild = successor.rightChild;
            successor.rightChild = n.rightChild;
        }
        return successor.obj;
    }
    public E findNext(E obj){
        return findnexthelper(obj,root);
    }
    public E findPrevious(E obj){
        return findPreviousHelper(obj,root);
        // Returns the previous object in the structure.  Previous is defined as the key that would precede the parameter object in an ordered list of objects, as determined by the Comparable interface.  Returns null if the parameter object is the first object in the tree
    }
    private E findPreviousHelper(E obj, Node<E>n ){
        if(n==null) return null;
        if(((Comparable<E>)obj).compareTo(n.obj)<0)
            return findPreviousHelper(obj,n.leftChild);
        if(((Comparable<E>)obj).compareTo(n.obj)>0)
            return findPreviousHelper(obj,n.rightChild);
        Node<E> successor = null;
        Node<E> successorParent = null;
        Node<E> current = n.leftChild;
        while(current!=null){
            successorParent = successor;
            successor = current;
            current = current.rightChild;
        }
        if(successor!=n.leftChild){
            successorParent.rightChild = successor.leftChild;
            successor.leftChild= n.leftChild;
        }
        return successor.obj;
    }
    public Node<E> rootNode(){
        return root;
        // Return the root node of the tree
    }
}
