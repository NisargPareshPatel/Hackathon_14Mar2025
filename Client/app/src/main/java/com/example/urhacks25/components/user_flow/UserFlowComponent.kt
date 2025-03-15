package com.example.urhacks25.components.user_flow

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import com.example.urhacks25.components.user_flow.map.UserMapComponent
import com.example.urhacks25.components.user_flow.product_page.UserProductPageComponent
import com.example.urhacks25.components.user_flow.store.UserStoreComponent

interface UserFlowComponent: BackHandlerOwner {
    val stack: Value<ChildStack<*, Child>>
    fun onBackClicked()

    sealed interface Child {
        class UserMap (
            val component: UserMapComponent
        ): Child

        class UserStore (
            val component: UserStoreComponent
        ): Child

        class ProductPage (
            val component: UserProductPageComponent
        ): Child
    }
}