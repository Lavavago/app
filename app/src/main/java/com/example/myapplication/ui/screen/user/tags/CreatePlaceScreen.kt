package com.example.myapplication.ui.screen

import android.Manifest
import android.app.TimePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.example.myapplication.model.DayOfWeek
import com.example.myapplication.model.PlaceType
import com.example.myapplication.ui.components.Map
import com.example.myapplication.viewmodel.CreatePlaceViewModel
import com.example.myapplication.viewmodel.PlacesViewModel
import com.example.myapplication.viewmodel.SaveResult
import com.mapbox.geojson.Point
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Locale
import java.text.SimpleDateFormat // Importación necesaria para el formato AM/PM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePlaceScreen(
    createPlaceViewModel: CreatePlaceViewModel = viewModel(),
    placesViewModel: PlacesViewModel,
    onClose: () -> Unit = {},
    onImageSelected: (String) -> Unit
) {

    val context = LocalContext.current

    // --- ESTADO DEL VIEWMODEL ---
    val title by createPlaceViewModel.title.collectAsState()
    val description by createPlaceViewModel.description.collectAsState()
    val address by createPlaceViewModel.address.collectAsState()
    val phone by createPlaceViewModel.phone.collectAsState()
    val photos by createPlaceViewModel.photos.collectAsState()
    val schedulesMap by createPlaceViewModel.schedules.collectAsState()
    val selectedType by createPlaceViewModel.type.collectAsState()
    val saveResult by createPlaceViewModel.saveResult.collectAsState()
    val locationState by createPlaceViewModel.location.collectAsState()


    var image by remember { mutableStateOf("")}

    val config = mapOf(
        "cloud_name" to "dxki0nzyu",
        "api_key" to "289513331981943",
        "api_secret" to "ojMlfLv7mkWs17-5V1-Yix_enoA"
    )

    val scope = rememberCoroutineScope()
    val cloudinary = Cloudinary(config)

    val fileLaucher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ){ uri: Uri? ->
        uri?.let {
            scope.launch(Dispatchers.IO) {
                val inputStream = context.contentResolver.openInputStream(it)
                inputStream?.use { stream ->
                    val result = cloudinary.uploader().upload(stream, ObjectUtils.emptyMap())
                    val imageUrl = result["secure_url"].toString()
                    image = imageUrl
                    onImageSelected(imageUrl)
                }
            }
        }

    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {
        if(it) {
            Toast.makeText(context, "Permiso concedido", Toast.LENGTH_SHORT).show()

        }else{
            Toast.makeText(context, "Permiso denegado", Toast.LENGTH_SHORT).show()
        }
    }





    // --- EFECTOS LATERALES ---
    // 1. Manejo del guardado exitoso
    LaunchedEffect(saveResult) {
        if (saveResult is SaveResult.Success) {
            val place = createPlaceViewModel.places.value.lastOrNull()
            if (place != null) {
                placesViewModel.addPlace(place)
            }

            // RESETEAR EL FORMULARIO después de guardar
            createPlaceViewModel.resetForm()

            onClose()
        }
    }

    // 2. Manejo del cierre manual
    DisposableEffect(Unit) {
        onDispose {
            // RESETEAR EL FORMULARIO si el usuario sale manualmente
            createPlaceViewModel.resetForm()
        }
    }

    // Convertimos Location del ViewModel a Point de Mapbox para el mapa
    val initialPoint = locationState?.let {
        Point.fromLngLat(it.longitude, it.latitude)
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Crear Nuevo Lugar",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                IconButton(onClick = onClose) {
                    Icon(Icons.Filled.Close, contentDescription = "Cerrar")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            // --- FORMULARIO DE ENTRADA DE DATOS ---
            CustomTextField(value = title, onValueChange = createPlaceViewModel::onTitleChange, placeholder = "Nombre del lugar")
            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(value = description, onValueChange = createPlaceViewModel::onDescriptionChange, placeholder = "Descripción")
            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(value = address, onValueChange = createPlaceViewModel::onAddressChange, placeholder = "Dirección")
            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(value = phone, onValueChange = createPlaceViewModel::onPhoneChange, placeholder = "Teléfono")
            Spacer(modifier = Modifier.height(16.dp))

            //  EDITOR DE HORARIOS INTERACTIVO
            DailyScheduleEditor(
                currentSchedules = schedulesMap,
                onScheduleChange = createPlaceViewModel::onDayScheduleChange
            )

            Spacer(modifier = Modifier.height(16.dp))

            // CustomTextField(value = photos, onValueChange = createPlaceViewModel::onPhotosChange, placeholder = "URL de foto ")
            Button(
                onClick = {
                    val permissonCheckResult = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_IMAGES)
                    } else {
                        ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                    }

                    if(permissonCheckResult == PackageManager.PERMISSION_GRANTED){
                        fileLaucher.launch("image/*")
                    }else{
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                            permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                        }else{
                            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }
                    }
                }
            ){
                Text(
                    text = "Seleccionar foto"
                )
            }

            AsyncImage(
                modifier = Modifier.width(200.dp),
                model = image,
                contentDescription = null
            )


            Spacer(modifier = Modifier.height(16.dp))

            // SELECTOR DE TIPO (CORREGIDO para UI/UX uniforme)
            PlaceTypeSelector(
                selectedType = selectedType,
                onTypeSelected = { createPlaceViewModel.onTypeChange(it) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 🗺️ MAPA para SELECCIONAR UBICACIÓN
            Text(
                text = "Selecciona la ubicación en el mapa (Requerido)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )

            Map(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp)),
                activateClik = true,
                initialLocation = initialPoint,
                onMapClickListener = { point ->
                    createPlaceViewModel.onLocationChange(point.latitude(), point.longitude())
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- BOTÓN GUARDAR ---
            Button(
                onClick = { createPlaceViewModel.savePlace() },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Guardar Lugar", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            }

            // Manejo de error visual
            if (saveResult is SaveResult.Error) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Error: Por favor, rellena todos los campos requeridos (Nombre, Descripción, Dirección y Ubicación en el mapa).",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}


// ----------------------------------------------------------------------------------
// --- COMPONENTES AUXILIARES DE HORARIOS INTERACTIVOS ---
// ----------------------------------------------------------------------------------

// COMPONENTE PRINCIPAL DE HORARIOS
@Composable
fun DailyScheduleEditor(
    currentSchedules: Map<DayOfWeek, String>,
    onScheduleChange: (DayOfWeek, String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
    ) {
        Text(
            text = "Configuración de Horarios (Toque el día para seleccionar la hora)",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        DayOfWeek.values().forEach { day ->
            ScheduleDayRow(
                day = day,
                initialSchedule = currentSchedules[day] ?: "",
                onScheduleChange = onScheduleChange
            )
        }
    }
}

// COMPONENTE AUXILIAR DE FILA (Tarjeta desplegable con TimePicker)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleDayRow(
    day: DayOfWeek,
    initialSchedule: String,
    onScheduleChange: (DayOfWeek, String) -> Unit
) {
    val context = LocalContext.current
    var scheduleText by rememberSaveable(key = day.name) { mutableStateOf(initialSchedule) }

    val displayText = when {
        scheduleText.equals("cerrado", true) -> "Cerrado"
        scheduleText.isBlank() -> "Sin horario / Cerrado"
        else -> scheduleText
    }

    LaunchedEffect(initialSchedule) {
        scheduleText = initialSchedule
    }

    // --- Lógica de TimePicker (AM/PM) ---
    val showStartTimePicker = {
        TimePickerDialog(
            context,
            { _, hourOfDay: Int, minute: Int ->
                val startHourFormatted = formatTimeForDisplay(hourOfDay, minute)
                showEndTimePicker(context, startHourFormatted, day, onScheduleChange)
            }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            Calendar.getInstance().get(Calendar.MINUTE),
            false // Formato 12 horas (AM/PM)
        ).show()
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = showStartTimePicker,
        colors = CardDefaults.cardColors(
            containerColor = if (scheduleText.isNotBlank() && !scheduleText.equals("cerrado", true)) Color(0xFFF0E6FF) else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = day.name.lowercase().replaceFirstChar { it.uppercase() },
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = displayText,
                    color = when {
                        scheduleText.equals("cerrado", true) -> MaterialTheme.colorScheme.error
                        scheduleText.isNotBlank() -> MaterialTheme.colorScheme.primary
                        else -> Color.Gray
                    }
                )
            }

            // Botones para acción rápida
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                // Opción para seleccionar directamente "Cerrado"
                TextButton(onClick = {
                    val closed = "Cerrado"
                    scheduleText = closed
                    onScheduleChange(day, closed)
                }) {
                    Text("Cerrado", color = MaterialTheme.colorScheme.error)
                }

                // Opción para abrir el selector
                TextButton(onClick = showStartTimePicker) {
                    Text("Seleccionar Horas")
                }
            }
        }
    }
}

// Función auxiliar para formatear hora y minuto en formato AM/PM (Ej: "09:30 AM")
private fun formatTimeForDisplay(hourOfDay: Int, minute: Int): String {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
    calendar.set(Calendar.MINUTE, minute)

    val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
    return formatter.format(calendar.time)
}

// Función auxiliar para mostrar el selector de hora de fin (AM/PM)
private fun showEndTimePicker(
    context: Context,
    startHourFormatted: String,
    day: DayOfWeek,
    onScheduleChange: (DayOfWeek, String) -> Unit
) {
    TimePickerDialog(
        context,
        { _, endHour: Int, endMinute: Int ->
            val endHourFormatted = formatTimeForDisplay(endHour, endMinute)
            val finalSchedule = "$startHourFormatted - $endHourFormatted"
            onScheduleChange(day, finalSchedule)
        }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
        Calendar.getInstance().get(Calendar.MINUTE),
        false // Formato 12 horas (AM/PM)
    ).show()
}


// ----------------------------------------------------------------------------------
// --- OTRAS FUNCIONES AUXILIARES (MODIFICADAS) ---
// ----------------------------------------------------------------------------------

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
fun PlaceTypeSelector(
    selectedType: PlaceType,
    onTypeSelected: (PlaceType) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    // Usamos ExposedDropdownMenuBox para simular el look de OutlinedTextField
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp) // Añadimos padding superior para el espacio
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(), // Esencial para el dropdown
            readOnly = true,
            value = selectedType.name,
            onValueChange = { /* Solo lectura */ },
            label = { Text("Tipo de lugar") }, // Label que reemplaza el texto anterior
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            PlaceType.values().forEach { type ->
                DropdownMenuItem(
                    text = { Text(type.name) },
                    onClick = {
                        onTypeSelected(type)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}