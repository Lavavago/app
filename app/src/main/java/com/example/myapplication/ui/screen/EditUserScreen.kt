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
import com.example.myapplication.viewmodel.UsersViewModel
import androidx.compose.ui.res.stringResource
import com.example.myapplication.R
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.myapplication.model.City // Asegúrate de importar City

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditUserScreen(
    // Inyección de la instancia del ViewModel.
    // Asegúrate de que todas tus pantallas (Login, Register, Edit) usen la misma instancia
    // o un mecanismo de inyección compartido si usas NavHost.
    createUserViewModel: UsersViewModel = viewModel(),
    onBack: () -> Unit
) {
    // 1. RECOLECCIÓN DE ESTADOS DEL VIEWMUDEL: Leemos los datos del usuario activo
    val name by createUserViewModel.name.collectAsState()
    val username by createUserViewModel.username.collectAsState()
    val email by createUserViewModel.email.collectAsState()
    val city by createUserViewModel.city.collectAsState()
    // val saveResult by createUserViewModel.userSaveResult.collectAsState()

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


            ProfileIcon(primaryColor)

            Spacer(modifier = Modifier.height(32.dp))

            // 2. USO DEL ESTADO 'name' Y FUNCIÓN 'onNameChange'
            FloatingTextField(
                value = name, // Muestra el nombre real del usuario
                onValueChange = {
                    createUserViewModel.onNameChange(it) // Permite editar el nombre
                    focusedField = "name"
                },
                label = stringResource(R.string.profile_full_name),
                isFocused = focusedField == "name",
                onFocus = { focusedField = if (it) "name" else null }
            )
            Spacer(modifier = Modifier.height(16.dp))


            // 3. USO DEL ESTADO 'username' Y FUNCIÓN 'onUsernameChange'
            FloatingTextField(
                value = username, // Muestra el username real
                onValueChange = {
                    createUserViewModel.onUsernameChange(it) // Permite editar el username
                    focusedField = "username"
                },
                label = stringResource(R.string.profile_username),
                isFocused = focusedField == "username",
                onFocus = { focusedField = if (it) "username" else null }
            )
            Spacer(modifier = Modifier.height(16.dp))


            // 4. USO DEL ESTADO 'email' (No editable)
            FloatingTextField(
                value = email, // Muestra el email real (No se puede editar en este ejemplo)
                onValueChange = { /* Ignorado, no es editable */ },
                label = "${stringResource(R.string.profile_email)} ${stringResource(R.string.not_editable)}",
                isFocused = focusedField == "email",
                onFocus = { focusedField = if (it) "email" else null },
                isEditable = false // Bloqueado
            )
            Spacer(modifier = Modifier.height(16.dp))


            // 5. USO DEL ESTADO 'city' (Muestra el nombre de la ciudad del usuario)
            // Se usa .name y se reemplazan los guiones bajos por espacios para mejor visualización.
            FloatingTextField(
                value = city.name.replace("_", " "),
                onValueChange = { /* Ignorado, se debería usar un Dropdown */ },
                label = stringResource(R.string.profile_city),
                isFocused = focusedField == "city",
                onFocus = { focusedField = if (it) "city" else null },
                isEditable = false // Se asume que se edita con un Dropdown aparte.
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 6. LLAMADA A LA FUNCIÓN DE GUARDAR
            PurpleButton(
                onClick = {
                    createUserViewModel.updateUser() // Llama a la lógica de guardado
                },
                text = stringResource(R.string.save_changes)
            )

            Spacer(modifier = Modifier.height(16.dp))

            /*saveResult?.let { ... } // Lógica de mensaje de guardado (dejada comentada)
*/
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}


// --- Componentes Reutilizados (No necesitan cambios) ---

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