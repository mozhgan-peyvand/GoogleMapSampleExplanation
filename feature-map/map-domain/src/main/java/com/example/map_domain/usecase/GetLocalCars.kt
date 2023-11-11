package com.example.map_domain.usecase

import com.example.map_domain.repository.CarsRepository
import com.example.map_model.CarEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocalCars @Inject constructor(
    private val carsRepository: CarsRepository
){

    operator fun invoke(): Flow<List<CarEntity>>{
        return carsRepository.getLocalCars()
    }

}