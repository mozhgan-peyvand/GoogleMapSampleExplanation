package com.example.map.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.models.Resource
import com.example.base.util.collectOn
import com.example.map_domain.usecase.GetLocalCars
import com.example.map_domain.usecase.GetRemoteCars
import com.example.map_model.CarView
import com.example.map_model.CarsListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val getRemoteCars: GetRemoteCars,
    private val getLocalCars: GetLocalCars
) : ViewModel() {

    private val _uiState = MutableStateFlow(CarsListUiState())
    val uiState: StateFlow<CarsListUiState> = _uiState.asStateFlow()


    init {
        getRemoteCarsList()
        getLocalCarsList()
    }

    private fun getLocalCarsList() {
        getLocalCars().collectOn(viewModelScope) { carsList ->
            val carViews: List<CarView> = carsList.map { carEntity ->
                CarView(carEntity = carEntity)
            }
            _uiState.update { it.copy(carsList = carViews) }
        }
    }



    private fun getRemoteCarsList() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            when (val result = getRemoteCars()) {
                is Resource.Success -> {
                    _uiState.update { it.copy(isLoading = false, error = null) }
                }
                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.error) }
                }
            }
        }
    }

}