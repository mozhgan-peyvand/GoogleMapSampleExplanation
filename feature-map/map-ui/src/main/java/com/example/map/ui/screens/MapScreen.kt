package com.example.map.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.map.ui.util.CarMap
import com.example.map.ui.util.LocationPermissionHandler
import com.example.map.ui.util.checkForLocationPermission
import com.example.map_model.CarsListUiState
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MapProperties

@Composable
fun MapScreen(
    viewModel: MapViewModel = hiltViewModel()
) {
    val carsState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    var hasLocationPermission by remember {
        mutableStateOf(checkForLocationPermission(context))
    }
    var getPermission by remember { mutableStateOf(false) }

    if (hasLocationPermission) {
        MapScreen(carsState)
    } else {
        LocationPermissionHandler(
            onPermissionGranted = { hasLocationPermission = true }
        )
    }
}


@Composable
fun MapScreen(
    carState: CarsListUiState,
) {
    var mapProperties by remember { mutableStateOf(MapProperties()) }

    when {
        carState.isLoading -> {
            // Handle loading state
            // Display a loading indicator
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                CircularProgressIndicator(
                    modifier = Modifier
                        .size(30.dp)
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary
                )

            }
        }

        carState.error != null -> {
            // Handle error state
            // Display an error message or retry button
            carState.error?.messageId?.let {
                Text(text = stringResource(id = it))
            }
        }

        carState.carsList.isNotEmpty() -> {
            // Handle success state

            CarMap(
                carViewList = carState.carsList,
                onChangeMapType = {
                    mapProperties = mapProperties.copy(mapType = it)
                },
                initialLatLng = LatLng(
                    carState.carsList[0].carEntity.latitude.toDouble(),
                    carState.carsList[0].carEntity.longitude.toDouble()
                ),
                mapProperties = mapProperties
            )
        }
    }
}



