package com.example.network_doctor.ui.speedtest

data class UiState(
    val speed: String = "0.0",
    val ping: String = "-",
    val downloadSpeed: String = "-",
    val uploadSpeed: String = "-",
    val maxSpeed: String = "-",
    val arcValue: Float = 0f,
    val inProgress: Boolean = false,
    val error: String? = null,
    val stageName: String = "", // DOWNLOAD, UPLOAD, etc.
    val graphData: List<Float> = emptyList() // Normalized 0-1 values for the graph
)