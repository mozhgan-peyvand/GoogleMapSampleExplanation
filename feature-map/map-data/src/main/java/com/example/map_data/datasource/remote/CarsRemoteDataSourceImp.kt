package com.example.map_data.datasource.remote

import com.example.base.api.NetworkHandler
import com.example.base.models.Resource
import com.example.map_model.CarEntity
import javax.inject.Inject

class CarsRemoteDataSourceImp @Inject constructor(
    private val service: CarsService,
    private val networkHandler: NetworkHandler
) : CarsRemoteDataSource {

    override suspend fun getCars(): Resource<List<CarEntity>> {
       return networkHandler.call {
           service.getCars()
       }
    }
}