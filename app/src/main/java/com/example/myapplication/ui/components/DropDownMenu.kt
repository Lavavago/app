package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdown( // Usaremos el nombre que definimos
    label: String,
    list: List<String>,
    onValueChange: (String) -> Unit,
    // 🔑 CLAVE: Añadimos un valor inicial, que viene del ViewModel (aunque esté vacío "")
    initialValue: String = ""
) {

    // 🔑 CLAVE: El estado local debe inicializarse con el valor que viene de fuera (el ViewModel)
    var expanded by remember { mutableStateOf(value = false) }
    var selectedItem by remember { mutableStateOf(value = initialValue) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {

        OutlinedTextField(
            readOnly = true,
            // 🔑 CLAVE: Usamos 'selectedItem' para mostrar el valor seleccionado.
            // Si el ViewModel hubiera cargado un valor inicial, también se mostraría.
            value = selectedItem,
            onValueChange = { },
            label = {
                // Si 'selectedItem' está vacío, usamos el 'label' (placeholder)
                Text(text = label)
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth(),
            // Estilo opcional para que se vea como un campo OutlinedTextField normal:
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            list.forEach { item -> // Renombramos 'it' a 'item' para mayor claridad
                DropdownMenuItem(
                    text = {
                        Text(text = item)
                    },
                    onClick = {
                        // 1. Actualiza el estado visual local
                        selectedItem = item
                        // 2. Notifica al ViewModel (¡Esto es lo que envía el dato!)
                        onValueChange(item)
                        // 3. Cierra el menú
                        expanded = false
                    }
                )
            }
        }
    }
}