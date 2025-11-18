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

    private val _schedule = MutableStateFlow("")
    val schedule: StateFlow<String> = _schedule.asStateFlow()

    private val _type = MutableStateFlow(PlaceType.RESTAURANTE)
    val type: StateFlow<PlaceType> = _type.asStateFlow()

    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> = _location.asStateFlow()

    private val _saveResult = MutableStateFlow<SaveResult?>(null)
    val saveResult: StateFlow<SaveResult?> = _saveResult.asStateFlow()

    private val _places = MutableStateFlow<List<Place>>(emptyList())
    val places: StateFlow<List<Place>> = _places.asStateFlow()


    fun onTitleChange(value: String) { _title.value = value }
    fun onDescriptionChange(value: String) { _description.value = value }
    fun onAddressChange(value: String) { _address.value = value }
    fun onPhoneChange(value: String) { _phone.value = value }
    fun onPhotosChange(value: String) { _photos.value = value }
    fun onScheduleChange(value: String) { _schedule.value = value }
    fun onTypeChange(value: PlaceType) { _type.value = value }

    fun onLocationChange(lat: Double, lng: Double) {
        _location.value = Location(latitude = lat, longitude = lng)
    }

    fun savePlace(): Place? {

        if (_title.value.isBlank() ||
            _description.value.isBlank() ||
            _address.value.isBlank() ||
            _phone.value.isBlank() ||
            _photos.value.isBlank() ||
            _schedule.value.isBlank() ||
            _location.value == null
        ) {
            _saveResult.value = SaveResult.Error
            return null
        }

        val newPlace = Place(
            title = _title.value,
            description = _description.value,
            address = _address.value,
            location = _location.value!!,
            images = listOf(_photos.value),
            phones = listOf(_phone.value),
            type = _type.value,
            schedules = listOf(_schedule.value)
        )

        _places.value = _places.value + newPlace

        _saveResult.value = SaveResult.Success

        return newPlace
    }

}
