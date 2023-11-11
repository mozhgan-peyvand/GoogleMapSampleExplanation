package com.example.map_data.datasource.remote

import com.example.map_model.CarEntity
import retrofit2.Response
import retrofit2.http.GET

interface CarsService {

    @GET("cars.json")
    suspend fun getCars(): Response<List<CarEntity>>
}