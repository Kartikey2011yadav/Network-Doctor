# Project Roadmap

This roadmap outlines the milestones to transform the concept into a professional Network Doctor application.

## Phase 1: Foundation & Core UI

**Goal**: Establish architecture and build the premium "Dark Mode" UI skeleton.

- [ ] Initialize Android Project with Jetpack Compose & Hilt.
- [ ] Implement Design System (Colors, Typography, Themes) matching the provided UI.
- [ ] Build Navigation Graph (Bottom Navigation: Speed, History, Data, Tools).
- [ ] Create basic Skeleton Screens for Speed Test and Dashboard.

## Phase 2: Core Speed Test Engine

**Goal**: Implement accurate and reliable speed testing.

- [ ] Implement `SpeedTestManager` (Download/Upload/Ping).
- [ ] Integrate with low-latency test server nodes (or NDT/Librespeed).
- [ ] Develop the "Futuristic Gauge" UI component with smooth animations.
- [ ] Implement real-time graph rendering for speed stability.
- [ ] Connect Engine to UI (ViewModel StateFlow).

## Phase 3: Diagnostics & Data Layers

**Goal**: Fetch and display deep network insights.

- [ ] Implement `DiagnosticsRepository` for Wifi/Mobile signal stats.
- [ ] Fetch IP details (External IP API), Gateway, DNS, MAC.
- [ ] Implement Room Database for storing Result History.
- [ ] Create History Screen with list/detail views of past tests.
- [ ] Add Permissions handling (Location, Phone State).

## Phase 4: Advanced Tools & Analysis

**Goal**: Add power-user features.

- [ ] **Wi-Fi Analyzer**: Canvas-based channel graph implementation.
- [ ] **Device Scanner**: ARP/Ping sweep implementation to list connected devices.
- [ ] **Data Usage**: `NetworkStatsManager` integration for per-app usage.
- [ ] **Network Boost**: Logic for Wifi reset/refresh actions.

## Phase 5: Polish & Monetization Preparation

**Goal**: Final UX refinements and production readiness.

- [ ] Premium Feature gating (Paywall UI).
- [ ] Settings (Units, Default Server, Theme toggle).
- [ ] Feedback/Rating flow.
- [ ] Battery & Performance optimization.
- [ ] Final comprehensive testing (Unit & UI Tests).
