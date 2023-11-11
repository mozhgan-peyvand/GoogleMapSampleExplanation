package com.example.map.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.map.ui.util.loadBitmapDescriptorFromUrl
import com.example.map_model.CarEntity
import com.example.map_ui.R
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShowBottomSheetButton(selectedCar: CarEntity?) {
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val scope = rememberCoroutineScope()

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val buttonHeight = with(LocalDensity.current) { 56.dp.toPx() }

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ModalBottomSheetLayout(sheetState = sheetState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 36.dp),
                sheetContent = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        modifier = Modifier
                            .clickable { /* TODO */ }
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        AsyncImage(
                            model = selectedCar?.carImageUrl,
                            placeholder = painterResource(id = R.drawable.round_block_24),
                            error = painterResource(id = R.drawable.round_block_24),
                            contentDescription = "The delasign logo",
                        )

                        Text("licensePlate: ${selectedCar?.licensePlate}")
                        Text("brand: ${selectedCar?.brand}")
                        Text(text = "fuelLevel:${selectedCar?.fuelLevel}")
                    }

                }) {


            }

            Button(
                onClick = {
                    scope.launch {
                        sheetState.show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomEnd)

            ) {
                Text(text = stringResource(id = R.string.car_detail))
            }
        }
    }
}


//
//@OptIn(ExperimentalMaterialApi::class)
//@Composable
//fun ShowBottomSheetButton(){
//    val sheetState = rememberModalBottomSheetState(
//        initialValue = ModalBottomSheetValue.Hidden
//    )
//    val scope = rememberCoroutineScope()
//
//    ModalBottomSheetLayout(sheetState = sheetState, sheetContent = {
//        Column(Modifier.padding(bottom = 32.dp)) {
//            repeat(3) { index ->
//                Row(horizontalArrangement = Arrangement.spacedBy(20.dp),
//                    modifier = Modifier
//                        .clickable { /* TODO */ }
//                        .fillMaxWidth()
//                        .padding(8.dp)) {
//                    Icon(
//                        Icons.Rounded.ShoppingCart, contentDescription = null
//                    )
//                    Text("Option $index")
//                }
//            }
//        }
//    }) {
//        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
//            Button(
//                modifier = Modifier.fillMaxWidth().padding(8.dp),
//                onClick = {
//                scope.launch {
//                    sheetState.show()
//                }
//            }) {
//                Text(text = stringResource(id = R.string.car_detail ))
//            }
//        }
//    }
//
//}