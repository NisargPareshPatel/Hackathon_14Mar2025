package com.example.urhacks25.components.auth_flow.register

import com.arkivanov.decompose.value.Value

interface RegisterComponent {
    val isLoading: Value<Boolean>
    val canContinue: Value<Boolean>
    fun onBackPressed()

    val registerAsStore: Value<Boolean>
    fun setRegisterAsStore(value: Boolean)

    // Generic
    val username: Value<String>
    fun setUsername(value: String)

    val password: Value<String>
    fun setPassword(value: String)

    val confirmPassword: Value<String>
    fun setConfirmPassword(value: String)

    // Store
    val location: Value<String>
    fun setLocation(value: String)

    val storeName: Value<String>
    fun setStoreName(value: String)

    // User
    val firstName: Value<String>
    fun setFirstName(value: String)

    val lastName: Value<String>
    fun setLastName(value: String)

    val phoneNumber: Value<String>
    fun setPhoneNumber(value: String)

    //
    fun dispatchAuthorization()
}