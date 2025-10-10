package com.example.myapplication.ui.config

import kotlinx.serialization.Serializable

sealed class RouteScreen {

    @Serializable
    data object Home : RouteScreen()

    @Serializable
    data object Login : RouteScreen()

    @Serializable
    data object Register : RouteScreen()

    @Serializable
    data object EditUser : RouteScreen()

    @Serializable
    data object HomeAdmin : RouteScreen()

    @Serializable
    data object CreatePlace : RouteScreen()


}