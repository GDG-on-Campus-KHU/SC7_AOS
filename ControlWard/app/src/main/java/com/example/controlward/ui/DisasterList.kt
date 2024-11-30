package com.example.controlward.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.controlward.Value
import com.example.controlward.Value.disasterCategory
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@SuppressLint("AutoboxingStateValueProperty")
@Composable
fun DisasterListScreen(navController: NavController) {
    val selectedIndex = remember { mutableIntStateOf(5) }
    val disasterList = remember {
        derivedStateOf {
            if (selectedIndex.value == 5)
                Value.disasterAllList
            else
                Value.disasterMap[disasterCategory[selectedIndex.value].first] ?: emptyList()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        TabRow(
            selectedTabIndex = selectedIndex.value,
        ) {
            disasterCategory.forEachIndexed { index, disaster ->
                Tab(
                    selected = selectedIndex.value == index,
                    onClick = { selectedIndex.value = index },
                    modifier = Modifier.background(Color(240, 230, 255, 255)),
                    text = { Text(text = disaster.first, style = TextStyle(fontSize = 10.sp)) }
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))

        Box {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(disasterList.value) { disaster ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
                            .padding(10.dp)
                            .clickable {
                                val encodedImage = URLEncoder.encode(
                                    disaster.image,
                                    StandardCharsets.UTF_8.toString()
                                )
                                navController.navigate("DisasterDetailScreen/${encodedImage}/${disaster.text}")
                            }
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(disaster.image),
                            modifier = Modifier
                                .fillMaxWidth(0.3f)
                                .aspectRatio(1f),
                            contentDescription = ""
                        )
                        Text(
                            text = disaster.text,
                            modifier = Modifier
                                .weight(1f)
                                .padding(10.dp),
                            maxLines = 1,
                        )
                    }
                    Spacer(modifier = Modifier.padding(bottom = 10.dp))
                }
            }

            Button(
                onClick = { navController.navigate("AddDisasterScreen") },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 40.dp)
            ) {
                Text(text = "추가하기")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDisasterList() {
    DisasterListScreen(rememberNavController())
}