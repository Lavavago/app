package com.example.myapplication.ui.screen.user.tags

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.model.Place
import com.example.myapplication.viewmodel.PlacesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetail(
    placesViewModel: PlacesViewModel,
    padding: PaddingValues,
    id: String
) {
    val place = placesViewModel.findById(id) ?: return

    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .background(Color(0xFFF5F3FF)) // fondo morado claro
            .verticalScroll(rememberScrollState()) // scroll activado
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = place.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(5) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFF5B21B6),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = "Como llegar",
                        fontSize = 14.sp,
                        color = Color(0xFF7C3AED)
                    )
                }
                Text(
                    text = "Abierto · Cierra a las 12 a.m",
                    color = Color(0xFF16A34A),
                    fontSize = 12.sp
                )
            }
            IconButton(onClick = { /* cerrar */ }) {
                Icon(Icons.Default.Close, contentDescription = null)
            }
        }

        Spacer(Modifier.height(12.dp))

        // Imagen principal
        if (place.images.isNotEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(place.images.first()),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(Modifier.height(12.dp))

        // Miniaturas
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(place.images.size) { index ->
                Image(
                    painter = rememberAsyncImagePainter(place.images[index]),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        // Tabs interactivos
        PlaceTabs(place)


        Spacer(Modifier.height(16.dp))



        // Footer con logo de uniLocal
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .background(Color(0xFF7C3AED), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color.White)
            }
            Spacer(Modifier.width(6.dp))
            Text("uniLocal", fontWeight = FontWeight.Bold, color = Color(0xFF7C3AED))
        }
    }
}

@Composable
fun PlaceTabs(place: Place) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Resumen", "Comentarios", "Reseña", "Horarios")

    ScrollableTabRow(
        selectedTabIndex = selectedTab,
        containerColor = Color.Transparent,
        contentColor = Color(0xFF7C3AED),
        edgePadding = 0.dp
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTab == index,
                onClick = { selectedTab = index },
                selectedContentColor = Color(0xFF7C3AED),
                unselectedContentColor = Color.Gray,
                text = {
                    Text(
                        text = title,
                        fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                    )
                }
            )
        }
    }

    Spacer(Modifier.height(16.dp))

    when (selectedTab) {
        //  Resumen
        0 -> {
            Column {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Text(
                        text = place.description,
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        modifier = Modifier.padding(12.dp)
                    )
                }

                Spacer(Modifier.height(12.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, tint = Color(0xFF7C3AED))
                        Spacer(Modifier.width(8.dp))
                        Text(place.address)
                    }
                }

                Spacer(Modifier.height(12.dp))

                place.phones.forEach { phone ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Icon(Icons.Default.Phone, contentDescription = null, tint = Color(0xFF7C3AED))
                            Spacer(Modifier.width(8.dp))
                            Text(phone)
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                }
            }
        }

        //  Comentarios quemados
        1 -> {
            var newComment by remember { mutableStateOf("") }

            Column {
                Text(
                    "💬 Comentarios de usuarios:",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
                Text("Juan: Muy buen lugar!", modifier = Modifier.padding(8.dp))
                Text("María: La comida deliciosa 😋", modifier = Modifier.padding(8.dp))
                Text("Pedro: Buen servicio pero demorado", modifier = Modifier.padding(8.dp))

                Spacer(Modifier.height(16.dp))

                // Caja para escribir comentario (decorativa)
                OutlinedTextField(
                    value = newComment,
                    onValueChange = { newComment = it },
                    placeholder = { Text("Escribe un comentario...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(Modifier.height(8.dp))

                // Botón "Enviar" (decorativo, no hace nada)
                Button(
                    onClick = { /* No hace nada, es solo decorativo */ },
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(horizontal = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7C3AED))
                ) {
                    Text("Enviar", color = Color.White)
                }
            }
        }


        //  Reseñas quemadas
        2 -> {
            Column {
                Text("⭐ Reseñas:", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
                Text("4.5 / 5 basado en 120 reseñas", modifier = Modifier.padding(8.dp))
                Text("Ambiente: ⭐⭐⭐⭐", modifier = Modifier.padding(8.dp))
                Text("Servicio: ⭐⭐⭐⭐⭐", modifier = Modifier.padding(8.dp))
                Text("Precio: ⭐⭐⭐⭐", modifier = Modifier.padding(8.dp))
            }
        }

        //  Horarios desde el lugar
        3 -> {
            Column {
                Text("🕒 Horarios:", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
                if (place.schedules.isNotEmpty()) {
                    place.schedules.forEach { horario ->
                        Text(horario, modifier = Modifier.padding(8.dp))
                    }
                } else {
                    Text("No hay horarios disponibles", modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}


