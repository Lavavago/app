package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CreatePlaceViewModel : ViewModel() {

    // Campos del formulario
    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _address = MutableStateFlow("")
    val address: StateFlow<String> = _address.asStateFlow()

    private val _category = MutableStateFlow("")
    val category: StateFlow<String> = _category.asStateFlow()

    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> = _phone.asStateFlow()

    private val _schedule = MutableStateFlow("")
    val schedule: StateFlow<String> = _schedule.asStateFlow()

    private val _location = MutableStateFlow("")
    val location: StateFlow<String> = _location.asStateFlow()

    private val _photos = MutableStateFlow("")
    val photos: StateFlow<String> = _photos.asStateFlow()

    // Mensaje de resultado (guardado con éxito)
    private val _saveResult = MutableStateFlow("")
    val saveResult: StateFlow<String> = _saveResult.asStateFlow()

    // Métodos para actualizar cada campo
    fun onNameChange(newValue: String) {
        _name.value = newValue
    }

    fun onAddressChange(newValue: String) {
        _address.value = newValue
    }

    fun onCategoryChange(newValue: String) {
        _category.value = newValue
    }

    fun onPhoneChange(newValue: String) {
        _phone.value = newValue
    }

    fun onScheduleChange(newValue: String) {
        _schedule.value = newValue
    }

    fun onLocationChange(newValue: String) {
        _location.value = newValue
    }

    fun onPhotosChange(newValue: String) {
        _photos.value = newValue
    }

    // Simulación de guardado del lugar
    fun savePlace() {
        if (name.value.isNotBlank() && address.value.isNotBlank()) {
            _saveResult.value = "Lugar \"${name.value}\" agregado con éxito "
        } else {
            _saveResult.value = "Por favor completa los campos obligatorios "
        }
    }
}
