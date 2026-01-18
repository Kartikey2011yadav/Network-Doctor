# Plan: Network Doctor Project Plan

This plan outlines the refactoring of the current prototype into a professional-grade network diagnostic tool. The goal is to separate the UI from network logic using Clean Architecture, implement real speed testing, and add diagnostic features.

### Steps
1. **Refactor Architecture**: Move `SpeedTestScreen` logic to `SpeedTestViewModel` and separate UI components into `ui/` and logic into `data/` packages.
2. **Implement Speed Test Logic**: Replace the dummy animation with a real `SpeedTestRepository` using `OkHttp` to download/upload test files and measure throughput.
3. **Build Diagnostics Module**: Create a `NetworkDiagnosticsManager` to fetch signal strength, IP details, and perform ping tests using Android's Connectivity APIs.
4. **Develop Network Boost**: Implement a "Boost" feature that clears DNS cache (if root/supported) or guides the user to refresh their connection, toggling Airplane mode logic if permitted.
5. **UI Polish & Navigation**: Implement a Navigation Graph to switch between Speed Test, Diagnostics, and Boost screens, applying the existing specific design theme.

### Further Considerations
1. **Speed Test Methodology**: Should we use a custom server implementation or relying on a public CDN (e.g., fast.com/Cloudflare) for downloading test blobs?
2. **Permissions**: Diagnostics will require `ACCESS_FINE_LOCATION` (for signal strength/SSID on older APIs) and potentially `read_phone_state`.

---

## Detailed Project Requirements

### 1. Architecture
We will adopt **MVVM (Model-View-ViewModel)** with **Clean Architecture** principles to ensure modularity.

*   **Tech Stack**:
    *   **UI**: Jetpack Compose (Material3).
    *   **Async**: Kotlin Coroutines & Flow.
    *   **Networking**: OkHttp (for crude socket/download tests) & Retrofit (for API calls like IP geolocation).
    *   **DI**: Hilt (recommended for cleanliness) or manual dependency injection.

*   **Project Structure**:
    ```text
    com.example.network_doctor
    ├── data            # Repositories, Data Sources, API definitions
    │   ├── model       # Data classes (SpeedTestResult, NetworkInfo)
    │   └── repository  # SpeedTestRepository, DiagnosticsRepository
    ├── domain          # UseCases (optional) and Interfaces
    ├── ui              # Presentation Layer
    │   ├── speedtest   # SpeedTestScreen, SpeedTestViewModel
    │   ├── diagnostics # DiagnosticsScreen, DiagnosticsViewModel
    │   ├── boost       # NetworkBoostScreen
    │   ├── common      # Reusable Composable widgets
    │   └── theme       # Color, Type, Theme (Existing)
    └── util            # Helper classes (formatting, constants)
    ```

### 2. Features

#### A. Speed Test (Real Implementation)
*   **Logic**:
    *   **Latency (Ping)**: simple ICMP ping or HTTP HEAD request to a reliable server (e.g., `8.8.8.8` or `google.com`). Record `time_start` and `time_end`.
    *   **Download**: Download a binary blob (10MB, 50MB, variable) from a high-bandwidth CDN.
        *   *Formula*: `(Total Bytes * 8) / (Time in seconds) = bits per second (bps)`.
    *   **Upload**: POST a generated binary blob to a test server endpoint.
*   **Architecture**: `SpeedTestRepository` exposes a `Flow<SpeedTestState>` that emits progress (0-100%) and current speed values, which the ViewModel observes to update the UI.

#### B. Network Diagnostics
*   **Signal Strength (RSSI)**: Use `TelephonyManager` and `ConnectivityManager` to get signal dBm (Cellular) or WiFi RSSI.
*   **DNS Resolution**: Attempt to resolve a hostname (e.g., `example.com`) and measure the time it takes.
*   **IP/Gateway**: Fetch Local IP via `LinkProperties` and Public IP via an external API (e.g., `ip-api.com`).
*   **Port Scanning**: Attempt to connect to specific ports (80, 443, 8080) on the Gateway IP (Use with caution/warning).

#### C. Network Boost
*   **Concept**: Since apps cannot fundamentally "speed up" the ISP, this feature optimizes the local device context.
*   **Actions**:
    *   **Refresh Connection**: Programmatically toggle WiFi (if allowed) or instruct user to toggle Airplane mode.
    *   **Flush DNS**: Java `InetAddress` cache clearing (limited scope) or browser cache clearing intents.
    *   **Background Check**: Check for data-saver mode status and warn user if enabled.

### 3. Network Concepts
*   **Measurement Units**:
    *   **Throughput**: The actual amount of data successfully moved (Mbps).
    *   **Latency**: Time delay between measurement and response (ms).
    *   **Jitter**: The variance in latency over time (stability).
*   **Signal**:
    *   **RSSI**: Received Signal Strength Indicator (measured in dBm, closer to 0 is better, e.g., -50 is great, -100 is bad).
    *   **SNR**: Signal-to-Noise Ratio (higher is better).

### 4. Milestones

#### Phase 1: Refactor & Foundation
*   Setup package structure (`ui`, `data`, `domain`).
*   Create `SpeedTestViewModel` and move state management out of the Composable.
*   Ensure the animation is driven by State, not just a standalone loop.

#### Phase 2: Speed Test Logic
*   Implement `PingTest` class.
*   Implement `DownloadTest` using OkHttp with progress listeners.
*   Integrate real data into the `SpeedTestScreen` gauges.

#### Phase 3: Diagnostics Module
*   Create `DiagnosticsScreen` UI.
*   Implement `NetworkInfoManager` to fetch IP, SSID, and Signal Strength.
*   Add permissions handling for Location/Phone State.

#### Phase 4: Boost & Polish
*   Implement "Boost" button logic (Reset connection hooks).
*   Add animations and graphs for the final report.
*   Final QA on different network types (WiFi vs LTE).
