package com.example.urhacks25.components

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.value.Value
import com.example.urhacks25.components.RootComponent.Child.*
import com.example.urhacks25.components.auth_flow.DefaultAuthFlowComponent
import com.example.urhacks25.components.store_flow.DefaultStoreFlowComponent
import com.example.urhacks25.components.user_flow.DefaultUserFlowComponent
import com.example.urhacks25.core.AppSettings
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DefaultRootComponent (
    componentContext: ComponentContext
): RootComponent, ComponentContext by componentContext, KoinComponent {
    private val appSettings by inject<AppSettings>()

    private val navigation = SlotNavigation<Config>()
    override val slot: Value<ChildSlot<*, RootComponent.Child>> = childSlot(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = {
            if (appSettings.token.isNotEmpty()) {
                if (appSettings.isStore) {
                    Config.Store
                } else {
                    Config.User
                }
            } else {
                Config.Authorization
            }
        },
        childFactory = { config, context ->
            when (config) {
                Config.Authorization -> Authorization(DefaultAuthFlowComponent(
                    onSuccess = {
                        if (appSettings.isStore) {
                            navigation.activate(Config.Store)
                        } else {
                            navigation.activate(Config.User)
                        }
                    }, componentContext = context
                ))

                Config.Store -> StoreFlow(
                    component = DefaultStoreFlowComponent(
                        onLogoutClicked = ::logout,
                        componentContext = context
                    )
                )

                Config.User -> UserFlow(
                    component = DefaultUserFlowComponent(
                        onLogoutClicked = ::logout,
                        componentContext = context
                    )
                )
            }
        }
    )

    private fun logout() {
        appSettings.id = ""
        appSettings.token = ""
        navigation.activate(Config.Authorization)
    }

    @Serializable
    private sealed interface Config {
        @SerialName("authorization") @Serializable data object Authorization: Config
        @SerialName("store") @Serializable data object Store: Config
        @SerialName("user") @Serializable data object User: Config
    }
}