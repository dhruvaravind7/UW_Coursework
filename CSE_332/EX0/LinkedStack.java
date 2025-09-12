// LinkedStack class
public class LinkedStack<T> implements MyStack<T> {
    
    // ListNode class
    private static class ListNode<T>{
        private final T data;
        private ListNode<T> next;

        private ListNode(T data, ListNode<T> next){
            this.data = data;
            this.next = next;
        }

        private ListNode(T data){
            this.data = data;
        }
    }

    // Fields representing the head and size of the stack.
    private ListNode<T> head;
    private int size;

    // Initializes the fields.
    public LinkedStack(){
        head = null;
        size = 0;
    }

    // Adds the inputted element at the front of the stack.
    public void push(T item){
        head = new ListNode<T>(item, head);
        size++;
    }

    // Returns if the stack is empty or not.
    public boolean isEmpty(){
        return size == 0;
    }
    
    // Returns the size of the stack
    public int size(){
        return size;
    }
    
    // If the stack is not empty, returns the front element and removes it.
    //  - Throws an IllegalStateException if the stack is empty
    public T pop(){
        if (isEmpty()){
            throw new IllegalStateException("Stack is empty");
        }
        T item = head.data;
        head = head.next;
        size--;
        return item;
    }

    // If the stack is not emtpy, returns the front element.
    //  - Throws an IllegalStateException if the stack is empty
    public T peek(){
        if (isEmpty()){
            throw new IllegalStateException("Stack is empty");
        }
        return head.data;
    }
}
