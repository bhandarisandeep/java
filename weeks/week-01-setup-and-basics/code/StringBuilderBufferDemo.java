// Day 3 — String vs StringBuilder vs StringBuffer, the pool, intern and format
public class StringBuilderBufferDemo {
    public static void main(String[] args) {
        // StringBuilder: mutable, fast, single thread
        StringBuilder sb = new StringBuilder("Java");
        sb.append(" Roadmap");          // changes the same object
        sb.insert(0, ">> ");
        System.out.println(sb + " | length " + sb.length());

        String snapshot = sb.toString(); // a NEW immutable String
        sb.append("!!");                 // the builder is still mutable
        System.out.println("snapshot: " + snapshot + " | builder: " + sb);
        System.out.println("reversed: " + new StringBuilder("level up").reverse());

        // Building a CSV in a loop is the classic StringBuilder use case
        StringBuilder csv = new StringBuilder("day,topic\n");
        String[] topics = {"basics", "methods", "arrays"};
        for (int i = 0; i < topics.length; i++) {
            csv.append(i + 1).append(',').append(topics[i]).append('\n');
        }
        System.out.print(csv);

        // StringBuffer: same API, but synchronized (safe when shared by threads, slower)
        StringBuffer log = new StringBuffer();
        log.append("thread-safe ").append("text");
        System.out.println(log);

        // Literal vs new vs intern
        String a = "Sandeep";              // a is a reference variable; the object sits in the pool
        String b = new String("Sandeep");  // always a new heap object
        String c = b.intern();             // the pooled version of the same text
        System.out.println("a == b: " + (a == b) + ", a == c: " + (a == c) + ", a.equals(b): " + a.equals(b));

        // Formatting
        System.out.println(String.format("%s is learning Java, week %d (%.1f%% done)", "Sandeep", 1, 5.5));
    }
}
