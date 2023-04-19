import java.util.NoSuchElementException;

public class LinkedStackOfStrings {
    private Node first = null;

    private class Node{
        String item;
        Node next;
    }

    public boolean isEmpty() {
        return first == null;
    }
    public void push(String item) {
        Node OldFirst = first;
        first = new Node();
        first.item = item;
        first.next = OldFirst;
    }

    public String pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack Underflow");
        }
        String item = first.item;
        first = first.next;
        return item;
    }

}
