import java.util.Arrays;

public class ReferenceDemo {
    public static void main(String[] args) {
        // Primitive: value copy
        int a = 5;
        int b = a;
        b = 10;
        System.out.println("a = " + a + ", b = " + b);           // a = 5, b = 10

        // Array: reference (address) copy
        int[] arr1 = {1, 2, 3};
        int[] arr2 = arr1;
        arr2[0] = 99;
        System.out.println("arr1 = " + Arrays.toString(arr1));   // [99, 2, 3]
        System.out.println("same object? " + (arr1 == arr2));    // true

        // String: reference type, but immutable
        String s1 = "hello";
        String s2 = s1;
        s2 = s2 + "!";                                           // creates a NEW String
        System.out.println("s1 = " + s1 + ", s2 = " + s2);       // hello, hello!

        // null reference
        String nothing = null;
        try {
            nothing.length();
        } catch (NullPointerException e) {
            System.out.println("NullPointerException: no address in the variable");
        }
    }
}
