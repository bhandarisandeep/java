// Run: java Args Sandeep 25
public class Args {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java Args <name> <age>");
            return;
        }
        String name = args[0];
        try {
            int age = Integer.parseInt(args[1]);   // "25" (text) -> 25 (number)
            System.out.println(name + " is " + age + " years old");
        } catch (NumberFormatException e) {
            // Untrusted input: "abc" would crash the program without this
            System.out.println("Age must be a number, got: " + args[1]);
        }
    }
}
