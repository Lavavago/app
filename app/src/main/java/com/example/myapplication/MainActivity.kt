package com.example.myapplication


import UniLocalTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.screen.Navigation
import androidx.activity.viewModels
import com.example.myapplication.viewmodel.UsersViewModel
import com.example.myapplication.viewmodel.ReviewsViewModel
import com.example.myapplication.viewmodel.PlacesViewModel
import com.example.myapplication.viewmodel.MainViewModel




class MainActivity : ComponentActivity() {
    private val usersViewModel: UsersViewModel by viewModels()
    private val reviewsViewModel: ReviewsViewModel by viewModels()
    private val placesViewModel: PlacesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val mainViewModel = MainViewModel(
            placesViewModel,
            reviewsViewModel,
            usersViewModel,
        )
        setContent {
            UniLocalTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(
                        mainViewModel = mainViewModel
                    )

                // LoginScreen()
                // CreatePlaceScreen()
                }
            }
        }
    }
}

