---
layout: default
permalink: /weeks/week-01-setup-and-basics/day-03/
title: "Week 1 · Day 3 — Arrays, Heap Size, StringBuilder, static and Constructors"
---

# Week 1 · Day 3 — Arrays, Heap Size, StringBuilder, static and Constructors

[← Day 2](../day-02/) · [Week 1 overview](../)

## Code Samples

| File | What it shows |
|---|---|
| [`ArraysDemo.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/ArraysDemo.java) | Creating arrays, defaults, `copyOf`, 2-D arrays, index and size validation, the size limit |
| [`StringBuilderBufferDemo.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/StringBuilderBufferDemo.java) | `StringBuilder` vs `StringBuffer`, `toString`, literal vs `new` vs `intern`, `String.format` |
| [`StringSecurityDemo.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/StringSecurityDemo.java) | Locale bug, log injection, constant-time comparison |
| [`PathTraversalDemo.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/PathTraversalDemo.java) | `../../etc/passwd` and the normalise-then-check fix |
| [`ToctouDemo.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/ToctouDemo.java) | A checked path changed before use: `StringBuilder` vs `String` |
| [`Student.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/Student.java) | Fields, a constructor, `static` counter, `static final` constant, validation |
| [`RaceConditionDemo.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/RaceConditionDemo.java) | Lost `count++` updates, `AtomicInteger`, seat double booking |

---

## 0. Corrections from the Methods Recap

- Java is **not the only** pass-by-value language; C, Python and JavaScript also pass values. What is special about Java is that it has **no** pass-by-reference option at all.
- Passing an **object or array** sends a **copy of its address**, so changes to its contents are visible outside. The copy is made when the method is called, not when an operation runs inside it.
- The "return a copy" rule is for **mutable** things (arrays, lists). Immutable values such as `String` can be returned directly.
- Overloading is **several methods** sharing one name with different parameter lists, not one method that accepts many types. The compiler picks which one runs.

---

## 1. Arrays

See [`ArraysDemo.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/ArraysDemo.java).

An array is a **fixed-size** container of values of **one type**.

```java
int[] scores = {90, 75, 60, 85, 70};   // with values
int[] empty  = new int[3];              // with a size
```

- **The size never changes.** `Arrays.copyOf(arr, newSize)` does not grow an array; it creates a **new, bigger array** and copies the values. For a growable collection use `ArrayList` (Week 3).
- **Indexes start at 0.** For size 5 the indexes are 0–4; for size 10 they are 0–9. The last index is always `length - 1`.
- **Length:** `arr.length`, with **no brackets** (unlike `String.length()`).
- **Default values** when created with a size: `0` for numbers, `false` for `boolean`, `null` for `String` and other objects.
- **Multi-dimensional arrays** are arrays of arrays, e.g. a table: `int[][] table = {{1,2,3},{4,5,6}}`.

> **Note:** An array is not a `List`. In Java, `List` is a separate interface covered in Week 3.

### How big can an array be?

1. **Index limit:** indexes are `int`, so the maximum is about **2.1 billion** elements. The JVM allows slightly fewer, because every array also stores some internal data (a header). Asking for more gives `OutOfMemoryError: Requested array size exceeds VM limit`.
2. **Heap limit (the practical one):** 2 billion `int`s need about **8 GB**. If the JVM's heap doesn't have that much free space, you get `OutOfMemoryError`.

### Heap size: `-Xms` and `-Xmx`

These options are given when starting the JVM:

| Option | Meaning | Mnemonic |
|---|---|---|
| `-Xms` | Initial (starting) heap size | X = non-standard JVM option, m = memory, **s = start** |
| `-Xmx` | Maximum heap size | **x = max** |

```bash
java -Xms512m -Xmx4g Hello    # start with 512 MB, allow up to 4 GB
```

- The suffix sets the unit: `m` = megabytes, `g` = gigabytes.
- In production, servers often set both to the **same value** to avoid resizing the heap at runtime.
- `-Xmx` is also a **safety wall**: a huge input can only exhaust the app's own limit, not the whole server's memory.

### 🔒 Security Corner

- **User-supplied index:** check `0 <= idx < arr.length` before using it, or you get `ArrayIndexOutOfBoundsException`.
- **User-supplied size:** always enforce an upper limit, or a huge number causes `OutOfMemoryError` (DoS).
- **Object arrays start full of `null`:** check before use, or you get `NullPointerException`.

---

## 2. String, StringBuilder and StringBuffer

