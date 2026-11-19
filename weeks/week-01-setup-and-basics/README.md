---
layout: default
permalink: /weeks/week-01-setup-and-basics/
title: "Week 1 — Setup and Basics"
---

# Week 1 — Setup and Basics

How Java runs, data types, strings, input, operators, loops, methods, arrays, `static` and a first look at constructors, each with a Security Corner.

## Days

| Day | Topics |
|---|---|
| [Day 1](day-01/) | JDK, JRE and JVM; `.class` vs `.jar`; Hello World; `String[] args`; primitive vs reference types; String immutability; integer overflow; passwords in `char[]` |
| [Day 2](day-02/) | Garbage Collector and memory leaks; `==` vs `equals`; the string pool; `Scanner` input; operators and short-circuit; fail-safe defaults; loops; methods, access modifiers, overloading and pass by value |
| [Day 3](day-03/) | Arrays and their size limit; `-Xms` / `-Xmx`; `String` vs `StringBuilder` vs `StringBuffer`; locale, log injection and timing attacks; path traversal and TOCTOU; `static`; constructors; race conditions |

## Prerequisites

Check that the JDK (not just a JRE) is installed:

```bash
java -version
javac -version
```

If `javac` is missing, install a JDK (17 or 21 LTS), for example Eclipse Temurin from adoptium.net. Full setup steps are in [Day 1](day-01/).

## Code Samples

All samples are in the [`code/`](https://github.com/bhandarisandeep/java/tree/main/weeks/week-01-setup-and-basics/code) folder. Run any of them with:

```bash
javac FileName.java
java FileName
```

| Day | File | What it shows |
|---|---|---|
| 1 | [`Hello.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/Hello.java) | The smallest Java program |
| 1 | [`Args.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/Args.java) | Command-line arguments and handling bad input |
| 1 | [`DataTypes.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/DataTypes.java) | All 8 primitives, their limits, and `null` |
| 1 | [`ReferenceDemo.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/ReferenceDemo.java) | Value copy vs reference copy, String immutability |
| 1 | [`OverflowDemo.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/OverflowDemo.java) | Silent integer overflow and `Math.multiplyExact` |
| 1 | [`PasswordDemo.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/PasswordDemo.java) | `String` vs `char[]` for passwords |
| 1 | [`Secrets.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/Secrets.java) | Hardcoded secrets vs environment variables |
| 2 | [`MemoryLeakDemo.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/MemoryLeakDemo.java) | A static cache that leaks memory until `OutOfMemoryError` |
| 2 | [`StringBasics.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/StringBasics.java) | `==` vs `equals`, the string pool, `StringBuilder` in a loop |
| 2 | [`ScannerInput.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/ScannerInput.java) | Reading keyboard input and validating it |
| 2 | [`Operators.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/Operators.java) | Integer division, short-circuit, fail-safe `switch` |
| 2 | [`Loops.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/Loops.java) | All four loops, `break`/`continue`, capped and off-by-one loops |
| 2 | [`PassByValueDemo.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/PassByValueDemo.java) | Pass by value with primitives and arrays, overloading |
| 2 | [`DefensiveCopy.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/DefensiveCopy.java) | Validating parameters and returning copies of internal data |
| 3 | [`ArraysDemo.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/ArraysDemo.java) | Creating arrays, defaults, `copyOf`, 2-D arrays, validation, the size limit |
| 3 | [`StringBuilderBufferDemo.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/StringBuilderBufferDemo.java) | `StringBuilder` vs `StringBuffer`, literal vs `new` vs `intern`, `String.format` |
| 3 | [`StringSecurityDemo.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/StringSecurityDemo.java) | Locale bug, log injection, constant-time comparison |
| 3 | [`PathTraversalDemo.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/PathTraversalDemo.java) | `../../etc/passwd` and the normalise-then-check fix |
| 3 | [`ToctouDemo.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/ToctouDemo.java) | A checked path changed before use: `StringBuilder` vs `String` |
| 3 | [`Student.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/Student.java) | Fields, a constructor, `static` counter, `static final` constant, validation |
| 3 | [`RaceConditionDemo.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/RaceConditionDemo.java) | Lost `count++` updates, `AtomicInteger`, seat double booking |

## Security Corners this week

Every day ends its topics with a Security Corner. They are all collected in [Secure Java](https://bhandarisandeep.com/java/secure-java/).
