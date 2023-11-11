package com.example.map_domain.usecase

import com.example.base.models.Resource
import com.example.map_domain.repository.CarsRepository
import javax.inject.Inject

class GetRemoteCars @Inject constructor(
    private val carsRepository: CarsRepository
) {
    suspend operator fun invoke(): Resource<Unit> {
        return carsRepository.getRemoteCarsList()
    }
}