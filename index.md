---
layout: default
title: "Java Roadmap: From Noob to Secure Code Reviewer"
description: "An 18-week, week-by-week path to learn core Java, Spring and Jakarta EE, build and deploy production-ready apps, and review Java code for security issues."
---

# Java Roadmap: From Noob to Secure Code Reviewer

An 18-week path to learn Java properly: core concepts first, then frameworks (Spring, Jakarta EE), production-ready deployment, code navigation, and finally security code review. Every week has its own folder with notes, runnable code samples and resources.

**Goal:** understand Java deeply enough to navigate an unfamiliar codebase, trace how a request flows through an API (filters, mappings, validation, models), build and deploy real apps, and spot security issues while reviewing code.

> *A Hindi version of this index is in the repo's [README](https://github.com/bhandarisandeep/java#readme).*

## The weekly rule: Security Corner

We learn secure coding from day one, not as an afterthought. Every week's notes end with a **Security Corner** that covers:

- The security issues related to that topic.
- A vulnerable snippet next to its secure version.
- What to check for during a code review.

All Security Corners are collected in [Secure Java](secure-java/), so every topic gets revised twice: once in context, once in the security summary.

## Phases at a glance

| Phase | Weeks | Focus |
|---|---|---|
| 1. Core Java | 1–6 | Language fundamentals, OOP, collections, modern Java, runtime internals, JDBC |
| 2. Web and Frameworks | 7–11 | Servlets and filters, Spring Core, Spring Boot REST, JPA/Hibernate, Jakarta EE/EJB |
| 3. Production-Ready Apps | 12–13 | Security config, testing, logging, Docker, deployment |
| 4. Code Navigation | 14 | Finding entry points and tracing requests end to end |
| 5. Security Review | 15–18 | Access control, injection, deserialization, XXE, SSRF and more |

## Phase 1: Core Java (Weeks 1–6)

**Week 1: Setup and Basics.** The difference between JDK, JRE and JVM; setting up IntelliJ IDEA and Maven/Gradle. Data types, variables, operators, if/else, loops, methods, arrays and String. *Practice:* small console programs such as a calculator.

**Week 2: OOP.** Classes, objects, constructors, inheritance, interfaces, abstract classes, polymorphism, encapsulation, packages, access modifiers, `static` and `final`, overloading vs overriding. *Practice:* a small library management model.

**Week 3: Exceptions, Collections, Generics.** Checked vs unchecked exceptions, try-with-resources. `List`, `Set`, `Map`, `Queue` and when to use which. Generics and type erasure. `equals` and `hashCode`.

**Week 4: Modern Java.** Lambdas, functional interfaces, Streams (`filter`, `map`, `collect`), `Optional`, records. File I/O, NIO and string formatting.

**Week 5: Runtime Internals (the most important week for code review).** How annotations are defined and read. Reflection: `Class.forName`, `getMethod`, `invoke`. Dynamic proxies and ClassLoaders. Runtime execution: `Runtime.exec`, `ProcessBuilder`, `ScriptEngine`. Serialization and deserialization: `Serializable`, `ObjectInputStream`, `readObject`. *Practice:* call a class's private method using reflection.

**Week 6: Concurrency, JVM and JDBC.** Threads, `ExecutorService`, `synchronized`; JVM memory basics (heap, stack, garbage collection). Connecting to a database with JDBC; `Statement` vs `PreparedStatement`.

## Phase 2: Web and Frameworks (Weeks 7–11)

**Week 7: Web Foundations.** HTTP basics, Servlets, Servlet Filters and Listeners, `filter-mapping` and `url-pattern` in `web.xml`, deploying on Tomcat. This is where you learn how a request actually enters an application.

**Week 8: Spring Core.** Inversion of Control, Dependency Injection, beans and their lifecycle, `@Component`, `@Configuration`, `@Bean`, AOP basics.

**Week 9: Spring Boot REST APIs.** `@RestController`, `@RequestMapping`, `@GetMapping` and friends, request body binding, Jackson (JSON), Bean Validation (`@Valid`, `@NotNull`, custom validators), exception handlers, interceptors and filters. *Practice:* a Hello World API, then a CRUD API.

**Week 10: Data Layer.** JPA, Hibernate, Spring Data repositories, entities and relationships, transactions, native queries vs JPQL.

**Week 11: Jakarta EE and EJB.** EJB (Stateless, Stateful), CDI, REST APIs with JAX-RS, deploying on WildFly or Payara, and how it all compares to Spring. A short introduction to Struts and Micronaut/Quarkus so you can recognise both legacy and modern codebases.

## Phase 3: Production-Ready Apps (Weeks 12–13)

**Week 12: Quality and Configuration.** Spring Security basics (authentication, authorization, the filter chain), application properties and profiles, logging, testing with JUnit and Mockito.

**Week 13: Deployment.** Packaging the app in Docker, environment variables and secrets, Spring Actuator, deploying a dummy API to the cloud or a local server.

## Phase 4: Code Navigation (Week 14)

Finding entry points in an unfamiliar codebase: controllers, routes, filters, interceptors. Tracing a request from Filter to Controller to Service to Repository. Finding where validation is applied and where security is configured. Using IntelliJ's Find Usages, Call Hierarchy and Go to Implementation. *Practice:* trace one endpoint of an open-source Spring project from start to finish.

## Phase 5: Security Review (Weeks 15–18)

**Week 15: Access Control.** Broken access control, IDOR, missing authorization checks, Spring Security misconfiguration, method-level security.

**Week 16: Injection.** SQL and HQL injection, command injection (`Runtime.exec`), Expression Language and SpEL injection, LDAP injection. Each one shown as vulnerable code next to fixed code.

