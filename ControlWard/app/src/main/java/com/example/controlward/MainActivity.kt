package com.example.controlward

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.controlward.ui.theme.ControlWardTheme

object Value {
    val disasterList =
        listOf(
            DisasterModel("ex1", "ex1", "37.5665", "126.9780"),
            DisasterModel("ex2", "ex2", "37.6665", "127.0780"),
            DisasterModel("ex3", "ex3", "37.5765", "126.9880"),
            DisasterModel("ex4", "ex4", "37.5565", "126.9680"),
            DisasterModel("ex5", "ex5", "37.5865", "126.9980"),
            DisasterModel("ex6", "ex6", "37.5465", "126.9580"),
            DisasterModel("ex7", "ex7", "37.5365", "126.9480")
        )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //위치 확인 -> disaster list 가져오기
        //val disasterList = DisasterViewModel().getDisasterList()

        setContent {
            ControlWardTheme {
                LayoutNavigator()
            }
        }
    }
}
