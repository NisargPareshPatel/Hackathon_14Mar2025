package com.example.urhacks25.ui.auth_flow

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.example.urhacks25.components.auth_flow.AuthFlowComponent

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun AuthFlow(
    component: AuthFlowComponent
) {
    Children(
        component.stack, animation = predictiveBackAnimation(
            backHandler = component.backHandler,
            fallbackAnimation = stackAnimation(fade() + slide(orientation = Orientation.Vertical)),
            onBack = component::onBackClicked,
        )
    ) { child ->
        when (val c = child.instance) {
            is AuthFlowComponent.Child.Landing -> Landing(c.component)
            is AuthFlowComponent.Child.Register -> Register(c.component)
            is AuthFlowComponent.Child.SignIn -> SignIn(c.component)
        }
    }
}