import java.nio.file.Path;
import java.nio.file.Paths;

// Day 3 — path traversal: the check, not the String type, is what protects you
// Try: java PathTraversalDemo report.txt
//      java PathTraversalDemo ../../etc/passwd
public class PathTraversalDemo {
    static final Path BASE = Paths.get("files").toAbsolutePath().normalize();

    public static void main(String[] args) {
        String input = args.length > 0 ? args[0] : "../../etc/passwd";

        // VULNERABLE: only joins strings, ".." walks out of the folder
        String unsafePath = "files/" + input;
        System.out.println("Unsafe path : " + unsafePath);

        // SECURE: resolve the FINAL path, normalise away "..", then check it is still inside BASE
        Path target = BASE.resolve(input).normalize();
        System.out.println("Resolved    : " + target);
        if (target.startsWith(BASE)) {
            System.out.println("ALLOWED     : inside " + BASE);
        } else {
            System.out.println("BLOCKED     : outside " + BASE);
        }
        // Note: if symlinks are possible, also compare target.toRealPath() against BASE.toRealPath().
    }
}
