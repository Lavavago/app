package com.example.myapplication.ui.components

import android.R.attr.label
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuBoxScope
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.HistoricalChange
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuBoxScope(
    label: String,
    list: List<String>,
    onValueChange: (String) -> Unit
    ){

    var expanded by remember { mutableStateOf( value = false) }
    var selectedItem by remember { mutableStateOf( value = "") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded}
    ) {

        OutlinedTextField(
            readOnly = true,
            value = selectedItem,
            onValueChange = { },
            label = {
                Text(
                    text =  label
                )
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)},
            modifier = Modifier
                .menuAnchor(type = MenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {expanded = false}
        ) {
            list.forEach{
                DropdownMenuItem(
                    text={
                        Text(text =it)
                    },
                    onClick = {
                        selectedItem = it
                        onValueChange(selectedItem)
                        expanded = false
                    }
                )
            }
        }
    }

}