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
                images = listOf(
                    "https://elbalconpaisa.com/images/about-img-1.png",
                    "https://plazaclaro.com.co/wp-content/uploads/2022/11/EL-PAISA.png",
                    "https://media-cdn.grubhub.com/image/upload/d_search:browse-images:default.jpg/w_300,q_100,fl_lossy,dpr_2.0,c_fit,f_auto,h_300/ptkdhwfhfdsfyywd3fp1"
                ),
                phones = listOf("31231321323", "31231321323"),
                type = PlaceType.RESTAURANTE,
                schedules = listOf("Lunes - Viernes: 8:00 AM - 10:00 PM", "Sábado - Domingo: 9:00 AM - 11:00 PM")
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
            ),

            Place(
                id = "3",
                title = "Café La Tertulia",
                description = "Un lugar acogedor para tomar café y leer",
                address = "Carrera 45 # 8-15",
                location = Location(1.24, 2.36),
                images = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTOoBVB7xl9x1Mrg0ErBSiLJr-clQRLZ2dzbg&s"),
                phones = listOf("3101111111"),
                type = PlaceType.CAFE,
                schedules = listOf()
            ),

            Place(
                id = "4",
                title = "Hotel Central",
                description = "Hotel en el centro de la ciudad",
                address = "Calle 10 # 5-20",
                location = Location(1.25, 2.38),
                images = listOf("https://q-xx.bstatic.com/xdata/images/hotel/max500/577544456.jpg?k=f59674cc3f80cd041a036e733539d0b990f7ad5c66b84bc1f95ec93771e44622&o="),
                phones = listOf("3142222222"),
                type = PlaceType.HOTEL,
                schedules = listOf()
            ),

            Place(
                id = "5",
                title = "Parque de la 93",
                description = "Espacio verde para disfrutar en familia",
                address = "Calle 93 # 13-00",
                location = Location(1.26, 2.39),
                images = listOf("https://files.visitbogota.co/drpl/sites/default/files/styles/max_650x650/public/2024-10/Gastronomi%CC%81aParque93_22122022%20%2817%29.jpg?itok=_OtDp9Gw"),
                phones = listOf(),
                type = PlaceType.PARK,
                schedules = listOf()
            ),

            Place(
                id = "6",
                title = "Museo del Oro",
                description = "Museo icónico de la ciudad",
                address = "Cra 6 # 15-88",
                location = Location(1.27, 2.40),
                images = listOf("https://d3nmwx7scpuzgc.cloudfront.net/sites/default/files/pagina_basica/museo-de-oro-bogota-fachada-2021-640x400.jpg"),
                phones = listOf("6013432222"),
                type = PlaceType.MUSEO,
                schedules = listOf()
            ),

            Place(
                id = "7",
                title = "Cinemark Centro",
                description = "Cine moderno con múltiples salas",
                address = "Av 7 # 45-60",
                location = Location(1.28, 2.41),
                images = listOf("https://assets.cinemark-core.com/5db771be04daec00076df3f5/vista/theaters/5e1b8b5927e07b0008e8fcd3/mobile/micentro-el-porvenir-2407-1580134677948.jpg"),
                phones = listOf("3153333333"),
                type = PlaceType.CINE,
                schedules = listOf()
            ),

            Place(
                id = "8",
                title = "Biblioteca Nacional",
                description = "La biblioteca más grande del país",
                address = "Calle 24 # 5-60",
                location = Location(1.29, 2.42),
                images = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPdhL0OBim5xj0uppW2fPjRnq4X6WcUc0nOQ&s"),
                phones = listOf("6015656565"),
                type = PlaceType.BIBLIOTECA,
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