package com.example.network_doctor.ui.devices

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.network_doctor.R
import com.example.network_doctor.domain.model.DeviceModel
import com.example.network_doctor.ui.theme.DarkGradient
import com.example.network_doctor.ui.theme.DarkSurface
import com.example.network_doctor.ui.theme.Green500

@Composable
fun ConnectedDevicesScreen(
    viewModel: ConnectedDevicesViewModel = hiltViewModel()
) {
    val devices by viewModel.devices.collectAsState()
    val isScanning by viewModel.isScanning.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGradient)
            .padding(16.dp)
    ) {
        Text(
            text = "CONNECTED DEVICES",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier
                .padding(top = 48.dp, bottom = 24.dp)
                .align(Alignment.CenterHorizontally)
        )

        ScanButton(isScanning = isScanning) {
            viewModel.toggleScan()
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (devices.isEmpty() && !isScanning) {
            EmptyState()
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(devices) { device ->
                    DeviceItem(device)
                }
            }
        }
    }
}

@Composable
fun ScanButton(isScanning: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Green500),
        modifier = Modifier.fillMaxWidth().height(50.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        if (isScanning) {
            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.size(8.dp))
            Text("STOP SCANNING", fontWeight = FontWeight.Bold)
        } else {
            Text("SCAN NETWORK", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun DeviceItem(device: DeviceModel) {
    Card(
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFF2A2E3B), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                 // Using generic icon if specific vector not available, or reuse existing
                 // Assuming R.drawable.person or similar exists
                 Icon(painterResource(id = R.drawable.wifi), contentDescription = null, tint = Green500)
            }
            
            Column(modifier = Modifier.padding(start = 16.dp).weight(1f)) {
                Text(text = device.hostName, color = Color.White, fontWeight = FontWeight.Bold)
                Text(text = device.ipAddress, color = Color.Gray, fontSize = 12.sp)
                Text(text = device.vendor, color = Color.Gray, fontSize = 10.sp)
            }
            
            if (device.isReachable) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Green500, CircleShape)
                )
            }
        }
    }
}

@Composable
fun EmptyState() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("No devices found yet.", color = Color.Gray)
        Text("Tap Scan to start.", color = Color.DarkGray, fontSize = 12.sp)
    }
}
