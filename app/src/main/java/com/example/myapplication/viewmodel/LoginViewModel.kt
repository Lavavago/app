package com.example.myapplication.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Role
import com.example.myapplication.model.User
import com.example.myapplication.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {


    private val fakeUsers = listOf(
        User("1", "Juan Pérez", "juanp", Role.ADMIN, "Bogotá", "juanp@example.com", "pass123"),
        User("2", "María Gómez", "mariag", Role.USER, "Medellín", "mariag@example.com", "pass456")
    )

    // Estado de los campos
    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    // Estado del resultado del login
    private val _loginResult = MutableStateFlow("")
    val loginResult: StateFlow<String> = _loginResult.asStateFlow()

    private val _isLoginSuccessful = MutableStateFlow(false)
    val isLoginSuccessful: StateFlow<Boolean> = _isLoginSuccessful.asStateFlow()


    fun onUsernameChange(newUsername: String) {
        _username.value = newUsername
    }

    fun onPasswordChange(newPassword: String) {
        _password.value = newPassword
    }


    fun validateLogin(context: Context) {
        val userFound = fakeUsers.find { it.username == username.value && it.password == password.value }
        if (userFound != null) {
            _loginResult.value = context.getString(R.string.login_success, userFound.name)
            _isLoginSuccessful.value = true
        } else {
            _loginResult.value = context.getString(R.string.login_error)
            _isLoginSuccessful.value = false
        }
    }
}

