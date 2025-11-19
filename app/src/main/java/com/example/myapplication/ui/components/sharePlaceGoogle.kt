package com.example.myapplication.ui.components

import android.content.Context
import android.content.Intent
import com.example.myapplication.model.Place

fun sharePlaceGoogle(context: Context, place: Place) {
    val text = """
        Mira este lugar: ${place.title}
        Ubicación: https://maps.google.com/?q=${place.location.latitude},${place.location.longitude}
    """.trimIndent()

    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, text)
        type = "text/plain"
    }

    val shareIntent = Intent.createChooser(sendIntent, "Compartir lugar")
    context.startActivity(shareIntent)
}
