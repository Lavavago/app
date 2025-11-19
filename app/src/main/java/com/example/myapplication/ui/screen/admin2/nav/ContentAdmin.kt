package com.example.myapplication.ui.screen.admin2.nav

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.screen.admin2.screens.HistoryScreen
import com.example.myapplication.ui.screen.admin2.screens.PlacesListScreen
import com.example.myapplication.ui.screen.admin2.screens.PrincipalScreen
import com.example.myapplication.ui.screen.admin2.screens.ProfileScreen

@Composable
fun ContentAdmin(
    padding: PaddingValues,
    navController: NavHostController,
    onLogout: () -> Unit,
    onEditProfile: () -> Unit
){

    NavHost(
        modifier = Modifier.padding(padding),
        navController = navController,
        startDestination = AdminScreen.Principal
    ){
        composable<AdminScreen.Principal> {
            PrincipalScreen(navController = navController)
        }
        composable<AdminScreen.PlacesList> {
            PlacesListScreen()
        }
        composable<AdminScreen.History> {
            HistoryScreen()
        }
        composable<AdminScreen.Profile> {
            ProfileScreen(
                onLogout = onLogout,
                onEditProfile = onEditProfile
            )
        }
    }

}