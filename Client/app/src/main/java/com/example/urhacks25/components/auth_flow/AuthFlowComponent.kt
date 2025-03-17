package com.example.urhacks25.components.auth_flow

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import com.example.urhacks25.components.auth_flow.landing.LandingComponent
import com.example.urhacks25.components.auth_flow.register.RegisterComponent
import com.example.urhacks25.components.auth_flow.sign_in.SignInComponent

interface AuthFlowComponent: BackHandlerOwner {
    val stack: Value<ChildStack<*, Child>>
    fun onBackClicked()

    sealed interface Child {
        class Landing (
            val component: LandingComponent
        ): Child

        class SignIn (
            val component: SignInComponent
        ): Child

        class Register (
            val component: RegisterComponent
        ): Child
    }
}