package com.example.myapplication.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.components.CustomButton
import com.example.myapplication.ui.components.CustomTextField
import com.example.myapplication.ui.components.CustomDropdownT

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.myapplication.model.City
import com.example.myapplication.model.DisplayableEnum
import com.example.myapplication.model.Role
import com.example.myapplication.model.User
import java.util.UUID
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place


@Composable
fun RegisterUserScreen(
    onNavigateToLogin: () -> Unit
) {

    val usersViewModel = LocalMainViewModel.current.usersViewModel

    var city by remember { mutableStateOf<DisplayableEnum>(City.ARMENIA) }
    val cities = City.entries

    var name by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.gps2),
            contentDescription = stringResource(R.string.user_icon_desc),
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))


        Text(
            text = stringResource(R.string.app_title),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Título de la pantalla
        Text(
            text = stringResource(R.string.create_user_title),
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        CustomTextField(
            value = name,
            onValueChange = { name = it },
            placeholder = stringResource(R.string.name2_label)
        )
        Spacer(modifier = Modifier.height(16.dp))


        CustomTextField(
            value = username,
            onValueChange = { username = it },
            placeholder = stringResource(R.string.username_label)
        )
        Spacer(modifier = Modifier.height(16.dp))


        CustomTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = stringResource(R.string.email_label)
        )
        Spacer(modifier = Modifier.height(16.dp))


        CustomTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = stringResource(R.string.password_label),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))


        CustomDropdownT(
            label = stringResource(R.string.txt_city),
            supportingText = stringResource(R.string.txt_city_error),
            list = cities,
            icon = Icons.Outlined.Place,
            onValueChange = {
                city = it
            }
        )

        Spacer(modifier = Modifier.height(32.dp))


        CustomButton(
            onClick = {
                val user = User(
                    id = UUID.randomUUID().toString(),
                    name = name,
                    username = email,
                    city = city as City,
                    email = email,
                    role = Role.USER,
                    password = password
                )
                usersViewModel.create(user)
                onNavigateToLogin()
                      },
            text = stringResource(R.string.save_user_button) // Usando "Guardar Usuario"
        )


        Spacer(modifier = Modifier.height(16.dp))

        /*saveResult?.let { result ->
            Text(
                text = when (result) {
                    UserSaveResult.Success -> stringResource(R.string.user_created_success)
                    UserSaveResult.Error -> stringResource(R.string.user_created_error)
                },
                color = when (result) {
                    UserSaveResult.Success -> MaterialTheme.colorScheme.primary
                    UserSaveResult.Error -> MaterialTheme.colorScheme.error
                },
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }*/

        Spacer(modifier = Modifier.height(16.dp))


        TextButton(onClick = onNavigateToLogin) {
            Text(text = stringResource(R.string.profile_return_login))
        }
    }
}