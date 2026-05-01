# Vayu Backend

The robust Spring Boot backend powering the Vayu interactive weather dashboard. This service handles direct communication with the OpenWeatherMap API and broadcasts real-time weather updates and user concurrency metrics via WebSockets.

## 🚀 Key Features & Recent Updates

*   **Two-Step Weather Pipeline:** Implements a highly reliable, sequential data fetch using `RestTemplate`. It first grabs current weather coordinates, then retrieves the highly detailed One Call 3.0 7-day forecast.
*   **Multiplayer WebSocket Broadcasting:** Maintains a continuous WebSocket connection with frontend clients. Pushes complex payloads containing both real-time weather data and dynamic "Live Viewers" counts for specific cities.
*   **Fail-Safe Error Handling:** Designed to fail loudly in the console while safely returning `null` to the frontend, preventing "zombie" UI states (like showing 0°C) when external APIs reject requests or rate-limit.
*   **Optimized Configuration Management:** Securely injects API keys and environment variables via Spring's `@Value` annotations.

## 🛠️ Tech Stack
*   **Java 17+**
*   **Spring Boot 3.x** (Web, WebSockets)
*   **Jackson** (JSON Serialization/Deserialization with strict `@JsonIgnoreProperties`)
*   **OpenWeatherMap API** (Current & One Call 3.0)

## ⚙️ Local Setup

1. Clone the repository.
2. Ensure you have an active OpenWeatherMap API key (with One Call 3.0 enabled).
3. In `src/main/resources/application.properties`, add your key:
   ```properties
   weather.api.key=YOUR_API_KEY_HERE
