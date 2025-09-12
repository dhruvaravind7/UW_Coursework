// LinkedQueue class
public class LinkedQueue<E> implements MyQueue<E>{
    
    // ListNode class
    private static class ListNode<E>{
        private final E data;
        private ListNode<E> next;

        private ListNode(E data, ListNode<E> next){
            this.data = data;
            this.next = next;
        }

        private ListNode(E data){
            this.data = data;
        }
    }

    // Fields representing the head and tails of the queue and the size of it.
    private ListNode<E> head;
    private ListNode<E> tail;
    private int size;

    // Initializes the fields.
    public LinkedQueue(){
        head = null;
        tail = null;
        size = 0;
    }

    // Adds the input to the tail of the queue
    public void enqueue(E item){
        if (head == null) {
            head = new ListNode<E>(item);
            tail = head;
        } else {
            tail.next = new ListNode<E>(item);
            tail = tail.next;
        }
        size++;
    }

    // If the queue is not empty, returns the head of the queue and removes it. 
    //  - Throws an IllegalStateException if the queue is empty
    public E dequeue(){
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        E item = head.data;
        head = head.next;
        size--;
        return item;
    }

    // If the queue is not empty, returs the head of the queue.
    //  - Throws an IllegalStateException if the queue is empty
    public E peek(){
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return head.data;
    }

    // Returns the size of the queue.
    public int size(){
        return size;
    }

    // Returns if the queue is empty or not
    public boolean isEmpty(){
        return size == 0;
    }
}
