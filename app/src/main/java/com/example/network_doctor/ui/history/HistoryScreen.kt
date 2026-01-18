package com.example.network_doctor.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.network_doctor.ui.theme.DarkGradient
import com.example.network_doctor.ui.theme.DarkSurface
import com.example.network_doctor.ui.theme.Green500
import com.example.network_doctor.ui.theme.Pink
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel()
) {
    val history by viewModel.history.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGradient)
            .padding(16.dp)
    ) {
        Text(
            text = "TEST HISTORY",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            modifier = Modifier
                .padding(top = 48.dp, bottom = 24.dp)
                .align(Alignment.CenterHorizontally)
        )

        if (history.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("No history available.", color = Color.Gray)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(history) { result ->
                     HistoryItem(result)
                }
            }
        }
    }
}

@Composable
fun HistoryItem(result: com.example.network_doctor.data.local.entity.TestResultEntity) {
    Card(
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            val date = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault()).format(Date(result.timestamp))
            Text(text = date, color = Color.Gray, fontSize = 12.sp, modifier = Modifier.padding(bottom = 8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "DOWNLOAD", color = Green500, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Text(text = "%.1f Mbps".format(result.downloadSpeed), color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
                Column {
                    Text(text = "PING", color = Pink, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Text(text = "${result.ping.toInt()} ms", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
                 Column {
                    Text(text = "UPLOAD", color = com.example.network_doctor.ui.theme.PurpleAccent, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                     // Using placeholder for now as upload is simulated/0
                    Text(text = "%.1f Mbps".format(result.uploadSpeed), color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
