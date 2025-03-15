package com.example.urhacks25.components

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.example.urhacks25.components.auth_flow.AuthFlowComponent
import com.example.urhacks25.components.store_flow.StoreFlowComponent
import com.example.urhacks25.components.user_flow.UserFlowComponent

interface RootComponent {
    val slot: Value<ChildSlot<*, Child>>

    sealed interface Child {
        class Authorization (
            val component: AuthFlowComponent
        ): Child

        /*class StoreFlow (
            val component: StoreFlowComponent
        ): Child

        class UserFlow (
            val component: UserFlowComponent
        ): Child*/
    }
}