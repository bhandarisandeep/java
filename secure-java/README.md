# Secure Java — Collected Security Corners

| Week·Day | Topic | Issue | Fix |
|---|---|---|---|
| W1·D1 | Compilation | Secrets readable via decompilation | Env vars / secrets manager |
| W1·D1 | Maven | Vulnerable dependencies (Log4Shell) | Review `mvn dependency:tree`, update |
| W1·D1 | `String[] args` | Untrusted input, crashes, injection | Validate, handle exceptions |
| W1·D1 | `int` math | Silent integer overflow | `Math.*Exact`, wider types |
| W1·D1 | Passwords | String can't be wiped | `char[]` + `Arrays.fill` |
