import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Day 2 — never hand out your internal mutable data
public class DefensiveCopy {
    static class Account {
        private final List<String> permissions = new ArrayList<>(List.of("READ"));
        private final String owner;

        Account(String owner) {
            if (owner == null || owner.isBlank()) {          // validate inside the public entry point
                throw new IllegalArgumentException("owner is required");
            }
            this.owner = owner;
        }

        // VULNERABLE: returns the real list; callers can modify it
        List<String> getPermissionsUnsafe() { return permissions; }

        // SECURE: return a copy (or an unmodifiable view)
        List<String> getPermissions() { return Collections.unmodifiableList(new ArrayList<>(permissions)); }

        // String is immutable, so returning it directly is safe
        String getOwner() { return owner; }
    }

    public static void main(String[] args) {
        Account acc = new Account("sandeep");

        acc.getPermissionsUnsafe().add("ADMIN");               // attacker-side code
        System.out.println("After unsafe getter: " + acc.getPermissions());   // [READ, ADMIN] !

        try {
            acc.getPermissions().add("SUPERUSER");
        } catch (UnsupportedOperationException e) {
            System.out.println("Safe getter blocked the change");
        }
    }
}
