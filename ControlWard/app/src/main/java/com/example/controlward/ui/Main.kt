package com.example.controlward.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
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
import com.example.controlward.R
import com.example.controlward.Value
import com.example.controlward.Value.disasterCategory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@SuppressLint("AutoboxingStateValueProperty")
@Composable
fun MainScreen() {
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(Value.location, 12f)
    }
    val uiSettings by remember {
        mutableStateOf(MapUiSettings(myLocationButtonEnabled = true))
    }
    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }
    val selectedIndex = remember { mutableIntStateOf(5) }
    val disasterList = remember {
        derivedStateOf {
            if (selectedIndex.value == 5)
                Value.disasterAllList
            else
                Value.disasterMap[disasterCategory[selectedIndex.value].first] ?: emptyList()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 40.dp)
    ) {
        TabColumn(
            selectedTabIndex = selectedIndex.value,
            tabs = disasterCategory.map { it.first },
            onTabSelected = { selectedIndex.value = it },
        )

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = properties,
            uiSettings = uiSettings,
            onMapClick = {
                selectedIndex.value = 5
            }
        ) {
            disasterList.value.forEach { disaster ->
                val position = LatLng(
                    disaster.location.first().toDouble(),
                    disaster.location.last().toDouble()
                )
                val hue = disasterCategory.find { it.first == disaster.category }?.second
                    ?: BitmapDescriptorFactory.HUE_RED
                val alpha = disaster.accuracy.toFloat()
                Marker(
                    state = MarkerState(position = position),
                    title = disaster.image,
                    snippet = disaster.text,
                    icon = customMarker(context, hue, alpha),
                    onInfoWindowClick = { },
                )
            }
        }

        IconButton(
            onClick = {
                cameraPositionState.position =
                    CameraPosition.fromLatLngZoom(Value.location, 12f)
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

fun customMarker(context: Context, hue: Float, alpha: Float): BitmapDescriptor {
    val vectorDrawable = ContextCompat.getDrawable(context, R.drawable.baseline_location_on_24)
        ?: return BitmapDescriptorFactory.defaultMarker(hue)

    val originalBitmap = Bitmap.createBitmap(
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(originalBitmap)
    vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
    vectorDrawable.draw(canvas)

    val resizedBitmap = Bitmap.createScaledBitmap(
        originalBitmap,
        (originalBitmap.width * (1 + 0.5 * alpha)).toInt(),
        (originalBitmap.height * (1 + 0.5 * alpha)).toInt(),
        false
    )

    val paint = Paint().apply {
        colorFilter = PorterDuffColorFilter(
            android.graphics.Color.HSVToColor((alpha * 255).toInt(), floatArrayOf(hue, 1f, 1f)),
            PorterDuff.Mode.SRC_IN
        )
    }

    val resizedCanvas = Canvas(resizedBitmap)
    resizedCanvas.drawBitmap(resizedBitmap, 0f, 0f, paint)

    return BitmapDescriptorFactory.fromBitmap(resizedBitmap)
}


@Preview(showBackground = true)
@Composable
fun PreviewMainLayout() {
    MainScreen()
}