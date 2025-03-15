package com.example.urhacks25.ui.user_flow

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.example.urhacks25.components.store_flow.StoreFlowComponent
import com.example.urhacks25.components.user_flow.UserFlowComponent
import com.example.urhacks25.ui.store_flow.CreateProduct
import com.example.urhacks25.ui.store_flow.ProductList

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun UserFlow(
    component: UserFlowComponent
) {
    Children(
        component.stack, animation = predictiveBackAnimation(
            backHandler = component.backHandler,
            fallbackAnimation = stackAnimation(fade() + slide(orientation = Orientation.Horizontal)),
            onBack = component::onBackClicked,
        )
    ) { child ->
        when (val c = child.instance) {
            is UserFlowComponent.Child.UserMap -> UserMap(c.component)
            is UserFlowComponent.Child.UserStore -> UserStore(c.component)
            is UserFlowComponent.Child.ProductPage -> ProductPage(c.component)
        }
    }
}