import java.util.Arrays;
import java.util.Random;

public class quickSort {

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static int partition(int[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        while (true) {
            while (a[++i] < a[lo]) {
                if (i == hi) break;
            }
            while (a[lo] < a[--j]) {
                if (j == lo) break;
            }
            if (i >= j) break;
            swap(a, i, j);
        }
        swap(a, j ,lo);
        return j;
    }

    private static void shuffle(int[] a) {
        Random rand = new Random();
        for (int i = 0; i < a.length; i++) {
            int randomIndexToSwap = rand.nextInt(a.length);
            swap(a, i, randomIndexToSwap);
        }
    }

    private static void sort(int[] a, int lo, int hi) {
        if (lo >= hi) return;
        int k = partition(a, lo, hi);
        sort(a, lo, k-1);
        sort(a, k+1, hi);
    }

    public static void sort(int[] a) {
        shuffle(a);
        sort(a, 0, a.length - 1);
    }

    public static void main(String[] args) {
        int[] arr = {1, 10, 782, 45, -345, 45, 0, 56};
        sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}