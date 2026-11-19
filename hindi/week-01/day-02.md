# Week 1 · Day 2 — Garbage Collector, Strings, Input, Operators, Loops, Methods (हिंदी)

[← Day 1](day-01.md) · [हिंदी notes का index](../README.md) · अगला: [Day 3 →](day-03.md) · [English version](../../weeks/week-01-setup-and-basics/day-02/README.md)

Code: [`code/` folder](https://github.com/bhandarisandeep/java/tree/main/weeks/week-01-setup-and-basics/code) — आज के samples: `MemoryLeakDemo`, `StringBasics`, `ScannerInput`, `Operators`, `Loops`, `PassByValueDemo`, `DefensiveCopy`।

---

## 0. Day 1 Recap के सुधार

| Recap में कहा | सही बात |
|---|---|
| JRE ही compiler है | JRE = JVM + standard libraries। Compiler (`javac`) सिर्फ़ JDK में है। |
| Logging JDK का extra tool है | Logging standard library का हिस्सा है। JDK के extras: `javac`, `jdb`, `jar`, `javap`। |
| Primitive immutable होते हैं, बदलने पर heap में नया बनता है | Primitive variable में value सीधे रखी होती है, बदलने पर उसी जगह नई value लिखी जाती है। "बदलने पर नया object" String की ख़ासियत है। |
| Primitive null हो सकते हैं | सिर्फ़ **reference** types `null` हो सकते हैं। Primitive में हमेशा value होती है। |
| Password `char` में रखो | `char[]` यानी char **array** में, और काम के बाद `Arrays.fill` से मिटाओ। |
| `boolean` 2 bytes का है | उसका size JVM पर निर्भर है। |

> **Note:** Phrase है "write once, run anywhere"। आठों primitives: `byte`, `short`, `int`, `long`, `float`, `double`, `char`, `boolean`।

---

## 1. Garbage Collector (GC)

- C जैसी भाषाओं में memory ख़ुद free करनी पड़ती है, भूले तो memory leak।
- Java में ये काम **Garbage Collector** करता है। ये समय-समय पर heap देखता है और जिन objects की तरफ़ **कोई variable इशारा नहीं करता**, उन्हें हटा देता है।
- **GC कब चलेगा, ये JVM तय करता है**, आप नहीं। इसीलिए पुराना password वाला String कब मिटेगा, पक्का नहीं।

> **Note:** GC को Week 6 में JVM memory के साथ गहराई से पढ़ेंगे।

### 🔒 Security Corner

- Java में भी memory leak होता है। Objects को किसी **static list या cache** में डालते रहें और कभी न हटाएँ, तो उनका पता बना रहता है और GC उन्हें छू नहीं सकता।
- नतीजा `OutOfMemoryError` और app बंद, यानी **denial of service (DoS)**। Attacker ऐसी requests बार-बार भेजकर ये करवा सकता है।
- ख़तरा data चोरी का नहीं, app के बंद होने का है। बचाव: cache की सीमा रखिए या data रखिए ही मत।
- `java -Xmx64m MemoryLeakDemo 1000` चलाकर देखिए।

---

## 2. Strings

### `==` बनाम `equals`

- Objects पर `==` देखता है कि दोनों variables का **पता एक है** या नहीं।
- `equals` देखता है कि **text एक है** या नहीं (अक्षर दर अक्षर)।
- Strings को हमेशा `equals` से मिलाइए। `==` पर error नहीं आता, code चल जाता है, बस कभी-कभी नतीजा ग़लत आता है। इसीलिए code review में ये ख़तरनाक bug है।

### String pool

- Quotes में लिखी strings (**literals**) pool में एक ही बार रखी जाती हैं। `"hello"` दो जगह लिखा, तो **दोनों एक ही object** हैं और `==` true देगा।
- Runtime पर बनी strings (input, जोड़, `new String(...)`) **नए objects** होती हैं, तो text एक होने पर भी `==` false देता है।
- यही उलझन `equals` इस्तेमाल करने की वजह है।

> **Note:** String **primitive नहीं, reference type** है। Arrays पर भी `==` सिर्फ़ पता देखता है; content मिलाने के लिए `Arrays.equals`।

### काम के methods

`length`, `substring`, `toUpperCase`, `toLowerCase`, `trim`, `contains`, `split`, `charAt`। String immutable है, इसलिए **ये सब नई String लौटाते हैं**, पुरानी नहीं बदलती।

### Loop में जोड़ना

Loop में `+` से बार-बार जोड़ने पर हर बार नई String बनती है। इसके लिए **`StringBuilder`** लीजिए, जो एक **mutable object** है और उसी जगह बदलाव करता है।

> **Note:** StringBuilder "primitive जैसा" नहीं है, वो एक mutable object है।

---

## 3. `Scanner` से keyboard input

| Method | क्या पढ़ता है |
|---|---|
| `next()` | एक **शब्द** (एक character नहीं) |
| `nextLine()` | पूरी line |
| `nextInt()` | एक integer |

### 🔒 Security Corner

- `nextInt()` की जगह `abc` डाला तो `InputMismatchException`। पहले `hasNextInt()` से चेक कीजिए या exception संभालिए।
- **हर input validate कीजिए**: type, **range** (उम्र 0–150) और **length** (नाम ≤ 50 अक्षर)।
- User input को सीधे SQL query या command में मत जोड़िए, यही **injection** की जड़ है (Week 16)।

---

## 4. Operators और Conditions

| प्रकार | Operators | ध्यान दें |
|---|---|---|
| Arithmetic | `+ - * / %` | `int / int` का नतीजा `int`: `7 / 3 = 2`। `%` शेषफल है। |
| Comparison | `== != > < >= <=` | `true` / `false` देते हैं |
| Assignment | `= += -= *= /=` | `x += 5` यानी `x = x + 5` |

**Logical:** `&&` (AND), `||` (OR), `!` (NOT)।

- Decimal नतीजा चाहिए तो कम से कम एक number decimal बनाइए: `7.0 / 3` या `(double) 7 / 3`। आम तौर पर `float` की जगह `double`।
- `if / else if / else` रास्ता चुनता है; कई fixed विकल्प हों तो `switch`।

### Short-circuit

**नतीजा पक्का होते ही Java आगे की शर्तें चेक करना बंद कर देती है**:

- `&&` में पहली शर्त `false` → दूसरी **चलती ही नहीं**।
- `||` में पहली शर्त `true` → दूसरी **चलती ही नहीं**।

Practical नियम: **पहरेदार शर्त पहले** लिखिए, जैसे null check, ताकि दूसरी शर्त तभी चले जब सुरक्षित हो।

```java
if (user != null && user.equals("admin"))   // सुरक्षित
if (user.equals("admin") && user != null)   // user null हुआ तो NullPointerException
```

### 🔒 Security Corner

- क्रम ज़रूरी है: पहले null check, फिर इस्तेमाल।
- **Fail-safe default:** access तय करने वाली हर if-else के आख़िरी `else` में, और `switch` के `default` में, **deny** होना चाहिए। जो साफ़ तौर पर allowed नहीं, वो मना। ये सिर्फ़ switch नहीं, हर access check पर लागू है।

---

## 5. Loops

| Loop | कब |
|---|---|
| `for` | पता हो कितनी बार चलाना है (शुरुआत; शर्त; बदलाव) |
| `while` | जब तक शर्त `true` है |
| `do-while` | `while` जैसा, पर body **कम से कम एक बार** चलती है (शर्त आख़िर में चेक) |
| for-each `for (int x : arr)` | Array या list के हर item पर, index की ज़रूरत नहीं। Python के `for x in list` जैसा। |

- `break` पूरा loop वहीं रोक देता है। *उदाहरण:* 100 numbers में prime ढूँढते हुए 40वें पर मिल गया, तो वहीं रुक गए।
- `continue` इस बारी का बाक़ी हिस्सा छोड़कर अगली बारी पर चला जाता है।

### 🔒 Security Corner

- **User तय करे कितनी बार loop चले:** बहुत बड़ी संख्या से CPU लगातार व्यस्त रहता है और app अटक जाता है। CPU "crash" नहीं होता, ये **denial of service** है। हमेशा ऊपरी सीमा रखिए।
- **Off-by-one:** `<` की जगह `<=` लिखा, तो loop array की हद से एक आगे चला जाता है और `ArrayIndexOutOfBoundsException` आता है।

---

## 6. Methods

Method code का एक नाम वाला टुकड़ा है, जिसे बार-बार call कर सकते हैं, ताकि एक ही logic बार-बार न लिखना पड़े।

```java
public static int add(int a, int b) { return a + b; }
```

| हिस्सा | उदाहरण | मतलब |
|---|---|---|
| Access modifier | `public` | कौन call कर सकता है |
| Return type | `int` / `void` | क्या लौटाएगा (`void` = कुछ नहीं) |
| नाम | `add` | किस नाम से call होगा |
| Parameters | `(int a, int b)` | अंदर आने वाली values |

### Access modifiers

| Modifier | कौन इस्तेमाल कर सकता है |
|---|---|
| `public` | कोई भी class |
| `protected` | वही package, और child classes |
| *(कुछ नहीं)* | सिर्फ़ वही package |
| `private` | **सिर्फ़ वही class।** Child या parent class भी नहीं। |

> **Note:** `static` कोई type या return type **नहीं** है। इसका मतलब है method class का है, object बनाए बिना call हो सकता है (Day 3 में और)।

### Overloading बनाम overriding

- **Overloading:** **एक ही नाम के कई methods**, जिनकी parameter list अलग हो (संख्या, type या क्रम)। आप जो values भेजते हैं, उन्हें देखकर compiler तय करता है कौन-सा चलेगा। **सिर्फ़ return type बदलना overloading नहीं**, compile error है।
- **Overriding:** **child class, parent का method** उसी नाम और उन्हीं parameters के साथ दोबारा लिखती है। Week 2 (OOP) में।

### Java हमेशा pass by value है

- **Primitive** भेजा → method को **value की copy** मिलती है। अंदर बदलने से बाहर असर नहीं।
- **Object या array** भेजा → **पते की copy** मिलती है। अंदर **content** बदला तो बाहर दिखेगा, पर parameter को **नया** object दे दिया तो बाहर कुछ नहीं बदलता।
- Copy **call करते समय** बनती है, return करते समय नहीं।

| Language | व्यवहार |
|---|---|
| Java | हमेशा pass by value (object का पता copy होता है) |
| Python | वही बात, जिसे अक्सर "pass by object reference" कहते हैं |
| C++ (`&`), C# (`ref`), PHP (`&`) | **असली pass by reference**: parameter को नई value दी, तो बाहर वाला variable भी बदल जाता है |

### 🔒 Security Corner

- हर `public` method एक दरवाज़ा है। **Parameters को method के अंदर ही validate कीजिए**, ये मत मानिए कि call करने वाले ने चेक किया होगा।
- अपनी अंदरूनी **mutable** list या array सीधे मत लौटाइए। बाहर वाला उसे बदल सकता है (जैसे permissions list में `"ADMIN"` जोड़ देना)। **Copy** या unmodifiable view लौटाइए।
- `String` जैसी immutable values सीधे लौटाना सुरक्षित है।

---

## Quick Revision

- GC बिना पते वाले objects हटाता है, JVM के हिसाब से। लगातार बढ़ती static collections → memory leak → `OutOfMemoryError` → DoS।
- Strings को `equals` से मिलाइए, `==` से नहीं। Literals pool में, runtime वाली strings नए objects।
- String methods नई String लौटाते हैं। बार-बार जोड़ने के लिए `StringBuilder`।
- `Scanner`: `next` = शब्द, `nextLine` = line, `nextInt` = int। Type, range और length validate।
- `int / int` = `int`। `&&` / `||` short-circuit, तो null check पहले। Default में **deny**।
- चार loops; `break` रोकता है, `continue` छोड़ता है। User वाले loop पर सीमा; off-by-one से सावधान।
- Method = access modifier + return type + नाम + parameters। `private` = सिर्फ़ वही class।
- Overloading = एक नाम, अलग parameters। Overriding = child class दोबारा लिखे (Week 2)।
- Java हमेशा pass by value। Mutable अंदरूनी data की copy लौटाइए।
