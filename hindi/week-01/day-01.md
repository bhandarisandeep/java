# Week 1 · Day 1 — Java कैसे चलती है, Hello World, Data Types (हिंदी)

[← हिंदी notes का index](../README.md) · अगला: [Day 2 →](day-02.md) · [English version](../../weeks/week-01-setup-and-basics/day-01/README.md)

Code: [`code/` folder](https://github.com/bhandarisandeep/java/tree/main/weeks/week-01-setup-and-basics/code)

## Prerequisites

1. चेक कीजिए कि JDK install है, सिर्फ़ JRE नहीं:
   ```bash
   java -version     # runtime (JVM)
   javac -version    # compiler — ये न चले तो JDK नहीं है
   ```
2. `javac` न मिले तो JDK 17 या 21 (LTS) install कीजिए, जैसे adoptium.net से Eclipse Temurin। `JAVA_HOME` सेट कीजिए और उसका `bin` folder `PATH` में डालिए।
3. Editor कोई भी चलेगा, IntelliJ IDEA Community सबसे आसान है।
4. हर sample ऐसे चलाइए: `javac FileName.java` फिर `java FileName`।

---

## 1. Java कैसे चलती है: JDK, JRE, JVM

- आप code `.java` फ़ाइल में लिखते हैं।
- `javac` (Java compiler) उसे सीधे machine code में नहीं, बल्कि **bytecode** में बदलता है, जो `.class` फ़ाइल में जाता है।
- उस bytecode को **JVM** (Java Virtual Machine) चलाता है। हर operating system का अपना JVM होता है, इसीलिए Java "write once, run anywhere" है।

| हिस्सा | उसमें क्या है | किस काम का |
|---|---|---|
| JVM | Bytecode चलाने वाला engine | Bytecode चलाना |
| JRE | JVM + Java की standard libraries (String, collections, files, networking…) | बनी-बनाई app (`.class` / `.jar`) चलाना |
| JDK | JRE + `javac`, debugger (`jdb`), `jar`, `javap` जैसे tools | App बनाना और चलाना |

- JDK, JRE का superset है। सिर्फ़ JRE हो तो code **compile नहीं** होगा, क्योंकि उसमें `javac` नहीं है।
- Logging (`java.util.logging`) और error handling (`try/catch`) JDK के tools नहीं हैं, ये language और standard library का हिस्सा हैं, इसलिए JRE पर भी चलते हैं।
- Technically compiler का इंतज़ाम (जैसे Eclipse का ECJ) कर लें तो JRE पर भी app बन सकती है, पर practically सब JDK ही install करते हैं।

> **Note:** Java 11 के बाद अलग JRE download आम तौर पर मिलता ही नहीं, सीधे JDK install करते हैं।
>
> **Note:** Android में bytecode JVM पर नहीं, **ART** नाम के अलग runtime पर चलता है। `MainActivity` Android का नाम है।

### `.class` बनाम `.jar`

- `javac` सिर्फ़ `.class` फ़ाइलें बनाता है, हर class की एक। एक `.java` फ़ाइल से कई `.class` भी बन सकती हैं (जैसे inner classes), और `javac` एक साथ कई फ़ाइलें compile कर सकता है।
- `.jar` बस एक **zip फ़ाइल** है, जिसमें बहुत सारी `.class` फ़ाइलें, resources और `META-INF/MANIFEST.MF` होती है। Manifest मुख्य रूप से बताती है कि program किस class से शुरू होगा (`Main-Class`)।
- `.jar` को `jar` tool या Maven / Gradle बनाते हैं, `javac` नहीं।

> **Note:** Maven framework नहीं, **build tool** है। अंदर से `javac` चलाता है, dependencies download करता है, tests चलाता है और jar बनाता है।

### 🔒 Security Corner

- **Decompile:** `.class` फ़ाइल को आसानी से वापस पढ़ने लायक़ code में बदला जा सकता है (`javap -c`, CFR, JD-GUI)। Code में password या API key कभी मत लिखिए। देखें [`Secrets.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/Secrets.java)।
- **Vulnerable dependencies:** Maven जो libraries लाता है, उनके पुराने, कमज़ोर versions आ जाना आम है। Log4Shell (Log4j) सबसे बड़ा उदाहरण है। `mvn dependency:tree` से जाँचते रहिए।

---

## 2. पहला Program: Hello World

देखें [`Hello.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/Hello.java)।

- Java में हर code किसी class के अंदर होता है।
- `public class` का नाम फ़ाइल के नाम से मिलना चाहिए: `public class Hello` → `Hello.java`। (class के आगे `static` नहीं लगता।)
- `public static void main(String[] args)` से JVM program शुरू करता है:
  - `public` → JVM बाहर से call कर सके।
  - `static` → object बनाए बिना चल जाए।
  - `void` → कुछ return नहीं करता।
- `System.out.println(...)` screen पर text छापता है।
- `javac Hello.java` से compile, `java Hello` से चलाइए (`.class` नहीं लिखते)।

### `String[] args` क्यों?

ये **command line arguments** के लिए है। `java Args Sandeep 25` चलाने पर `args[0] = "Sandeep"` और `args[1] = "25"`। Command line से सब कुछ text बनकर आता है, इसलिए `"25"` को `Integer.parseInt` से number बनाना पड़ता है। देखें [`Args.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/Args.java)।

### 🔒 Security Corner

- `args` बाहर से आने वाला, **untrusted input** है।
- `Integer.parseInt("abc")` से `NumberFormatException` आता है, संभाला नहीं तो program crash।
- Args को सीधे shell command या file path में डाला, तो **command injection** या **path traversal** हो सकता है (Week 16)।

---

## 3. Data Types

देखें [`DataTypes.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/DataTypes.java)।

### Primitive types (8) — value सीधे रखते हैं

| Type | Size | क्या रखता है |
|---|---|---|
| `byte` | 1 byte | −128 से 127 |
| `short` | 2 bytes | लगभग −32 हज़ार से +32 हज़ार |
| `char` | 2 bytes | एक अक्षर |
| `int` | 4 bytes | लगभग ±2.1 billion |
| `float` | 4 bytes | decimal (≈7 अंक) |
| `long` | 8 bytes | लगभग ±9 quintillion (ms में time, बड़े IDs) |
| `double` | 8 bytes | decimal (≈15 अंक) |
| `boolean` | JVM पर निर्भर | `true` / `false` |

- `short` और `long` दोनों पूरे numbers रखते हैं, फ़र्क़ सिर्फ़ size और range का है।
- Primitive कभी `null` **नहीं** हो सकते।

### Reference (non-primitive) types — पता रखते हैं

- जैसे `String`, arrays और आपकी अपनी classes।
- `String` और arrays Java **ख़ुद देती है**। ये non-primitive इसलिए हैं क्योंकि ये **reference types** हैं, इसलिए नहीं कि हम इन्हें बनाते हैं।
- असली object **heap** में रहता है, variable में बस उसका पता (जैसे घर का address)।
- `null` का मतलब: variable में कोई पता नहीं। उसे इस्तेमाल किया तो `NullPointerException`।

### Value copy बनाम reference copy

देखें [`ReferenceDemo.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/ReferenceDemo.java)।

- `int b = a;` → **value** की copy। `b` बदलने से `a` नहीं बदलता।
- `int[] arr2 = arr1;` → सिर्फ़ **पते** की copy। दोनों एक ही array की तरफ़ इशारा करते हैं, तो `arr2[0] = 99` करने पर `arr1[0]` भी बदल जाता है।

### String immutable है

- String reference type है, लेकिन बनने के बाद **बदली नहीं जा सकती**।
- `s2 = s2 + "!"` एक **नई** String बनाता है, `s2` उसकी तरफ़ इशारा करने लगता है, `s1` पुरानी पर ही रहता है।
- पुरानी String हमेशा नहीं रहती। कोई variable उसकी तरफ़ इशारा न करे, तो **Garbage Collector** बाद में उसे हटा देता है। Size text की लंबाई पर निर्भर है, कोई fixed bytes नहीं।

> **Note:** बदलने पर String primitive जैसी "दिखती" है, पर primitive **नहीं** है।

### 🔒 Security Corner

**1. Integer overflow** — देखें [`OverflowDemo.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/OverflowDemo.java)
- `int` की हद पार होते ही value **चुपचाप घूम जाती है**, अक्सर negative हो जाती है (`30,000,000 × 100` बन जाता है `-1,294,967,296`)। **कोई exception नहीं आता**, यही ख़तरा है।
- जैसे shopping app में `quantity * price` बहुत बड़ी quantity पर negative total दे सकता है।
- बचाव: `Math.multiplyExact` / `Math.addExact` overflow पर जानबूझकर `ArithmeticException` देते हैं, ताकि गड़बड़ी पकड़ में आए।

**2. Passwords: `String` बनाम `char[]`** — देखें [`PasswordDemo.java`](https://github.com/bhandarisandeep/java/blob/main/weeks/week-01-setup-and-basics/code/PasswordDemo.java)
- String में रखा password मिटाया नहीं जा सकता। GC के हटाने तक memory में रहता है, और उस बीच **heap dump** में साफ़ पढ़ा जा सकता है।
- `char[]` बदला जा सकता है। काम होते ही `Arrays.fill(pwd, '\0')` से मिटा दीजिए।
- इसी वजह से `JPasswordField.getPassword()` और `Console.readPassword()` भी `char[]` देते हैं।

> **Note:** ये बचाव की एक अतिरिक्त परत है, पूरा इलाज नहीं। जिसके पास memory dump लेने की पहुँच है, वो उससे बड़ी समस्या है।
>
> **P.S.:** बीच में कहीं `char[]` से String बना ली (जैसे log के लिए), तो String वाली copy फिर memory में आ जाएगी और फ़ायदा ख़त्म।

> **Buffer overflow पर Note:** Pure Java में C वाला buffer overflow लगभग नहीं होता, क्योंकि JVM हर array access की सीमा चेक करता है (`ArrayIndexOutOfBoundsException`)। Integer overflow, memory leak और बड़े input से memory भरना असली ख़तरे हैं।

---

## Quick Revision

- `javac`: `.java` → `.class` (bytecode)। JVM उसे चलाता है। JRE चला सकता है, compile नहीं।
- `.jar` = classes + resources + manifest का zip, `jar`/Maven बनाते हैं।
- `main(String[] args)`: args = command line input = untrusted।
- 8 primitives value रखते हैं, reference types पता। सिर्फ़ reference `null` हो सकते हैं।
- String immutable। Password के लिए `char[]`, और इस्तेमाल के बाद मिटाइए।
- Integer overflow चुपचाप होता है। `Math.*Exact` इस्तेमाल कीजिए।
