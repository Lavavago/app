package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed class UserSaveResult {
    object Success : UserSaveResult()
    object Error : UserSaveResult()
}

class CreateUserViewModel : ViewModel() {

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _city = MutableStateFlow("")
    val city: StateFlow<String> = _city

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _userSaveResult = MutableStateFlow<UserSaveResult?>(null)
    val userSaveResult: StateFlow<UserSaveResult?> = _userSaveResult

    // Métodos para actualizar campos
    fun onNameChange(newValue: String) { _name.value = newValue }
    fun onUsernameChange(newValue: String) { _username.value = newValue }
    fun onEmailChange(newValue: String) { _email.value = newValue }
    fun onCityChange(newValue: String) { _city.value = newValue }
    fun onPasswordChange(newValue: String) { _password.value = newValue }

    // Guardar usuario (creación)
    fun saveUser() {
        _userSaveResult.value =
            if (_name.value.isBlank() || _username.value.isBlank() ||
                _email.value.isBlank() || _city.value.isBlank() ||
                _password.value.isBlank()
            ) {
                UserSaveResult.Error
            } else {
                UserSaveResult.Success
            }
    }

    // Actualizar usuario (edición)
    fun updateUser() {
        _userSaveResult.value =
            if (_name.value.isBlank() || _username.value.isBlank() || _city.value.isBlank()) {
                UserSaveResult.Error
            } else {
                UserSaveResult.Success
            }
    }
}
