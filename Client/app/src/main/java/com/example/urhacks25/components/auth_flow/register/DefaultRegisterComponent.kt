package com.example.urhacks25.components.auth_flow.register

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.example.urhacks25.core.ApiController
import com.example.urhacks25.core.AppSettings
import com.example.urhacks25.core.api_model.ApiStoreModel
import com.example.urhacks25.core.api_model.ApiUserModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class DefaultRegisterComponent (
    private val onBack: () -> Unit,
    private val onSuccess: () -> Unit,
    componentContext: ComponentContext
): RegisterComponent, ComponentContext by componentContext, KoinComponent {
    private val scope = coroutineScope()
    private val apiController by inject<ApiController>()
    private val appSettings by inject<AppSettings>()

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

    override val location = MutableValue(LatLng(0.0, 0.0))
    override fun setLocation(value: LatLng) {
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

    override val error = MutableValue("")
    override fun dismissError() {
        error.value = ""
    }

    private fun check() {
        canContinue.value = username.value.isNotEmpty() && password.value.isNotEmpty() && password.value == confirmPassword.value && if (registerAsStore.value) {
            storeName.value.isNotEmpty()
        } else {
            firstName.value.isNotEmpty() && lastName.value.isNotEmpty() && phoneNumber.value.isNotEmpty()
        }
    }

    override fun dispatchAuthorization() {
        scope.launch {
            isLoading.value = true

            if (registerAsStore.value) {
                apiController.storeRegister(ApiStoreModel(
                    name = storeName.value,
                    email = username.value,
                    lat = location.value.latitude.toString(),
                    long = location.value.longitude.toString(),
                    password = password.value
                )).onSuccess { r ->
                    appSettings.token = r.token!!
                    appSettings.isStore = true
                    onSuccess()
                }
            } else {
                apiController.userRegister(ApiUserModel(
                    phone = phoneNumber.value,
                    email = username.value,
                    firstName = firstName.value,
                    lastName = lastName.value,
                    password = password.value
                )).onSuccess { r ->
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