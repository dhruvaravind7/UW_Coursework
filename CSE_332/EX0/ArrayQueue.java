// ArrayQueue class
public class ArrayQueue<T> implements MyQueue<T>{

    // The fields that represent the queue in an array, the head and tail indexes, and the size of the queue
    // The queue is implemented as a circular array, so the head and tail indexes wrap around when they reach the end of the array.
    private T[] queue;
    private int head;
    private int tail;
    private int size;

    // Constructor to initialize the queue with a default size of 10
    public ArrayQueue() {
        queue = (T[]) new Object[10];
        head = 0;
        tail = 0;
        size = 10;
    }

    // Returns if the queue is empty
    public boolean isEmpty(){
        return (head == tail);
    }

    // Returns the number of items currently in the queue
    public int size() {
        return (tail - head);
    }

    // Adds an item into the queue
    // If the queue is full, it doubles the size of the array and copies the items over
    public void enqueue(T item) {
        if ((head % size) == (tail % size) && head != tail) {
            // The queue is full, so we need to double the size of the array
            T[] tempQueue = (T[]) new Object[size * 2];
            for (int i = 0; i < size; i++) {
                tempQueue[i] = queue[(head + i) % size];
            }
            head = 0;
            tail = size;
            size *= 2;
            queue = tempQueue;
        }
        queue[tail % size] = item;
        tail++;
    }

    // Removes and returns the least-recently added item from the queue
    // Throws an IllegalStateException if the queue is empty
    public T dequeue() {
           
        // If queue is empty
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        T item = queue[head % size];
        head++;
        return (item);
    }

    // Returns the least-recently added item from the queue
    // Throws an IllegalStateException if the queue is empty
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return (queue[head % size]);
    }
}
 