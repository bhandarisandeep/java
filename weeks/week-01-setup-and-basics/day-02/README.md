---
layout: default
permalink: /weeks/week-01-setup-and-basics/day-02/
title: "Week 1 · Day 2 — Garbage Collector, Strings, Input, Operators, Loops, Methods"
---

# Week 1 · Day 2 — Garbage Collector, Strings, Input, Operators, Loops, Methods

[← Day 1](../day-01/) · [Week 1 overview](../) · Next: [Day 3 →](../day-03/)

## Code Samples

| File | What it shows |
|---|---|
| [`MemoryLeakDemo.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/MemoryLeakDemo.java) | A static cache that leaks memory until `OutOfMemoryError` |
| [`StringBasics.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/StringBasics.java) | `==` vs `equals`, the string pool, `StringBuilder` in a loop |
| [`ScannerInput.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/ScannerInput.java) | Reading keyboard input and validating it |
| [`Operators.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/Operators.java) | Integer division, short-circuit, fail-safe `switch` |
| [`Loops.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/Loops.java) | All four loops, `break`/`continue`, capped and off-by-one loops |
| [`PassByValueDemo.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/PassByValueDemo.java) | Pass by value with primitives and arrays, overloading |
| [`DefensiveCopy.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/DefensiveCopy.java) | Validating parameters and returning copies of internal data |

Run any of them with `javac FileName.java` and then `java FileName`.

---

## 0. Corrections from the Day 1 Recap

| Said in recap | Correct version |
|---|---|
| JRE is the Java compiler | JRE = JVM + standard libraries. The compiler (`javac`) is only in the JDK. |
| Logging is an extra JDK tool | Logging is part of the standard library. JDK extras are `javac`, `jdb`, `jar`, `javap`. |
| Primitives are immutable; a change creates a new heap object | A primitive variable holds its value directly; assigning a new value overwrites it in place. "New object on change" is a **String** behaviour. |
| Primitives can be null | Only **reference** types can be `null`. Primitives always hold a value. |
| Store passwords in a `char` | Use a `char[]` (char **array**) and wipe it with `Arrays.fill` after use. |
| `boolean` is 2 bytes | Its size is JVM-dependent (usually 1 byte in arrays). |

> **Note:** The phrase is "write once, run anywhere". The 8 primitives are `byte`, `short`, `int`, `long`, `float`, `double`, `char`, `boolean`.

---

## 1. Garbage Collector (GC)

- In languages like C you free memory yourself; forget it and you get a memory leak.
- In Java the **Garbage Collector** does this. It periodically scans the heap and removes objects that **no variable refers to any more**.
- **The JVM decides when GC runs**, not you. That is why an old password `String` may stay in memory for an unknown time.

> **Note:** GC is covered in depth in Week 6 (JVM memory).

### 🔒 Security Corner

- Java can still leak memory. If you keep adding objects to a **static list or cache** and never remove them, they stay reachable, so the GC can never free them.
- The result is `OutOfMemoryError` and the app stops: a **denial of service (DoS)**. An attacker can trigger it by sending many requests that each add to such a cache.
- The risk is availability, not data theft. Fix: bound caches (max size, eviction) or don't keep the data.
- See [`MemoryLeakDemo.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/MemoryLeakDemo.java). Run `java -Xmx64m MemoryLeakDemo 1000` to watch it fail.

---

## 2. Strings

See [`StringBasics.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/StringBasics.java).

### Comparing strings: `==` vs `equals`

- `==` on objects checks whether both variables hold the **same address**.
- `equals` checks whether the **text is the same** (character by character).
- Always compare strings with `equals`. Using `==` compiles and runs without error, it just gives the wrong answer sometimes. That makes it a common, sneaky bug in code reviews.

### The string pool

- String **literals** (text in quotes) are stored once in the **string pool**. Writing `"hello"` twice gives the **same object**, so `==` returns `true`.
- Strings created at runtime (input, concatenation, `new String(...)`) are **new objects**, so `==` returns `false` even for the same text.
- This inconsistency is exactly why `equals` is the rule.

> **Note:** `String` is a **reference type, not a primitive**. `==` on arrays also compares addresses; compare array contents with `Arrays.equals`.

### Useful methods

`length`, `substring`, `toUpperCase`, `toLowerCase`, `trim`, `contains`, `split`, `charAt`.
Because Strings are immutable, **every one of these returns a new String**; the original never changes.

### Building strings in loops

Joining strings with `+` inside a loop creates a new String every time. Use **`StringBuilder`**, which is a **mutable object** that changes in place.

> **Note:** `StringBuilder` is not "like a primitive". It is a mutable object.

---

## 3. Keyboard Input with `Scanner`

See [`ScannerInput.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/ScannerInput.java).

| Method | Reads |
|---|---|
| `next()` | One **word** (not one character) |
| `nextLine()` | The whole line |
| `nextInt()` | One integer |

### 🔒 Security Corner

- Typing `abc` where `nextInt()` expects a number throws `InputMismatchException`. Check with `hasNextInt()` first or handle the exception.
- **Validate every input**: type, **range** (age 0–150) and **length** (name ≤ 50 characters).
- Never paste user input directly into an SQL query or a shell command. That is the root of **injection** bugs (Week 16).

---

## 4. Operators and Conditions

