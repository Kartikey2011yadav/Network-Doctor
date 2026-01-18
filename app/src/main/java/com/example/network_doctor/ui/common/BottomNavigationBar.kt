package com.example.network_doctor.ui.common

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.network_doctor.R
import com.example.network_doctor.ui.theme.DarkColor
import com.example.network_doctor.ui.theme.Pink

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem("speed_test", R.drawable.speed),
        NavigationItem("diagnostics", R.drawable.wifi),
        NavigationItem("connected_devices", R.drawable.person), // Using Person for devices (User's network)
        NavigationItem("history", R.drawable.settings), // Placeholder if no explicit history icon
        NavigationItem("profile", R.drawable.settings) 
    )
    
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(containerColor = DarkColor) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo("speed_test") { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Pink,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                    indicatorColor = Color.Transparent
                ),
                icon = {
                    Icon(painterResource(id = item.icon), null)
                }
            )
        }
    }
}

data class NavigationItem(val route: String, val icon: Int)
