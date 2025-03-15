package com.example.urhacks25.components.auth_flow.register

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import org.koin.core.component.KoinComponent

class DefaultRegisterComponent (
    private val onBack: () -> Unit,
    private val onSuccess: () -> Unit,
    componentContext: ComponentContext
): RegisterComponent, ComponentContext by componentContext, KoinComponent {
    override val isLoading = MutableValue(false)
    override val canContinue = MutableValue(false)

    override fun onBackPressed() {
        onBack()
    }

    override val registerAsStore = MutableValue(false)
    override fun setRegisterAsStore(value: Boolean) {
        registerAsStore.value = value
        check()
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

    override val confirmPassword = MutableValue("")
    override fun setConfirmPassword(value: String) {
        confirmPassword.value = value
        check()
    }

    override val location = MutableValue("")
    override fun setLocation(value: String) {
        location.value = value
        check()
    }

    override val storeName = MutableValue("")
    override fun setStoreName(value: String) {
        storeName.value = value
        check()
    }

    override val firstName = MutableValue("")
    override fun setFirstName(value: String) {
        firstName.value = value
        check()
    }

    override val lastName = MutableValue("")
    override fun setLastName(value: String) {
        lastName.value = value
        check()
    }

    override val phoneNumber = MutableValue("")
    override fun setPhoneNumber(value: String) {
        phoneNumber.value = value
        check()
    }

    private fun check() {
        canContinue.value = username.value.isNotEmpty() && password.value.isNotEmpty() && password.value == confirmPassword.value && if (registerAsStore.value) {
            location.value.isNotEmpty() && storeName.value.isNotEmpty()
        } else {
            firstName.value.isNotEmpty() && lastName.value.isNotEmpty() && phoneNumber.value.isNotEmpty()
        }
    }

    override fun dispatchAuthorization() {
        TODO("Not yet implemented")
    }
}