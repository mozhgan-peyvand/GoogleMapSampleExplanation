package com.example.map_model

import com.example.base.models.Exceptions

data class CarsListUiState (
    val carsList: List<CarView> = emptyList(),
    val error: Exceptions? = null,
    val isLoading: Boolean = false
)