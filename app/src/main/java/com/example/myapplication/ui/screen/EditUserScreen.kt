package com.example.myapplication.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.viewmodel.CreateUserViewModel
import com.example.myapplication.viewmodel.UserSaveResult
import androidx.compose.ui.res.stringResource
import com.example.myapplication.R
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditUserScreen(
    createUserViewModel: CreateUserViewModel = viewModel(),
    onBack: () -> Unit
) {
    // 1. Recolección de estados del ViewModel
    val name by createUserViewModel.name.collectAsState()
    val username by createUserViewModel.username.collectAsState()
    val email by createUserViewModel.email.collectAsState()
    val city by createUserViewModel.city.collectAsState()
    val saveResult by createUserViewModel.userSaveResult.collectAsState()

    var focusedField by remember { mutableStateOf<String?>(null) }
    val primaryColor = MaterialTheme.colorScheme.primary

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // --- SECCIÓN DEVOLVER ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                // Usa R.string.back para "Devolver"
                text = stringResource(R.string.back),
                fontSize = 16.sp,
                color = Color.Gray,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clickable(onClick = onBack)
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Contenido con scroll
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 1. Icono de Perfil
            ProfileIcon(primaryColor)

            Spacer(modifier = Modifier.height(32.dp))

            // 2. Campos de Edición

            // Nombre Completo (Editable)
            FloatingTextField(
                value = name,
                onValueChange = {
                    createUserViewModel.onNameChange(it)
                    focusedField = "name"
                },
                label = stringResource(R.string.profile_full_name),
                isFocused = focusedField == "name",
                onFocus = { focusedField = if (it) "name" else null }
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Nombre de usuario (Editable)
            FloatingTextField(
                value = username,
                onValueChange = {
                    createUserViewModel.onUsernameChange(it)
                    focusedField = "username"
                },
                label = stringResource(R.string.profile_username),
                isFocused = focusedField == "username",
                onFocus = { focusedField = if (it) "username" else null }
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Correo Electrónico (BLOQUEADO: isEditable = false)
            FloatingTextField(
                value = email,
                onValueChange = { /* Ignorado, no es editable */ },

                // Usamos 'not_editable' (asumiendo que lo definiste)
                // Si no tienes 'not_editable', cambia a: label = "${stringResource(R.string.profile_email)} (${stringResource(R.string.back)})",
                label = "${stringResource(R.string.profile_email)} ${stringResource(R.string.not_editable)}",

                isFocused = focusedField == "email",
                onFocus = { focusedField = if (it) "email" else null },
                isEditable = false // <-- DESHABILITA LA EDICIÓN
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Ciudad de residencia (Editable)
            FloatingTextField(
                value = city,
                onValueChange = {
                    createUserViewModel.onCityChange(it)
                    focusedField = "city"
                },
                label = stringResource(R.string.profile_city),
                isFocused = focusedField == "city",
                onFocus = { focusedField = if (it) "city" else null }
            )

            Spacer(modifier = Modifier.height(32.dp))


            // 3. Botón "Guardar cambios"
            PurpleButton(
                onClick = { createUserViewModel.updateUser() },
                text = stringResource(R.string.save_changes)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Mensaje de resultado
            saveResult?.let { result ->
                Text(
                    text = when(result) {
                        UserSaveResult.Success -> stringResource(R.string.user_updated_success)
                        UserSaveResult.Error -> stringResource(R.string.user_updated_error)
                    },
                    color = when(result) {
                        UserSaveResult.Success -> MaterialTheme.colorScheme.primary
                        UserSaveResult.Error -> MaterialTheme.colorScheme.error
                    },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        } // Fin del Column de Scroll
    } // Fin del Column principal
}


@Composable
fun ProfileIcon(primaryColor: Color) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .border(2.dp, primaryColor, CircleShape)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Person,
            contentDescription = stringResource(R.string.profile_icon_desc),
            tint = primaryColor,
            modifier = Modifier.size(64.dp)
        )
    }
}

// -------------------------------------------------------------------------------------------------
// Componente: FloatingTextField
// -------------------------------------------------------------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FloatingTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isFocused: Boolean,
    onFocus: (Boolean) -> Unit,
    isEditable: Boolean = true
) {
    val primaryColor = MaterialTheme.colorScheme.primary

    val borderColor = when {
        !isEditable -> Color.LightGray.copy(alpha = 0.5f)
        isFocused -> primaryColor
        else -> Color.LightGray.copy(alpha = 0.5f)
    }
    val borderWidth = if (isFocused && isEditable) 2.dp else 1.dp
    val elevation = if (isEditable) 4.dp else 1.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(12.dp),
                ambientColor = Color.Black.copy(alpha = 0.1f),
                spotColor = Color.Black.copy(alpha = 0.15f)
            )
            .background(Color.White, RoundedCornerShape(12.dp))
            .border(borderWidth, borderColor, RoundedCornerShape(12.dp))
            .then(if (isEditable) Modifier.onFocusChanged { focusState -> onFocus(focusState.isFocused) } else Modifier)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = isEditable,
            label = {
                Text(
                    text = label,
                    color = if (isEditable && isFocused) primaryColor else Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                cursorColor = primaryColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                disabledTextColor = Color.Black.copy(alpha = 0.6f)
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

// -------------------------------------------------------------------------------------------------
// Componente: PurpleButton
// -------------------------------------------------------------------------------------------------

@Composable
fun PurpleButton(onClick: () -> Unit, text: String) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp),
                ambientColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
                spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
            )
    ) {
        Text(text = text, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
    }
}