**Week 17: Deserialization and XML.** How Java native deserialization is actually exploited: gadget chains and the idea behind ysoserial. The risks of Jackson polymorphic typing. XXE and safe XML parser configuration.

**Week 18: Remaining Essentials and Practice.** SSRF, path traversal, file upload issues, secrets in code, vulnerable dependencies. *Practice:* review intentionally vulnerable apps such as OWASP WebGoat.

## Weekly Index

| Week | Topic | What you will learn |
|---|---|---|
| 01 | [Setup and Basics](weeks/week-01-setup-and-basics/) | JDK/JVM/JRE, IntelliJ, Maven/Gradle, data types, loops, methods, arrays, String |
| 02 | [OOP](weeks/week-02-oop/) | Classes, objects, inheritance, interfaces, abstract classes, polymorphism, packages, access modifiers, static/final |
| 03 | [Exceptions, Collections, Generics](weeks/week-03-exceptions-collections-generics/) | Checked/unchecked, try-with-resources, List/Set/Map/Queue, generics, equals/hashCode |
| 04 | [Modern Java](weeks/week-04-modern-java/) | Lambdas, Streams, Optional, records, File I/O, NIO |
| 05 | [Runtime Internals](weeks/week-05-runtime-internals/) | Annotations, reflection, dynamic proxies, ClassLoaders, Runtime.exec/ProcessBuilder/ScriptEngine, serialization |
| 06 | [Concurrency, JVM, JDBC](weeks/week-06-concurrency-jvm-jdbc/) | Threads, ExecutorService, JVM memory, JDBC, Statement vs PreparedStatement |
| 07 | [Web Foundations](weeks/week-07-servlets-filters/) | HTTP, Servlets, Filters, web.xml filter-mapping, Tomcat |
| 08 | [Spring Core](weeks/week-08-spring-core/) | IoC, DI, bean lifecycle, @Component/@Configuration/@Bean, AOP |
| 09 | [Spring Boot REST APIs](weeks/week-09-spring-boot-rest/) | @RestController, request mapping, Jackson, Bean Validation, exception handlers, interceptors |
| 10 | [Data Layer](weeks/week-10-data-layer/) | JPA, Hibernate, Spring Data, transactions, JPQL vs native queries |
| 11 | [Jakarta EE and EJB](weeks/week-11-jakarta-ee-ejb/) | EJB, CDI, JAX-RS, WildFly/Payara, intro to Struts/Quarkus |
| 12 | [Quality and Configuration](weeks/week-12-quality-config/) | Spring Security basics, profiles, logging, JUnit, Mockito |
| 13 | [Deployment](weeks/week-13-deployment/) | Docker, secrets, Actuator, deploying a dummy API |
| 14 | [Code Navigation](weeks/week-14-code-navigation/) | Entry points, Filter → Controller → Service → Repository tracing, IntelliJ Call Hierarchy |
| 15 | [Access Control](weeks/week-15-access-control/) | Broken access control, IDOR, Spring Security misconfiguration |
| 16 | [Injection](weeks/week-16-injection/) | SQL/HQL, command, EL/SpEL, LDAP injection: vulnerable vs fixed code |
| 17 | [Deserialization and XML](weeks/week-17-deserialization-xml/) | Gadget chains, the ysoserial concept, Jackson polymorphic typing, XXE |
| 18 | [Remaining Security and Practice](weeks/week-18-misc-security/) | SSRF, path traversal, file upload, secrets, dependencies, WebGoat |

## Miscellaneous Topics (optional, good to have)

Not required for the roadmap. Explore them if you have time, or skip them freely.

- **GUI:** desktop apps with Swing and JavaFX.
- **Android:** Android apps in Java, Activities, Intents and app permissions.
- **Microservices:** Spring Cloud, API Gateway, service discovery, message queues such as Kafka.
- **Design Patterns:** Singleton, Factory, Builder, Observer, to read the structure of large codebases faster.
- **Build and Tooling:** Maven and Gradle in depth, dependency trees, plugins.
- **Advanced JVM:** JIT, bytecode (`javap`), memory leaks, profiling.
- **Other JVM languages:** an introduction to Kotlin, Groovy and Scala.
- **Reactive Programming:** Spring WebFlux and Project Reactor.

## Repository Structure

```
.
├── index.md             # This page (English, the Jekyll / GitHub Pages home page)
├── README.md            # Hindi version of the index (shown on GitHub)
├── _config.yml          # GitHub Pages / Jekyll configuration
├── weeks/
│   └── week-01-setup-and-basics/
│       ├── README.md    # That week's notes, in English, with front matter
│       ├── code/        # Runnable code samples
│       └── resources.md # Links and artifacts
├── secure-java/         # All Security Corners collected in one place
├── misc/                # Optional topics
└── assets/              # Images and diagrams
```

## Progress

Tick each week off as it is completed.

- [ ] Week 01: Setup and Basics
- [ ] Week 02: OOP
- [ ] Week 03: Exceptions, Collections, Generics
- [ ] Week 04: Modern Java
- [ ] Week 05: Runtime Internals
- [ ] Week 06: Concurrency, JVM, JDBC
- [ ] Week 07: Web Foundations
- [ ] Week 08: Spring Core
- [ ] Week 09: Spring Boot REST APIs
- [ ] Week 10: Data Layer
- [ ] Week 11: Jakarta EE and EJB
- [ ] Week 12: Quality and Configuration
- [ ] Week 13: Deployment
- [ ] Week 14: Code Navigation
- [ ] Week 15: Access Control
- [ ] Week 16: Injection
- [ ] Week 17: Deserialization and XML
- [ ] Week 18: Remaining Security and Practice
