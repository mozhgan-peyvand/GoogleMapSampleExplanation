package com.example.map_data.datasource.local

import com.example.map_model.CarEntity
import kotlinx.coroutines.flow.Flow

interface CarsLocalDataSource {

    fun getCarsList(): Flow<List<CarEntity>>

    suspend fun insertCarsList(cars: List<CarEntity>)

    suspend fun removeCarsList()

}