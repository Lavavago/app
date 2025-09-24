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
        startDestination = RouteScreen.Home
    ){

        composable<RouteScreen.Login> {
            LoginScreen(
                onNavigateToHome = {
                    navController.navigate(RouteScreen.Home)
                }
            )
        }

        composable<RouteScreen.CreatePlace> {
            CreatePlaceScreen()
        }

        composable<RouteScreen.Home> {
            HomeScreen()
        }

    }

}
