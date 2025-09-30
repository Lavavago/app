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
import com.example.myapplication.ui.components.CustomDropdown // <<-- ¡Asegúrate de que esta ruta sea correcta!
import com.example.myapplication.viewmodel.CreatePlaceViewModel
import com.example.myapplication.viewmodel.SaveResult


// --- Listas de Opciones para los Dropdowns ---
private val CATEGORY_OPTIONS = listOf(
    "Restaurante", "Cafetería", "Museo", "Parque", "Librería", "Otro..."
)

private val SCHEDULE_OPTIONS = listOf(
    "Lunes a Sábado (8AM - 8PM)", "Lunes a Viernes (9AM - 5PM)", "Abierto 24 Horas"
)

private val LOCATION_OPTIONS = listOf(
    "Seleccionar en Mapa", "Usar Coordenada Actual"
)

private val PHOTOS_OPTIONS = listOf(
    "1 Foto", "3 Fotos", "5 Fotos o Más"
)
// ---------------------------------------------


@Composable
fun CreatePlaceScreen(
    createPlaceViewModel: CreatePlaceViewModel = viewModel(),
    onClose: () -> Unit = {}
) {
    // 1. Obtener el estado del ViewModel
    val name by createPlaceViewModel.name.collectAsState()
    val address by createPlaceViewModel.address.collectAsState()
    val category by createPlaceViewModel.category.collectAsState()
    val phone by createPlaceViewModel.phone.collectAsState()
    val schedule by createPlaceViewModel.schedule.collectAsState()
    val location by createPlaceViewModel.location.collectAsState()
    val photos by createPlaceViewModel.photos.collectAsState()
    val saveResult by createPlaceViewModel.saveResult.collectAsState()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // --- Encabezado y Título ---
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Código del Logo y uniLocal
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Nota: Asegúrate de tener R.drawable.gps2
                    Image(
                        painter = painterResource(id = R.drawable.gps2),
                        contentDescription = "Logo de ubicación uniLocal",
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "uniLocal", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = onClose) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Cerrar formulario")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Añade un lugar", fontSize = 24.sp, fontWeight = FontWeight.Normal, modifier = Modifier.weight(1f))
                Icon(imageVector = Icons.Filled.Create, contentDescription = "Editar título", tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f))
            }


            // --- CAMPOS DEL FORMULARIO DE LUGAR ---
            Spacer(modifier = Modifier.height(8.dp))

            // 1. Nombre del sitio (TextField)
            CustomTextField(
                value = name, onValueChange = createPlaceViewModel::onNameChange, placeholder = "Nombre del sitio"
            )
            Spacer(modifier = Modifier.height(16.dp))

            // 2. Dirección exacta (TextField)
            CustomTextField(
                value = address, onValueChange = createPlaceViewModel::onAddressChange, placeholder = "Dirección exacta"
            )
            Spacer(modifier = Modifier.height(16.dp))

            // 3. Categoría (Dropdown)
            CustomDropdown(
                label = "Categoría",
                list = CATEGORY_OPTIONS,
                onValueChange = createPlaceViewModel::onCategoryChange,
                initialValue = category
            )
            Spacer(modifier = Modifier.height(16.dp))

            // 4. Teléfono (TextField)
            CustomTextField(
                value = phone, onValueChange = createPlaceViewModel::onPhoneChange, placeholder = "Teléfono"
            )
            Spacer(modifier = Modifier.height(16.dp))

            // 5. Calendario para horarios (Dropdown)
            CustomDropdown(
                label = "Calendario para horarios",
                list = SCHEDULE_OPTIONS,
                onValueChange = createPlaceViewModel::onScheduleChange,
                initialValue = schedule
            )
            Spacer(modifier = Modifier.height(16.dp))

            // 6. Ubicación mapa interactivo (Dropdown)
            CustomDropdown(
                label = "Ubicación mapa interactivo",
                list = LOCATION_OPTIONS,
                onValueChange = createPlaceViewModel::onLocationChange,
                initialValue = location
            )
            Spacer(modifier = Modifier.height(16.dp))

            // 7. Fotografías (Dropdown)
            CustomDropdown(
                label = "Fotografías",
                list = PHOTOS_OPTIONS,
                onValueChange = createPlaceViewModel::onPhotosChange,
                initialValue = photos
            )

            Spacer(modifier = Modifier.height(48.dp))

            // --- Botón "Agregar" ---
            Button(
                onClick = createPlaceViewModel::savePlace,
                modifier = Modifier.fillMaxWidth().height(50.dp).padding(horizontal = 40.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text(text = "Agregar", fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            }

            // Mensaje de resultado
            if (saveResult != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = when (saveResult) {
                        is SaveResult.Success -> "¡Lugar guardado con éxito! Revisa la consola."
                        is SaveResult.Error -> "Error: Por favor, llena todos los campos."
                        else -> ""
                    },
                    color = if (saveResult is SaveResult.Success) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

// --- Componente auxiliar CustomTextField ---

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
        label = null,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
        )
    )
}
