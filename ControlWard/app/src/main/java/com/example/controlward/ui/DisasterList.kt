package com.example.controlward.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.controlward.R
import com.example.controlward.Value

@Composable
fun DisasterListLayout(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            items(Value.disasterList) { disaster ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
                        .padding(10.dp)
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_launcher_background),
                        modifier = Modifier
                            .fillMaxWidth(0.3f)
                            .aspectRatio(1f),
                        contentDescription = ""
                    )
                    Text(
                        text = disaster.disasterText,
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
            onClick = { navController.navigate("AddDisaster") },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp)
        ) {
            Text(text = "추가하기")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDisasterList() {
    DisasterListLayout(rememberNavController())
}