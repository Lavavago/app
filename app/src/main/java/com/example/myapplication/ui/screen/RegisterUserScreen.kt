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
import androidx.compose.ui.res.painterResource // Importación para cargar la imagen
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R // Importación del R para acceder a los recursos
import com.example.myapplication.ui.components.CustomButton
import com.example.myapplication.ui.components.CustomTextField
import com.example.myapplication.viewmodel.CreateUserViewModel
import com.example.myapplication.viewmodel.UserSaveResult

@Composable
fun RegisterUserScreen(
    createUserViewModel: CreateUserViewModel = viewModel(),
    onLogout: () -> Unit // Función para volver/navegar
) {
    // Estados del ViewModel (deben inicializarse con "")
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
        // Logo (Imagen del pin de ubicación morado)
        Image(
            // **IMPORTANTE:** Cambia 'ic_unilocal_logo' por el nombre real de tu archivo en res/drawable.
            painter = painterResource(id = R.drawable.gps2),
            contentDescription = "Logo uniLocal",
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Nombre de la app
        Text(
            text = "uniLocal",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        // --- 1. Nombre ---
        CustomTextField(
            value = name,
            onValueChange = { createUserViewModel.onNameChange(it) },
            placeholder = "Nombre"
        )
        Spacer(modifier = Modifier.height(16.dp))

        // --- 2. Nombre de usuario ---
        CustomTextField(
            value = username,
            onValueChange = { createUserViewModel.onUsernameChange(it) },
            placeholder = "Nombre de usuario"
        )
        Spacer(modifier = Modifier.height(16.dp))

        // --- 3. Gmail ---
        CustomTextField(
            value = email,
            onValueChange = { createUserViewModel.onEmailChange(it) },
            placeholder = "Gmail"
        )
        Spacer(modifier = Modifier.height(16.dp))

        // --- 4. Contraseña ---
        CustomTextField(
            value = password,
            onValueChange = { createUserViewModel.onPasswordChange(it) },
            placeholder = "Contraseña",
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // --- 5. Ciudad de residencia ---
        CustomTextField(
            value = city,
            onValueChange = { createUserViewModel.onCityChange(it) },
            placeholder = "Ciudad de residencia"
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón principal con el texto "Iniciar sesión"
        CustomButton(
            onClick = { createUserViewModel.saveUser() },
            text = "Iniciar sesión"
        )

        // --- Mensajes de retroalimentación (para errores o éxito) ---
        Spacer(modifier = Modifier.height(16.dp))

        saveResult?.let { result ->
            Text(
                text = when (result) {
                    UserSaveResult.Success -> "Usuario creado con éxito"
                    UserSaveResult.Error -> "Error al crear usuario"
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

        // Botón opcional para regresar
        TextButton(onClick = onLogout) {
            Text(text = "Volver")
        }
    }
}