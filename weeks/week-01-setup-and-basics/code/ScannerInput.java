import java.util.Scanner;

// Day 2 — reading keyboard input safely
// Run: java ScannerInput   (then type your name and age)
public class ScannerInput {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("Name: ");
        String name = in.nextLine().trim();          // whole line
        if (name.isEmpty() || name.length() > 50) {
            System.out.println("Name must be 1-50 characters");
            return;
        }

        System.out.print("Age: ");
        if (!in.hasNextInt()) {                      // avoids InputMismatchException
            System.out.println("Age must be a whole number");
            return;
        }
        int age = in.nextInt();
        if (age < 0 || age > 150) {                  // validate the RANGE, not just the type
            System.out.println("Age must be between 0 and 150");
            return;
        }

        System.out.println("Hello " + name + ", age " + age);
        // next()     -> one word
        // nextLine() -> the whole line
        // nextInt()  -> one int (throws InputMismatchException on "abc")
    }
}
