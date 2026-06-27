# Advent of Code 2025

Repository of my **Advent of Code 2025** solutions in **Java**. Beyond solving each puzzle, the goal of the project is to apply object-oriented design principles: every day is organized in a **layered architecture** and built on the SOLID principles and several design patterns.

## Layered architecture

Each day is structured in four layers, with dependencies always pointing towards the domain:

```
software.ulpgc.aoc.dayNN
├── model         Pure domain: Value Objects (records) and abstractions. Depends on nothing.
├── io            Input boundary: loader interfaces (what is needed, not how it is read).
├── control       Orchestrates the use case: controllers, algorithmic engines, builders and strategies.
└── application   Details and startup: I/O implementations, assembly and the Mains (composition root).
```

**Dependency direction:** `application → control → (io + model)` and `io → model`. The domain (`model`) never imports another layer.

## Principles and patterns applied

* **SOLID:** Single Responsibility (SRP), Open/Closed (OCP), Dependency Inversion (DIP) and Interface Segregation (ISP).
* **Patterns:** Strategy, Builder + Fluent API, Factory (incl. *static factory methods* such as `Range.fromText`), Facade and composition root.
* **Others:** immutability (records), dependency injection, high cohesion and low coupling, DRY, KISS and YAGNI.

The recurring idea: the two parts of each day (A and B) share the core and only change an **injected strategy** (a lambda or an implementation selected by a factory), so that the use-case code stays closed to modification.

## Project structure

* **`doc/`**: detailed documentation for each day (`dayNN.md`), with an introduction to the problem, the layered architecture, a class-by-class explanation, the principles applied and a conclusion. It also includes study material in `doc/pdf/`.
* **`src/main/java/`**: source code with the solutions, one folder per day.
* **`src/main/resources/`**: input files (`dayNNinput`).
* **`src/test/java/`**: unit tests (JUnit 5) that verify each part against the examples from the statement.

## Day index

| Day | Topic                                  | Documentation |
|----:|----------------------------------------|---------------|
| 01 | Secret entrance                        | [doc/day01.md](doc/day01.md) |
| 02 | Gift shop                              | [doc/day02.md](doc/day02.md) |
| 03 | Lobby (energy / Greedy)                | [doc/day03.md](doc/day03.md) |
| 04 | Print department (grid)                | [doc/day04.md](doc/day04.md) |
| 05 | Cafeteria (freshness ranges)           | [doc/day05.md](doc/day05.md) |
| 06 | Garbage compactor (parsing)            | [doc/day06.md](doc/day06.md) |
| 07 | Laboratories (beam simulation)         | [doc/day07.md](doc/day07.md) |
| 08 | Circuits (3D box merging)              | [doc/day08.md](doc/day08.md) |
| 09 | Cinema (largest rectangle)             | [doc/day09.md](doc/day09.md) |
| 10 | Factory (button machines)              | [doc/day10.md](doc/day10.md) |
| 11 | Reactor (route counting)               | [doc/day11.md](doc/day11.md) |
| 12 | Tree farm (packing)                    | [doc/day12.md](doc/day12.md) |

## Build and run

**Maven** project with **Java 17**.

```bash
# Compile
mvn compile

# Run the tests
mvn test

# Run a specific solution (e.g. day 7, part A)
mvn exec:java -Dexec.mainClass="software.ulpgc.aoc.day07.application.a.Main07A"
```
