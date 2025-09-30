package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// --- Data Model: Define User structure ---
data class User(
    val id: String,
    val name: String,
    val username: String,
    val city: String,
    val email: String,
    val passwordHash: String? = null
)
// ------------------------------------------

sealed class UserSaveResult {
    object Success : UserSaveResult()
    object Error : UserSaveResult()
}

class CreateUserViewModel : ViewModel() {

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _city = MutableStateFlow("")
    val city: StateFlow<String> = _city.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _userSaveResult = MutableStateFlow<UserSaveResult?>(null)
    val userSaveResult: StateFlow<UserSaveResult?> = _userSaveResult.asStateFlow()

    // ❌ Bloque INIT comentado para que los campos de REGISTRO NO TENGAN DATOS.
    // Los campos ahora se inicializan con la cadena vacía ("").
    /*init {
        // This simulates the EditUserScreen launching and fetching data.
        loadUserData()
    }*/

    // --- Data Loading Function (Comentada) ---
    /*
    fun loadUserData() {
        val fakeUser = User(
            id = "1",
            name = "juan perez",
            username = "juanp",
            city = "Bogota",
            email = "juanp@example.com"
        )

        // Populate StateFlows with fixed data
        _name.value = fakeUser.name
        _username.value = fakeUser.username
        _email.value = fakeUser.email
        _city.value = fakeUser.city

        // Reset any previous save results
        _userSaveResult.value = null
    }
    */
    // -----------------------------

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
        println("User Created: Name=${_name.value}")
    }

    // Actualizar usuario (edición)
    fun updateUser() {
        // Solo verifica campos usados para actualizar (Name, Username, City)
        _userSaveResult.value =
            if (_name.value.isBlank() || _username.value.isBlank() || _city.value.isBlank()) {
                UserSaveResult.Error
            } else {
                UserSaveResult.Success
            }
        println("User Updated: Name=${_name.value}")
    }
}