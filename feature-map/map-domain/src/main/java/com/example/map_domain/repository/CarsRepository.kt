package com.example.map_domain.repository

import com.example.base.models.Resource
import com.example.map_model.CarEntity
import kotlinx.coroutines.flow.Flow

interface CarsRepository {

    fun getLocalCars(): Flow<List<CarEntity>>

    suspend fun getRemoteCarsList(): Resource<Unit>

}