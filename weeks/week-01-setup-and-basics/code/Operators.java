// Day 2 — operators, short-circuit and fail-safe defaults
public class Operators {
    public static void main(String[] args) {
        System.out.println("7 / 3        = " + (7 / 3));            // 2  (int / int = int)
        System.out.println("7 % 3        = " + (7 % 3));            // 1  (remainder)
        System.out.println("7.0 / 3      = " + (7.0 / 3));          // 2.333...
        System.out.println("(double) 7/3 = " + ((double) 7 / 3));   // 2.333...

        int x = 10;
        x += 5;                                                     // x = x + 5
        System.out.println("x            = " + x);

        // Short-circuit: && stops at the first false, || stops at the first true
        String user = null;
        if (user != null && user.equals("admin")) {                 // SAFE: null check first
            System.out.println("admin");
        } else {
            System.out.println("Not admin (no NullPointerException)");
        }
        // if (user.equals("admin") && user != null)  -> NullPointerException!

        System.out.println(canDelete("ADMIN") + " " + canDelete("EDITOR") + " " + canDelete("HACKER"));
    }

    // Fail-safe default: anything not explicitly allowed is denied
    static boolean canDelete(String role) {
        switch (role) {
            case "ADMIN":
                return true;
            case "EDITOR":
            case "VIEWER":
                return false;
            default:
                return false;   // NEVER "true" here
        }
    }
}
