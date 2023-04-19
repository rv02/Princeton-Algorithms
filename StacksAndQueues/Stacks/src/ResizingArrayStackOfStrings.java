public class ResizingArrayStackOfStrings {
    private String[] stack;
    private int N;

    public ResizingArrayStackOfStrings() {
        stack = new String[1];
        N = 0;
    }

    private void resize(int capacity) {
        String[] temp = new String[capacity];
        for (int i = 0; i < N; i++) {
            temp[i] = stack[i];
        }
        stack = temp;
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public void push(String item) {
        if (N == stack.length) {
            resize(2 * stack.length);
        }
        stack[N++] = item;
    }

    public String pop() {
        String item = stack[--N];
        stack[N] = null;
        if (N > 0 && N == stack.length / 4) {
            resize(stack.length / 2);
        }
        return item;
    }
}
