import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<E> implements Iterable<E> {
    // 虚拟头尾节点
    final private Node<E> head, tail;
    private int size;

    // 双链表节点
    private static class Node<E> {
        E val;
        Node<E> next;
        Node<E> prev;

        Node(E val) {
            this.val = val;
        }
    }

    // 构造函数初始化头尾节点
    public MyLinkedList() {
        this.head = new Node<>(null);
        this.tail = new Node<>(null);
        head.next = tail;
        tail.prev = head;
        this.size = 0;
    }


    /***** 增 *****/

    public void addLast(E e) {
        // 构建节点
        Node<E> x = new Node<>(e);
        // 拿到tail的前一个节点
        Node temp = tail.prev;
        // temp <-> x <-> tail
        x.next = tail;
        x.prev = temp;
        temp.next = x;
        tail.prev = x;
        size++;
    }

    public void addFirst(E e) {
        // 构建节点
        Node<E> x = new Node<>(e);
        // 拿到head的后一个节点
        Node temp = head.next;
        // head <-> x <-> temp
        x.prev = head;
        x.next = temp;
        head.next = x;
        temp.prev = x;
        size++;
    }

    public void add(int index, E element) {
        if (index == size) {
            addLast(element);
            return;
        }
        // 构建新增节点
        Node<E> x = new Node<>(element);
        Node node = getNode(index);
        // temp <-> node  ---->  temp <-> x <-> node
        Node temp = node.prev;
        x.prev = temp;
        x.next = node;
        node.prev = x;
        temp.next = x;
        size++;
    }

    /***** 删 *****/

    public E removeFirst() {
        // 判断链表是否小于1
        if(size < 1){
            throw new NoSuchElementException();
        }
        // head <-> del <-> temp ----> head <-> temp
        Node del = head.next;
        Node temp = del.next;
        head.next = temp;
        temp.prev = head;

        del.prev = null;
        del.next = null;
        
        size--
        return del.val;

    }

    public E removeLast() {
        // 判断链表是否小于1
        if(size < 1){
            throw new NoSuchElementException();
        }
        // temp <-> del <-> tail ----> temp <-> tail
        Node del = tail.prev;
        Node temp = del.prev;
        temp.next = tail;
        tail.prev = temp;

        del.prev = null;
        del.next = null;

        size--;
        return del.val;

    }

    public E remove(int index) {
        Node delNode = getNode(index);
        E oldVal = delNode.val;
        delNode.prev.next = delNode.next;
        delNode.next.prev = delNode.prev;
        delNode.prev = null;
        delNode.next = null;
        size--;
        return oldVal;

    }

    /***** 查 *****/

    public E get(int index) {
        return getNode(index).val;
        
    }

    public E getFirst() {
        if(size < 1){
            throw new NoSuchElementException();
        }
        return head.next.val;
    }

    public E getLast() {
        if(size < 1){
            throw new NoSuchElementException();
        }
        return tail.prev.val;

    }

    /***** 改 *****/

    public E set(int index, E val) {
        Node x = getNode(index);
        E oldVal = x.val;
        x.val = val;
        return oldVal;
    }

    /***** 其他工具函数 *****/

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private Node<E> getNode(int index) {
        checkElementIndex(index);
        Node<E> p = head.next;
        // TODO: 可以优化，通过 index 判断从 head 还是 tail 开始遍历
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p;
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < size;
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= size;
    }

    /**
     * 检查 index 索引位置是否可以存在元素
     */
    private void checkElementIndex(int index) {
        if (!isElementIndex(index))
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    /**
     * 检查 index 索引位置是否可以添加元素
     */
    private void checkPositionIndex(int index) {
        if (!isPositionIndex(index))
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            Node<E> p = head.next;

            @Override
            public boolean hasNext() {
                return p != tail;
            }

            @Override
            public E next() {
                E val = p.val;
                p = p.next;
                return val;
            }
        };
    }

}