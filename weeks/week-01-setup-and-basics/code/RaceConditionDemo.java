import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

// Day 3 — count++ is not one step, so threads lose updates
public class RaceConditionDemo {
    static int unsafeCount = 0;
    static final AtomicInteger safeCount = new AtomicInteger();

    // Seat booking: "is it free?" then "book it" = check-then-act
    static boolean seat77Booked = false;
    static int bookings = 0;

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int t = 0; t < 8; t++) {
            Thread th = new Thread(() -> {
                for (int i = 0; i < 100_000; i++) {
                    unsafeCount++;                  // read, add, write: 3 steps
                    safeCount.incrementAndGet();    // one atomic step
                }
            });
            threads.add(th); th.start();
        }
        for (Thread th : threads) th.join();
        System.out.println("expected      : 800000");
        System.out.println("unsafeCount++ : " + unsafeCount + (unsafeCount < 800_000 ? "  <- updates lost" : ""));
        System.out.println("AtomicInteger : " + safeCount.get());

        // Double booking: two users check at the same moment
        Thread u1 = new Thread(RaceConditionDemo::bookSeat77);
        Thread u2 = new Thread(RaceConditionDemo::bookSeat77);
        u1.start(); u2.start(); u1.join(); u2.join();
        System.out.println("Seat 77 bookings: " + bookings + (bookings > 1 ? "  <- double booking!" : "  (no race this run)"));
        // Real fix: make check+book one atomic unit (synchronized, DB lock/transaction, unique constraint).
    }

    static void bookSeat77() {
        if (!seat77Booked) {                        // CHECK
            try { Thread.sleep(50); } catch (InterruptedException ignored) { }  // widen the race window
            seat77Booked = true;                    // ACT
            synchronized (RaceConditionDemo.class) { bookings++; }
        }
    }
}
