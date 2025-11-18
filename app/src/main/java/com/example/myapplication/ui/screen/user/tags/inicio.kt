package com.example.myapplication.ui.screen.user.tags

import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.R
import com.example.myapplication.ui.components.Map
import com.example.myapplication.ui.screen.LocalMainViewModel
import com.example.myapplication.viewmodel.PlacesViewModel
import com.example.myapplication.ui.screen.user.nav.RouteTab
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.rememberIconImage
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.viewport.data.DefaultViewportTransitionOptions

@Composable
fun inicio(navController: NavController, placesViewModel: PlacesViewModel = viewModel()) {
    Column(modifier = Modifier.fillMaxSize()) {

        TopSearchAndFilterSection(
            modifier = Modifier.fillMaxWidth(),
            navController = navController,
            placesViewModel = placesViewModel
        )

        val placesViewModel = LocalMainViewModel.current.placesViewModel
        val places by placesViewModel.places.collectAsState()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .background(Color(0xFFE0E0E0), RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {

            Map (
                places = places,
                modifier = Modifier.fillMaxWidth()
            )

        }

        Text(
            text = "Armenia / Quindío",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(24.dp)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopSearchAndFilterSection(
    modifier: Modifier = Modifier,
    navController: NavController,
    placesViewModel: PlacesViewModel
) {
    Column(
        modifier = modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        var query by rememberSaveable { mutableStateOf("") }
        var expanded by rememberSaveable { mutableStateOf(false) }

        // Observamos los lugares quemados desde el ViewModel
        val places by placesViewModel.places.collectAsState()

        // Filtramos los lugares en tiempo real
        val filteredPlaces = remember(query, places) {
            if (query.isBlank()) places
            else places.filter {
                it.title.contains(query, ignoreCase = true)
            }
        }

        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            inputField = {
                SearchBarDefaults.InputField(
                    query = query,
                    onQueryChange = { newQuery ->
                        query = newQuery
                        expanded = true // muestra sugerencias al escribir
                    },
                    onSearch = {
                        expanded = false
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = { Text(text = "Buscar aquí") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Perfil",
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .clickable {
                                    navController.navigate(RouteTab.ProfileScreen) {
                                        popUpTo(RouteTab.Inicio) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                        )
                    }
                )
            },
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            // Sugerencias filtradas en tiempo real
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                if (filteredPlaces.isEmpty()) {
                    Text(
                        text = "No se encontraron lugares",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                } else {
                    filteredPlaces.forEach { place ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    query = place.title
                                    expanded = false
                                }
                                .padding(vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(place.images.firstOrNull()),
                                contentDescription = place.title,
                                modifier = Modifier
                                    .size(45.dp)
                                    .clip(RoundedCornerShape(10.dp)),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Column {
                                Text(
                                    text = place.title,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 15.sp,
                                    color = Color.Black
                                )
                                Text(
                                    text = place.description,
                                    fontSize = 13.sp,
                                    color = Color.Gray,
                                    maxLines = 1
                                )
                            }
                        }
                        Divider(color = Color.LightGray, thickness = 0.5.dp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // --- Chips de Filtro ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CustomFilterChip(label = "Restaurante", iconRes = R.drawable.cuchara)
            CustomFilterChip(label = "Cafetería", iconRes = R.drawable.cafeteria)
            CustomFilterChip(label = "Museo", iconRes = R.drawable.cuadro)
            CustomFilterChip(label = "Comida rápida", iconRes = R.drawable.hamburguesa)
            CustomFilterChip(label = "Hotel", iconRes = R.drawable.recurso)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun CustomFilterChip(label: String, iconRes: Int) {
    var isSelected by remember { mutableStateOf(false) }

    Surface(
        onClick = { isSelected = !isSelected },
        shape = RoundedCornerShape(50),
        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.White,
        border = BorderStroke(1.dp, if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray),
        modifier = Modifier.shadow(
            elevation = 1.dp,
            shape = RoundedCornerShape(50),
            ambientColor = Color.Black.copy(alpha = 0.05f),
            spotColor = Color.Black.copy(alpha = 0.1f)
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = label,
                tint = if (isSelected) Color.White else Color.Gray.copy(alpha = 0.7f),
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(6.dp))
            Text(
                text = label,
                color = if (isSelected) Color.White else Color.Gray.copy(alpha = 0.7f),
                fontSize = 14.sp
            )
        }
    }
}