See [`StringBuilderBufferDemo.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/StringBuilderBufferDemo.java).

These are **three separate classes** that hold text. They are not methods or "types" of String.

| Class | Mutable? | Thread-safe? | Use it for |
|---|---|---|---|
| `String` | No (immutable) | Yes, because it can't change | Most text that doesn't change: names, messages, keys, paths |
| `StringBuilder` | Yes | No | Joining text repeatedly in one thread, e.g. building a report or CSV in a loop. The most common choice. |
| `StringBuffer` | Yes | Yes (synchronized, so slower) | Text shared and changed by several threads at once. Rare in modern code. |

- `StringBuilder` has its own methods: `append`, `insert`, `delete`, `reverse`, `length`. Call them on the builder object (`sb.append(...)`). `concat` belongs to `String`, not to `StringBuilder`.
- `toString()` returns a **new, immutable String** with the current text. The builder itself **stays mutable** and can keep changing.
- Changing a `String` always creates a **new String object**; a `StringBuilder` changes the **same object**.

> **Note on examples:** dates belong in `LocalDate`, not in text. Numeric counters (like booked seats) belong in `AtomicInteger`, not in `StringBuffer`.

### Reference variable vs object

`String a = "Sandeep";` — `a` is a **reference variable** that holds the address of the String object. The object itself sits in the string pool. Saying "a is a String" is fine in conversation, but in a code review the difference matters.

### Creating String objects

| Way | Where the object lives | `==` with the literal |
|---|---|---|
| Literal `"Sandeep"` | String pool; the same literal reuses the same object | `true` |
| `new String("Sandeep")` | Always a brand-new heap object | `false` |
| `b.intern()` | Returns the pooled object with the same text | `true` |

- `==` compares **references** (true only if both point to the same object). `equals` compares the **characters**.
- `String.format("%s is in week %d", name, 1)` builds formatted text.

### Why immutability makes sharing safe

Because a String can never change, you can pass the same String to many methods or threads without making copies. That is why Java takes file paths, URLs, class names and similar values as Strings. Hand a `StringBuilder` to a method instead, and that method can change it under you.

### 🔒 Security Corner

See [`StringSecurityDemo.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/StringSecurityDemo.java).

1. **Locale-sensitive case conversion.** `toLowerCase()` uses the system locale. In Turkish, capital `I` lowercases to a dotless `ı`, so `"ADMIN".toLowerCase()` becomes `"admın"` and a check against `"admin"` silently fails. For security checks use `toLowerCase(Locale.ROOT)` or `equalsIgnoreCase`.
2. **Log injection.** If user input with a newline is written straight into a log, an attacker can forge fake log entries such as `INFO: User admin logged in`. Encode or strip `\r` and `\n` before logging.
3. **Timing attacks.** `equals` stops at the first different character, so how long the comparison takes reveals how much of a secret matched. Compare passwords, tokens and MACs with a **constant-time** method such as `MessageDigest.isEqual`.

---

## 3. Path Traversal and TOCTOU

### Path traversal

See [`PathTraversalDemo.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/PathTraversalDemo.java).

An app shows files only from a `files` folder and builds the path as `"files/" + userInput`.

- **Attack:** the user sends `../../etc/passwd`. The path becomes `files/../../etc/passwd`. Each `..` goes up one folder, so the app opens the system password file.
- **Defence:** resolve the **final, joined path**, normalise it (remove `..` and `.`) to get the canonical path, then **check it is still inside the base folder**. If not, refuse.

```java
Path base = Paths.get("files").toAbsolutePath().normalize();
Path target = base.resolve(userInput).normalize();
if (!target.startsWith(base)) throw new SecurityException("outside base folder");
```

> **Note:** Checking only the raw user input is a common mistake. Check the **final joined path**, because that is what gets opened.
>
> **P.S.:** If symbolic links are possible, compare `toRealPath()` values too.

> **Key point:** Immutability does **not** stop path traversal. Whether you use `String`, `StringBuilder` or `StringBuffer`, a malicious path stored faithfully is still malicious. **The check is what stops the attack.**

### TOCTOU: Time Of Check vs Time Of Use

See [`ToctouDemo.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/ToctouDemo.java).

Immutability matters **after** the check:

1. A path sits in a `StringBuilder` that two threads share.
2. Thread A checks it: `files/report.txt`. The check passes.
3. Before A opens the file, thread B changes the **same object** to `/etc/passwd`.
4. Thread A opens the file using the object, which now says `/etc/passwd`.

The check happened on `report.txt`, but the password file was opened. With a `String`, step 3 is impossible: B can only create a **new** String, and A still holds the one it checked.

