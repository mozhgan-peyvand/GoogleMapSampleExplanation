package com.example.map_model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class CarEntity(
    @PrimaryKey
    val id: String,
    val modelId: String,
    val modelName: String,
    val name: String,
    val brand: String,
    val group: String,
    val series: String,
    val fuelType: String,
    val fuelLevel: Float,
    val transmission: String,
    val licensePlate: String,
    val latitude: String,
    val longitude: String,
    val innerCleanliness: String,
    val carImageUrl: String
)