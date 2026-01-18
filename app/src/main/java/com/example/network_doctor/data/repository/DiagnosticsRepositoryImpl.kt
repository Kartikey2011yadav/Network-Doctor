package com.example.network_doctor.data.repository

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.Build
import androidx.core.app.ActivityCompat
import com.example.network_doctor.domain.model.NetworkDetails
import com.example.network_doctor.domain.repository.DiagnosticsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import javax.inject.Inject

class DiagnosticsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : DiagnosticsRepository {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager

    override fun getNetworkDetails(): Flow<NetworkDetails> = flow {
        while (true) {
            emit(fetchNetworkDetails())
            delay(2000) // Update every 2 seconds
        }
    }

    private fun fetchNetworkDetails(): NetworkDetails {
        val activeNetwork = connectivityManager.activeNetwork
        val caps = connectivityManager.getNetworkCapabilities(activeNetwork)
        val linkProperties = connectivityManager.getLinkProperties(activeNetwork)

        var ssid = "Unknown"
        var bssid = "-"
        var signalStrength = 0
        var linkSpeed = 0
        var frequency = 0

        if (caps?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true) {
            val wifiInfo = wifiManager.connectionInfo
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                ssid = wifiInfo.ssid.replace("\"", "")
                bssid = wifiInfo.bssid ?: "-"
            } else {
                ssid = "Permission Req"
            }
            signalStrength = wifiInfo.rssi
            linkSpeed = wifiInfo.linkSpeed
            frequency = wifiInfo.frequency
        }

        val ipAddress = getIpAddress()
        val gateway = linkProperties?.routes?.firstOrNull { it.isDefaultRoute }?.gateway?.hostAddress ?: "-"
        val dnsServers = linkProperties?.dnsServers
        val dns1 = dnsServers?.getOrNull(0)?.hostAddress ?: "-"
        val dns2 = dnsServers?.getOrNull(1)?.hostAddress ?: "-"

        return NetworkDetails(
            ssid = ssid,
            bssid = bssid,
            ipAddress = ipAddress,
            gateway = gateway,
            dns1 = dns1,
            dns2 = dns2,
            linkSpeed = linkSpeed,
            frequency = frequency,
            signalStrength = signalStrength
        )
    }

    private fun getIpAddress(): String {
        try {
            val interfaces = NetworkInterface.getNetworkInterfaces()
            while (interfaces.hasMoreElements()) {
                val intf = interfaces.nextElement()
                val addrs = intf.inetAddresses
                while (addrs.hasMoreElements()) {
                    val addr = addrs.nextElement()
                    if (!addr.isLoopbackAddress && addr is Inet4Address) {
                        return addr.hostAddress ?: "-"
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return "-"
    }
}
