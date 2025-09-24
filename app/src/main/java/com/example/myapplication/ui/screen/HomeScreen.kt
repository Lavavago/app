// ui/screen/HomeScreen.kt
package com.example.myapplication.ui.screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.ui.screen.nav.RouteTab
import com.example.myapplication.ui.screen.tags.ProfileScreen
import com.example.myapplication.ui.screen.tags.CreatePlaceScreen
import com.example.myapplication.ui.screen.tags.ExploreScreen
import com.example.myapplication.ui.screen.tags.SafeScreen
import com.example.myapplication.ui.screen.tags.inicio
import com.example.myapplication.ui.config.RouteScreen

@Composable
fun HomeScreen(onLogout: () -> Unit,
               onEditProfile: () -> Unit) {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBarUser()
        },
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


@Composable
fun ContentUser(
    padding: PaddingValues,
    navController: NavHostController,
    onLogout: () -> Unit,
    onEditProfile: () -> Unit
) {


    NavHost(
        modifier = Modifier.padding( padding),
        navController = navController,
        startDestination = RouteTab.Inicio
    ){
        composable<RouteTab.Inicio> {
            inicio()
        }
        composable<RouteTab.ExploreScreen> {
            ExploreScreen()
        }
        composable<RouteTab.SafeScreen> {
            SafeScreen()
        }
        composable<RouteTab.CreatePlaceScreen> {
            CreatePlaceScreen()
        }
        composable<RouteTab.ProfileScreen> {
            ProfileScreen(onLogout = onLogout,
                            onEditProfile = onEditProfile)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarUser() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.tittle_home),
            )
        }
    )

}

@Composable
fun BottomBarUser(
    navController: NavController
) {

    val startDestination = Destination.Home
    var selectedDestination by rememberSaveable { mutableStateOf(startDestination.ordinal) }

    NavigationBar(
    ) {

        Destination.entries.forEachIndexed { index, destination ->

            NavigationBarItem(
                label = {
                    Text(
                        text = stringResource(id = destination.label),
                    )
                },
                onClick = {
                    selectedDestination = index
                    navController.navigate(destination.route)
                },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = stringResource(id = destination.label)
                    )
                },
                selected = selectedDestination == index

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