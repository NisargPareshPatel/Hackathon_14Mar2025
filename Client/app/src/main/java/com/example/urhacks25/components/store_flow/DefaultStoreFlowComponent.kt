package com.example.urhacks25.components.store_flow

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.example.urhacks25.components.store_flow.StoreFlowComponent.Child
import com.example.urhacks25.components.store_flow.create_product.DefaultStoreCreateProductComponent
import com.example.urhacks25.components.store_flow.product_list.DefaultStoreProductListComponent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class DefaultStoreFlowComponent (
    componentContext: ComponentContext
): StoreFlowComponent, ComponentContext by componentContext {
    private val navigator = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, Child>> = childStack(
        source = navigator,
        serializer = Config.serializer(),
        initialStack = { listOf(Config.ProdList) },
        childFactory = { config, context ->
            when (config) {
                Config.ProdList -> {
                    Child.ProductList(DefaultStoreProductListComponent(onCreateClicked = {
                        navigator.pushNew(Config.CreateProd)
                    }, onProductClicked = { id ->

                    }, componentContext = context))
                }

                Config.CreateProd -> {
                    Child.CreateProduct(DefaultStoreCreateProductComponent(
                        onBack = navigator::pop,
                        onAdded = {

                        },
                        componentContext = context
                    ))
                }
            }
        }
    )

    override fun onBackClicked() {
        navigator.pop()
    }

    @Serializable
    private sealed interface Config {
        @SerialName("list") @Serializable data object ProdList: Config
        @SerialName("create") @Serializable data object CreateProd: Config
    }
}