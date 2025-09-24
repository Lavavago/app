package com.example.myapplication.ui.screen.tags

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.ui.components.CustomTextField
import com.example.myapplication.ui.components.CustomButton
import com.example.myapplication.viewmodel.CreatePlaceViewModel
import com.example.myapplication.viewmodel.SaveResult

@Composable
fun CreatePlaceScreen(createPlaceViewModel: CreatePlaceViewModel = viewModel()) {
    val name by createPlaceViewModel.name.collectAsState()
    val address by createPlaceViewModel.address.collectAsState()
    val category by createPlaceViewModel.category.collectAsState()
    val phone by createPlaceViewModel.phone.collectAsState()
    val schedule by createPlaceViewModel.schedule.collectAsState()
    val location by createPlaceViewModel.location.collectAsState()
    val photos by createPlaceViewModel.photos.collectAsState()
    val saveResult by createPlaceViewModel.saveResult.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {


        Icon(
            imageVector = Icons.Filled.LocationOn,
            contentDescription = stringResource(R.string.location_icon_desc),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(60.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.app_title),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.add_place_title),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(32.dp))


        CustomTextField(
            value = name,
            onValueChange = { createPlaceViewModel.onNameChange(it) },
            label = stringResource(R.string.name_label)
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = address,
            onValueChange = { createPlaceViewModel.onAddressChange(it) },
            label = stringResource(R.string.address_label)
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = category,
            onValueChange = { createPlaceViewModel.onCategoryChange(it) },
            label = stringResource(R.string.category_label)
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = phone,
            onValueChange = { createPlaceViewModel.onPhoneChange(it) },
            label = stringResource(R.string.phone_label)
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = schedule,
            onValueChange = { createPlaceViewModel.onScheduleChange(it) },
            label = stringResource(R.string.schedule_label)
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = location,
            onValueChange = { createPlaceViewModel.onLocationChange(it) },
            label = stringResource(R.string.map_location_label)
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = photos,
            onValueChange = { createPlaceViewModel.onPhotosChange(it) },
            label = stringResource(R.string.photos_label)
        )

        Spacer(modifier = Modifier.height(32.dp))


        CustomButton(
            onClick = { createPlaceViewModel.savePlace() },
            text = stringResource(R.string.add_button)
        )

        Spacer(modifier = Modifier.height(16.dp))


        saveResult?.let { result ->
            Text(
                text = when (result) {
                    SaveResult.Success -> stringResource(R.string.place_saved_success)
                    SaveResult.Error -> stringResource(R.string.place_saved_error)
                },
                color = when (result) {
                    SaveResult.Success -> MaterialTheme.colorScheme.primary
                    SaveResult.Error -> MaterialTheme.colorScheme.error
                },
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}




