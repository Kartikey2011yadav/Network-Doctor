package com.example.network_doctor.ui.profile

import android.content.Context
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.network_doctor.R
import com.example.network_doctor.ui.theme.DarkGradient
import com.example.network_doctor.ui.theme.DarkSurface
import com.example.network_doctor.ui.theme.Green500

@Composable
fun ProfileScreen() {
    val scrollState = rememberScrollState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGradient)
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "PROFILE & SETTINGS",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier
                .padding(top = 48.dp, bottom = 24.dp)
                .align(Alignment.CenterHorizontally)
        )

        DeviceInfoSection()
        
        Spacer(modifier = Modifier.height(24.dp))
        
        SettingsSection()
        
        Spacer(modifier = Modifier.height(24.dp))
        
        AboutSection()
    }
}

@Composable
fun DeviceInfoSection() {
    SectionHeader(title = "DEVICE INFORMATION")
    Card(
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            InfoRow(label = "Model", value = "${Build.MANUFACTURER} ${Build.MODEL}")
            Divider()
            InfoRow(label = "Android Version", value = Build.VERSION.RELEASE)
            Divider()
            InfoRow(label = "SDK Level", value = Build.VERSION.SDK_INT.toString())
            Divider()
            InfoRow(label = "Build ID", value = Build.ID)
        }
    }
}

@Composable
fun SettingsSection() {
    SectionHeader(title = "SETTINGS")
    Card(
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            var isDarkTheme by remember { mutableStateOf(true) }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Dark Theme", color = Color.White)
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = { isDarkTheme = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Green500,
                        checkedTrackColor = Color.DarkGray,
                        uncheckedThumbColor = Color.Gray,
                        uncheckedTrackColor = Color.DarkGray
                    )
                )
            }
            Text(text = "Global theme toggle implementation pending", color = Color.Gray, fontSize = 10.sp)
        }
    }
}

@Composable
fun AboutSection() {
    SectionHeader(title = "ABOUT")
    Card(
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Network Doctor", fontWeight = FontWeight.Bold, color = Color.White)
            Text(text = "Version 1.0.0", color = Green500, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "This application provides advanced network diagnostics and speed testing capabilities. Use responsibly.",
                color = Color.LightGray,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        text = title,
        color = Color.Gray,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
    )
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = Color.Gray)
        Text(text = value, color = Color.White, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun Divider() {
    HorizontalDivider(color = Color.DarkGray, thickness = 0.5.dp)
}
