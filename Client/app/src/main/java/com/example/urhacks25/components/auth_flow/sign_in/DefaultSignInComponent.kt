package com.example.urhacks25.components.auth_flow.sign_in

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import org.koin.core.component.KoinComponent

class DefaultSignInComponent (
    private val onBack: () -> Unit,
    private val onRegister: () -> Unit,
    private val onSuccess: () -> Unit,
    componentContext: ComponentContext
): SignInComponent, ComponentContext by componentContext, KoinComponent {
    override val isLoading = MutableValue(false)
    override val canContinue = MutableValue(false)

    override fun onBackPressed() {
        onBack()
    }

    override val signAsStore = MutableValue(false)
    override fun setSignAsStore(value: Boolean) {
        signAsStore.value = value
    }

    override val username = MutableValue("")
    override fun setUsername(value: String) {
        username.value = value
        check()
    }

    override val password = MutableValue("")
    override fun setPassword(value: String) {
        password.value = value
        check()
    }

    private fun check() {
        canContinue.value = username.value.isNotEmpty() && password.value.isNotEmpty()
    }

    override fun onRegisterClicked() {
        onRegister()
    }

    override fun dispatchAuthorization() {
        TODO("Not yet implemented")
    }
}