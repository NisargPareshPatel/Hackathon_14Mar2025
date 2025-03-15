package com.example.urhacks25.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.value.Value
import com.example.urhacks25.components.auth_flow.DefaultAuthFlowComponent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class DefaultRootComponent (
    componentContext: ComponentContext
): RootComponent, ComponentContext by componentContext {
    private val navigation = SlotNavigation<Config>()
    override val slot: Value<ChildSlot<*, RootComponent.Child>> = childSlot(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = { Config.Authorization },
        childFactory = { config, context ->
            when (config) {
                Config.Authorization -> RootComponent.Child.Authorization(DefaultAuthFlowComponent(
                    onSuccess = {

                    }, componentContext = context
                ))
            }
        }
    )

    @Serializable
    private sealed interface Config {
        @SerialName("authorization") @Serializable data object Authorization: Config
    }
}