// Day 2 — loops, break/continue, and two loop bugs
public class Loops {
    static final int MAX_ITEMS = 1_000;     // upper limit for user-controlled loops

    public static void main(String[] args) {
        for (int i = 1; i <= 3; i++) System.out.print("for " + i + "  ");
        System.out.println();

        int n = 3;
        while (n > 0) { System.out.print("while " + n + "  "); n--; }
        System.out.println();

        int tries = 0;
        do { tries++; } while (tries < 0);  // body runs once even though the condition is false
        System.out.println("do-while ran " + tries + " time");

        int[] nums = {4, 9, 15, 7, 21};
        for (int num : nums) System.out.print(num + " ");   // for-each (like Python "for x in list")
        System.out.println();

        // break: stop the whole loop at the first prime
        for (int num : nums) {
            if (isPrime(num)) { System.out.println("First prime: " + num); break; }
        }
        // continue: skip just this iteration
        for (int num : nums) {
            if (num % 3 == 0) continue;
            System.out.println("Not divisible by 3: " + num);
        }

        // SECURITY: a user-controlled count must be capped
        int requested = args.length > 0 ? Integer.parseInt(args[0]) : 5;
        int count = Math.min(requested, MAX_ITEMS);
        System.out.println("Processing " + count + " items (requested " + requested + ")");

        // BUG: off-by-one (<= instead of <)
        try {
            for (int i = 0; i <= nums.length; i++) { int v = nums[i]; }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Off-by-one: " + e.getMessage());
        }
    }

    static boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i * i <= n; i++) if (n % i == 0) return false;
        return true;
    }
}
