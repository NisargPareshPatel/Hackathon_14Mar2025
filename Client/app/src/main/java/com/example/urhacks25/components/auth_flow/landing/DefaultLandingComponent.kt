package com.example.urhacks25.components.auth_flow.landing

import com.arkivanov.decompose.ComponentContext

class DefaultLandingComponent (
    private val onSignIn: () -> Unit,
    private val onSignUp: () -> Unit,
    componentContext: ComponentContext
): LandingComponent, ComponentContext by componentContext {
    override fun onSignInClicked() {
        onSignIn()
    }

    override fun onSignUpClicked() {
        onSignUp()
    }
}