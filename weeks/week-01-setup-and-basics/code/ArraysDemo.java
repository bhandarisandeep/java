import java.util.Arrays;

// Day 3 — arrays
// Try: java ArraysDemo 2        (valid index)
//      java ArraysDemo 7        (rejected index)
//      java ArraysDemo 2 huge   (asks for an array bigger than the JVM allows)
public class ArraysDemo {
    static final int MAX_SIZE = 10_000;   // cap for user-chosen sizes

    public static void main(String[] args) {
        int[] scores = {90, 75, 60, 85, 70};   // created with values
        int[] empty = new int[3];               // created with a size
        String[] names = new String[2];
        boolean[] flags = new boolean[2];

        System.out.println("length        : " + scores.length);          // 5 (no brackets)
        System.out.println("first / last  : " + scores[0] + " / " + scores[scores.length - 1]);
        System.out.println("int defaults  : " + Arrays.toString(empty));  // [0, 0, 0]
        System.out.println("bool defaults : " + Arrays.toString(flags));  // [false, false]
        System.out.println("obj defaults  : " + Arrays.toString(names));  // [null, null]

        // Arrays never grow: copyOf makes a NEW bigger array and copies the values
        int[] bigger = Arrays.copyOf(scores, 7);
        System.out.println("copyOf(7)     : " + Arrays.toString(bigger) + " same object? " + (bigger == scores));

        // 2-D array: an array of arrays (a table)
        int[][] table = { {1, 2, 3}, {4, 5, 6} };
        System.out.println("table[1][2]   : " + table[1][2]);             // 6

        // SECURITY 1: validate a user-supplied index
        int idx = args.length > 0 ? Integer.parseInt(args[0]) : 1;
        if (idx >= 0 && idx < scores.length) {
            System.out.println("scores[" + idx + "]     : " + scores[idx]);
        } else {
            System.out.println("Index " + idx + " rejected (valid: 0-" + (scores.length - 1) + ")");
        }

        // SECURITY 2: never trust a user-chosen size
        int requestedSize = 500;
        if (requestedSize < 0 || requestedSize > MAX_SIZE) throw new IllegalArgumentException("size too large");
        int[] buffer = new int[requestedSize];
        System.out.println("buffer size   : " + buffer.length);

        // SECURITY 3: object arrays start full of null
        String first = names[0];
        System.out.println("names[0] is " + (first == null ? "null - check before use" : first));

        // The limit: index is an int, and the heap must have room
        if (args.length > 1 && args[1].equals("huge")) {
            try {
                long[] huge = new long[Integer.MAX_VALUE];
            } catch (OutOfMemoryError e) {
                System.out.println("OutOfMemoryError: " + e.getMessage());
            }
        }
    }
}
