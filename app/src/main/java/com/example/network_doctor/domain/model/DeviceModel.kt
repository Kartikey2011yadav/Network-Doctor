package com.example.network_doctor.domain.model

data class DeviceModel(
    val ipAddress: String,
    val macAddress: String = "Unavailable", // Android 10+ restriction
    val hostName: String = "Unknown Device",
    val isReachable: Boolean = true,
    val vendor: String = "Unknown Vendor"
)
