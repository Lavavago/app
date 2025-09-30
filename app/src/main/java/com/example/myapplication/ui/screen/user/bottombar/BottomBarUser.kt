package com.example.myapplication.ui.screen.user.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myapplication.R
import com.example.myapplication.ui.screen.user.nav.RouteTab


@Composable
fun BottomBarUser(
    navController: NavController
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val primaryColor = MaterialTheme.colorScheme.primary // El color morado del tema

    NavigationBar(
    ) {

        Destination.entries.forEachIndexed { index, destination ->

            val isSelected = currentDestination?.route == destination.route::class.qualifiedName

            NavigationBarItem(
                label = {
                    Text(
                        text = stringResource(id = destination.label),
                    )
                },
                onClick = {
                    navController.navigate(destination.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = stringResource(id = destination.label)
                    )
                },
                selected = isSelected,
                // Configuración de colores para que todos los íconos sean morados
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = primaryColor,
                    unselectedIconColor = primaryColor,
                    selectedTextColor = primaryColor,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            )
        }
    }
}


enum class Destination(
    val route: RouteTab,
    val label: Int,
    val icon: ImageVector

){
    Home(RouteTab.Inicio, R.string.menu_home, Icons.Default.Home ),
    Safe(RouteTab.SafeScreen, R.string.menu_safe, Icons.Default.Favorite ),
    CreatePlace(RouteTab.CreatePlaceScreen, R.string.menu_create_place, Icons.Default.Add )
}