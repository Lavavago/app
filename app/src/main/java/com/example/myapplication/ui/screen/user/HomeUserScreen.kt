package com.example.myapplication.ui.screen.user

import androidx.compose.foundation.background // Importar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color // Importar
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.screen.user.bottombar.BottomBarUser
import com.example.myapplication.ui.screen.user.nav.ContentUser

@Composable
fun HomeScreen(onLogout: () -> Unit,
               onEditProfile: () -> Unit) {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2)),

        bottomBar = {
            BottomBarUser(
                navController = navController
            )
        }
    ) { padding ->
        ContentUser(
            navController = navController,
            padding = padding,
            onLogout = onLogout,
            onEditProfile = onEditProfile
        )
    }
}