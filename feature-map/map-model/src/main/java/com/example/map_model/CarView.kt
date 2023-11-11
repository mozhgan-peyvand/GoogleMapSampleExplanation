package com.example.map_model

import com.google.android.gms.maps.model.BitmapDescriptor

data class CarView(
    val carEntity: CarEntity,
    var isReserved: Boolean = false,
)