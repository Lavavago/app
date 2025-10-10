// ui/screen/user/nav/ContentUser.kt
package com.example.myapplication.ui.screen.user.nav

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.myapplication.ui.screen.CreatePlaceScreen
import com.example.myapplication.ui.screen.user.tags.ExploreScreen
import com.example.myapplication.ui.screen.user.tags.PlaceDetail
import com.example.myapplication.ui.screen.user.tags.ProfileScreen
import com.example.myapplication.ui.screen.user.tags.SafeScreen
import com.example.myapplication.ui.screen.user.tags.inicio
import com.example.myapplication.viewmodel.CreatePlaceViewModel
import com.example.myapplication.viewmodel.PlacesViewModel

@Composable
fun ContentUser(
    padding: PaddingValues,
    navController: NavHostController,
    onLogout: () -> Unit,
    onEditProfile: () -> Unit
) {
    // Creamos los ViewModels una sola vez para que se compartan entre pantallas
    val placesViewModel: PlacesViewModel = viewModel()
    val createPlaceViewModel: CreatePlaceViewModel = viewModel()

    NavHost(
        modifier = Modifier.padding(padding),
        navController = navController,
        startDestination = RouteTab.Inicio
    ) {
        composable<RouteTab.Inicio> {
            inicio(navController = navController)
        }

        composable<RouteTab.ExploreScreen> {
            ExploreScreen()
        }

        // Pantalla principal de lugares (favoritos + creados)
        composable<RouteTab.SafeScreen> {
            SafeScreen(
                padding = padding,
                placesViewModel = placesViewModel,
                createPlaceViewModel = createPlaceViewModel,
                onNavigateToCreatePlace = {
                    navController.navigate(RouteTab.CreatePlaceScreen)
                },
                onPlaceClick = { id ->
                    navController.navigate(RouteTab.PlaceDetail(id))
                }
            )
        }


        // Pantalla para crear un nuevo lugar
        composable<RouteTab.CreatePlaceScreen> {
            CreatePlaceScreen(
                createPlaceViewModel = createPlaceViewModel,
                placesViewModel = placesViewModel,
                onClose = { navController.popBackStack() } // vuelve atrás al guardar
            )
        }

        composable<RouteTab.ProfileScreen> {
            ProfileScreen(
                onLogout = onLogout,
                onEditProfile = onEditProfile
            )
        }

        // Pantalla de detalle de un lugar
        composable<RouteTab.PlaceDetail> {
            val args = it.toRoute<RouteTab.PlaceDetail>()
            PlaceDetail(
                placesViewModel = placesViewModel,
                padding = padding,
                id = args.id
            )
        }
    }
}
