# 🧩 Task Simulator & Scheduler

This project is a high-performance task management simulator (mini-scheduler) written in Java.
It moves beyond a simple CRUD, focusing on **Operating System scheduling concepts**, advanced data structures, and a robust layered architecture.

## 🏗️ Project Architecture & Design Patterns

The system follows a strict **Layered Architecture** to ensure maintainability and separation of concerns:

* **Model**: Domain entities (`Task`, `Priority`, `TaskStatus`).
* **Repository**: Persistence layer handling **JSON Serialization/Deserialization** via Jackson.
* **Service**: The core engine. Implements **Lazy Evaluation** for task updates and aging.
* **Controller**: Intermediary bridge between UI and business logic.
* **UI**: Console-based interface with **Input Sanitization** (ID parsing and bracket cleaning).

## ⚙️ Advanced Logic & Algorithms

This simulator implements several advanced features inspired by OS process management:

* **Dynamic Priority Scheduling**: Leverages a `PriorityQueue` with custom `Comparators` to ensure the most critical missions are handled first.
* **Aging Algorithm (Anti-Starvation)**: Prevents `LOW` priority tasks from being ignored indefinitely by automatically promoting them to higher priorities based on wait-time thresholds.
* **Composite Sorting**: Listing logic uses multi-criteria sorting (`Priority` + `CreatedAt`) for a fair and professional display.
* **Soft Delete & Data Retention**: Completed or cancelled tasks are visually hidden from the CLI to keep it clean, but are preserved in the JSON records for auditing.



## 🧠 Core Technologies & Concepts

* **Language**: Java 17+
* **Build System**: Maven
* **Data Structures**: `PriorityQueue`, `ArrayList`, `EnumMap`.
* **Time Management**: `java.time` (Duration, LocalDateTime) for precise scheduling.
* **Persistence**: JSON-based storage for state recovery between sessions.

## 🚀 How to Run

1.  Ensure you have **Maven** and **JDK 17+** installed.
2.  Clone the repository.
3.  Run `mvn clean install`.
4.  Execute the `Main` class.
5.  Use commands: `add`, `execute`, `list`, `update`, `help`, `exit`.

---
*Developed with a focus on core logic, clean code, and "low-level" efficiency.*