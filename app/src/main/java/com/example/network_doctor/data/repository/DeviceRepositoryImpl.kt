package com.example.network_doctor.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.LinkProperties
import com.example.network_doctor.domain.model.DeviceModel
import com.example.network_doctor.domain.repository.DeviceRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import java.net.Inet4Address
import java.net.InetAddress
import java.util.Collections
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class DeviceRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : DeviceRepository {

    @Volatile
    private var isScanning = false

    override fun stopScan() {
        isScanning = false
    }

    override fun scanNetwork(): Flow<List<DeviceModel>> = flow {
        isScanning = true
        val foundDevices = Collections.synchronizedList(mutableListOf<DeviceModel>())
        
        val myIp = getLocalIpAddress()
        if (myIp == null) {
            emit(emptyList()) // No connection
            return@flow
        }

        val subnetPrefix = myIp.substringBeforeLast(".")

        // Add Gateway (usually .1)
        foundDevices.add(DeviceModel(ipAddress = "$subnetPrefix.1", hostName = "Gateway", isReachable = true))
        // Add Self
        foundDevices.add(DeviceModel(ipAddress = myIp, hostName = "This Device", isReachable = true))
        emit(foundDevices.toList()) // Initial emit

        coroutineScope {
            val tasks = (1..254).map { i ->
                async(Dispatchers.IO) {
                    if (!isScanning) return@async null
                    
                    val testIp = "$subnetPrefix.$i"
                    // Skip self and gateway if already added (simple check)
                    if (testIp == myIp || testIp == "$subnetPrefix.1") return@async null

                    try {
                        val inetAddress = InetAddress.getByName(testIp)
                        // Timeout 200ms for fast scan
                        if (inetAddress.isReachable(200)) {
                            val device = DeviceModel(
                                ipAddress = testIp,
                                hostName = inetAddress.hostName ?: "Unknown",
                                isReachable = true
                            )
                            foundDevices.add(device)
                            // Emit update for every found device (or batch them if too frequent)
                            // For simplicity, we won't emit on every single one here to avoid flow pressure,
                            // but we could emit periodically.
                        }
                    } catch (e: Exception) {
                        // Ignore
                    }
                }
            }
            // Wait for all pings to complete
            tasks.awaitAll()
        }
        
        // Final emit
        emit(foundDevices.toList().sortedBy { it.ipAddress.substringAfterLast(".").toIntOrNull() ?: 0 })
        isScanning = false

    }.flowOn(Dispatchers.IO)


    private fun getLocalIpAddress(): String? {
        try {
            val interfaces = java.net.NetworkInterface.getNetworkInterfaces()
            while (interfaces.hasMoreElements()) {
                val intf = interfaces.nextElement()
                val addrs = intf.inetAddresses
                while (addrs.hasMoreElements()) {
                    val addr = addrs.nextElement()
                    if (!addr.isLoopbackAddress && addr is Inet4Address) {
                        return addr.hostAddress
                    }
                }
            }
        } catch (ex: Exception) { }
        return null
    }
}
