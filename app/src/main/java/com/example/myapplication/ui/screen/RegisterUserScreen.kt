package com.example.myapplication.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.myapplication.ui.components.CustomButton
import com.example.myapplication.ui.components.CustomTextField
import com.example.myapplication.viewmodel.CreateUserViewModel
import com.example.myapplication.viewmodel.UserSaveResult
import androidx.compose.ui.res.stringResource
import com.example.myapplication.R

@Composable
fun RegisterUserScreen(
    createUserViewModel: CreateUserViewModel = viewModel(),
    onLogout: () -> Unit
) {
    val name by createUserViewModel.name.collectAsState()
    val username by createUserViewModel.username.collectAsState()
    val email by createUserViewModel.email.collectAsState()
    val city by createUserViewModel.city.collectAsState()
    val password by createUserViewModel.password.collectAsState()
    val saveResult by createUserViewModel.userSaveResult.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Icon(
            imageVector = Icons.Filled.Person,
            contentDescription = stringResource(R.string.create_user_title),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(60.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.create_user_title),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        CustomTextField(
            value = name,
            onValueChange = { createUserViewModel.onNameChange(it) },
            label = stringResource(R.string.name2_label)
        )
        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = username,
            onValueChange = { createUserViewModel.onUsernameChange(it) },
            label = stringResource(R.string.username_label)
        )
        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = email,
            onValueChange = { createUserViewModel.onEmailChange(it) },
            label = stringResource(R.string.email_label)
        )
        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = city,
            onValueChange = { createUserViewModel.onCityChange(it) },
            label = stringResource(R.string.city_label)
        )
        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = password,
            onValueChange = { createUserViewModel.onPasswordChange(it) },
            label = stringResource(R.string.password_label),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(32.dp))

        CustomButton(
            onClick = { createUserViewModel.saveUser() },
            text = stringResource(R.string.save_user_button)
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
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Button(onClick = onLogout) {
            Text(
                text = stringResource(id = R.string.txt_back)
            )
        }
    }
}
