package com.example.network_doctor.domain.repository

import com.example.network_doctor.domain.model.NetworkDetails
import kotlinx.coroutines.flow.Flow

interface DiagnosticsRepository {
    fun getNetworkDetails(): Flow<NetworkDetails>
}
