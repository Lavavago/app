package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Location
import com.example.myapplication.model.Place
import com.example.myapplication.model.PlaceType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlacesViewModel: ViewModel() {
    private val _places = MutableStateFlow(emptyList<Place>())
    val places: StateFlow<List<Place>> = _places.asStateFlow()

    init {
        loadPlaces()
    }

    fun loadPlaces() {

        _places.value = listOf(
            Place(
                    id = "1",
                    title = "Restaurante El paisa",
                    description = "El mejor restaurante paisa",
                    address = "Cra 12 # 12 - 12",
                    location = Location(1.23, 2.34),
                    images = listOf("https://elbalconpaisa.com/images/about-img-1.png"),
                    phones = listOf("31231321323", "31231321323"),
                    type = PlaceType.RESTAURANTE,
                    schedules = listOf()
            ),

            Place(
            id = "2",
            title = "Bar test",
            description = "Un bar test",
            address = "Calle 12 # 12 - 12",
            location = Location(1.23, 2.34),
            images = listOf("https://cdn0.uncomo.com/es/posts/6/8/4/como_gestionar_un_bar_22486_orig.jpg"),
            phones = listOf("31231321323", "31231321323"),
            type = PlaceType.BAR,
            schedules = listOf()
            )
        )

    }

    fun addPlace(place: Place) {
        _places.value = _places.value + place
    }

    fun findById(id: String): Place? {
        return _places.value.find { it.id == id }
    }

    fun findByType(type: PlaceType): List<Place> {
        return _places.value.filter { it.type == type }
    }

    fun findByName(name: String): List<Place> {
        return _places.value.filter { it.title.contains(name) }
    }

    fun removePlace(place: Place) {
        _places.value = _places.value - place
    }

    fun updatePlace(place: Place) {
        _places.value = _places.value.map { if (it.id == place.id) place else it }
    }
}