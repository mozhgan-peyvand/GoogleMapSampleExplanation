package com.example.map_data.datasource.remote

import com.example.base.models.Resource
import com.example.map_model.CarEntity

interface CarsRemoteDataSource {

    suspend fun getCars(): Resource<List<CarEntity>>

}