package com.example.myapplication.ui.screen.nav

import kotlinx.serialization.Serializable

sealed class RouteTab {

    @Serializable
    data object Inicio : RouteTab()

    @Serializable
    data object ExploreScreen : RouteTab()

    @Serializable
    data object SafeScreen : RouteTab()

    @Serializable
    data object CreatePlaceScreen : RouteTab()

    @Serializable
    data object ProfileScreen : RouteTab()


}