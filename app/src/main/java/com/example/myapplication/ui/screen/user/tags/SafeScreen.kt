package com.example.myapplication.ui.screen.user.tags

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myapplication.viewmodel.PlacesViewModel

@Composable
fun SafeScreen(placesViewModel: PlacesViewModel) {

    val places by placesViewModel.places.collectAsState()

    LazyColumn {
        items(places){

            ListItem(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .clickable {

                    },
                headlineContent = {
                    Text(text = it.title)
                },
                supportingContent = {
                    Text(text = it.description)
                },
                trailingContent = {
                    AsyncImage(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .width(100.dp)
                            .height(100.dp),
                        model = it.images[0],
                        contentDescription = it.title,
                        contentScale = ContentScale.Crop
                    )
                }
            )
        }
    }
}
