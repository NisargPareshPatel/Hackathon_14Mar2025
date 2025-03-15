package com.example.urhacks25.components.user_flow.product_page

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.example.urhacks25.core.ApiController
import com.example.urhacks25.core.AppSettings
import com.example.urhacks25.core.api_model.ApiProductModel
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Optional

class DefaultUserProductPageComponent (
    private val onBackClicked: () -> Unit,
    private val onBookingConfirmed: () -> Unit,
    private val storeId: String,
    private val productId: String,
    componentContext: ComponentContext
): UserProductPageComponent, ComponentContext by componentContext, KoinComponent {
    private val scope = coroutineScope()
    private val apiController by inject<ApiController>()
    private val appSettings by inject<AppSettings>()

    override val isLoading = MutableValue(false)
    override val item = MutableValue(Optional.empty<ApiProductModel>())

    init {
        doOnCreate {
            scope.launch {
                isLoading.value = true

                apiController.getProdById(productId).onSuccess { s ->
                    item.value = Optional.of(s)
                }

                isLoading.value = false
            }
        }
    }

    override fun onBackPressed() {
        onBackClicked.invoke()
    }

    override fun requestBooking() {
        TODO("Not yet implemented")
    }
}