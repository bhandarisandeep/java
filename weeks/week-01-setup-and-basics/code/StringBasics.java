// Day 2 — == vs equals, the string pool, and StringBuilder
public class StringBasics {
    public static void main(String[] args) {
        String a = "hello";                 // literal -> string pool
        String b = "hello";                 // same literal -> SAME pooled object
        String c = new String("hello");     // new -> always a NEW object on the heap
        String d = "hel" + args.length + "lo"; // built at runtime -> new object

        System.out.println("a == b      : " + (a == b));      // true  (same address)
        System.out.println("a == c      : " + (a == c));      // false (different address)
        System.out.println("a.equals(c) : " + a.equals(c));   // true  (same text)
        System.out.println("d           : " + d);

        // String methods never change the original; they return a NEW String
        String name = "  Sandeep  ";
        String trimmed = name.trim();
        System.out.println("[" + name + "] -> [" + trimmed + "]");
        System.out.println(trimmed.toUpperCase() + " " + trimmed.length() + " "
                + trimmed.substring(0, 3) + " " + trimmed.contains("deep"));

        // Joining many times in a loop: StringBuilder changes one object in place
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 5; i++) {
            sb.append(i).append(i < 5 ? "," : "");
        }
        System.out.println("Built: " + sb);   // 1,2,3,4,5
    }
}
