package com.example.myapplication.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R

import com.example.myapplication.ui.components.CustomTextField
import com.example.myapplication.ui.components.CustomButton
import com.example.myapplication.viewmodel.CreatePlaceViewModel

@Composable
fun CreatePlaceScreen(createPlaceViewModel: CreatePlaceViewModel = viewModel()) {

    val name by createPlaceViewModel.name.collectAsState()
    val address by createPlaceViewModel.address.collectAsState()
    val category by createPlaceViewModel.category.collectAsState()
    val phone by createPlaceViewModel.phone.collectAsState()
    val schedule by createPlaceViewModel.schedule.collectAsState()
    val location by createPlaceViewModel.location.collectAsState()
    val photos by createPlaceViewModel.photos.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()), // hace scroll vertical
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        // Icono y título
        Icon(
            imageVector = Icons.Filled.LocationOn,
            contentDescription = "Ubicación",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(60.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "uniLocal",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Añade un lugar",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Campos de texto
        CustomTextField(
            value = name,
            onValueChange = { createPlaceViewModel.onNameChange(it) },
            label = "Nombre del sitio"
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = address,
            onValueChange = { createPlaceViewModel.onAddressChange(it) },
            label = "Dirección exacta"
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = category,
            onValueChange = { createPlaceViewModel.onCategoryChange(it) },
            label = "Categoría"
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = phone,
            onValueChange = { createPlaceViewModel.onPhoneChange(it) },
            label = "Teléfono"
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = schedule,
            onValueChange = { createPlaceViewModel.onScheduleChange(it) },
            label = "Calendario para horarios"
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = location,
            onValueChange = { createPlaceViewModel.onLocationChange(it) },
            label = "Ubicación mapa interactivo"
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = photos,
            onValueChange = { createPlaceViewModel.onPhotosChange(it) },
            label = "Fotografías"
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón
        CustomButton(
            onClick = { createPlaceViewModel.savePlace() },
            text = "Agregar"
        )
    }
}


