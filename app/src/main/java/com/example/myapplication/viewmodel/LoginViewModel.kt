package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Role
import com.example.myapplication.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {

    // Lista de usuarios "de prueba" para la demostración
    private val fakeUsers = listOf(
        User(
            id = "1",
            name = "Juan Pérez",
            username = "juanp",
            role = Role.ADMIN,
            city = "Bogotá",
            email = "juanp@example.com",
            password = "pass123"
        ),
        User(
            id = "2",
            name = "María Gómez",
            username = "mariag",
            role = Role.USER,
            city = "Medellín",
            email = "mariag@example.com",
            password = "pass456"
        )
    )

    // Estado del UI para los campos de texto
    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    // Estado del UI para el mensaje de resultado (Bienvenido o error)
    private val _loginResult = MutableStateFlow("")



    val loginResult: StateFlow<String> = _loginResult.asStateFlow()

    fun onUsernameChange(newUsername: String) {
        _username.value = newUsername
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }

    fun validateLogin() {
        val userFound = fakeUsers.find { it.username == username.value && it.password == password.value }
        if (userFound != null) {
            _loginResult.value = "¡Bienvenido, ${userFound.name}!"
        } else {
            _loginResult.value = "Usuario o contraseña incorrecta."
        }
    }
}