package com.example.myapplication.model

import java.util.UUID

data class Place (
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val address: String,
    val location: Location,
    val images: List<String>,
    val phones: List<String>,
    val type: PlaceType,
    val schedules: Map<DayOfWeek, String>,
    var visits: Int = 0
)