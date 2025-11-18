package com.example.myapplication.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.model.PlaceType
import com.example.myapplication.ui.components.Map
import com.example.myapplication.viewmodel.CreatePlaceViewModel
import com.example.myapplication.viewmodel.PlacesViewModel
import com.example.myapplication.viewmodel.SaveResult
import com.mapbox.geojson.Point

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePlaceScreen(
    createPlaceViewModel: CreatePlaceViewModel = viewModel(),
    placesViewModel: PlacesViewModel,
    onClose: () -> Unit = {}
) {

    var clickedPoint by rememberSaveable { mutableStateOf<Point?>(null) }

    val title by createPlaceViewModel.title.collectAsState()
    val description by createPlaceViewModel.description.collectAsState()
    val address by createPlaceViewModel.address.collectAsState()
    val phone by createPlaceViewModel.phone.collectAsState()
    val photos by createPlaceViewModel.photos.collectAsState()
    val schedule by createPlaceViewModel.schedule.collectAsState()
    val selectedType by createPlaceViewModel.type.collectAsState()
    val saveResult by createPlaceViewModel.saveResult.collectAsState()

    LaunchedEffect(saveResult) {
        if (saveResult is SaveResult.Success) {

            // Ya está creado, simplemente agréguelo
            val place = createPlaceViewModel.places.value.lastOrNull()
            if (place != null) {
                placesViewModel.addPlace(place)
            }

            onClose()
        }
    }


    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            // Encabezado
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.gps2),
                        contentDescription = stringResource(R.string.location_icon_desc),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.app_title),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = onClose) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(R.string.txt_back)
                    )
                }
            }

            // Título
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.add_place_title),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Filled.Create,
                    contentDescription = stringResource(R.string.edit_profile),
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                )
            }

            // FORMULARIO
            CustomTextField(value = title, onValueChange = createPlaceViewModel::onTitleChange, placeholder = "Nombre del lugar")
            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(value = description, onValueChange = createPlaceViewModel::onDescriptionChange, placeholder = "Descripción")
            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(value = address, onValueChange = createPlaceViewModel::onAddressChange, placeholder = "Dirección")
            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(value = phone, onValueChange = createPlaceViewModel::onPhoneChange, placeholder = "Teléfono")
            Spacer(modifier = Modifier.height(16.dp))

            ScheduleDropdown(
                selectedSchedule = schedule,
                onScheduleSelected = createPlaceViewModel::onScheduleChange
            )
            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(value = photos, onValueChange = createPlaceViewModel::onPhotosChange, placeholder = "URL de foto")
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Tipo de lugar",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            PlaceTypeSelector(
                selectedType = selectedType,
                onTypeSelected = { createPlaceViewModel.onTypeChange(it) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ============================
            //        MAPA
            // ============================
            Text(
                text = "Ubicación",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {}
                ) {
                    Map(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(6.dp),
                        activateClik = true,
                        onMapClickListener = { point ->
                            clickedPoint = point
                            createPlaceViewModel.onLocationChange(point.latitude(), point.longitude())
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            // BOTÓN GUARDAR
            Button(
                onClick = { createPlaceViewModel.savePlace() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 40.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text(
                    text = "Guardar lugar",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            if (saveResult != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = when (saveResult) {
                        is SaveResult.Success -> "Lugar guardado correctamente"
                        is SaveResult.Error -> "Por favor, completa todos los campos y selecciona la ubicación"
                        else -> ""
                    },
                    color = if (saveResult is SaveResult.Success)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleDropdown(
    selectedSchedule: String,
    onScheduleSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val scheduleOptions = listOf(
        "Lunes - Viernes 8:00 AM - 8:00 PM",
        "Sábados - Domingos - Festivos 9:00 AM - 6:00 PM",
        "Abierto 24 horas",
        "Solo fines de semana"
    )

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        TextField(
            value = selectedSchedule,
            onValueChange = {},
            readOnly = true,
            label = { Text("Selecciona un horario") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            scheduleOptions.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onScheduleSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun PlaceTypeSelector(
    selectedType: PlaceType,
    onTypeSelected: (PlaceType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(onClick = { expanded = !expanded }) {
            Text(text = selectedType.name)
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            PlaceType.values().forEach { type ->
                DropdownMenuItem(
                    text = { Text(type.name) },
                    onClick = {
                        onTypeSelected(type)
                        expanded = false
                    }
                )
            }
        }
    }
}
