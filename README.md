# Vayu Backend | Spring Boot WebSocket Adapter ☁️

Vayu is a real-time weather application. This repository houses the Java Spring Boot backend, which acts as a "Smart Push" WebSocket adapter for the OpenWeatherMap REST API.

## 🏗️ Architecture & Optimization
OpenWeatherMap is strictly a pull-based REST API. To provide the frontend with a live data stream without overwhelming the client with redundant data, this backend implements a **Caching Adapter Pattern**:

1. **Scheduled Polling:** A `@Scheduled` task polls the OpenWeather API every 60 seconds for active client cities.
2. **State Cache:** A `ConcurrentHashMap` tracks the last broadcasted JSON state for every individual WebSocket session.
3. **Smart Broadcasting:** The server compares the newly fetched API data against the cached state. It only pushes a new message to the client over the WebSocket if the weather data has *actually changed* (e.g., temperature drops, wind shifts).

**Result:** Massive reduction in redundant network bandwidth and zero unnecessary frontend DOM re-renders.

## 🛠️ Tech Stack
* **Framework:** Java Spring Boot
* **Networking:** Spring WebSockets, Spring Web (RestTemplate)
* **Concurrency:** `ConcurrentHashMap` for thread-safe session tracking
* **External API:** OpenWeatherMap API

## 🚀 Running Locally

1. Clone the repository:
   ```bash
   git clone [https://github.com/yourusername/vayu-backend.git](https://github.com/yourusername/vayu-backend.git)