package com.example.myapplication.ui.screen.user.tags

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
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
import com.example.myapplication.viewmodel.CreatePlaceViewModel
import com.example.myapplication.viewmodel.PlacesViewModel

@Composable
fun SafeScreen(
    padding: PaddingValues,
    placesViewModel: PlacesViewModel,
    createPlaceViewModel: CreatePlaceViewModel,
    onNavigateToCreatePlace: () -> Unit,
    onPlaceClick: (String) -> Unit
) {
    val places by placesViewModel.places.collectAsState()
    val createdPlaces by createPlaceViewModel.places.collectAsState()

    LazyColumn(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {

        // Sección de lugares favoritos
        item {
            Text(
                text = "Lugares favoritos",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        }

        items(places) { place ->
            PlaceItem(
                place = place,
                onClick = { onPlaceClick(place.id) } // ahora navega al detalle
            )
        }

        // Nueva sección: Mis lugares creados
        if (createdPlaces.isNotEmpty()) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Mis lugares creados",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }

            items(createdPlaces) { created ->
                CreatedPlaceItem(
                    place = created,
                    onClick = {
                        // Si el lugar tiene ID, navega al detalle igual que los favoritos
                        onPlaceClick(created.id)
                    }
                )
            }
        }
    }
}

@Composable
fun CreatedPlaceItem(
    place: com.example.myapplication.model.Place,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = place.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = place.address,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            Text(
                text = place.type.displayName,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF00796B)
            )
        }

        AsyncImage(
            model = place.images.firstOrNull(),
            contentDescription = place.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(10.dp))
        )
    }

    Divider(color = Color.LightGray.copy(alpha = 0.3f))
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
        // Columna con textos
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

            Text(
                text = "${place.type.displayName} - ${place.address}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Text(
                text = "Cerrado",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )
        }

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
