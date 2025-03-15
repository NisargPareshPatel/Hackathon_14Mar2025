package com.example.urhacks25.components.store_flow.create_product

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.example.urhacks25.components.store_flow.product_list.StoreProductListComponent
import com.example.urhacks25.core.ApiController
import com.example.urhacks25.core.AppSettings
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue
import kotlin.math.exp

class DefaultStoreCreateProductComponent (
    private val onBack: () -> Unit,
    private val onAdded: () -> Unit,
    componentContext: ComponentContext,
) : StoreCreateProductComponent, ComponentContext by componentContext, KoinComponent {
    private val scope = coroutineScope()
    private val appSettings by inject<AppSettings>()
    private val apiController by inject<ApiController>()

    override val isLoading = MutableValue(false)
    override val canContinue = MutableValue(false)
    override val expiryDate = MutableValue(Clock.System.now())
    override val name = MutableValue("")
    override val photoPath = MutableValue("")
    override val price = MutableValue(0.0)

    override fun onBackPressed() {
        onBack.invoke()
    }

    override fun setName(value: String) {
        name.value = value
        check()
    }

    override fun setPhotoPath(value: String) {
        photoPath.value = value
        check()
    }

    override fun setPrice(value: Double) {
        price.value = value
        check()
    }

    override fun setExpiryDate(value: Instant) {
        expiryDate.value = value
        check()
    }

    private fun check() {
        canContinue.value = name.value.isNotEmpty() && price.value >= 0.0 && expiryDate.value > Clock.System.now()
    }

    override fun dispatchCreation() {
        scope.launch {
            isLoading.value = true

            // 1. Upload picture to CDN

            // 2. Add to database

            isLoading.value = false
        }
    }
}