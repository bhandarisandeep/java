import java.util.Arrays;

public class PasswordDemo {
    public static void main(String[] args) {
        // WEAK: String cannot be wiped; stays in heap until GC
        String pwdString = "Secret@123";
        System.out.println("Checking (String): " + check(pwdString.toCharArray()));
        // pwdString can't be cleared here

        // BETTER: char[] can be overwritten after use
        char[] pwd = {'S','e','c','r','e','t','@','1','2','3'};
        System.out.println("Checking (char[]): " + check(pwd));
        Arrays.fill(pwd, '\0');                     // wipe it
        boolean wiped = true;
        for (char ch : pwd) if (ch != '\0') wiped = false;
        System.out.println("After wipe, every char is zero: " + wiped);

        // P.S. Don't do this: new String(pwd) brings a String copy back into memory
    }

    static boolean check(char[] input) {
        char[] expected = "Secret@123".toCharArray();
        boolean ok = Arrays.equals(input, expected);
        Arrays.fill(expected, '\0');
        return ok;
    }
}
