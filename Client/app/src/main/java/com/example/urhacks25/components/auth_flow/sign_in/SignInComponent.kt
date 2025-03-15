package com.example.urhacks25.components.auth_flow.sign_in

import com.arkivanov.decompose.value.Value

interface SignInComponent {
    val isLoading: Value<Boolean>
    val canContinue: Value<Boolean>
    fun onBackPressed()

    val signAsStore: Value<Boolean>
    fun setSignAsStore(value: Boolean)

    val username: Value<String>
    fun setUsername(value: String)

    val password: Value<String>
    fun setPassword(value: String)

    val error: Value<String>
    fun dismissError()

    fun onRegisterClicked()
    fun dispatchAuthorization()
}