// ui/screen/HomeUserScreen.kt
package com.example.myapplication.ui.screen.user

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.ui.screen.user.bottombar.BottomBarUser
import com.example.myapplication.ui.screen.user.nav.ContentUser

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
