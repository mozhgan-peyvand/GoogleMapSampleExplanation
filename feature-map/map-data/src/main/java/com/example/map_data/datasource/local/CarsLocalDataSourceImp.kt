package com.example.map_data.datasource.local

import com.example.map_model.CarEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CarsLocalDataSourceImp @Inject constructor(
    private val carsDao: CarsDao
): CarsLocalDataSource {
    override fun getCarsList(): Flow<List<CarEntity>> {
        return carsDao.getCarsList()
    }

    override suspend fun insertCarsList(cars: List<CarEntity>) {
        carsDao.insertCarsList(cars)
    }

    override suspend fun removeCarsList() {
        carsDao.deleteCarsList()
    }
}