package com.example.myapplication.ui.screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.utils.SharedPrefsUtil

import com.example.myapplication.ui.config.RouteScreen
import com.example.myapplication.ui.screen.user.HomeScreen
import com.example.myapplication.ui.screen.admin.HomeAdmin

// import com.example.myapplication.ui.screen.user.tags.CreatePlaceScreen // Ya no lo necesitas aquí


@Composable
fun Navigation(){

    val context = LocalContext.current
    val navController = rememberNavController()
    val user = SharedPrefsUtil.getPreferences(context)

    NavHost(
        navController = navController,
        startDestination = RouteScreen.HomeAdmin
    ){

        composable<RouteScreen.Login> {
            LoginScreen(
                onNavigateToHome = {

                    navController.navigate(RouteScreen.Home) {
                        popUpTo(RouteScreen.Login) { inclusive = true }
                    }
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

                    navController.popBackStack()
                }
            )
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

        composable<RouteScreen.HomeAdmin> {
            HomeAdmin()
        }

    }

}


