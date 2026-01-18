package com.example.network_doctor.domain.repository

import com.example.network_doctor.domain.model.SpeedTestResult
import kotlinx.coroutines.flow.Flow

interface SpeedTestRepository {
    fun startSpeedTest(): Flow<SpeedTestResult>
}
