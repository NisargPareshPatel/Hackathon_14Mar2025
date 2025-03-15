package com.example.urhacks25.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.example.urhacks25.components.RootComponent
import com.example.urhacks25.ui.auth_flow.AuthFlow
import com.example.urhacks25.ui.store_flow.StoreFlow

@Composable
fun Root(component: RootComponent) {
    val childSlot by component.slot.subscribeAsState()

    childSlot.child?.let { child ->
        when (val c = child.instance) {
            is RootComponent.Child.Authorization -> AuthFlow(c.component)
            is RootComponent.Child.StoreFlow -> StoreFlow(c.component)
        }
    }
}