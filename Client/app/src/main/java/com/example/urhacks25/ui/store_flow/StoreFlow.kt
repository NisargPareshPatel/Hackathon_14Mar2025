package com.example.urhacks25.ui.store_flow

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.example.urhacks25.components.store_flow.StoreFlowComponent

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun StoreFlow(
    component: StoreFlowComponent
) {
    Children(
        component.stack, animation = predictiveBackAnimation(
            backHandler = component.backHandler,
            fallbackAnimation = stackAnimation(fade() + slide(orientation = Orientation.Vertical)),
            onBack = component::onBackClicked,
        )
    ) { child ->
        when (val c = child.instance) {
            is StoreFlowComponent.Child.ProductList -> ProductList(c.component)
            is StoreFlowComponent.Child.CreateProduct -> CreateProduct(c.component)
        }
    }
}