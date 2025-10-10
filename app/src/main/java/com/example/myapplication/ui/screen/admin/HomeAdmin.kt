package com.example.myapplication.ui.screen.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.ui.screen.admin.nav.ContentAdmin
import com.example.myapplication.ui.screen.admin.bottombar.BottomBarAdmin

@Composable
fun HomeAdmin(
    onLogout: () -> Unit,
    onEditProfile: () -> Unit
){

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2)),

        bottomBar = {
            BottomBarAdmin(
                navController = navController
            )
        }
    ) { padding ->
        ContentAdmin(
            navController = navController,
            padding = padding,
            onLogout = onLogout,
            onEditProfile = onEditProfile
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarAdmin(){
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.title_admin)
            )
        }
    )
}