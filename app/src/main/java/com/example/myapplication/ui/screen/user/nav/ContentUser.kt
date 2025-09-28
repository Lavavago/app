package com.example.myapplication.ui.screen.user.nav

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.ui.screen.user.nav.RouteTab
import com.example.myapplication.ui.screen.user.tags.CreatePlaceScreen
import com.example.myapplication.ui.screen.user.tags.ExploreScreen
import com.example.myapplication.ui.screen.user.tags.ProfileScreen
import com.example.myapplication.ui.screen.user.tags.SafeScreen
import com.example.myapplication.ui.screen.user.tags.inicio
import com.example.myapplication.viewmodel.PlacesViewModel


@Composable
fun ContentUser(
    padding: PaddingValues,
    navController: NavHostController,
    onLogout: () -> Unit,
    onEditProfile: () -> Unit
) {

    val placesViewModel: PlacesViewModel = viewModel()

    NavHost(
        modifier = Modifier.padding(padding),
        navController = navController,
        startDestination = RouteTab.Inicio
    ) {
        composable<RouteTab.Inicio> {
            inicio()
        }
        composable<RouteTab.ExploreScreen> {
            ExploreScreen()
        }
        composable<RouteTab.SafeScreen> {
            SafeScreen(
                placesViewModel = placesViewModel
             )
        }
        composable<RouteTab.CreatePlaceScreen> {
            CreatePlaceScreen()
        }
        composable<RouteTab.ProfileScreen> {
            ProfileScreen(
                onLogout = onLogout,
                onEditProfile = onEditProfile
            )
        }
    }

}