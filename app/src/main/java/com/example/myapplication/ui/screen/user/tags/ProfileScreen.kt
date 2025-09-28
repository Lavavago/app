package com.example.myapplication.ui.screen.user.tags

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.model.Role
import com.example.myapplication.model.User
import androidx.compose.ui.res.stringResource
import com.example.myapplication.R

@Composable
fun ProfileScreen(onLogout: () -> Unit,
                  onEditProfile: () -> Unit,) {
    val fakeUser = User(
        id = "1",
        name = "Juan Pérez",
        username = "juanp",
        role = Role.ADMIN,
        city = "Bogotá",
        email = "juanp@example.com",
        password = "pass123"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Icono superior
        Icon(
            imageVector = Icons.Filled.Person,
            contentDescription = stringResource(R.string.profile_icon_desc),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.profile_title),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(32.dp))

        ProfileItem(label = stringResource(R.string.label_id), value = fakeUser.id)
        ProfileItem(label = stringResource(R.string.label_name), value = fakeUser.name)
        ProfileItem(label = stringResource(R.string.label_username), value = fakeUser.username)
        ProfileItem(label = stringResource(R.string.label_email), value = fakeUser.email)
        ProfileItem(label = stringResource(R.string.label_city), value = fakeUser.city)
        ProfileItem(label = stringResource(R.string.label_role), value = fakeUser.role.toString())

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = onEditProfile) {
            Text(stringResource(R.string.edit_profile))
        }

        Spacer(modifier = Modifier.height(16.dp))
        
        Button(onClick = onLogout) {
            Text(stringResource(R.string.logout))
        }
    }
}

@Composable
private fun ProfileItem(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}
