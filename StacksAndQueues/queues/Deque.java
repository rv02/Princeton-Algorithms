import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first, last;
    private int n;

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
    }

    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    public boolean isEmpty() { return n == 0; }

    public int size() { return n; }

    public void addFirst(Item item) {
        if (item == null) { throw new IllegalArgumentException(); }
        Node<Item> oldFirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldFirst;
        first.prev = null;
        if (isEmpty()) { last = first; }
        else { oldFirst.prev = first; }
        n++;
    }

    public void addLast(Item item) {
        if (item == null) { throw new IllegalArgumentException(); }
        Node<Item> oldLast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        last.prev = oldLast;
        if (isEmpty()) { first = last; }
        else { oldLast.next = last; }
        n++;
    }

    public Item removeFirst() {
        if (isEmpty()) { throw new NoSuchElementException("Deque Underflow"); }
        Item item = first.item;
        first = first.next;
        n--;
        if (isEmpty()) { last = null; }
        else { first.prev = null; }
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) { throw new NoSuchElementException("Queue Underflow"); }
        Item item = last.item;
        last = last.prev;
        n--;
        if (isEmpty()) { first = null; }
        else { last.next = null; }
        return item;
    }

    public Iterator<Item> iterator() { return new LinkedIterator(first); }

    private class LinkedIterator implements Iterator<Item> {
        private Node<Item> current;

        public LinkedIterator(Node<Item> first) { current = first; }

        public boolean hasNext() { return current != null; }

        public void remove() { throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        deque.addLast("Hi");
        StdOut.println(deque.isEmpty());
        StdOut.println(deque.size());
        deque.addLast("World");
        deque.addFirst("temp");
        StdOut.println(deque.removeFirst());
        for (String a : deque) {
            StdOut.print(a + " ");
        }
        StdOut.println();
        StdOut.println(deque.removeLast());
        StdOut.println(deque.removeFirst());
        StdOut.println(deque.isEmpty() + " " + deque.size());
    }

}
