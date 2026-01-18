package com.example.network_doctor.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.network_doctor.ui.common.BottomNavigationBar
import com.example.network_doctor.ui.diagnostics.DiagnosticsScreen
import com.example.network_doctor.ui.speedtest.SpeedTestScreen
import com.example.network_doctor.ui.theme.NetworkDoctorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NetworkDoctorTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomNavigationBar(navController) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "speed_test",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("speed_test") {
                            SpeedTestScreen()
                        }
                        composable("diagnostics") {
                            DiagnosticsScreen()
                        }
                        // Placeholders for other routes
                        composable("connected_devices") { }
                        composable("tools") { }
                    }
                }
            }
        }
    }
}