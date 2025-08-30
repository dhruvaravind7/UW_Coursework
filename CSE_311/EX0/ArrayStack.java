// ArrayStack class
public class ArrayStack<T> implements MyStack<T> {

    // Fields of the ArrayStack representing the stack, the position where the next element will go, and the size of the stack.
    // - Tail is NOT the position of the last element but one space after it.
    // - Size is NOT the number of elements in the stack but the actual size of the array that represents the stack.
    private T[] stack;
    private int tail;
    private int size;

    // Initializes the fields.
    // - The default size of the stack is 10 elements.
    public ArrayStack(){
        stack = (T[]) new Object[10];
        size = 10;
        tail = 0;
    }
    
    // Adds the inputted item to the tail of the stack.
    // - If the stack is full, it doubles the size of the stack.
    public void push(T item) {
        if (tail == size){
            T[] tempStack = (T[]) new Object[size*2];
            for (int i = 0; i < size; i++){
                tempStack[i] = stack[i];
            }
            tail = size;
            size *= 2;
            stack = tempStack;
        }    

        stack[(tail) % size] = item;
        tail++;
    }

    // Removes the most recently added item from the stack and returns it.
    // - If the stack is empty, it throws an IllegalStateException.
    public T pop() {
        if (isEmpty()){
            throw new IllegalStateException("There are no elements in the stack");
        }
        T item = stack[(tail - 1) % size];
        tail--;
        return (item);
    }

    // Returns the most recently added item in the stack without removing it.
    // - If the stack is empty, it throws an IllegalStateException.
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("There are no elements in the stack");
        }
        return (stack[(tail - 1) % size]);
    }

    // Returns the number of items in the stack.
    // - This is NOT the size of the array that represents the stack.
    public int size() {
        return (tail);
    }

    // Returns a boolean indicating whether the stack has items.
    // - This is NOT whether the size of the array that represents the stack is 0.
    public boolean isEmpty(){
        return (tail == 0);
    }
}  
