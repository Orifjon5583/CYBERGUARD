package com.cyberguard.security

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.cyberguard.security.ui.components.CyberBottomNavBar
import com.cyberguard.security.ui.components.CyberTopAppBar
import com.cyberguard.security.ui.screens.AdminScreen
import com.cyberguard.security.ui.screens.ApkScannerScreen
import com.cyberguard.security.ui.screens.HomeScreen
import com.cyberguard.security.ui.screens.TelegramScreen
import com.cyberguard.security.ui.theme.CyberGuardTheme
import com.cyberguard.security.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CyberGuardTheme {
                CyberGuardApp()
            }
        }
    }
}

@Composable
fun CyberGuardApp(mainViewModel: MainViewModel = viewModel()) {
    var currentScreen by remember { mutableStateOf("Asosiy") }

    Scaffold(
        topBar = {
            CyberTopAppBar(
                title = "CYBERGUARD",
                subtitle = when (currentScreen) {
                    "Telegram" -> "Telegram Nazorati"
                    "APK Skaner" -> "Apk Skaner"
                    "Admin" -> "Admin Sozlamalari"
                    else -> "Asosiy"
                }
            )
        },
        bottomBar = {
            CyberBottomNavBar(
                currentScreen = currentScreen,
                onScreenSelected = { screen -> currentScreen = screen }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentScreen) {
                "Asosiy" -> HomeScreen(viewModel = mainViewModel)
                "Telegram" -> TelegramScreen(viewModel = mainViewModel)
                "APK Skaner" -> ApkScannerScreen(viewModel = mainViewModel)
                "Admin" -> AdminScreen(viewModel = mainViewModel)
                else -> HomeScreen(viewModel = mainViewModel)
            }
        }
    }
}
