package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class Place(
    val name: String,
    val address: String,
    val category: String,
    val phone: String,
    val schedule: String,
    val location: String,
    val photos: String
)

sealed class SaveResult {
    object Success : SaveResult()
    object Error : SaveResult()
}



class CreatePlaceViewModel : ViewModel() {


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

    private val _saveResult = MutableStateFlow<SaveResult?>(null)
    val saveResult: StateFlow<SaveResult?> = _saveResult.asStateFlow()


    init {
        // El formulario iniciará completamente vacío.
    }

    // Métodos para actualizar cada campo
    fun onNameChange(newValue: String) { _name.value = newValue }
    fun onAddressChange(newValue: String) { _address.value = newValue }
    fun onCategoryChange(newValue: String) { _category.value = newValue }
    fun onPhoneChange(newValue: String) { _phone.value = newValue }
    fun onScheduleChange(newValue: String) { _schedule.value = newValue }
    fun onLocationChange(newValue: String) { _location.value = newValue }
    fun onPhotosChange(newValue: String) { _photos.value = newValue }

    // La función loadPlace se mantiene por si la usas en el futuro (ej. para editar)
    fun loadPlace(place: Place) {
        _name.value = place.name
        _address.value = place.address
        _category.value = place.category
        _phone.value = place.phone
        _schedule.value = place.schedule
        _location.value = place.location
        _photos.value = place.photos

        _saveResult.value = null
    }


    fun savePlace() {

        _saveResult.value =
            if (name.value.isBlank() || address.value.isBlank() || category.value.isBlank() ||
                phone.value.isBlank() || schedule.value.isBlank() || location.value.isBlank() || photos.value.isBlank()
            ) {
                SaveResult.Error
            } else {
                SaveResult.Success
            }

        // Simular la impresión de los datos a guardar.
        // Aquí es donde verás todos los datos si los has seleccionado en la interfaz:
        println("--- Datos Recibidos en ViewModel ---")
        println("Name: ${name.value}")
        println("Address: ${address.value}")
        println("Category: ${category.value}")
        println("Phone: ${phone.value}")
        println("Schedule: ${schedule.value}")
        println("Location: ${location.value}")
        println("Photos: ${photos.value}")
        println("------------------------------------")
    }
}