package com.example.controlward.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.controlward.Value
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MainLayout() {
    val startPosition = LatLng(37.5665, 126.9780)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(startPosition, 12f)
    }
    val uiSettings by remember {
        mutableStateOf(MapUiSettings(myLocationButtonEnabled = true))
    }
    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 40.dp)
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = properties,
            uiSettings = uiSettings,
//            onMapClick = { latLng ->
//                coroutineScope.launch {
//                    currentAddress = getAddressFromLatLng(context, latLng).toString()
//                    Toast.makeText(context, currentAddress, Toast.LENGTH_SHORT).show()
//                    buttonEnabled = true
//                }
//            }
        ) {
            Value.disasterList.forEach { disaster ->
                val position = LatLng(
                    disaster.locationX.toDouble(),
                    disaster.locationY.toDouble()
                )
                Marker(
                    state = MarkerState(position = position),
                    title = disaster.image,
                    snippet = disaster.disasterText,
                    onInfoWindowClick = { }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainLayout() {
    MainLayout()
}