package com.example.map.ui.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import com.example.map.ui.screens.ShowBottomSheetButton
import com.example.map_model.CarEntity
import com.example.map_model.CarView
import com.example.map_ui.R
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun CarMap(
    carViewList: List<CarView>,
    onChangeMapType: (mapType: MapType) -> Unit,
    initialLatLng: LatLng,
    mapProperties: MapProperties = MapProperties()
) {
    val currentContext = LocalContext.current
    val localCoroutineScope = rememberCoroutineScope()

    var selectedCar by remember {
        mutableStateOf<CarEntity?>(null)
    }
    var marker by remember { mutableStateOf<Marker?>(null) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialLatLng, 12f)
    }

    var isClicked by remember { mutableStateOf(false) }


    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            onMapClick = {}
        ) {
            carViewList.forEach { carView ->

                var archesIconState by remember { mutableStateOf<BitmapDescriptor?>(null) }
                //new
                LaunchedEffect(carView.carEntity.carImageUrl) {
                    val bitmapDescriptor =
                        loadBitmapDescriptorFromUrl(
                            currentContext,
                            carView.carEntity.carImageUrl,
                            R.drawable.round_block_24
                        )
                    archesIconState = bitmapDescriptor
                }


                val markerPosition = LatLng(
                    carView.carEntity.latitude.toDouble(),
                    carView.carEntity.longitude.toDouble()
                )

                Marker(
                    state = MarkerState(position = markerPosition),
                    icon = if (markerPosition == marker?.position)
                        createBitmapDescriptorFromResource(
                            applicationContext = currentContext,
                            drawableResourceId = R.drawable.ic_google_map
                        )
                    else
                        archesIconState,
                    onClick = { currentMarker ->
                        if (isClicked.not()) {
                            marker = currentMarker
                            selectedCar = carView.carEntity
                            localCoroutineScope.launch(Dispatchers.Default) {
                                delay(60000) //60000
                                isClicked = false
                            }
                            isClicked = true
                        }
                        true
                    }
                )
            }
        }

        MapState(
            onChangeMapType = onChangeMapType
        )
    }

    if (isClicked) {
        UpdateCarReserveStatus(marker = marker, carViews = carViewList, isReserved = true)
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            // Button to show the bottom sheet
            ShowBottomSheetButton(
                selectedCar
            )
        }
    } else {
        UpdateCarReserveStatus(marker = marker, carViews = carViewList, isReserved = false)

    }
}


@Composable
fun UpdateCarReserveStatus(marker: Marker?, carViews: List<CarView>, isReserved: Boolean) {
    carViews.forEach { carView ->
        if (carView.carEntity.latitude == marker?.position?.latitude.toString() &&
            carView.carEntity.longitude == marker?.position?.longitude.toString()
        ) {
            carView.isReserved = isReserved
        }
    }
}

@Composable
fun MapState(onChangeMapType: (mapType: MapType) -> Unit) {

    var isMapTypeMenuExpanded by remember { mutableStateOf(false) }
    var mapTypeMenuSelectedText by remember {
        mutableStateOf(MapType.NORMAL.name.lowercase().capitalize(Locale.current))
    }

    Column(
        modifier = Modifier.padding(4.dp)
    ) {
        Button(
            onClick = { isMapTypeMenuExpanded = true },
            content = {
                Text(text = mapTypeMenuSelectedText)
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Dropdown Icon",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
            }
        )

        DropdownMenu(
            modifier = Modifier.padding(4.dp),
            expanded = isMapTypeMenuExpanded,
            onDismissRequest = { isMapTypeMenuExpanded = false }
        ) {
            MapType.values().forEach { mapType ->
                DropdownMenuItem(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        onChangeMapType(mapType)
                        mapTypeMenuSelectedText =
                            mapType.name.lowercase().capitalize(Locale.current)
                        isMapTypeMenuExpanded = false
                    },
                    text = {
                        Text(
                            text = mapType.name.lowercase().capitalize(Locale.current),
                        )
                    }
                )
            }
        }
    }
}
