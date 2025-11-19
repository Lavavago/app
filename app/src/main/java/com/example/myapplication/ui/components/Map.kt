package com.example.myapplication.ui.components

import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.model.Place
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.compose.annotation.generated.PointAnnotation
import com.mapbox.maps.extension.compose.annotation.rememberIconImage
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.plugin.gestures.addOnMapClickListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.viewport.data.DefaultViewportTransitionOptions

@Composable
fun Map(
    modifier: Modifier = Modifier,
    places: List<Place> = emptyList(),
    activateClik: Boolean = false,
    // --- CAMBIO 1: Agregamos initialLocation ---
    initialLocation: Point? = null,
    onMapClickListener: (Point) -> Unit = {}
) {

    // --- CAMBIO 2: Inicializamos clickedPoint con initialLocation ---
    // Usamos remember para mantener el estado de la ubicación seleccionada
    var clickedPoint by remember { mutableStateOf(initialLocation) }

    // También usamos LaunchedEffect para actualizar clickedPoint si initialLocation cambia
    // (Esto es útil si el ViewModel carga un lugar existente)
    LaunchedEffect(initialLocation) {
        if (initialLocation != clickedPoint) {
            clickedPoint = initialLocation
        }
    }

    val context = LocalContext.current

    // ... (El resto de la lógica de permisos, ViewportState y Markers se mantiene) ...

    val hasPermission = rememberLocationPermissionState{
        Toast.makeText(
            context,
            if (it) "Ha conceguido permiso para acceder a su ubicacion" else "No ha concedido permiso para acceder a su ubicacion",
            Toast.LENGTH_SHORT
        ).show(
        )
    }

    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            zoom(7.0)
            center(initialLocation ?: Point.fromLngLat(-75.6491181, 4.4687891)) // Centro inicial o la ubicación pasada
            pitch(45.0)
        }
    }

    val marker = rememberIconImage(
        key = R.drawable.red_marker,
        painter = painterResource(id = R.drawable.red_marker)
    )

    MapboxMap(
        modifier = modifier,
        mapViewportState = mapViewportState
    ) {

        // Listener de clics
        MapEffect(Unit) { mapView ->
            mapView.gestures.addOnMapClickListener { point ->
                if (activateClik) {
                    onMapClickListener(point)
                    clickedPoint = point // Actualiza el marcador visible
                }
                true
            }
        }

        // Seguir al usuario si tiene permisos
        if (hasPermission) {
            MapEffect(key1 = "follow_puck_location") { mapView ->
                mapView.location.updateSettings {
                    locationPuck = createDefault2DPuck(withBearing = true)
                    enabled = true
                    puckBearing = PuckBearing.COURSE
                    puckBearingEnabled = true
                }

                mapViewportState.transitionToFollowPuckState(
                    defaultTransitionOptions = DefaultViewportTransitionOptions.Builder()
                        .maxDurationMs(0)
                        .build()
                )
            }
        }

        // Punto que el usuario selecciona (usa clickedPoint)
        clickedPoint?.let {
            PointAnnotation(point = it) {
                iconImage = marker
            }
        }

        // Mostrar markers de lugares
        if (places.isNotEmpty()) {
            places.forEach { place ->
                PointAnnotation(
                    point = Point.fromLngLat(
                        place.location.longitude,
                        place.location.latitude
                    )
                ) {
                    iconImage = marker
                }
            }
        }
    }
}


@Composable
fun rememberLocationPermissionState(
    permission: String = android.Manifest.permission.ACCESS_FINE_LOCATION,
    onPermissionResult: (Boolean) -> Unit
): Boolean {
    val context = LocalContext.current
    val permissionGranted = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ){ granted ->
        permissionGranted.value = granted
        onPermissionResult(granted)
    }

    LaunchedEffect(Unit) {
        if (!permissionGranted.value){
            launcher.launch(permission)
        }
    }

    return permissionGranted.value

}