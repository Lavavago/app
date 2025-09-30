package com.example.myapplication.ui.screen.user.tags

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
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.screen.user.nav.RouteTab

@Composable
fun inicio(navController: NavController) {
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


@Composable
fun TopSearchAndFilterSection(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        // --- Barra de Búsqueda ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .shadow(
                    elevation = 8.dp, // Sombra muy alta
                    shape = RoundedCornerShape(28.dp),
                    ambientColor = Color.Black.copy(alpha = 0.3f),
                    spotColor = Color.Black.copy(alpha = 0.5f)
                )
                .background(Color.White, RoundedCornerShape(28.dp))
                .height(56.dp)
                .clickable { /* Lógica de búsqueda */ },
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    Icons.Default.Search,
                    contentDescription = "Buscar",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.width(8.dp))

                Text(
                    text = "Buscar aquí",
                    color = Color.Gray,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )


                Icon(
                    Icons.Default.Person,
                    contentDescription = "Perfil",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable {
                        navController.navigate(RouteTab.ProfileScreen) {
                            popUpTo(RouteTab.Inicio) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // --- Chips de Filtro ---
        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(label = "Restaurante", iconRes = R.drawable.cuchara)
            FilterChip(label = "Cafetería", iconRes = R.drawable.cafeteria)
            FilterChip(label = "Museo", iconRes = R.drawable.cuadro)
            FilterChip(label="comida rapida",iconRes = R.drawable.hamburguesa)
            FilterChip(label="Hotel",iconRes = R.drawable.recurso)

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