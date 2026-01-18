package com.example.network_doctor.ui.diagnostics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.network_doctor.ui.theme.DarkGradient
import com.example.network_doctor.ui.theme.DarkSurface
import com.example.network_doctor.ui.theme.Green500

@Composable
fun DiagnosticsScreen(
    viewModel: DiagnosticsViewModel = hiltViewModel()
) {
    val details by viewModel.networkDetails.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGradient)
            .padding(16.dp)
    ) {
        Text(
            text = "NETWORK DIAGNOSTICS",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier.padding(top = 48.dp, bottom = 24.dp).align(Alignment.CenterHorizontally)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                InfoCard("Connection", listOf(
                    "SSID" to details.ssid,
                    "BSSID" to details.bssid,
                    "Signal Strength" to "${details.signalStrength} dBm",
                    "Frequency" to "${details.frequency} MHz",
                    "Link Speed" to "${details.linkSpeed} Mbps"
                ))
            }

            item {
                InfoCard("Configuration", listOf(
                    "IP Address" to details.ipAddress,
                    "Gateway" to details.gateway,
                    "Subnet Mask" to details.subnetMask,
                    "DNS 1" to details.dns1,
                    "DNS 2" to details.dns2
                ))
            }
        }
    }
}

@Composable
fun InfoCard(title: String, items: List<Pair<String, String>>) {
    Card(
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = Green500,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            items.forEach { (label, value) ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = label, color = Color.Gray)
                    Text(text = value, color = Color.White)
                }
            }
        }
    }
}
