# Network Doctor - Architecture

## Overview

The Network Doctor app follows the **MVVM (Model-View-ViewModel)** architectural pattern with **Clean Architecture** principles. This ensures separation of concerns, testability, and modularity.

## Technology Stack

- **Language**: Kotlin
- **UI Toolkit**: Jetpack Compose (Material3)
- **Concurrency**: Kotlin Coroutines & Flow
- **Networking**: OkHttp (Speed tests), Retrofit (IP/Geo APIs)
- **Database**: Room (History storage)
- **Dependency Injection**: Hilt
- **Build System**: Gradle (Kotlin DSL)
- **Charts**: MPAndroidChart or Vico (for graphs)

## Project Structure

```text
com.example.network_doctor
├── data
│   ├── local          # Room Database
│   │   ├── dao        # ResultDao
│   │   ├── entity     # TestResultEntity
│   │   └── AppDatabase.kt
│   ├── remote         # API definitions
│   │   ├── dto        # Data Transfer Objects
│   │   └── NetworkApi.kt
│   ├── repository     # Implementation of repositories
│   │   ├── SpeedTestRepositoryImpl.kt
│   │   ├── DiagnosticsRepositoryImpl.kt
|   |   └── HistoryRepositoryImpl.kt
│   └── source         # Data sources (SpeedTestEngine, SysNetworkManager)
├── domain
│   ├── model          # Core business models
│   ├── repository     # Interface definitions
│   └── usecase        # Business logic (e.g., RunFullScanUseCase)
├── ui
│   ├── main           # MainActivity, MainViewModel (Global state)
│   ├── speedtest      # SpeedTestScreen, ViewModel
│   ├── history        # HistoryScreen, ViewModel
│   ├── diagnostics    # NetworkInfoScreen, ViewModel
│   ├── tools          # WifiAnalyzer, DeviceScanner
│   ├── common         # Shared Composables (Gauges, Cards, Graphs)
│   ├── theme          # Compose Theme (Type, Color, Shapes)
│   └── navigation     # NavHost, Routes
└── util               # Constants, Permissions, Formatters
```

## Layer Responsibilities

### UI Layer (Presentation)

- **Composables**: Pure functions of state. They observe the ViewModel's state and emit events.
- **ViewModel**: Holds the `UiState` (StateFlow). Handles UI events, executes business logic via Repositories, and updates state.

### Domain Layer (Business Logic)

- **Repositories (Interfaces)**: Defines contract for data operations.
- **Use Cases**: Encapsulates specific business rules (e.g., `CalculateJitterUseCase`).

### Data Layer (Data Access)

- **Repositories (Impl)**: Coordinates data from network/local sources.
- **Local Source**: Room Database for persisting test history.
- **Remote Source**: Speed test server nodes, IP Geolocation APIs.
- **System Service**: Wrappers around `ConnectivityManager`, `TelephonyManager`, `WifiManager`.

## Modularization Strategy

To ensure scalability:

1.  **Feature Modules** (Optional for now): Could split `feature:speedtest`, `feature:diagnostics` later.
2.  **UI Components library**: Reusable implementation of the "Gauge" and "Graph" components.
