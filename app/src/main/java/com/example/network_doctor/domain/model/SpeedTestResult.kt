package com.example.network_doctor.domain.model

enum class TestStage {
    IDLE, PING, DOWNLOAD, UPLOAD, FINISHED
}

data class SpeedTestResult(
    val stage: TestStage = TestStage.IDLE,
    val speedMbps: Float = 0f,
    val pingMs: Float = 0f,
    val progress: Float = 0f, // 0.0 to 1.0
    val error: String? = null
)
