package com.example.urhacks25.components.store_flow

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import com.example.urhacks25.components.store_flow.product_list.StoreProductListComponent

interface StoreFlowComponent: BackHandlerOwner {
    val stack: Value<ChildStack<*, Child>>
    fun onBackClicked()

    sealed interface Child {
        class ProductList (
            val component: StoreProductListComponent
        ): Child
    }
}