import java.util.Arrays;

// Day 2 — Java is ALWAYS pass by value
public class PassByValueDemo {
    static void changeNumber(int x) { x = 100; }               // gets a copy of the value
    static void changeContent(int[] arr) { arr[0] = 99; }      // gets a copy of the address -> same array
    static void reassign(int[] arr) { arr = new int[]{7, 7}; } // only the local copy points elsewhere

    // Overloading: same name, different parameter lists
    static int add(int a, int b) { return a + b; }
    static int add(int a, int b, int c) { return a + b + c; }
    static double add(double a, double b) { return a + b; }
    // static long add(int a, int b) { ... }  -> compile error: only the return type differs

    public static void main(String[] args) {
        int n = 5;
        changeNumber(n);
        System.out.println(n);                          // 5

        int[] nums = {1, 2, 3};
        changeContent(nums);
        System.out.println(Arrays.toString(nums));      // [99, 2, 3]

        reassign(nums);
        System.out.println(Arrays.toString(nums));      // still [99, 2, 3]

        System.out.println(add(2, 3) + " " + add(2, 3, 4) + " " + add(1.5, 2.5)); // 5 9 4.0
    }
}
