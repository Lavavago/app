package com.example.myapplication.ui.screen.user.tags

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myapplication.model.Place
import com.example.myapplication.viewmodel.PlacesViewModel

@Composable
fun SafeScreen(
    padding: PaddingValues,
    placesViewModel: PlacesViewModel,
    onNavigateToCreatePlace: (String) -> Unit
) {
    val places by placesViewModel.places.collectAsState()

    LazyColumn(
        modifier = Modifier.padding(padding)
    ) {
        items(places) { place ->
            PlaceItem(
                place = place,
                onClick = { onNavigateToCreatePlace(place.id) }
            )
        }
    }
}

@Composable
fun PlaceItem(
    place: Place,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Columna con textos a la izquierda
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        ) {
            Text(
                text = place.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = place.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            // ⭐ Rating fijo como en tu ejemplo (4.5)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "4,0",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                repeat(4) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Star",
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(16.dp)
                    )
                }
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Empty Star",
                    tint = Color.LightGray,
                    modifier = Modifier.size(16.dp)
                )
            }

            // Dirección
            Text(
                text = "${place.type.displayName} - ${place.address}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            // Estado (cerrado fijo, podrías usar schedules más adelante)
            Text(
                text = "Cerrado",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )
        }

        // Imagen a la derecha
        AsyncImage(
            model = place.images.firstOrNull(),
            contentDescription = place.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
        )
    }

    Divider(color = Color.LightGray.copy(alpha = 0.3f))
}
