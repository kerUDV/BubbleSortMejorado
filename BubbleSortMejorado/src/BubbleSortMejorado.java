import java.util.Arrays;
import java.util.Random;

public class BubbleSortMejorado {

    static class Metrics {
        long comparisons;
        long swaps;
        long nanos;

        @Override
        public String toString() {
            return comparisons + "," + swaps + "," + nanos;
        }
    }

    static Metrics bubbleNormal(int[] a) {
        Metrics m = new Metrics();
        long start = System.nanoTime();

        int n = a.length;
        for (int pass = 0; pass < n - 1; pass++) {
            for (int i = 1; i < n - pass; i++) {
                m.comparisons++;
                if (a[i - 1] > a[i]) {
                    swap(a, i - 1, i);
                    m.swaps++;
                }
            }
        }

        m.nanos = System.nanoTime() - start;
        return m;
    }

    static Metrics bubbleMejorado(int[] a) {
        Metrics m = new Metrics();
        long start = System.nanoTime();

        int n = a.length;
        while (n > 1) {
            boolean swapped = false;
            int lastSwap = 0;

            for (int i = 1; i < n; i++) {
                m.comparisons++;
                if (a[i - 1] > a[i]) {
                    swap(a, i - 1, i);
                    m.swaps++;
                    swapped = true;
                    lastSwap = i;
                }
            }

            if (!swapped) break;
            n = lastSwap;
        }

        m.nanos = System.nanoTime() - start;
        return m;
    }

    static void swap(int[] a, int i, int j) {
        int tmp = a[i]; a[i] = a[j]; a[j] = tmp;
    }

    static int[] ordered(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = i;
        return a;
    }

    static int[] reversed(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = n - 1 - i;
        return a;
    }

    static int[] randomArray(int n, long seed) {
        Random r = new Random(seed);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = r.nextInt(n * 10);
        return a;
    }

    static int[] nearlySorted(int n, int k, long seed) {
        int[] a = ordered(n);
        Random r = new Random(seed);
        for (int t = 0; t < k; t++) {
            int i = r.nextInt(n);
            int j = r.nextInt(n);
            swap(a, i, j);
        }
        return a;
    }

    public static void main(String[] args) {
        int[] sizes = {100, 500, 1000, 2000, 5000};
        long seed = 12345L;

        System.out.println("scenario,n,algo,comparisons,swaps,nanos,isSorted");

        for (int n : sizes) {
            runScenario("ordered", ordered(n), n);
            runScenario("reversed", reversed(n), n);
            runScenario("random", randomArray(n, seed), n);
            runScenario("nearlySorted", nearlySorted(n, Math.max(1, n / 100), seed), n);
        }
    }

    static void runScenario(String scenario, int[] base, int n) {
        int[] a1 = Arrays.copyOf(base, base.length);
        Metrics m1 = bubbleNormal(a1);
        System.out.println(scenario + "," + n + ",normal," + m1 + "," + isSorted(a1));

        int[] a2 = Arrays.copyOf(base, base.length);
        Metrics m2 = bubbleMejorado(a2);
        System.out.println(scenario + "," + n + ",mejorado," + m2 + "," + isSorted(a2));
    }

    static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i - 1] > a[i]) return false;
        }
        return true;
    }
}