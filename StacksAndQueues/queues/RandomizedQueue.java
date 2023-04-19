import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int first;
    private int last;
    private int n;

    public RandomizedQueue() {
        q = (Item[]) new Object[2];
        first = 0;
        last = 0;
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    private void resize(int capacity) {
        assert capacity >= n;
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = q[(first + i) % q.length];
        }
        q = copy;
        first = 0;
        last = n;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (n == q.length) resize(2 * q.length);
        q[last++] = item;
        if (last == q.length) last = 0;
        n++;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue Underflow");
        int randIndex = (StdRandom.uniform(n) + first) % q.length;
        Item temp = q[first];
        q[first] = q[randIndex];
        q[randIndex] = temp;
        Item item = q[first];
        q[first] = null;
        n--;
        first++;
        if (first == q.length) first = 0;
        if (n > 0 && n == q.length / 4) resize(q.length / 2);
        return item;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Queue Underflow");
        int rand = StdRandom.uniform(n);
        return q[(first + rand) % q.length];
    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private int[] getArray() {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = i;
        StdRandom.shuffle(arr);
        return arr;
    }

    private class ArrayIterator implements Iterator<Item> {
        private final int[] arr = getArray();
        private int i = 0;

        public boolean hasNext() {
            return i < n;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = q[(arr[i] + first) % q.length];
            i++;
            return item;
        }
    }

    public static void main(String[] args) {
        int n = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);
        StdOut.println(queue.sample());
        StdOut.println(queue.sample());
        StdOut.println(queue.sample());
        for (int i = 0; i < n; i++) {
            for (int a : queue) {
                for (int b : queue)
                    StdOut.print(a + "-" + b + " ");
                StdOut.println();
            }
            queue.dequeue();
            StdOut.println(queue.isEmpty() + " " + queue.size());
        }
    }
}