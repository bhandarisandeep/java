import java.util.ArrayList;
import java.util.List;

// Day 2 — Garbage Collector and memory leaks.
// Run normally:          java MemoryLeakDemo
// Watch it fail:         java -Xmx64m MemoryLeakDemo 1000
public class MemoryLeakDemo {
    // VULNERABLE: a static "cache" that only ever grows.
    // Everything in it stays reachable, so the GC can never free it.
    private static final List<byte[]> CACHE = new ArrayList<>();

    public static void main(String[] args) {
        int requests = args.length > 0 ? Integer.parseInt(args[0]) : 50;
        try {
            for (int i = 1; i <= requests; i++) {
                handleRequest();                       // each "request" leaks 1 MB
                if (i % 10 == 0) {
                    long usedMb = (Runtime.getRuntime().totalMemory()
                            - Runtime.getRuntime().freeMemory()) / (1024 * 1024);
                    System.out.println("Requests: " + i + ", heap used ~" + usedMb + " MB");
                }
            }
        } catch (OutOfMemoryError e) {
            // In a real server this takes the whole app down: denial of service.
            int leaked = CACHE.size();
            CACHE.clear();   // free memory first, or even printing the message fails
            System.out.println("OutOfMemoryError after " + leaked + " requests");
        }
    }

    static void handleRequest() {
        byte[] data = new byte[1024 * 1024];
        CACHE.add(data);   // never removed
        // FIX: bound the cache (max size / eviction), or don't keep it at all.
        // An object that nothing refers to any more is cleaned up by the GC automatically.
    }
}
