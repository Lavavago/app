package com.example.myapplication.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import com.example.myapplication.model.Role

import com.example.myapplication.utils.SharedPrefsUtil


import com.example.myapplication.ui.config.RouteScreen
import com.example.myapplication.ui.screen.user.HomeScreen
import com.example.myapplication.ui.screen.admin.HomeAdmin
import com.example.myapplication.viewmodel.MainViewModel

// import com.example.myapplication.ui.screen.user.tags.CreatePlaceScreen // Ya no lo necesitas aquí

val LocalMainViewModel = staticCompositionLocalOf<MainViewModel> { error("MainViewModel is not provided") }
@Composable
fun Navigation(
    mainViewModel: MainViewModel
) {

    val context = LocalContext.current
    val navController = rememberNavController()
    val user = SharedPrefsUtil.getPreferences(context)

    val startDestination = if (user.isEmpty()) {
        RouteScreen.Login
    } else {
        if (user["role"] == "ADMIN") {
            RouteScreen.HomeAdmin
        } else {
            RouteScreen.Home
        }
    }


    Surface(
        modifier = Modifier.fillMaxSize()
    ) {

        CompositionLocalProvider(
            LocalMainViewModel provides mainViewModel
        ) {

            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {

                composable<RouteScreen.Login> {
                    LoginScreen(
                        onNavigateToHome = { userId, role ->

                            SharedPrefsUtil.savePreferences(context, userId, role)

                            if(role == Role.ADMIN){
                                navController.navigate(RouteScreen.HomeAdmin)
                            }else{
                                navController.navigate(RouteScreen.Home)
                            }
                        },
                        onNavigateToCreateUser = {
                            navController.navigate(RouteScreen.Register)
                        }
                    )
                }
                composable<RouteScreen.Register> {
                    RegisterUserScreen(
                        onNavigateToLogin = {
                            navController.navigate(RouteScreen.Login) {
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
                            SharedPrefsUtil.clearPreferences(context)
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
                    HomeAdmin(
                        onLogout = {
                            SharedPrefsUtil.clearPreferences(context)
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
    }
}


