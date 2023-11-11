package com.example.map_data.repositories

import com.example.base.models.Resource
import com.example.map_data.datasource.local.CarsLocalDataSource
import com.example.map_data.datasource.remote.CarsRemoteDataSource
import com.example.map_domain.repository.CarsRepository
import com.example.map_model.CarEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CarsRepositoryImpl @Inject constructor(
    private val carsRemoteDataSource: CarsRemoteDataSource,
    private val carsLocalDataSource: CarsLocalDataSource
) : CarsRepository {
    override  fun getLocalCars(): Flow<List<CarEntity>> {
        return carsLocalDataSource.getCarsList()
    }

    override suspend fun getRemoteCarsList(): Resource<Unit> {
        return when (val result = carsRemoteDataSource.getCars()) {

            is Resource.Success -> {

                carsLocalDataSource.removeCarsList()

                carsLocalDataSource.insertCarsList(result.data)

                Resource.Success(Unit)
            }

            is Resource.Error -> {
                Resource.Error(result.error)
            }
        }
    }


}