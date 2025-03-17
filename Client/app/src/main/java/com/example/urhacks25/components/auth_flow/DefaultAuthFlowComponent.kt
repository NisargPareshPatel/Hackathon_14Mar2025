package com.example.urhacks25.components.auth_flow

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.example.urhacks25.components.auth_flow.AuthFlowComponent.Child
import com.example.urhacks25.components.auth_flow.landing.DefaultLandingComponent
import com.example.urhacks25.components.auth_flow.register.DefaultRegisterComponent
import com.example.urhacks25.components.auth_flow.sign_in.DefaultSignInComponent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class DefaultAuthFlowComponent (
    private val onSuccess: () -> Unit,
    componentContext: ComponentContext
): AuthFlowComponent, ComponentContext by componentContext {
    private val navigator = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, Child>> = childStack(
        source = navigator,
        serializer = Config.serializer(),
        initialStack = { listOf(Config.Landing) },
        childFactory = { config, context ->
            when (config) {
                Config.Landing -> {
                    Child.Landing(DefaultLandingComponent(onSignUp = {
                        navigator.pushNew(Config.Register)
                    }, onSignIn = {
                        navigator.pushNew(Config.SignIn)
                    }, componentContext = context))
                }

                Config.SignIn -> {
                    Child.SignIn(
                        DefaultSignInComponent(
                            onBack = navigator::pop,
                            onSuccess = onSuccess,
                            onRegister = {
                                navigator.pushNew(Config.Register)
                            },
                            componentContext = context
                        )
                    )
                }

                Config.Register -> {
                    Child.Register(
                        DefaultRegisterComponent(
                            onBack = navigator::pop,
                            onSuccess = onSuccess,
                            componentContext = context
                        )
                    )
                }
            }
        }
    )

    override fun onBackClicked() {
        navigator.pop()
    }

    @Serializable
    private sealed interface Config {
        @SerialName("landing") @Serializable data object Landing: Config
        @SerialName("sign_in") @Serializable data object SignIn: Config
        @SerialName("register") @Serializable data object Register: Config
    }
}