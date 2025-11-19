package com.example.myapplication.ui.screen.user.tags

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.model.DayOfWeek
import com.example.myapplication.model.Place
import com.example.myapplication.ui.components.sharePlaceGoogle
import com.example.myapplication.viewmodel.PlacesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetail(
    placesViewModel: PlacesViewModel,
    padding: PaddingValues,
    id: String
) {
    val place = placesViewModel.findById(id) ?: return

    LaunchedEffect(id) {
        placesViewModel.increaseVisits(id)
    }

    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .background(Color(0xFFF5F3FF))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // --- Header con botón de compartir ---
        val context = LocalContext.current

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = place.title,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF4A148C)
            )

            IconButton(onClick = {
                sharePlaceGoogle(context, place)   // o sharePlaceMapbox(context, place)
            }) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Compartir",
                    tint = Color(0xFF7C3AED)
                )
            }
        }
        Spacer(Modifier.height(4.dp))


// ⬇⬇⬇ CONTADOR VISITAS ⬇⬇⬇
        Text(
            text = "Visitado ${place.visits} veces",
            fontSize = 14.sp,
            color = Color(0xFF7C3AED), // Púrpura bonito
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(16.dp))

        // --- Galería de Imágenes ---
        if (place.images.isNotEmpty()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(place.images) { imageUrl ->
                    Image(
                        painter = rememberAsyncImagePainter(imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // --- Tabs interactivos ---
        PlaceTabs(place)


        Spacer(Modifier.height(16.dp))

        // --- Footer (Ejemplo de implementación básica) ---
        Divider(color = Color(0xFFE0E0E0))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("¡Disfruta tu visita!", color = Color.Gray, fontSize = 14.sp)
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
        contentColor = Color(0xFF7C3AED), // Púrpura principal
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
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
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

                // Dirección
                InfoCard(Icons.Default.LocationOn, "Dirección", place.address)

                // Teléfonos
                if (place.phones.isNotEmpty()) {
                    InfoCard(Icons.Default.Phone, "Teléfonos", place.phones.joinToString(", "))
                }
            }
        }

        //  Comentarios quemados (Mantenido como placeholder)
        1 -> {
            Text("Aquí iría la lista de comentarios.", color = Color.Gray)
            // Ejemplo de un comentario
            ReviewCard(user = "Usuario 1", rating = 5, comment = "¡Me encantó el ambiente y la comida!")
        }


        //  Reseñas quemadas (Mantenido como placeholder)
        2 -> {
            Text("Aquí iría el formulario para dejar una reseña.", color = Color.Gray)
            // Ejemplo de una reseña
            ReviewCard(user = "Usuario 2", rating = 4, comment = "Un poco lento el servicio, pero vale la pena.")
        }

        //  Horarios desde el lugar (¡CORREGIDO: Usa el ScheduleList!)
        3 -> {
            Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                Text("🕒 Horarios de Atención:", fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))

                // USAMOS EL NUEVO COMPONENTE para mostrar el mapa
                if (place.schedules.isEmpty()) {
                    Text(
                        "No hay horarios disponibles para este lugar.",
                        modifier = Modifier.padding(top = 8.dp),
                        color = Color.Gray
                    )
                } else {
                    ScheduleList(schedules = place.schedules)
                }
            }
        }
    }
}


// ----------------------------------------------------------------------------------
// --- COMPONENTES DE SOPORTE ADICIONALES ---
// ----------------------------------------------------------------------------------

@Composable
fun InfoCard(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, content: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = Color(0xFF7C3AED),
                modifier = Modifier.size(24.dp).padding(end = 8.dp)
            )
            Column {
                Text(title, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                Text(content, fontSize = 14.sp, color = Color.DarkGray)
            }
        }
    }
}

@Composable
fun ReviewCard(user: String, rating: Int, comment: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.Star,
                    contentDescription = "Rating",
                    tint = Color(0xFFFFC107),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "$rating/5",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Spacer(Modifier.width(8.dp))
                Text("— $user", color = Color.Gray, fontSize = 14.sp)
            }
            Spacer(Modifier.height(4.dp))
            Text(comment, color = Color.DarkGray, fontSize = 14.sp)
        }
    }
}

// ----------------------------------------------------------------------------------
// --- NUEVO COMPONENTE DE HORARIOS PARA DETALLE (Estaba Correcto) ---
// ----------------------------------------------------------------------------------

@Composable
fun ScheduleList(schedules: Map<DayOfWeek, String>) {

    if (schedules.isEmpty()) {
        return
    }

    Column {
        schedules.forEach { (day, time) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Día (Formateado: Lunes, Martes, etc.)
                    Text(
                        text = day.name.lowercase().replaceFirstChar { it.uppercase() },
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    // Horario
                    Text(
                        text = time,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF7C3AED)
                    )
                }
            }
        }
    }
}