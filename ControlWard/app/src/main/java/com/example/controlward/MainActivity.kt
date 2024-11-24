package com.example.controlward

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.controlward.ui.MainLayout
import com.example.controlward.ui.theme.ControlWardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ControlWardTheme {
                LayoutNavigator()
            }
        }
    }
}
