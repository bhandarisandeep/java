# Week 1 · Day 3 — Arrays, Heap Size, StringBuilder, static और Constructors (हिंदी)

[← Day 2](day-02.md) · [हिंदी notes का index](../README.md) · [English version](../../weeks/week-01-setup-and-basics/day-03/README.md)

Code: [`code/` folder](https://github.com/bhandarisandeep/java/tree/main/weeks/week-01-setup-and-basics/code) — आज के samples: `ArraysDemo`, `StringBuilderBufferDemo`, `StringSecurityDemo`, `PathTraversalDemo`, `ToctouDemo`, `Student`, `RaceConditionDemo`।

---

## 0. Methods Recap के सुधार

- Java **अकेली** pass by value भाषा नहीं है; C, Python और JavaScript भी value ही भेजती हैं। Java की ख़ास बात ये है कि उसमें pass by reference का **विकल्प ही नहीं** है।
- **Object या array** भेजने पर उसके **पते की copy** जाती है, इसलिए content बदला तो बाहर दिखेगा। Copy call करते ही बनती है, अंदर operation करते समय नहीं।
- "Copy लौटाओ" वाला नियम **mutable** चीज़ों (arrays, lists) के लिए है। String immutable है, उसे सीधे लौटा सकते हैं।
- Overloading **एक ही नाम के कई methods** हैं, एक method जो कई types ले, वो नहीं। कौन-सा चलेगा, compiler तय करता है।

---

## 1. Arrays

Array एक ही type की कई values का **fixed-size** डिब्बा है।

```java
int[] scores = {90, 75, 60, 85, 70};   // values देकर
int[] empty  = new int[3];              // size देकर
```

- **Size कभी नहीं बदलता।** `Arrays.copyOf(arr, newSize)` array को बढ़ाता नहीं, एक **नया बड़ा array** बनाकर values copy करता है। बढ़ने वाली collection के लिए `ArrayList` (Week 3)।
- **Index 0 से शुरू।** Size 5 हो तो index 0–4, size 10 हो तो 0–9। आख़िरी index हमेशा `length - 1`।
- **Length:** `arr.length`, **बिना brackets** के (String के `length()` से उलट)।
- **Default values** (size देकर बनाने पर): numbers के लिए `0`, `boolean` के लिए `false`, String और objects के लिए `null`।
- **Multi-dimensional:** array के अंदर array, जैसे table: `int[][] table = {{1,2,3},{4,5,6}}`।

> **Note:** Array को "list" मत कहिए। Java में `List` अलग चीज़ है, Week 3 में आएगी।

### Array कितना बड़ा हो सकता है?

1. **Index की सीमा:** index `int` है, तो ज़्यादा से ज़्यादा लगभग **2.1 billion** items। JVM थोड़ा कम देता है, क्योंकि हर array के साथ थोड़ी अंदरूनी जानकारी (header) भी रखनी पड़ती है। उससे ज़्यादा माँगा तो `OutOfMemoryError: Requested array size exceeds VM limit`।
2. **Heap की सीमा (असली रुकावट):** 2 billion `int` लगभग **8 GB** लेंगे। JVM की heap में उतनी जगह ख़ाली न हो तो `OutOfMemoryError`।

### Heap size: `-Xms` और `-Xmx`

ये JVM चलाते समय दिए जाने वाले options हैं:

| Option | मतलब | याद रखने का तरीका |
|---|---|---|
| `-Xms` | शुरुआती heap | X = JVM का non-standard option, m = memory, **s = start** |
| `-Xmx` | ज़्यादा से ज़्यादा heap | **x = max** |

```bash
java -Xms512m -Xmx4g Hello    # 512 MB से शुरू, 4 GB तक बढ़ने दो
```

- आख़िरी अक्षर unit बताता है: `m` = MB, `g` = GB।
- Production में servers अक्सर दोनों **बराबर** रखते हैं, ताकि बीच में heap बार-बार न बढ़ानी पड़े।
- `-Xmx` एक **सुरक्षा दीवार** भी है: बहुत बड़ा input सिर्फ़ app की अपनी सीमा तक जाएगा, पूरे server की memory नहीं खाएगा।

> **Note (याद रखिए):** "s" का मतलब start है, size नहीं; "m" का मतलब memory है, maximum नहीं।

### 🔒 Security Corner

- **User का दिया index:** इस्तेमाल से पहले `0 <= idx < arr.length` चेक कीजिए, वरना `ArrayIndexOutOfBoundsException`।
- **User का दिया size:** हमेशा ऊपरी सीमा रखिए, वरना बड़ी संख्या से `OutOfMemoryError` (DoS)।
- **Object वाले array में ख़ाली जगह `null`:** बिना चेक इस्तेमाल किया तो `NullPointerException`।

---

## 2. String, StringBuilder और StringBuffer

ये text रखने वाली **तीन अलग classes** हैं, String के methods या उसके "types" नहीं।

| Class | Mutable? | Thread-safe? | कब इस्तेमाल |
|---|---|---|---|
| `String` | नहीं (immutable) | हाँ, क्योंकि बदल ही नहीं सकती | ज़्यादातर जगह, जब text बार-बार नहीं बदलना: नाम, messages, keys, paths |
| `StringBuilder` | हाँ | नहीं | एक thread में बार-बार जोड़ना, जैसे loop में report या CSV की lines। सबसे आम। |
| `StringBuffer` | हाँ | हाँ (synchronized, इसलिए धीमा) | जब कई threads एक ही text object को एक साथ बदलें। आज कम मिलता है। |

- `StringBuilder` के अपने methods हैं: `append`, `insert`, `delete`, `reverse`, `length`। इन्हें builder object पर call करते हैं (`sb.append(...)`)। `concat` String का method है, StringBuilder का नहीं।
- `toString()` उस समय के text से एक **नई, immutable String** बनाकर देता है। Builder ख़ुद **mutable ही रहता है**, उसमें आगे भी जोड़ सकते हैं।
- String बदलने पर हमेशा **नया object** बनता है; StringBuilder **उसी object** में बदलाव करता है।

> **उदाहरणों पर Note:** Date के लिए `LocalDate` class है, उसे text में नहीं रखते। Seats की गिनती number है, उसके लिए `StringBuffer` नहीं, `AtomicInteger`।

### Reference variable बनाम object

`String a = "Sandeep";` — `a` ख़ुद object नहीं, एक **reference variable** है जो String object का पता रखता है। Object string pool में बनता है। बोलचाल में "a एक String है" कहना ठीक है, पर code review में ये फ़र्क़ काम आता है।

### String object बनाने के तरीके

| तरीका | Object कहाँ | Literal के साथ `==` |
|---|---|---|
| Literal `"Sandeep"` | String pool; वही literal दोबारा लिखा तो वही object | `true` |
| `new String("Sandeep")` | Heap में हमेशा बिल्कुल नया object | `false` |
| `b.intern()` | उसी text वाला pool का object लौटाता है | `true` |

- `==` सिर्फ़ **पते** मिलाता है (दोनों एक ही object पर हों तभी true)। `equals` **अक्षर** मिलाता है।
- `String.format("%s week %d में है", name, 1)` से values को text में सजाते हैं।

### Immutability से sharing सुरक्षित क्यों

String कभी बदल नहीं सकती, इसलिए एक ही String को बिना copy बनाए कई methods या threads को दे सकते हैं। इसीलिए Java file paths, URLs, class names जैसी चीज़ें String में लेती है। StringBuilder किसी method को दिया, तो वो उसे अंदर से बदल सकता है।

### 🔒 Security Corner

1. **Locale पर निर्भर case बदलना:** `toLowerCase()` system का locale इस्तेमाल करता है। Turkish में बड़ा `I` छोटा होकर बिंदु बिना `ı` बनता है, तो `"ADMIN".toLowerCase()` बन जाता है `"admın"` और `"admin"` वाली जाँच चुपचाप fail। Security checks में `toLowerCase(Locale.ROOT)` या `equalsIgnoreCase` लीजिए।
2. **Log injection:** User input में नई line का character हो और उसे सीधे log में लिख दिया, तो attacker नक़ली log entry बना सकता है, जैसे `INFO: User admin logged in`। Log करने से पहले `\r` और `\n` हटाइए या encode कीजिए।
3. **Timing attack:** `equals` पहला अलग अक्षर मिलते ही रुक जाता है, तो समय नापकर पता चलता है secret का कितना हिस्सा मिला। Password, token, MAC को **constant-time** तरीके से मिलाइए, जैसे `MessageDigest.isEqual`।

---

## 3. Path Traversal और TOCTOU

### Path traversal

App सिर्फ़ `files` folder से file दिखाती है और path बनाती है `"files/" + userInput`।

- **Attack:** user भेजता है `../../etc/passwd`। Path बनता है `files/../../etc/passwd`। हर `..` एक folder पीछे जाता है, तो system की password file खुल जाती है।
- **बचाव:** जोड़ने के बाद जो **पूरा path** बना, उसे normalise कीजिए (`..` और `.` हटाकर असली, यानी canonical path निकालिए), फिर **चेक कीजिए कि वो base folder के अंदर ही है**। नहीं है तो मना।

```java
Path base = Paths.get("files").toAbsolutePath().normalize();
Path target = base.resolve(userInput).normalize();
if (!target.startsWith(base)) throw new SecurityException("base folder से बाहर");
```

> **Note:** सिर्फ़ user का input चेक करना आम गलती है। **जोड़कर बना आख़िरी path** चेक कीजिए, क्योंकि file उसी से खुलती है।
>
> **P.S.:** Symlinks हो सकते हैं तो `toRealPath()` से भी मिलाइए।

> **ख़ास बात:** Immutability path traversal **नहीं** रोकती। String हो, StringBuilder या StringBuffer, ग़लत path ईमानदारी से वैसा ही रखा जाएगा। **Attack को चेक रोकता है।**

### TOCTOU: Time Of Check बनाम Time Of Use

Immutability का फ़र्क़ **चेक के बाद** पड़ता है:

1. Path एक StringBuilder में है, जो दो threads, A और B, के पास है।
2. Thread A चेक करता है: `files/report.txt`। चेक pass।
3. File खुलने से पहले Thread B **उसी object** को `/etc/passwd` बना देता है।
4. Thread A उसी object से path पढ़कर file खोलता है, जो अब `/etc/passwd` है।

चेक `report.txt` पर हुआ, खुली password file। String में तीसरा कदम मुमकिन ही नहीं: B सिर्फ़ **नई** String बना सकता है, A के हाथ में वही चेक की हुई String रहती है।

- Thread B आम तौर पर server पर चल रहा **attacker का code नहीं** होता। वो आपकी ही app का code है, जो attacker की **दूसरी request** संभाल रहा है, क्योंकि app ग़लती से एक mutable object requests के बीच share कर रही है।
- Attacker बहुत सारी requests **एक साथ** भेजता है, ताकि कोई एक timing पर बैठ जाए।
- **Review tip:** देखिए कोई mutable object threads या requests के बीच share तो नहीं हो रहा, **ख़ासकर `static` fields में**।

---

## 4. `static`

- `static` का मतलब है वो चीज़ **class की है, किसी एक object की नहीं**। पूरी app में उसकी **एक ही copy** होती है।
- `static` method **object बनाए बिना** call होता है, इसीलिए `main` static है।
- `static` का मतलब "न बदलने वाला" **नहीं** है। Static field कोई भी बदल सकता है, और बदलाव सबको दिखता है। "न बदलने वाला" `final` है।
- Constant होता है `static final`, जैसे `Math.PI`।

### उदाहरण: students गिनना

```java
public class Student {              // class के नाम के बाद () नहीं
    static int count = 0;           // एक साझा copy, class load होते समय सिर्फ़ एक बार set
    String name;                    // हर object का अपना name और age
    int age;

    Student(String name, int age) { // constructor
        this.name = name;           // this.name = field, name = parameter
        this.age = age;
        count++;                    // हर नए Student पर बढ़ता है
    }
}
// new Student("Sandeep", 30); ... पढ़ें Student.count से
```

- `count` अपने-आप नहीं बढ़ता, constructor में `count++` लिखना पड़ता है।
- `count` को class के **अंदर, लेकिन constructor और methods के बाहर** लिखते हैं। Java में class के बाहर variable हो ही नहीं सकता।
- Constructor के **अंदर** `count = 0` लिखा, तो हर नया student गिनती फिर से शून्य कर देगा।

### Web apps में static fields ख़तरनाक क्यों

- Web app में हर request आम तौर पर **अपने अलग thread** में चलती है। Method के अंदर के local variables उस request के अपने होते हैं, तो सुरक्षित हैं।
- `static` field **एक ही copy है जिसे सारे threads देखते हैं**, तो एक user की request का लिखा data दूसरे user की request को दिख सकता है।
- **Spring की झलक:** controllers और services आम तौर पर **singleton** होते हैं (पूरी app में एक ही object), तो उनके साधारण, non-static fields भी सारी requests में share होते हैं। Controller के field में user से जुड़ी value दिखे, तो code review में ये **बड़ा लाल झंडा** है।

---

## 5. Constructors (Week 2 की झलक)

- Constructor का नाम **class के नाम जैसा** होता है और उसमें **कोई return type नहीं, `void` भी नहीं**। `new Student(...)` लिखने पर ये अपने-आप चलता है। Class को "call" नहीं करते।
- Parameters नाम के बाद गोल brackets में: `Student(String name, int age) { ... }`। Body curly braces में।
- `name` और `age` को class में constructor के बाहर **fields** के तौर पर भी लिखना पड़ता है।

**गड़बड़:** `void Student() { }` compile हो जाता है, पर वो constructor नहीं, `Student` नाम का एक आम method है, और `new` पर नहीं चलेगा। Code review में ये लगभग हमेशा bug होता है।

**Default constructor:**

- आपने **कोई** constructor नहीं लिखा, तो compiler ख़ुद एक ख़ाली, बिना parameters वाला constructor जोड़ देता है। `new Student()` चल जाता है और fields को default values (`0`, `null`) मिलती हैं।
- Parameters वाला **कोई भी** constructor लिख दिया, तो compiler default वाला **नहीं** जोड़ता; तब `new Student()` compile error देगा, जब तक आप उसे ख़ुद न लिखें।

### 🔒 Security Corner

- **Type का चेक compiler पहले ही करता है।** `int` age में कोई text भेज ही नहीं सकता। असली काम **content** validate करना है: उम्र 0–150, नाम की लंबाई सीमित, सिर्फ़ ठीक अक्षर।
- `name` से **injection** का ख़तरा इस पर निर्भर है कि वो आगे कहाँ इस्तेमाल होगा (SQL query, HTML, log)। Input पर validate, output पर encode।
- **`count++` thread-safe नहीं।** ये तीन कदम हैं: पढ़ना, बढ़ाना, लिखना। दो threads एक साथ student बनाएँ तो गिनती एक कम रह सकती है। `AtomicInteger` लीजिए।

---

## 6. Race Conditions

- Thread-safe object (जैसे `StringBuffer`) सिर्फ़ **हर एक operation** को सुरक्षित बनाता है।
- "Seat 77 ख़ाली है?" और फिर "book करो" **दो अलग कदम** हैं (check-then-act)। दो users एक साथ चेक करें, तो दोनों को ख़ाली दिखेगी और दोनों book कर लेंगे: **double booking**।
- असली systems इसे **database lock, transaction या unique constraint** से रोकते हैं, ताकि चेक और booking एक ही इकाई में हों।

> **Note:** Concurrency को Week 6 में गहराई से पढ़ेंगे।

---

## Quick Revision

- Arrays: fixed size, index `0` से `length - 1`, `arr.length` बिना brackets, defaults `0` / `false` / `null`। `Arrays.copyOf` नया array बनाता है।
- Array की हद ≈ 2.1 billion (थोड़ा कम); असली सीमा heap। `-Xms` = शुरुआती heap, `-Xmx` = max heap।
- User का index और size validate; object arrays में `null` चेक।
- `String` immutable; `StringBuilder` mutable और तेज़; `StringBuffer` mutable और synchronized। `toString()` नई immutable String देता है।
- Variable पता रखता है, object नहीं। Literal pool में; `new` हमेशा नया; `==` पता मिलाता है, `equals` text।
- Security checks में `Locale.ROOT`, log से पहले new lines हटाइए, secrets constant-time में मिलाइए।
- Path traversal **आख़िरी normalised path** चेक करने से रुकता है, String type से नहीं। Immutability चेक के बाद के **TOCTOU** बदलाव रोकती है।
- `static` = class की एक copy, "constant" नहीं (`final` है)। Static fields और Spring singleton के fields requests में share होते हैं।
- Constructor = class का नाम, कोई return type नहीं। Default constructor तभी मिलता है जब आपने कोई न लिखा हो।
- `count++` और check-then-act race conditions हैं: `AtomicInteger`, locks या transactions।
