# Humanitarian Aid Supply Collection System

---

## 🌟 Project Overview
This project is a **Java-based system** designed to manage humanitarian aid collections. It processes live data from a provided Web API to organize **AidBoxes**, **Containers**, **Vehicles**, and **Routes** — optimizing the collection of essential goods.

The system prioritizes collection based on container capacity and type, ensuring perishable goods are handled promptly.

---

## 🎯 Objectives
- Apply **Object-Oriented Programming (OOP)** concepts.
- Create a modular and extensible system in Java.
- Integrate with an external API and parse JSON data.
- Implement automated container collection logic.
- Build a functional interface for planning and management.

---

## 🚀 Features Implemented

### API Integration
- Developed `HTTPProvider` for API interactions.
- Retrieved data: AidBoxes, Containers, Container Types, Vehicles, Distances, Sensor Readings.
- Converted JSON data into Java objects.

### System Architecture
- Classes: `AidBox`, `Container`, `ContainerType`, `Vehicle`, `Route`, `Measurement`, `AlertManager`.
- Applied OOP principles: encapsulation, inheritance, polymorphism.

### Collection Logic
- Automatic collection when container capacity > 80%.
- Priority collection for perishable goods.
- Vehicle assignment based on capacity and route efficiency.
- Container swaps between AidBoxes and Vehicles.

### Alerts System
- Logs invalid API data with timestamp.
- Provides alert reports for review.

### User Interface
- Console-based interface for:
  - Viewing AidBoxes and containers
  - Checking vehicle availability
  - Triggering collection routines
  - Viewing alerts

---

## 🛠 Technologies Used
- **Java**
- [OkHttp](https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp/4.12.0) (HTTP client)
- [json-simple](https://code.google.com/archive/p/json-simple/) (JSON parsing)

---

## 📌 Notes
- All API calls are encapsulated in `HTTPProvider`.
- Fully respects professor’s constraints.
- No use of unauthorized Java APIs or collections.

---

## 📚 How to Run
1. Clone the repository.
2. Compile the project.
3. Run `Main.java`.
4. Follow console prompts to interact with the system.

---

**Author:** Diogo Batista
**Course:** Object-Oriented Programming  
**Year:** 2023/2024
