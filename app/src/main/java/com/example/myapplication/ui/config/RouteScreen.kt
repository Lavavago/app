package com.example.myapplication.ui.config

import kotlinx.serialization.Serializable

sealed class RouteScreen {

    @Serializable
    data object Home : RouteScreen()

    @Serializable
    data object Login : RouteScreen()

    @Serializable
    data object CreatePlace : RouteScreen()


}