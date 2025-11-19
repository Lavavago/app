package com.example.myapplication.ui.screen.user.tags

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.model.Role
import com.example.myapplication.model.City
import com.example.myapplication.model.User
// ⚠️ ASUME QUE ESTA CLASE Y PROPIEDAD EXISTEN EN TU PROYECTO
import com.example.myapplication.ui.screen.LocalMainViewModel
import com.example.myapplication.viewmodel.UsersViewModel

@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    onEditProfile: () -> Unit,
    // 1. Inyectar el ViewModel Compartido (Igual que en Register y Edit)
    viewModel: UsersViewModel = LocalMainViewModel.current.usersViewModel
) {
    // 2. Eliminar la variable 'fakeUser'

    // 3. Recolectar los Estados del Usuario Activo
    val name by viewModel.name.collectAsState()
    val username by viewModel.username.collectAsState()
    val city by viewModel.city.collectAsState()
    val email by viewModel.email.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(modifier = Modifier.height(32.dp))

        // 4. Usar la versión corregida de ProfileHeaderSection (no requiere el objeto User)
        ProfileHeaderSection(
            onEditProfile = onEditProfile
        )

        Spacer(modifier = Modifier.height(32.dp))


        // 5. Usar los datos recolectados (name, username, etc.)
        ProfileDataField(
            label = stringResource(R.string.profile_full_name),
            value = name // ⬅️ DATO ACTIVO
        )
        ProfileDataField(
            label = stringResource(R.string.profile_username),
            value = username // ⬅️ DATO ACTIVO
        )
        ProfileDataField(
            label = stringResource(R.string.profile_city),
            // Convertimos el Enum a un String legible
            value = city.name.replace("_", " ") // ⬅️ DATO ACTIVO
        )
        ProfileDataField(
            label = stringResource(R.string.profile_email),
            value = email // ⬅️ DATO ACTIVO
        )

        Spacer(modifier = Modifier.height(48.dp))


        Text(
            text = stringResource(R.string.profile_return_login),
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier
                .clickable(onClick = onLogout)
                .padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}


// 6. CORRECCIÓN EN PROFILEHEADERSECTION: Ya no necesita el objeto User.
@Composable
fun ProfileHeaderSection(onEditProfile: () -> Unit) {
    val primaryColor = MaterialTheme.colorScheme.primary

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ícono de Perfil
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

        Spacer(modifier = Modifier.width(16.dp))

        // Botón Editar Perfil
        Surface(
            onClick = onEditProfile,
            shape = RoundedCornerShape(20.dp),
            color = Color.White,
            border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f)),
            modifier = Modifier.shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = Color.Black.copy(alpha = 0.1f),
                spotColor = Color.Black.copy(alpha = 0.15f)
            )
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = stringResource(R.string.edit_profile),
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.edit_profile),
                    color = Color.DarkGray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}


@Composable
private fun ProfileDataField(label: String, value: String) {
    val primaryColor = MaterialTheme.colorScheme.primary

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Etiqueta
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color.DarkGray,
            modifier = Modifier.offset(y = 8.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Contenido
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .shadow(
                    elevation = 3.dp,
                    shape = RoundedCornerShape(10.dp),
                    ambientColor = Color.Black.copy(alpha = 0.1f),
                    spotColor = Color.Black.copy(alpha = 0.2f)
                )
                .background(Color.White, RoundedCornerShape(10.dp))
                .border(
                    width = 1.dp,
                    color = if (label.contains("Nombre") || label.contains("usuario"))
                        primaryColor.copy(alpha = 0f)
                    else Color.White,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}