import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Locale;

// Day 3 — three String pitfalls that become security bugs
public class StringSecurityDemo {
    public static void main(String[] args) {
        // 1. Locale-sensitive case conversion
        Locale turkish = Locale.forLanguageTag("tr");
        String role = "ADMIN";
        System.out.println("tr lower : " + role.toLowerCase(turkish)
                + " -> equals(\"admin\")? " + role.toLowerCase(turkish).equals("admin"));   // false!
        System.out.println("ROOT     : " + role.toLowerCase(Locale.ROOT)
                + " -> equals(\"admin\")? " + role.toLowerCase(Locale.ROOT).equals("admin")); // true
        // Fix: use Locale.ROOT for security checks, or equalsIgnoreCase.

        // 2. Log injection
        String username = "guest\nINFO: User admin logged in successfully";
        System.out.println("--- vulnerable log ---");
        System.out.println("INFO: Failed login for " + username);           // looks like 2 entries
        System.out.println("--- sanitised log ---");
        System.out.println("INFO: Failed login for " + sanitizeForLog(username));

        // 3. Timing attack on secret comparison
        String expectedToken = "s3cr3t-token-123";
        String providedToken = "s3cr3t-token-999";
        // equals() returns as soon as one character differs -> response time leaks how much matched.
        boolean ok = MessageDigest.isEqual(                // constant-time comparison
                providedToken.getBytes(StandardCharsets.UTF_8),
                expectedToken.getBytes(StandardCharsets.UTF_8));
        System.out.println("token valid (constant-time): " + ok);
    }

    static String sanitizeForLog(String s) {
        return s.replace("\r", "\\r").replace("\n", "\\n");
    }
}
