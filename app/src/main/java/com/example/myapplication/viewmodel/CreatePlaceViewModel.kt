package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.model.DayOfWeek
import com.example.myapplication.model.Location
import com.example.myapplication.model.Place
import com.example.myapplication.model.PlaceType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

sealed class SaveResult {
    object Success : SaveResult()
    object Idle : SaveResult()
    object Error : SaveResult()
}

class CreatePlaceViewModel : ViewModel() {

    // --- Definiciones de StateFlow ---

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

    private val _schedules = MutableStateFlow(mapOf<DayOfWeek, String>())
    val schedules: StateFlow<Map<DayOfWeek, String>> = _schedules.asStateFlow()

    private val _type = MutableStateFlow(PlaceType.RESTAURANTE)
    val type: StateFlow<PlaceType> = _type.asStateFlow()

    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> = _location.asStateFlow()

    private val _saveResult = MutableStateFlow<SaveResult>(SaveResult.Idle)
    val saveResult: StateFlow<SaveResult> = _saveResult.asStateFlow()

    private val _places = MutableStateFlow<List<Place>>(emptyList())
    val places: StateFlow<List<Place>> = _places.asStateFlow()


    // --- Funciones de Modificación ---

    fun onTitleChange(value: String) { _title.value = value }
    fun onDescriptionChange(value: String) { _description.value = value }
    fun onAddressChange(value: String) { _address.value = value }
    fun onPhoneChange(value: String) { _phone.value = value }
    fun onPhotosChange(value: String) { _photos.value = value }
    fun onTypeChange(value: PlaceType) { _type.value = value }

    fun onLocationChange(lat: Double, lng: Double) {
        _location.value = Location(latitude = lat, longitude = lng)
    }

    fun onDayScheduleChange(day: DayOfWeek, newTime: String) {
        val currentMap = _schedules.value.toMutableMap()

        // Si el valor es una hora o "Cerrado", lo guardamos. Si es vacío, no lo guardamos o lo eliminamos.
        if (newTime.isBlank()) {
            currentMap.remove(day)
        } else {
            currentMap[day] = newTime.trim()
        }

        _schedules.value = currentMap.toMap()
    }

    // --- FUNCIÓN CRUCIAL DE RESETEO ---
    /**
     * Resetea todas las variables de estado del formulario a sus valores iniciales (vacíos/nulos).
     */
    fun resetForm() {
        _title.value = ""
        _description.value = ""
        _address.value = ""
        _phone.value = ""
        _photos.value = ""
        _schedules.value = emptyMap() // Limpia todos los horarios
        _type.value = PlaceType.RESTAURANTE
        _location.value = null // El mapa se reseteará a su estado inicial
        _saveResult.value = SaveResult.Idle // Resetea el indicador de guardado/error
    }


    // --- Función de Guardado ---

    fun savePlace(): Place? {

        // Validación de campos requeridos
        if (_title.value.isBlank() ||
            _description.value.isBlank() ||
            _address.value.isBlank() ||
            _location.value == null
        ) {
            _saveResult.value = SaveResult.Error
            return null
        }

        val imagesList = _photos.value.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        val phonesList = _phone.value.split(",").map { it.trim() }.filter { it.isNotEmpty() }


        val newPlace = Place(
            id = UUID.randomUUID().toString(),
            title = _title.value,
            description = _description.value,
            address = _address.value,
            location = _location.value!!,
            images = imagesList,
            phones = phonesList,
            type = _type.value,
            schedules = _schedules.value
        )

        _places.value = _places.value + newPlace
        _saveResult.value = SaveResult.Success

        return newPlace
    }
}