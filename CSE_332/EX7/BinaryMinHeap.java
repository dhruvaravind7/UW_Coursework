import java.lang.reflect.Array;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
 
public class BinaryMinHeap <T extends Comparable<T>> implements MyPriorityQueue<T> {
    private int size; // Maintains the size of the data structure
    private T[] arr; // The array containing all items in the data structure
                     // index 0 must be utilized
    private Map<T, Integer> itemToIndex; // Keeps track of which index of arr holds each item.

    public BinaryMinHeap(){
        // This line just creates an array of type T. We're doing it this way just
        // because of weird java generics stuff (that I frankly don't totally understand)
        // If you want to create a new array anywhere else (e.g. to resize) then
        // You should mimic this line. The second argument is the size of the new array.
        arr = (T[]) Array.newInstance(Comparable.class, 10);
        size = 0;
        itemToIndex = new HashMap<>();
    }

    // move the item at index i "rootward" until
    // the heap property holds
    private void percolateUp(int i){
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (arr[parent].compareTo(arr[i]) > 0) {
                T temp = arr[i];
                arr[i] = arr[parent];
                arr[parent] = temp;
                itemToIndex.put(arr[i], i);
                itemToIndex.put(arr[parent], parent);
                i = parent;
            } else {
                break;
            }
        }
    }

    // move the item at index i "leafward" until
    // the heap property holds
    private void percolateDown(int i){
        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int smallest = i;
            if (left < size && arr[left].compareTo(arr[smallest]) < 0) {
                smallest = left;
            }
            if (right < size && arr[right].compareTo(arr[smallest]) < 0) {
                smallest = right;
            }
            if (smallest != i) {
                T temp = arr[i];
                arr[i] = arr[smallest];
                arr[smallest] = temp;
                itemToIndex.put(arr[i], i);
                itemToIndex.put(arr[smallest], smallest);
                i = smallest;
            } else {
                break;
            }
        }
    }


    // copy all items into a larger array to make more room.
    private void resize(){
        T[] larger = (T[]) Array.newInstance(Comparable.class, arr.length*2);
        for(int i = 0; i < arr.length; i++){
            larger[i] = arr[i];
        }
        arr = larger;
    }
    
    // Inserts the given item into the heap.
    public void insert(T item){
        if(size == arr.length){
            resize();
        }
        arr[size] = item;
        itemToIndex.put(item, size);
        size++;
        percolateUp(size-1);
    }

    // Removes and returns the "top priority" item from the heap.
    public T extract(){
        if(isEmpty()){
            throw new IllegalStateException("Heap is empty!");
        }
        T item = arr[0];
        arr[0] = arr[size-1];
        itemToIndex.put(arr[0], 0);
        itemToIndex.remove(item);
        size--;
        percolateDown(0);
        return item;
    }

    // Remove the item at the given index.
    // Make sure to maintain the heap property!
    private T remove(int index){
        if(index >= size){
            throw new IllegalArgumentException("Index out of bounds!");
        }
        T removedItem = arr[index];
        T temp = arr[index];
        arr[index] = arr[size - 1];
        arr[size - 1] = temp;
        itemToIndex.put(arr[index], index);
        itemToIndex.remove(removedItem);
        size--;
        if (index < size) {
            updatePriority(index);
        }
        return removedItem;
    }

    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public void remove(T item){
        remove(itemToIndex.get(item));
    }

    // Determine whether to percolate up/down
    // the item at the given index, then do it!
    private void updatePriority(int index){
        if(index < 0 || index >= size){
            throw new IllegalArgumentException("Index out of bounds!");
        }
        T item = arr[index];
        int parent = (index - 1) / 2;
        if(index > 0 && arr[parent].compareTo(item) > 0){
            percolateUp(index);
        } else {
            percolateDown(index);
        }
    }

    // This method gets called after the client has 
    // changed an item in a way that may change its
    // priority. In this case, the client should call
    // updatePriority on that changed item so that 
    // the heap can restore the heap property.
    // Throws an IllegalArgumentException if the given
    // item is not an element of the priority queue.
    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public void updatePriority(T item){
	    if(!itemToIndex.containsKey(item)){
            throw new IllegalArgumentException("Given item is not present in the priority queue!");
	    }
        updatePriority(itemToIndex.get(item));
    }

    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public boolean isEmpty(){
        return size == 0;
    }

    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public int size(){
        return size;
    }

    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public T peek(){
        if(isEmpty()){
            throw new IllegalStateException();
        }
        return arr[0];
    }
    
    // We have provided a recommended implementation
    // You're welcome to do something different, though!
    public List<T> toList(){
        List<T> copy = new ArrayList<>();
        for(int i = 0; i < size; i++){
            copy.add(i, arr[i]);
        }
        return copy;
    }

    // For debugging
    public String toString(){
        if(size == 0){
            return "[]";
        }
        String str = "[(" + arr[0] + " " + itemToIndex.get(arr[0]) + ")";
        for(int i = 1; i < size; i++ ){
            str += ",(" + arr[i] + " " + itemToIndex.get(arr[i]) + ")";
        }
        return str + "]";
    }
    
}
