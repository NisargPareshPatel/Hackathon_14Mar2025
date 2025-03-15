package com.example.urhacks25.components.auth_flow.sign_in

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.example.urhacks25.core.ApiController
import com.example.urhacks25.core.AppSettings
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DefaultSignInComponent (
    private val onBack: () -> Unit,
    private val onRegister: () -> Unit,
    private val onSuccess: () -> Unit,
    componentContext: ComponentContext
): SignInComponent, ComponentContext by componentContext, KoinComponent {
    private val scope = coroutineScope()
    private val apiController by inject<ApiController>()
    private val appSettings by inject<AppSettings>()

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

    override val error = MutableValue("")
    override fun dismissError() {
        error.value = ""
    }

    private fun check() {
        canContinue.value = username.value.isNotEmpty() && password.value.isNotEmpty()
    }

    override fun onRegisterClicked() {
        onRegister()
    }

    override fun dispatchAuthorization() {
        scope.launch {
            isLoading.value = true

            if (signAsStore.value) {
                apiController.storeLogin(username.value, password.value).onSuccess { r ->
                    appSettings.token = r.token!!
                    appSettings.isStore = true
                    onSuccess()
                }
            } else {
                apiController.userLogin(username.value, password.value).onSuccess { r ->
                    appSettings.token = r.token!!
                    appSettings.isStore = false
                    onSuccess()
                }
            }.onFailure {
                error.value = it.message.orEmpty()
            }

            isLoading.value = false
        }
    }
}