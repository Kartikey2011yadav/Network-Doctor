package com.example.network_doctor.domain.model

data class NetworkDetails(
    val ssid: String = "Unknown",
    val bssid: String = "-",
    val ipAddress: String = "-",
    val gateway: String = "-",
    val subnetMask: String = "-",
    val dns1: String = "-",
    val dns2: String = "-",
    val linkSpeed: Int = 0,
    val frequency: Int = 0,
    val signalStrength: Int = 0 // RSSI
)
