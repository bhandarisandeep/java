# Java Roadmap: Noob से Secure Code Reviewer तक

18 हफ़्तों में Java के core concepts, frameworks (Spring, Jakarta EE), prod-ready deployment और फिर security code review सीखने का रास्ता। हर हफ़्ते का अपना folder है जिसमें notes, code samples और resources हैं।

> **English version:** पूरा English index [index.md](index.md) में है, और यही Jekyll / GitHub Pages का home page है। हर हफ़्ते के notes English में ही लिखे जाते हैं।

## हर हफ़्ते का नियम: Security Corner

हर हफ़्ते के notes के आख़िर में एक **Security Corner** होगा: उस topic के security issues, vulnerable और secure snippets साथ-साथ, और code review checklist। आख़िर में सब मिलाकर [secure-java/](secure-java/) बनेगा, ताकि दोहरा revision हो।

## Phases

1. **Core Java** — हफ़्ते 1–6
2. **Web और Frameworks** — हफ़्ते 7–11
3. **Prod-Ready Apps** — हफ़्ते 12–13
4. **Code Navigation** — हफ़्ता 14
5. **Security नज़रिए से** — हफ़्ते 15–18

## Weekly Index

| Week | Topic | क्या सीखेंगे |
|---|---|---|
| 01 | [Setup और Basics](weeks/week-01-setup-and-basics/) | JDK/JVM/JRE, IntelliJ, Maven/Gradle, data types, loops, methods, arrays, String |
| 02 | [OOP](weeks/week-02-oop/) | Class, object, inheritance, interface, abstract, polymorphism, packages, access modifiers, static/final |
| 03 | [Exceptions, Collections, Generics](weeks/week-03-exceptions-collections-generics/) | checked/unchecked, try-with-resources, List/Set/Map/Queue, generics, equals/hashCode |
| 04 | [Modern Java](weeks/week-04-modern-java/) | Lambdas, Streams, Optional, records, File I/O, NIO |
| 05 | [Runtime की गहराई](weeks/week-05-runtime-internals/) | Annotations, Reflection, dynamic proxies, ClassLoaders, Runtime.exec/ProcessBuilder/ScriptEngine, Serialization |
| 06 | [Concurrency, JVM, JDBC](weeks/week-06-concurrency-jvm-jdbc/) | Threads, ExecutorService, JVM memory, JDBC, Statement vs PreparedStatement |
| 07 | [Web की नींव](weeks/week-07-servlets-filters/) | HTTP, Servlets, Filters, web.xml filter-mapping, Tomcat |
| 08 | [Spring Core](weeks/week-08-spring-core/) | IoC, DI, Beans lifecycle, @Component/@Configuration/@Bean, AOP |
| 09 | [Spring Boot REST APIs](weeks/week-09-spring-boot-rest/) | @RestController, request mapping, Jackson, Bean Validation, exception handlers, interceptors |
| 10 | [Data Layer](weeks/week-10-data-layer/) | JPA, Hibernate, Spring Data, transactions, JPQL vs native query |
| 11 | [Jakarta EE और EJB](weeks/week-11-jakarta-ee-ejb/) | EJB, CDI, JAX-RS, WildFly/Payara, Struts/Quarkus परिचय |
| 12 | [Quality और Config](weeks/week-12-quality-config/) | Spring Security basics, profiles, logging, JUnit, Mockito |
| 13 | [Deployment](weeks/week-13-deployment/) | Docker, secrets, Actuator, dummy API deploy |
| 14 | [Code Navigation](weeks/week-14-code-navigation/) | Entry points, Filter→Controller→Service→Repository trace, IntelliJ Call Hierarchy |
| 15 | [Access Control](weeks/week-15-access-control/) | Broken access control, IDOR, Spring Security misconfig |
| 16 | [Injection](weeks/week-16-injection/) | SQL/HQL, command, EL/SpEL, LDAP injection — vulnerable vs fixed code |
| 17 | [Deserialization और XML](weeks/week-17-deserialization-xml/) | Gadget chains, ysoserial concept, Jackson polymorphic typing, XXE |
| 18 | [बाक़ी Security + अभ्यास](weeks/week-18-misc-security/) | SSRF, path traversal, file upload, secrets, dependencies, WebGoat |

## Miscellaneous (optional)

GUI (Swing/JavaFX), Android, Microservices, Design Patterns, Maven/Gradle गहराई से, Advanced JVM, Kotlin/Groovy/Scala, Reactive (WebFlux) — देखें [misc/](misc/)।

## Repo Structure

```
.
├── index.md             # English index (Jekyll home page)
├── README.md            # यही Hindi index (GitHub पर दिखता है)
├── _config.yml          # GitHub Pages / Jekyll config
├── weeks/
│   └── week-01-setup-and-basics/
│       ├── README.md    # उस हफ़्ते के notes (front matter के साथ)
│       ├── code/        # code samples (Maven project)
│       └── resources.md # links, artifacts
├── secure-java/         # सारे Security Corners का संग्रह
├── misc/
└── assets/              # images, diagrams
```

## Progress

हर हफ़्ता पूरा होने पर नीचे टिक करें।

- [ ] Week 01: Setup और Basics
- [ ] Week 02: OOP
- [ ] Week 03: Exceptions, Collections, Generics
- [ ] Week 04: Modern Java
- [ ] Week 05: Runtime की गहराई
- [ ] Week 06: Concurrency, JVM, JDBC
- [ ] Week 07: Web की नींव
- [ ] Week 08: Spring Core
- [ ] Week 09: Spring Boot REST APIs
- [ ] Week 10: Data Layer
- [ ] Week 11: Jakarta EE और EJB
- [ ] Week 12: Quality और Config
- [ ] Week 13: Deployment
- [ ] Week 14: Code Navigation
- [ ] Week 15: Access Control
- [ ] Week 16: Injection
- [ ] Week 17: Deserialization और XML
- [ ] Week 18: बाक़ी Security + अभ्यास
