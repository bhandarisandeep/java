import java.util.concurrent.CountDownLatch;

// Day 3 — Time Of Check vs Time Of Use (TOCTOU), and why immutability helps
// The latches only force the "unlucky timing" that attackers try to hit with many parallel requests.
public class ToctouDemo {
    // A mutable object wrongly shared between requests (e.g. a static field)
    static final StringBuilder SHARED_PATH = new StringBuilder("files/report.txt");
    static volatile String sharedString = "files/report.txt";

    public static void main(String[] args) throws Exception {
        System.out.println("--- StringBuilder (mutable) ---");
        run(true);
        System.out.println("--- String (immutable) ---");
        run(false);
    }

    static void run(boolean mutable) throws Exception {
        CountDownLatch checked = new CountDownLatch(1);
        CountDownLatch changed = new CountDownLatch(1);

        Thread a = new Thread(() -> {
            try {
                String held = sharedString;                          // String: A keeps its own reference
                String checkedValue = mutable ? SHARED_PATH.toString() : held;
                boolean ok = checkedValue.startsWith("files/") && !checkedValue.contains("..");
                System.out.println("A checks " + checkedValue + " -> " + (ok ? "OK" : "DENIED"));
                checked.countDown();
                changed.await();                                    // the race window
                String used = mutable ? SHARED_PATH.toString() : held;
                System.out.println("A opens  " + used);
            } catch (InterruptedException ignored) { }
        });

        Thread b = new Thread(() -> {
            try {
                checked.await();
                if (mutable) {
                    SHARED_PATH.setLength(0);
                    SHARED_PATH.append("/etc/passwd");               // changes the SAME object
                } else {
                    sharedString = "/etc/passwd";                     // can only make a NEW String
                }
                changed.countDown();
            } catch (InterruptedException ignored) { }
        });

        a.start(); b.start(); a.join(); b.join();
        SHARED_PATH.setLength(0); SHARED_PATH.append("files/report.txt");
        sharedString = "files/report.txt";
    }
}
