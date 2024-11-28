package com.example.controlward.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import com.example.controlward.Value
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.tasks.await

@SuppressLint("AutoboxingStateValueProperty")
@Composable
fun MainScreen() {
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState { }
    val uiSettings by remember {
        mutableStateOf(MapUiSettings(myLocationButtonEnabled = true))
    }
    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }
    val disasterCategory = listOf("범죄", "지진", "홍수", "폭설", "쓰나미")
    val selectedIndex = remember { mutableIntStateOf(5) }
    val disasterList = remember {
        derivedStateOf {
            when (selectedIndex.value) {
                0 -> Value.disasterListCrime
                1 -> Value.disasterListEarthQuake
                2 -> Value.disasterListFlood
                3 -> Value.disasterListHeavySnow
                4 -> Value.disasterListTsunami
                else -> emptyList()
            }
        }
    }
    var userLocation = Value.location

    LaunchedEffect(Unit) {
        val location = getCurrentLocation(context, fusedLocationClient)
        location?.let {
            userLocation = LatLng(it.latitude, it.longitude)
            cameraPositionState.position =
                CameraPosition.fromLatLngZoom(userLocation, 15f)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 40.dp)
    ) {
        TabColumn(
            selectedTabIndex = selectedIndex.value,
            tabs = disasterCategory,
            onTabSelected = { selectedIndex.value = it },
        )

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = properties,
            uiSettings = uiSettings,
        ) {
            disasterList.value.forEach { disaster ->
                val position = LatLng(
                    disaster.location.first().toDouble(),
                    disaster.location.last().toDouble()
                )
                Marker(
                    state = MarkerState(position = position),
                    title = disaster.image,
                    snippet = disaster.text,
                    onInfoWindowClick = { }
                )
            }
        }

        IconButton(
            onClick = {
                cameraPositionState.position =
                    CameraPosition.fromLatLngZoom(userLocation, 15f)
            },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(20.dp)
                .background(Color.White, RoundedCornerShape(100))
        ) {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = "",
                tint = Color.Black
            )
        }
    }
}

@Composable
fun TabColumn(
    selectedTabIndex: Int,
    tabs: List<String>,
    onTabSelected: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.3f)
            .zIndex(1f)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        tabs.forEachIndexed { index, tab ->
            val isSelected = selectedTabIndex == index
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp)
                    .border(
                        if (isSelected) 2.dp else 1.dp,
                        if (isSelected) Color.Blue else Color.Gray,
                        RoundedCornerShape(8.dp)
                    )
                    .background(
                        Color(240, 230, 255, 255),
                        RoundedCornerShape(8.dp)
                    )
                    .clickable { onTabSelected(index) }
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = tab,
                    color = if (isSelected) Color.Blue else Color.Black,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

suspend fun getCurrentLocation(
    context: Context,
    fusedLocationClient: FusedLocationProviderClient,
): Location? {
    if (ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        return null
    }

    return try {
        fusedLocationClient.lastLocation.await()
    } catch (e: Exception) {
        null
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainLayout() {
    MainScreen()
}