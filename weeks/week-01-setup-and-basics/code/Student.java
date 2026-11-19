import java.util.concurrent.atomic.AtomicInteger;

// Day 3 — fields, a constructor, static members and validation
public class Student {                       // no () after a class name
    static int count = 0;                    // ONE copy for the whole class, set once at class load
    static final AtomicInteger SAFE_COUNT = new AtomicInteger();   // thread-safe counter
    static final int MAX_AGE = 150;          // static final = constant

    String name;                             // every object gets its own name and age
    int age;

    Student(String name, int age) {          // constructor: class name, NO return type
        if (name == null || name.isBlank() || name.length() > 50) {
            throw new IllegalArgumentException("name must be 1-50 characters");
        }
        if (age < 0 || age > MAX_AGE) {
            throw new IllegalArgumentException("age must be 0-" + MAX_AGE);
        }
        this.name = name;                    // this.name = the field, name = the parameter
        this.age = age;
        count++;                             // NOT "count = 0" here, that would reset it every time
        SAFE_COUNT.incrementAndGet();
    }

    // PITFALL: with "void" this is NOT a constructor, just an ordinary method named Student
    // void Student() { }

    public static void main(String[] args) {
        new Student("Sandeep", 30);
        new Student("Megha", 28);
        System.out.println("Student.count = " + Student.count);   // read via the class name

        try {
            new Student("X", 999);
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected: " + e.getMessage());
        }
        // new Student();  -> compile error: once you write a constructor with parameters,
        //                    Java no longer adds the default no-argument constructor.
    }
}
