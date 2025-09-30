package com.example.myapplication.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.ui.components.CustomButton
import com.example.myapplication.ui.components.CustomTextField
import com.example.myapplication.viewmodel.CreateUserViewModel
import com.example.myapplication.viewmodel.UserSaveResult

@Composable
fun RegisterUserScreen(
    createUserViewModel: CreateUserViewModel = viewModel(),
    onLogout: () -> Unit
) {

    val name by createUserViewModel.name.collectAsState()
    val username by createUserViewModel.username.collectAsState()
    val email by createUserViewModel.email.collectAsState()
    val password by createUserViewModel.password.collectAsState()
    val city by createUserViewModel.city.collectAsState()

    val saveResult by createUserViewModel.userSaveResult.collectAsState()

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
            onValueChange = { createUserViewModel.onNameChange(it) },
            placeholder = stringResource(R.string.name2_label)
        )
        Spacer(modifier = Modifier.height(16.dp))


        CustomTextField(
            value = username,
            onValueChange = { createUserViewModel.onUsernameChange(it) },
            placeholder = stringResource(R.string.username_label)
        )
        Spacer(modifier = Modifier.height(16.dp))


        CustomTextField(
            value = email,
            onValueChange = { createUserViewModel.onEmailChange(it) },
            placeholder = stringResource(R.string.email_label)
        )
        Spacer(modifier = Modifier.height(16.dp))


        CustomTextField(
            value = password,
            onValueChange = { createUserViewModel.onPasswordChange(it) },
            placeholder = stringResource(R.string.password_label),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))


        CustomTextField(
            value = city,
            onValueChange = { createUserViewModel.onCityChange(it) },
            placeholder = stringResource(R.string.profile_city)
        )

        Spacer(modifier = Modifier.height(32.dp))


        CustomButton(
            onClick = { createUserViewModel.saveUser() },
            text = stringResource(R.string.save_user_button) // Usando "Guardar Usuario"
        )


        Spacer(modifier = Modifier.height(16.dp))

        saveResult?.let { result ->
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
        }

        Spacer(modifier = Modifier.height(16.dp))


        TextButton(onClick = onLogout) {
            Text(text = stringResource(R.string.profile_return_login))
        }
    }
}