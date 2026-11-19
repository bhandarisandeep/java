public class OverflowDemo {
    public static void main(String[] args) {
        int quantity = 30_000_000;
        int price = 100;

        // VULNERABLE: 3,000,000,000 does not fit in an int; it silently wraps to a negative number, no exception
        int total = quantity * price;
        System.out.println("Unsafe total = " + total);

        // SECURE: throws ArithmeticException on overflow
        try {
            int safeTotal = Math.multiplyExact(quantity, price);
            System.out.println("Safe total = " + safeTotal);
        } catch (ArithmeticException e) {
            System.out.println("Overflow detected: " + e.getMessage());
        }

        // Alternative: use a wider type
        long wide = (long) quantity * price;
        System.out.println("Using long = " + wide);
    }
}
