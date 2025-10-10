package com.example.myapplication.ui.screen.admin.nav

import kotlinx.serialization.Serializable

sealed class AdminScreen {

    @Serializable
    data object PlacesList : AdminScreen()

    @Serializable
    data object History : AdminScreen()

    @Serializable
    data object Profile : AdminScreen()

    @Serializable
    data object Principal : AdminScreen()

}