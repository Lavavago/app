package com.example.myapplication.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.config.RouteScreen
import com.example.myapplication.ui.screen.tags.CreatePlaceScreen


@Composable
fun Navigation(){

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = RouteScreen.Login
    ){

        composable<RouteScreen.Login> {
            LoginScreen(
                onNavigateToHome = {
                    navController.navigate(RouteScreen.Home)
                },
                onNavigateToCreateUser = {
                    navController.navigate(RouteScreen.Register)
                }
            )
        }
        composable<RouteScreen.Register> {
            RegisterUserScreen(
                onLogout = {
                    navController.navigate(RouteScreen.Login) {
                        popUpTo(RouteScreen.Home) { inclusive = true }
                    }
                }
            )
        }

        composable<RouteScreen.EditUser> {
            EditUserScreen(
                onBack = {
                    navController.navigate(RouteScreen.Home)
                }
            )
        }
        composable<RouteScreen.CreatePlace> {
            CreatePlaceScreen()
        }

        composable<RouteScreen.Home> {
            HomeScreen(
                onLogout = {
                    navController.navigate(RouteScreen.Login) {
                        popUpTo(RouteScreen.Home) { inclusive = true }
                    }
                },
                onEditProfile = {
                    navController.navigate(RouteScreen.EditUser)
                }
            )
        }

    }

}

@Composable
fun RegisterScreen() {
    TODO("Not yet implemented")
}