- Thread B is usually **not attacker code** running on the server. It is your own app handling **another request from the attacker**, because the app wrongly shares a mutable object between requests.
- Attackers send **many concurrent requests** so that one of them hits the right timing.
- **Review tip:** look for mutable objects shared across threads or requests, **especially in `static` fields**.

---

## 4. `static`

- `static` means the member **belongs to the class, not to any one object**. There is **one copy** for the whole app.
- A `static` method can be called **without creating an object**, which is why `main` is `static`.
- `static` does **not** mean "cannot change". Anyone can change a static field, and every object sees the change. "Cannot change" is `final`.
- A constant is `static final`, e.g. `Math.PI`.

### Example: counting students

See [`Student.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/Student.java).

```java
public class Student {              // no () after a class name
    static int count = 0;           // one shared copy, initialised ONCE when the class loads
    String name;                    // each object has its own name and age
    int age;

    Student(String name, int age) { // constructor
        this.name = name;           // this.name = the field, name = the parameter
        this.age = age;
        count++;                    // grows with every new Student
    }
}
// new Student("Sandeep", 30); ... read it as Student.count
```

- `count` doesn't grow by itself; the constructor must do `count++`.
- Declare `count` **inside the class but outside** the constructor and methods. Java has no variables outside a class.
- Writing `count = 0` **inside the constructor** would reset the count with every new student.

### Why static fields are risky in web apps

- In a web app each request usually runs in **its own thread**. Local variables inside a method belong to that request, so they are safe.
- A `static` field is **one copy that every thread sees**, so data one user's request writes can leak into another user's request.
- **Spring preview:** controllers and services are usually **singletons** (one object for the whole app), so even their normal, non-static fields are shared across all requests. A controller field that stores a user-specific value is a **big red flag** in code review.

---

## 5. Constructors (preview of Week 2)

- A constructor has the **same name as the class** and **no return type, not even `void`**. It runs automatically when you write `new Student(...)`. You don't "call the class".
- Parameters go in round brackets after the name: `Student(String name, int age) { ... }`. The body goes in curly braces.
- `name` and `age` must also be declared as **fields** in the class, outside the constructor.

**Pitfall:** `void Student() { }` compiles, but it is an ordinary method named `Student`, not a constructor, and it won't run on `new`. In a code review this is almost always a bug.

**Default constructor:**

- If you write **no** constructor, the compiler adds an empty, no-argument one, so `new Student()` works and fields get default values (`0`, `null`).
- If you write **any** constructor with parameters, the compiler does **not** add the default one; `new Student()` then fails to compile unless you write it yourself.

### 🔒 Security Corner

- **The compiler already checks types.** Nobody can pass text where an `int` age is expected. The real job is validating the **content**: age within 0–150, name length limited, only allowed characters.
- Whether `name` can lead to **injection** depends on where it is used later (an SQL query, HTML, a log line). Validate on input and encode on output.
- **`count++` is not thread-safe.** It is three steps: read, add, write. If two threads create students at the same time, one update can be lost. Use `AtomicInteger`.

---

## 6. Race Conditions

See [`RaceConditionDemo.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/RaceConditionDemo.java).

- A thread-safe object (like `StringBuffer`) only makes **each single operation** safe.
- "Is seat 77 free?" and then "book seat 77" are **two separate steps** (check-then-act). If two users check at the same moment, both see the seat free and both book it: a **double booking**.
- Real systems prevent this with **database locks, transactions or unique constraints**, so the check and the booking happen as one unit.

> **Note:** Concurrency is covered in depth in Week 6.

---

## Quick Revision

- Arrays: fixed size, index `0` to `length - 1`, `arr.length` without brackets, defaults `0` / `false` / `null`. `Arrays.copyOf` makes a new array.
- Max array ≈ 2.1 billion elements (slightly less); the heap is the real limit. `-Xms` = start heap, `-Xmx` = max heap.
- Validate user-supplied indexes and sizes; check object arrays for `null`.
- `String` immutable; `StringBuilder` mutable and fast; `StringBuffer` mutable and synchronized. `toString()` returns a new immutable String.
- A variable holds a reference, not the object. Literals are pooled; `new` always creates a new object; `==` compares references, `equals` compares text.
- Use `Locale.ROOT` for security checks, strip newlines before logging, compare secrets in constant time.
- Path traversal is stopped by checking the **final normalised path**, not by the String type. Immutability prevents **TOCTOU** changes after the check.
- `static` = one copy per class, not "constant" (`final` is). Static fields and Spring singleton fields are shared across requests.
- Constructor = class name, no return type. Default constructor exists only if you write none.
- `count++` and check-then-act are race conditions: use `AtomicInteger`, locks or transactions.

---

[← Day 2](../day-02/) · [Week 1 overview](../)
