package com.example.myapplication.ui.screen.user.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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

    NavigationBar(
    ) {

        Destination.entries.forEachIndexed { index, destination ->

            var isSelected = currentDestination?.route == destination.route::class.qualifiedName

            NavigationBarItem(
                label = {
                    Text(
                        text = stringResource(id = destination.label),
                    )
                },
                onClick = {
                    navController.navigate(destination.route)
                },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = stringResource(id = destination.label)
                    )
                },
                selected = isSelected

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
    Explorer(RouteTab.ExploreScreen, R.string.menu_Explorer, Icons.Default.Search ),
    Safe(RouteTab.SafeScreen, R.string.menu_safe, Icons.Default.Favorite ),
    CreatePlace(RouteTab.CreatePlaceScreen, R.string.menu_create_place, Icons.Default.Add ),
    Profile(RouteTab.ProfileScreen, R.string.menu_profile, Icons.Default.Person ),

}