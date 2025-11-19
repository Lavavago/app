package com.example.myapplication.ui.screen.admin2.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.screen.admin2.nav.AdminScreen

@Composable
fun PrincipalScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {

        TopSearchAndFilterSection(
            modifier = Modifier.fillMaxWidth(),
            navController = navController
        )


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 8.dp)

                .background(Color(0xFFE0E0E0), RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Área del Mapa (Placeholder)",
                color = Color.DarkGray,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Text(
            text = "Armenia/Quindio",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(24.dp)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopSearchAndFilterSection(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        // estado del query y expanded
        var query by rememberSaveable { mutableStateOf("") }
        var expanded by rememberSaveable { mutableStateOf(false) }

        // SearchBar (Material3, experimental)
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            inputField = {
                SearchBarDefaults.InputField(
                    query = query,
                    onQueryChange = { query = it },
                    // onSearch recibe el string de la búsqueda
                    onSearch = { searchQuery ->
                        expanded = false
                        // Aquí ejecutas la lógica de búsqueda con searchQuery o con `query`
                        // p.e. viewModel.search(searchQuery)
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
                                    navController.navigate(AdminScreen.Profile) {
                                        popUpTo(AdminScreen.Principal) { saveState = true }
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
            // opcional preguntar a laura si se implementa
            // contenido mostrado al expandir (sugerencias)
            // Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
               // Text(text = "Sugerencia 1: Café en Armenia")
               // Spacer(modifier = Modifier.height(6.dp))
               // Text(text = "Sugerencia 2: Restaurante cerca")
               // Spacer(modifier = Modifier.height(6.dp))
               // Text(text = "Sugerencia 3: Museo del Oro")
            // }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // --- Chips de Filtro  ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(label = "Restaurante", iconRes = R.drawable.cuchara)
            FilterChip(label = "Cafetería", iconRes = R.drawable.cafeteria)
            FilterChip(label = "Museo", iconRes = R.drawable.cuadro)
            FilterChip(label = "Comida rápida", iconRes = R.drawable.hamburguesa)
            FilterChip(label = "Hotel", iconRes = R.drawable.recurso)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}


@Composable
fun FilterChip(label: String, iconRes: Int) {
    var isSelected by remember { mutableStateOf(false) }

    Surface(
        onClick = { isSelected = !isSelected },
        shape = RoundedCornerShape(50),
        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.White,
        border = BorderStroke(1.dp, if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray),
        // Sombra de Chips (sutil)
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