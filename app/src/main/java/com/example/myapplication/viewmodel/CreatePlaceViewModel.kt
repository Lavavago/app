package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CreatePlaceViewModel : ViewModel() {


    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _address = MutableStateFlow("")
    val address: StateFlow<String> = _address

    private val _category = MutableStateFlow("")
    val category: StateFlow<String> = _category

    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> = _phone

    private val _schedule = MutableStateFlow("")
    val schedule: StateFlow<String> = _schedule

    private val _location = MutableStateFlow("")
    val location: StateFlow<String> = _location

    private val _photos = MutableStateFlow("")
    val photos: StateFlow<String> = _photos

    // Resultado de guardar el lugar (éxito o error)
    private val _saveResult = MutableStateFlow<SaveResult?>(null)
    val saveResult: StateFlow<SaveResult?> = _saveResult

    // Métodos para actualizar cada campo
    fun onNameChange(newValue: String) { _name.value = newValue }
    fun onAddressChange(newValue: String) { _address.value = newValue }
    fun onCategoryChange(newValue: String) { _category.value = newValue }
    fun onPhoneChange(newValue: String) { _phone.value = newValue }
    fun onScheduleChange(newValue: String) { _schedule.value = newValue }
    fun onLocationChange(newValue: String) { _location.value = newValue }
    fun onPhotosChange(newValue: String) { _photos.value = newValue }


    fun savePlace() {
        _saveResult.value =
            if (name.value.isBlank() || address.value.isBlank() || category.value.isBlank() ||
                phone.value.isBlank() || schedule.value.isBlank() || location.value.isBlank() || photos.value.isBlank()
            ) {
                SaveResult.Error
            } else {
                SaveResult.Success
            }
    }
}


sealed class SaveResult {
    object Success : SaveResult()
    object Error : SaveResult()
}
