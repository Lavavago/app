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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.ui.components.CustomDropdown
import com.example.myapplication.viewmodel.CreatePlaceViewModel
import com.example.myapplication.viewmodel.SaveResult


@Composable
fun CreatePlaceScreen(
    createPlaceViewModel: CreatePlaceViewModel = viewModel(),
    onClose: () -> Unit = {}
) {

    val name by createPlaceViewModel.name.collectAsState()
    val address by createPlaceViewModel.address.collectAsState()
    val category by createPlaceViewModel.category.collectAsState()
    val phone by createPlaceViewModel.phone.collectAsState()
    val schedule by createPlaceViewModel.schedule.collectAsState()
    val location by createPlaceViewModel.location.collectAsState()
    val photos by createPlaceViewModel.photos.collectAsState()
    val saveResult by createPlaceViewModel.saveResult.collectAsState()


    val categoryOptions = stringArrayResource(id = R.array.category_options).toList()
    val scheduleOptions = stringArrayResource(id = R.array.schedule_options).toList()
    val locationOptions = stringArrayResource(id = R.array.location_options).toList()
    val photosOptions = stringArrayResource(id = R.array.photos_options).toList()


    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
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
                    Icon(imageVector = Icons.Filled.Close, contentDescription = stringResource(R.string.txt_back))
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.add_place_title),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.weight(1f)
                )
                Icon(imageVector = Icons.Filled.Create, contentDescription = stringResource(R.string.edit_profile), tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f))
            }


            Spacer(modifier = Modifier.height(8.dp))


            CustomTextField(
                value = name,
                onValueChange = createPlaceViewModel::onNameChange,
                placeholder = stringResource(R.string.name_label)
            )
            Spacer(modifier = Modifier.height(16.dp))


            CustomTextField(
                value = address,
                onValueChange = createPlaceViewModel::onAddressChange,
                placeholder = stringResource(R.string.address_label)
            )
            Spacer(modifier = Modifier.height(16.dp))


            CustomDropdown(
                label = stringResource(R.string.category_label),
                list = categoryOptions,
                onValueChange = createPlaceViewModel::onCategoryChange,
                initialValue = category
            )
            Spacer(modifier = Modifier.height(16.dp))


            CustomTextField(
                value = phone,
                onValueChange = createPlaceViewModel::onPhoneChange,
                placeholder = stringResource(R.string.phone_label)
            )
            Spacer(modifier = Modifier.height(16.dp))


            CustomDropdown(
                label = stringResource(R.string.schedule_label),
                list = scheduleOptions,
                onValueChange = createPlaceViewModel::onScheduleChange,
                initialValue = schedule
            )
            Spacer(modifier = Modifier.height(16.dp))


            CustomDropdown(
                label = stringResource(R.string.map_location_label),
                list = locationOptions,
                onValueChange = createPlaceViewModel::onLocationChange,
                initialValue = location
            )
            Spacer(modifier = Modifier.height(16.dp))


            CustomDropdown(
                label = stringResource(R.string.photos_label),
                list = photosOptions,
                onValueChange = createPlaceViewModel::onPhotosChange,
                initialValue = photos
            )

            Spacer(modifier = Modifier.height(48.dp))


            Button(
                onClick = createPlaceViewModel::savePlace,
                modifier = Modifier.fillMaxWidth().height(50.dp).padding(horizontal = 40.dp),
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text(
                    text = stringResource(R.string.add_button),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }


            if (saveResult != null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = when (saveResult) {
                        is SaveResult.Success -> stringResource(R.string.place_saved_success) + " (Revisa la consola)"
                        is SaveResult.Error -> stringResource(R.string.place_saved_error)
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