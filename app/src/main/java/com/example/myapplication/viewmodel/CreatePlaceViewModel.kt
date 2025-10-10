package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Location
import com.example.myapplication.model.Place
import com.example.myapplication.model.PlaceType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class SaveResult {
    object Success : SaveResult()
    object Error : SaveResult()
}

class CreatePlaceViewModel : ViewModel() {

    // Campos del formulario
    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    private val _address = MutableStateFlow("")
    val address: StateFlow<String> = _address.asStateFlow()

    private val _phone = MutableStateFlow("")
    val phone: StateFlow<String> = _phone.asStateFlow()

    private val _photos = MutableStateFlow("")
    val photos: StateFlow<String> = _photos.asStateFlow()

    // Campo del horario seleccionado
    private val _schedule = MutableStateFlow("")
    val schedule: StateFlow<String> = _schedule.asStateFlow()

    // Lista de horarios predefinidos
    val availableSchedules = listOf(
        "Lunes - Viernes 8:00AM - 8:00PM",
        "Sábados - Domingos - Festivos 9:00AM - 6:00PM",
        "24 Horas",
        "Cerrado temporalmente"
    )

    // Tipo del lugar
    private val _type = MutableStateFlow(PlaceType.RESTAURANTE)
    val type: StateFlow<PlaceType> = _type.asStateFlow()

    private val _saveResult = MutableStateFlow<SaveResult?>(null)
    val saveResult: StateFlow<SaveResult?> = _saveResult.asStateFlow()

    private val _places = MutableStateFlow<List<Place>>(emptyList())
    val places: StateFlow<List<Place>> = _places.asStateFlow()

    // Métodos para actualizar los campos
    fun onTitleChange(value: String) { _title.value = value }
    fun onDescriptionChange(value: String) { _description.value = value }
    fun onAddressChange(value: String) { _address.value = value }
    fun onPhoneChange(value: String) { _phone.value = value }
    fun onPhotosChange(value: String) { _photos.value = value }
    fun onScheduleChange(value: String) { _schedule.value = value }
    fun onTypeChange(value: PlaceType) { _type.value = value }

    // Guardar lugar
    fun savePlace() {
        if (_title.value.isBlank() || _description.value.isBlank() || _address.value.isBlank() ||
            _phone.value.isBlank() || _photos.value.isBlank() || _schedule.value.isBlank()
        ) {
            _saveResult.value = SaveResult.Error
            return
        }

        val newPlace = Place(
            title = _title.value,
            description = _description.value,
            address = _address.value,
            location = Location(latitude = 0.0, longitude = 0.0),
            images = listOf(_photos.value),
            phones = listOf(_phone.value),
            type = _type.value,
            schedules = listOf(_schedule.value)
        )

        _places.value = _places.value + newPlace

        // Limpiar campos
        _title.value = ""
        _description.value = ""
        _address.value = ""
        _phone.value = ""
        _photos.value = ""
        _schedule.value = ""
        _type.value = PlaceType.RESTAURANTE

        _saveResult.value = SaveResult.Success
    }
}
