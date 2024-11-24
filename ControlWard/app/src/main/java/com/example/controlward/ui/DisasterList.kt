package com.example.controlward.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun DisasterListLayout(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
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