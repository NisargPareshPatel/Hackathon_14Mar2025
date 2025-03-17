package com.example.urhacks25

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import com.example.urhacks25.components.DefaultRootComponent
import com.example.urhacks25.ui.Root
import com.example.urhacks25.ui.theme.Urhacks25Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootComponent = DefaultRootComponent(defaultComponentContext())

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
        )

        setContent {
            Urhacks25Theme {
                Root(rootComponent)
            }
        }
    }
}