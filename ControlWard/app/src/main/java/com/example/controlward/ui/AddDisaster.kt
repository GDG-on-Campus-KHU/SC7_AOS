package com.example.controlward.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.controlward.R

@Composable
fun AddDisasterLayout() {
    var disasterText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )
        Spacer(modifier = Modifier.padding(bottom = 20.dp))

        BasicTextField(
            value = disasterText,
            onValueChange = { if (it.length <= 100) disasterText = it },
            modifier = Modifier
                .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
                .padding(10.dp)
                .fillMaxSize()
                .weight(1f)
        )
        Spacer(modifier = Modifier.padding(bottom = 20.dp))

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.padding(bottom = 40.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_add_24),
                contentDescription = ""
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAddDisaster() {
    AddDisasterLayout()
}