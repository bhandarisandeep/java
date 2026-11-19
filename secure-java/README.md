---
layout: default
permalink: /secure-java/
title: "Secure Java — All Security Corners"
---

# Secure Java — All Security Corners

Every Security Corner from the daily notes, in one place for revision. Each row links back to the day it was covered.

[← Java Roadmap home](../)

## Week 1 — Setup and Basics

| Day | Topic | Issue | Fix |
|---|---|---|---|
| [1](../weeks/week-01-setup-and-basics/day-01/) | Compilation | Secrets in code are readable after decompiling `.class` files | Environment variables or a secrets manager |
| [1](../weeks/week-01-setup-and-basics/day-01/) | Maven | Vulnerable dependency versions (Log4Shell) | Review `mvn dependency:tree`, keep libraries updated |
| [1](../weeks/week-01-setup-and-basics/day-01/) | `String[] args` | Untrusted input: crashes, injection | Validate, handle exceptions |
| [1](../weeks/week-01-setup-and-basics/day-01/) | `int` math | Silent integer overflow | `Math.*Exact`, wider types |
| [1](../weeks/week-01-setup-and-basics/day-01/) | Passwords | A `String` can't be wiped from memory | `char[]` + `Arrays.fill` |
| [2](../weeks/week-01-setup-and-basics/day-02/) | Garbage Collector | Ever-growing static cache → `OutOfMemoryError` → DoS | Bounded caches, eviction |
| [2](../weeks/week-01-setup-and-basics/day-02/) | Strings | `==` compares addresses, gives wrong results | `equals` |
| [2](../weeks/week-01-setup-and-basics/day-02/) | `Scanner` input | Wrong type, out-of-range or oversized input | `hasNextInt`, range and length checks; never concatenate into SQL or commands |
| [2](../weeks/week-01-setup-and-basics/day-02/) | Conditions | `NullPointerException` from wrong order | Null check first (short-circuit) |
| [2](../weeks/week-01-setup-and-basics/day-02/) | Access checks | Default branch grants access | Fail-safe default: deny |
| [2](../weeks/week-01-setup-and-basics/day-02/) | Loops | User-controlled loop count → CPU DoS | Cap the count |
| [2](../weeks/week-01-setup-and-basics/day-02/) | Loops | Off-by-one → `ArrayIndexOutOfBoundsException` | `<` not `<=` with `length` |
| [2](../weeks/week-01-setup-and-basics/day-02/) | Methods | Unvalidated parameters in public methods | Validate inside every public method |
| [2](../weeks/week-01-setup-and-basics/day-02/) | Methods | Returning internal mutable lists lets callers change them | Return copies or unmodifiable views |
| [3](../weeks/week-01-setup-and-basics/day-03/) | Arrays | User-supplied index out of range | Check `0 <= idx < length` |
| [3](../weeks/week-01-setup-and-basics/day-03/) | Arrays | User-supplied size → `OutOfMemoryError` | Upper limit on sizes; `-Xmx` as a wall |
| [3](../weeks/week-01-setup-and-basics/day-03/) | Arrays | `null` elements in object arrays | Check before use |
| [3](../weeks/week-01-setup-and-basics/day-03/) | Strings | Locale-sensitive `toLowerCase` breaks checks (Turkish `I`) | `Locale.ROOT` or `equalsIgnoreCase` |
| [3](../weeks/week-01-setup-and-basics/day-03/) | Logging | Log injection with newline characters | Strip or encode `\r` and `\n` |
| [3](../weeks/week-01-setup-and-basics/day-03/) | Secrets | Timing attack on `equals` | Constant-time `MessageDigest.isEqual` |
| [3](../weeks/week-01-setup-and-basics/day-03/) | Files | Path traversal (`../../etc/passwd`) | Normalise the final path, check it stays inside the base folder |
| [3](../weeks/week-01-setup-and-basics/day-03/) | Shared state | TOCTOU: a checked mutable value changes before use | Immutable values; don't share mutable objects |
| [3](../weeks/week-01-setup-and-basics/day-03/) | `static` / Spring | Static fields and singleton fields leak data between requests | Keep request data in local variables |
| [3](../weeks/week-01-setup-and-basics/day-03/) | Constructors | Unvalidated content in fields | Validate range, length and characters; encode on output |
| [3](../weeks/week-01-setup-and-basics/day-03/) | Concurrency | `count++` loses updates; check-then-act double booking | `AtomicInteger`, locks, DB transactions or constraints |
