---
layout: default
title: "Week 1 · Day 1 — How Java Runs, Hello World, Data Types"
---

# Week 1 · Day 1 — How Java Runs, Hello World, Data Types

## Prerequisites

1. **Check that the JDK is installed** (not just a JRE):
   ```bash
   java -version     # runtime (JVM)
   javac -version    # compiler — if this fails, you only have a JRE or nothing
   ```
2. If `javac` is missing, install a JDK (17 or 21 LTS), e.g. Eclipse Temurin from adoptium.net, and make sure `JAVA_HOME` is set and `bin` is on your `PATH`.
3. Any editor works; IntelliJ IDEA Community is recommended.
4. Run every sample from the `code/` folder:
   ```bash
   javac FileName.java
   java FileName
   ```

---

## 1. How Java Runs: JDK, JRE, JVM

- You write source code in a `.java` file.
- `javac` (the Java compiler) turns it into **bytecode**, stored in `.class` files.
- The **JVM** (Java Virtual Machine) runs the bytecode. Every OS has its own JVM, which is why Java is "write once, run anywhere".

| Component | Contains | Used for |
|---|---|---|
| JVM | Engine that executes bytecode | Running bytecode |
| JRE | JVM + Java standard class libraries (String, collections, I/O, networking…) | Running existing apps (`.class` / `.jar`) |
| JDK | JRE + `javac`, debugger (`jdb`), `jar`, `javap` and other tools | Building and running apps |

- JDK is a superset of JRE. A JRE alone **cannot compile** your code because it has no `javac`.
- Logging (`java.util.logging`) and error handling (`try/catch`) are part of the language and standard library, not JDK tools, so they work on a JRE too.

> **Note:** Since Java 11, a separate JRE download is generally not offered. You install the JDK.
>
> **Note:** Android does not run bytecode on a standard JVM. It uses a separate runtime called **ART**. (`MainActivity` is an Android naming convention.)

### `.class` vs `.jar`

- `javac` produces **only `.class` files**, one per class. A single `.java` file can produce several `.class` files (e.g. inner classes). `javac` can also compile many files at once.
- A `.jar` is a **zip archive** of many `.class` files plus resources and a `META-INF/MANIFEST.MF`. The manifest mainly declares the entry point (`Main-Class`).
- `.jar` files are created by the `jar` tool or by build tools like **Maven / Gradle**.

> **Note:** Maven is a **build tool**, not a framework. It calls `javac` internally, downloads dependencies, runs tests and packages the jar.

### 🔒 Security Corner

- **Decompilation:** `.class` files can be easily turned back into readable code (e.g. with `javap -c` or decompilers like CFR/JD-GUI). Never hardcode passwords or API keys in code. See `code/Secrets.java`.
- **Vulnerable dependencies:** Maven may pull old, vulnerable library versions. Log4Shell (CVE-2021-44228, Log4j) is the classic example. Always review the dependency tree (`mvn dependency:tree`).

---

## 2. First Program: Hello World

See `code/Hello.java`.

- Every piece of code lives inside a **class**.
- A `public class` must have the **same name as the file**: `public class Hello` → `Hello.java`.
- `public static void main(String[] args)` is the entry point the JVM calls:
  - `public` → the JVM can call it from outside.
  - `static` → runs without creating an object.
  - `void` → returns nothing.
- `System.out.println(...)` prints text.
- Compile with `javac Hello.java`, run with `java Hello` (no `.class` extension).

### Why `String[] args`?

`args` holds **command-line arguments**. Running `java Args Sandeep 25` gives `args[0] = "Sandeep"` and `args[1] = "25"`. Everything from the command line arrives as text, so `"25"` must be converted with `Integer.parseInt`. See `code/Args.java`.

### 🔒 Security Corner

- `args` is **external, untrusted input**. Never trust it blindly.
- `Integer.parseInt("abc")` throws `NumberFormatException`, and the program crashes if it is not handled.
- If args are passed directly into a shell command or file path, they can lead to **command injection** or **path traversal**. (Covered in depth in Week 16.)

---

## 3. Data Types

### Primitive types (8) — hold the value directly

| Type | Size | Holds |
|---|---|---|
| `byte` | 1 byte | −128 to 127 |
| `short` | 2 bytes | about −32,768 to 32,767 |
| `char` | 2 bytes | one character |
| `int` | 4 bytes | about ±2.1 billion |
| `float` | 4 bytes | decimal (≈7 digits) |
| `long` | 8 bytes | about ±9.2 quintillion (time in ms, large IDs) |
| `double` | 8 bytes | decimal (≈15 digits) |
| `boolean` | JVM-dependent | `true` / `false` |

- `short` vs `long`: both hold whole numbers; only size and range differ.
- Primitives **can never be `null`**.

### Reference (non-primitive) types — hold an address

- Examples: `String`, arrays, and your own classes.
- `String` and arrays are **provided by Java**. They are non-primitive because they are **reference types**, not because we create them.
- The actual object lives on the **heap**. The variable stores only its address (like a house address).
- `null` means the variable holds no address. Using it throws `NullPointerException`.

### Value copy vs reference copy

See `code/ReferenceDemo.java`.

- `int b = a;` copies the **value**. Changing `b` does not change `a`.
- `int[] arr2 = arr1;` copies the **address**. Both point to the same array, so `arr2[0] = 99` also changes `arr1[0]`.

### String is immutable

- A `String` is a reference type, but it **cannot be changed** after creation.
- `s2 = s2 + "!"` creates a **new** String; `s2` now points to it, while `s1` still points to the old one.
- The old String is not kept forever. When nothing refers to it, the **Garbage Collector** removes it later. Memory size depends on text length, not a fixed number of bytes.

> **Note:** String "behaves like" a primitive when updated, but it is **not** a primitive.

### 🔒 Security Corner

**1. Integer overflow** — see `code/OverflowDemo.java`
- Going past `int`'s maximum **silently wraps** to a negative number. **No exception is thrown**, which is what makes it dangerous.
- Example: `quantity * price` in a shopping app can become negative with a huge quantity.
- Fix: `Math.multiplyExact` / `Math.addExact` deliberately throw `ArithmeticException` on overflow so you can catch it.

**2. Passwords: `String` vs `char[]`** — see `code/PasswordDemo.java`
- A password in a `String` cannot be wiped, because Strings are immutable. It stays in memory until GC clears it, and a **heap dump** in that window can reveal it.
- A `char[]` is mutable. After use, overwrite it with `Arrays.fill(pwd, '\0')`, so a later dump shows empty characters.
- Java's own APIs (e.g. `JPasswordField.getPassword()`, `Console.readPassword()`) return `char[]` for this reason.

> **Note:** This is defence in depth, not complete protection. An attacker who can already take memory dumps is a bigger problem.
>
> **P.S.:** If you convert the `char[]` into a `String` anywhere (e.g. for logging), a String copy is back in memory and the benefit is lost.

> **Note on buffer overflows:** Classic C-style buffer overflows are practically not possible in pure Java, because the JVM bounds-checks every array access (`ArrayIndexOutOfBoundsException`). Integer overflow, memory leaks and memory exhaustion from huge inputs are real risks.

---

## Quick Revision

- `javac`: `.java` → `.class` (bytecode). The JVM runs it. A JRE can run but not compile.
- `.jar` = zip of classes + resources + manifest, built by `jar`/Maven.
- `main(String[] args)`: args = command-line input = untrusted.
- 8 primitives hold values; reference types hold addresses; only references can be `null`.
- Strings are immutable. Use `char[]` for passwords and wipe them after use.
- Integer overflow is silent. Use `Math.*Exact`.
