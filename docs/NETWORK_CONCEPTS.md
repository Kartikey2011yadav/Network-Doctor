# Network Concepts

## Performance Metrics

### Throughput (aka "Speed")
- **Definition**: The rate at which data is successfully delivered over a communication channel.
- **Unit**: Megabits per second (Mbps). Note that file storage is usually Megabytes (MB), so 8 Mbps = 1 MB/s.

### Latency (Ping)
- **Definition**: The time it takes for a data packet to travel from the source to the destination and back.
- **Importance**: Critical for gaming, VoIP, and video calls. High latency causes "lag".
- **Target**: < 50ms is good; < 20ms is excellent.

### Jitter
- **Definition**: The variation in packet delay (latency) over time.
- **Impact**: High jitter causes buffering in streaming or robotic voice in calls, even if the average speed is high.

## Signal Quality

### RSSI (Received Signal Strength Indicator)
- **Definition**: A measurement of the power linked to the received radio signal.
- **Scale**: Negative dBm values.
    - **-50 dBm**: Excellent signal.
    - **-70 dBm**: Good.
    - **-90 dBm**: Poor/Unstable.
    - **-100 dBm**: No useful connection.

### SNR (Signal-to-Noise Ratio)
- **Definition**: The ratio of signal power to the noise (interference) power.
- **Impact**: Higher is better. A strong signal with high noise (low SNR) will still perform poorly (packet loss).

## Infrastructure

### DNS (Domain Name System)
- **Role**: The phonebook of the internet. Translates `google.com` to `142.250.x.x`.
- **Diagnostics**: Slow DNS resolution makes browsing feel sluggish even if download speed is fast.

### Gateway
- **Role**: The device (usually the router) that routes traffic from the local network to the outside internet.
