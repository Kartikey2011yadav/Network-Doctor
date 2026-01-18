# Network Doctor - Features

## 1. Professional Speed Test

Provides a highly accurate, real-time measurement of network performance with a premium UI.

### Core Metrics

- **Download Speed**:
  - Multi-threaded download tests for varying file sizes.
  - Saturation of bandwidth to find true capacity.
- **Upload Speed**:
  - Uploading binary data blocks to efficient endpoints.
- **Latency (Ping)**:
  - **Idle Latency**: Ping when network is not under load.
  - **Loaded Latency**: Ping during download/upload (Bufferbloat detection).
- **Jitter**:
  - Variance in packet arrival time (crucial for VoIP/Gaming).
- **Packet Loss**:
  - Percentage of packets failed to reach destination.

### UI Experience

- **Futuristic Gauge**: Smooth animations, gradient coloring (Cyan/Purple theme).
- **Real-time Graphs**: Line charts for stability visualization.
- **Server Selection**: Option to choose nearest or specific test servers.
- **Result History**: Local database storage of all past tests with detailed breakdowns.

## 2. Deep Network Diagnostics

Analyzes the current connection state and hardware values with granular detail.

### Connection Info

- **Identifiers**: SSID (Name), BSSID (MAC), Vendor (Manufacturer lookup).
- **Configuration**: Internal IP, External IP (Geo-location), Gateway, Subnet Mask, DHCP Server.
- **DNS**: Primary and Secondary DNS servers detection.
- **Signal Analysis**:
  - **RSSI (dBm)**: Real-time signal strength.
  - **Link Speed**: Current tailored max speed.
  - **Frequency**: 2.4GHz / 5GHz / 6GHz band identification.
  - **Channel**: Current Wi-Fi channel and width (20/40/80/160MHz).
- **Security**: Protocol detection (WPA2/WPA3).

## 3. Wi-Fi Analyzer (Channel Graph)

Visualizes the Wi-Fi environment to find the best channel.

- **Channel Graph**: Visual representation of all nearby networks on the 2.4GHz and 5GHz bands.
- **Interference Heatmap**: Identify crowded channels to recommend "Best Channel" to the user.
- **Signal Strength Meter**: Live updating signal strength for walking surveys.

## 4. Device Discovery (Who Uses My Wi-Fi)

Scans the local network to find all connected devices.

- **ARP Scan / Ping Sweep**: Rapidly identify active hosts on the subnet.
- **Device Identification**: Resolve Hostnames, MAC addresses, and attempt to identify device type (Phone, PC, IoT) via OUI lookup.
- **Port Scanner**: Simple scan of common ports (80, 443, 22, etc.) on discovered devices.

## 5. Data Usage Monitor

Tracks app-wise and total data consumption.

- **Daily/Monthly/Yearly Stats**: Visual breakdown of Mobile vs Wi-Fi usage.
- **App Breakdown**: Identify bandwidth-hungry applications.
- **Alerts**: Custom data limits and warning thresholds.

## 6. Network Tools (Advanced)

Utilities for power users and network admins.

- **Ping Monitor**: Continuous ping to a target (e.g., 8.8.8.8) with graph to monitor stability over time.
- **Traceroute**: Visual path trace to a destination to find hop delays.
- **DNS Changer**: (VPN Service based) Easy toggle to switch DNS providers (Google, Cloudflare, OpenDNS) without root.
- **Network Boost**:
  - Soft refresh of Wi-Fi connection.
  - Clear app-cache (where permissible).
  - DNS Flush simulation (Airplane mode toggle prompts).

## 7. User Experience & Customization

- **Dark/Light Mode**: Full theme support (Dark mode default as per design).
- **History & Reports**: Export results as PDF/CSV.
- **Premium Features**: Ad-free experience, advanced server selection, cloud sync history.