See [`Operators.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/Operators.java).

| Type | Operators | Note |
|---|---|---|
| Arithmetic | `+ - * / %` | `int / int` gives an `int`: `7 / 3 = 2`. `%` is the remainder. |
| Comparison | `== != > < >= <=` | Return `true` / `false` |
| Assignment | `= += -= *= /=` | `x += 5` means `x = x + 5` |

**Logical operators:** `&&` (AND), `||` (OR), `!` (NOT). AND and OR short-circuit (below).

- For a decimal result, make at least one operand decimal: `7.0 / 3` or `(double) 7 / 3`. Prefer `double` over `float`.
- `if / else if / else` picks a path; `switch` is cleaner for many fixed options.

### Short-circuit evaluation

Java **stops evaluating as soon as the result is certain**:

- `&&`: if the first condition is `false`, the second is **not evaluated**.
- `||`: if the first condition is `true`, the second is **not evaluated**.

Practical rule: put the **guard condition first** (for example the null check), so the second condition only runs when it is safe.

```java
if (user != null && user.equals("admin"))   // safe
if (user.equals("admin") && user != null)   // NullPointerException when user is null
```

### 🔒 Security Corner

- Order matters: null check first, then use the object.
- **Fail-safe default:** in any access decision, the final `else` or the `switch` `default` must **deny**. Anything not explicitly allowed is refused. This applies to every if-else chain that grants access, not only to `switch`.

---

## 5. Loops

See [`Loops.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/Loops.java).

| Loop | Use when |
|---|---|
| `for` | You know how many times to run (start; condition; update) |
| `while` | Keep going while a condition is `true` |
| `do-while` | Like `while`, but the body runs **at least once** (condition checked at the end) |
| for-each `for (int x : arr)` | Visit every element of an array or list; no index needed. Same idea as Python's `for x in list`. |

- `break` stops the whole loop. *Example:* searching 100 numbers for a prime and stopping at the 40th when you find one.
- `continue` skips the rest of the current iteration and moves to the next one.

### 🔒 Security Corner

- **User-controlled loop counts:** if a user decides how many times a loop runs, a huge number keeps the CPU busy and the app stops responding. The CPU does not "crash"; it is a **denial of service**. Always cap the count.
- **Off-by-one:** writing `<=` instead of `<` goes one step past the end of an array and throws `ArrayIndexOutOfBoundsException`. Uncaught, that crashes the request.

---

## 6. Methods

A method is a named piece of code you can call again and again, so you don't repeat the same logic.

```java
public static int add(int a, int b) { return a + b; }
```

| Part | Example | Meaning |
|---|---|---|
| Access modifier | `public` | Who can call it |
| Return type | `int` / `void` | What it gives back (`void` = nothing) |
| Name | `add` | How you call it |
| Parameters | `(int a, int b)` | Values passed in |

### Access modifiers

| Modifier | Who can access |
|---|---|
| `public` | Any class |
| `protected` | Same package, plus subclasses |
| *(none)* | Same package only |
| `private` | **Only the same class.** Not even subclasses or the parent class. |

> **Note:** `static` is **not a type and not a return type**. It means the method belongs to the class, so you can call it without creating an object (more on Day 3).

### Overloading vs overriding

- **Overloading:** several methods with the **same name but different parameter lists** (number, type or order). The compiler picks one based on the arguments. Changing **only the return type is not overloading**; it is a compile error.
- **Overriding:** a **subclass rewrites a parent's method** with the same name and same parameters. Covered in Week 2 (OOP).

### Java is always pass by value

See [`PassByValueDemo.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/PassByValueDemo.java).

- **Primitive** argument: the method gets a **copy of the value**. Changing it inside the method does not affect the caller.
- **Object or array** argument: the method gets a **copy of the address**. Changing the **contents** is visible outside, but assigning a **new** object to the parameter is not.
- The copy is made **when the method is called**, not when it returns.

| Language | Behaviour |
|---|---|
| Java | Always pass by value (object addresses are copied) |
| Python | Same idea, often called "pass by object reference" |
| C++ (`&`), C# (`ref`), PHP (`&`) | Offer **true pass by reference**: reassigning the parameter changes the caller's variable |

### 🔒 Security Corner

See [`DefensiveCopy.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/DefensiveCopy.java).

- Every `public` method is a door. **Validate its parameters inside the method**; never assume the caller already checked them.
- Never return your **internal mutable** list or array directly. The caller could modify it (for example, add `"ADMIN"` to a permissions list). Return a **copy** or an unmodifiable view.
- Immutable values like `String` are safe to return directly.

---

## Quick Revision

- GC frees unreachable objects, on the JVM's schedule. Ever-growing static collections leak memory → `OutOfMemoryError` → DoS.
- Compare strings with `equals`, never `==`. Literals are pooled; runtime strings are new objects.
- String methods return new Strings. Use `StringBuilder` for repeated joining.
- `Scanner`: `next` = word, `nextLine` = line, `nextInt` = int. Validate type, range and length.
- `int / int` = `int`. `&&` / `||` short-circuit, so put the null check first. Default to **deny**.
- Four loops; `break` ends, `continue` skips. Cap user-controlled loops; watch for off-by-one.
- Method = access modifier + return type + name + parameters. `private` = same class only.
- Overloading = same name, different parameters. Overriding = subclass rewrites (Week 2).
- Java is always pass by value. Return copies of mutable internals.

---

[← Day 1](../day-01/) · [Week 1 overview](../) · Next: [Day 3 →](../day-03/)
