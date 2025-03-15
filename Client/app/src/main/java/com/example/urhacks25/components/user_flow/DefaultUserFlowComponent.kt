package com.example.urhacks25.components.user_flow

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.example.urhacks25.components.user_flow.DefaultUserFlowComponent.Config.*
import com.example.urhacks25.components.user_flow.UserFlowComponent.Child
import com.example.urhacks25.components.user_flow.UserFlowComponent.Child.*
import com.example.urhacks25.components.user_flow.map.DefaultUserMapComponent
import com.example.urhacks25.components.user_flow.product_page.DefaultUserProductPageComponent
import com.example.urhacks25.components.user_flow.store.DefaultUserStoreComponent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class DefaultUserFlowComponent(
    onLogoutClicked: () -> Unit,
    componentContext: ComponentContext
) : UserFlowComponent, ComponentContext by componentContext {
    private val navigator = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, Child>> = childStack(
        source = navigator,
        serializer = Config.serializer(),
        initialStack = { listOf(Map) },
        childFactory = { config, context ->
            when (config) {
                Map -> {
                    UserMap(
                        DefaultUserMapComponent(
                            onStoreClicked = { id, name ->
                                navigator.pushNew(Store(id, name))
                            },
                            onLogoutClicked = onLogoutClicked,
                            componentContext = context
                        )
                    )
                }

                is Store -> {
                    UserStore(
                        DefaultUserStoreComponent(
                            onBackClicked = ::onBackClicked,
                            onProductClicked = { id ->
                                navigator.pushNew(Config.Product(productId = id, storeId = config.id))
                            },
                            storeId = config.id,
                            storeName = config.name,
                            componentContext = context
                        )
                    )
                }

                is Product -> {
                    ProductPage(
                        DefaultUserProductPageComponent(
                            onBackClicked = ::onBackClicked,
                            onBookingConfirmed = ::onBackClicked,
                            storeId = config.storeId,
                            productId = config.productId,
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
        @SerialName("map")
        @Serializable
        data object Map : Config

        @SerialName("store")
        @Serializable
        data class Store (
            val id: String,
            val name: String
        ) : Config

        @SerialName("product")
        @Serializable
        data class Product (
            val storeId: String,
            val productId: String
        ) : Config
    }
}