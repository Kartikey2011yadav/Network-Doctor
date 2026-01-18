package com.example.network_doctor.domain.repository

import com.example.network_doctor.domain.model.DeviceModel
import kotlinx.coroutines.flow.Flow

interface DeviceRepository {
    fun scanNetwork(): Flow<List<DeviceModel>>
    fun stopScan()
}
