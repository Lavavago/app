package com.example.myapplication.ui.screen.user.tags

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.R
import com.example.myapplication.model.PlaceType
import com.example.myapplication.ui.components.FilterChipItem
import com.example.myapplication.ui.components.Map
import com.example.myapplication.ui.screen.LocalMainViewModel
import com.example.myapplication.ui.screen.user.nav.RouteTab
import com.example.myapplication.viewmodel.PlacesViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun inicio(navController: NavController, placesViewModel: PlacesViewModel = viewModel()) {

    val placesViewModel = LocalMainViewModel.current.placesViewModel
    val places by placesViewModel.places.collectAsState()

    // Estados de búsqueda y filtros
    var query by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }
    var selectedFilters by remember { mutableStateOf<List<PlaceType>>(emptyList()) }

    // --- Filtro por búsqueda ---
    val filteredBySearch = remember(query, places) {
        if (query.isBlank()) places
        else places.filter { it.title.contains(query, ignoreCase = true) }
    }

    // --- Filtro por tipos ---
    val filteredByType = remember(selectedFilters, filteredBySearch) {
        if (selectedFilters.isEmpty()) filteredBySearch
        else filteredBySearch.filter { selectedFilters.contains(it.type) }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // --- Barra de Búsqueda + Perfil ---
        // --- Barra de Búsqueda + Perfil ---
        SearchBar(
            query = query,
            onQueryChange = {
                query = it
                expanded = true
            },
            onSearch = {
                expanded = false
            },
            active = expanded,
            onActiveChange = { expanded = it },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Buscar",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            trailingIcon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Perfil",
                    modifier = Modifier.clickable {
                        navController.navigate(RouteTab.ProfileScreen)
                    }
                )
            },
            placeholder = { Text("Buscar aquí") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            // --- SUGERENCIAS ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                if (filteredBySearch.isEmpty()) {
                    Text("No se encontraron lugares", color = Color.Gray)
                } else {
                    filteredBySearch.forEach { place ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    query = place.title
                                    expanded = false
                                }
                                .padding(vertical = 8.dp),
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
                            Spacer(Modifier.width(10.dp))
                            Column {
                                Text(place.title, fontWeight = FontWeight.Bold)
                                Text(
                                    place.description,
                                    fontSize = 13.sp,
                                    color = Color.Gray,
                                    maxLines = 1
                                )
                            }
                        }
                        Divider()
                    }
                }
            }
        }


        // --- FILTROS POR TIPO ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            FilterChipItem(
                label = "Restaurante",
                iconRes = R.drawable.cuchara,
                type = PlaceType.RESTAURANTE,
                selectedFilters = selectedFilters,
                onChange = { selectedFilters = it }
            )

            FilterChipItem(
                label = "Cafetería",
                iconRes = R.drawable.cafeteria,
                type = PlaceType.CAFE,
                selectedFilters = selectedFilters,
                onChange = { selectedFilters = it }
            )

            FilterChipItem(
                label = "Museo",
                iconRes = R.drawable.cuadro,
                type = PlaceType.MUSEO,
                selectedFilters = selectedFilters,
                onChange = { selectedFilters = it }
            )

            FilterChipItem(
                label = "Hotel",
                iconRes = R.drawable.recurso,
                type = PlaceType.HOTEL,
                selectedFilters = selectedFilters,
                onChange = { selectedFilters = it }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // --- MAPA ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp)
                .background(Color(0xFFE0E0E0), RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Map(
                places = filteredByType,
                modifier = Modifier.fillMaxSize()
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
