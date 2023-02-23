import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayList<E> implements Iterable<E> {
    // 真正存储数据的底层数组
    private E[] data;
    // 记录当前元素个数
    private int size;
    // 默认初始容量
    private static final int INIT_CAP = 1;

    public MyArrayList() {
        this(INIT_CAP);
    }

    public MyArrayList(int initCapacity) {
        data = (E[]) new Object[initCapacity];
        size = 0;
    }

    // 增
    public void add(int index, E e) {
        // 检查index是否越界
        checkPositionIndex(index);
        // 判断是否已满，是扩容原来的2倍
        if(isFull()){
            resize(size * 2);
        }
        // 移动数组
        System.arraycopy(data, index, data, index + 1, size - index);
        data[index] = e;
        size++;
        
    }

    public void addLast(E e) {
        // 判断是否已满，是扩容原来的2倍
        if(isFull()){
            resize(size * 2);
        }
        // 向末尾添加元素
        data[size] = e;
        size++;
    }

    public void addFirst(E e) {
        add(0, e);
    }

    // 删
    public E remove(int index) {
         // 检查索引越界
        checkElementIndex(index);
        // 进行缩容
        int cup = data.length;
        if(size == cup / 4){
            resize(cup / 2);
        }
        E deletedVal = data[index];
        System.arraycopy(data, index + 1, data, index, size - index - 1);
        data[size - 1] = null;
        size--;
        return deletedVal;

    }

    public E removeLast() {
        // 判断是否为空
        if(isEmpty()){
            throw new NoSuchElementException();
        }

        // 进行缩容
        int cup = data.length;
        if(size == cup / 4){
            resize(cup / 2);
        }
        // 保存被删除的元素
        E deletedVal = data[size - 1];
        data[size - 1] = null;
        size--;
        return deletedVal;
    }

    public E removeFirst() {
        return remove(0);
    }

    // 查
    public E get(int index) {
        return data[index];
        
    }

    // 改
    public E set(int index, E element) {
       E oldVal = data[index];
       data[index] = element;
    }


    // 工具方法
    // 将 data 的容量改为 newCap
    private void resize(int newCap) {
        if(size > newCap){return;}
        E[] temp = new (E[]) Object[newCap];
        System.arraycopy(data, 0, temp, 0, size);
        data = temp;

    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull(){
        return size == data.length;
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
        return new Iterator<E>() {
            private int p = 0;

            @Override
            public boolean hasNext() {
                return p != size;
            }

            @Override
            public E next() {
                return data[p++];
            }
        };
    }
}