package com.example.urhacks25.components.store_flow.create_product

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.cloudinary.android.MediaManager
import com.example.urhacks25.components.store_flow.product_list.StoreProductListComponent
import com.example.urhacks25.core.ApiController
import com.example.urhacks25.core.AppSettings
import com.example.urhacks25.core.api_model.ApiProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.text.SimpleDateFormat
import java.util.TimeZone
import kotlin.getValue
import kotlin.math.exp
import kotlin.time.Duration.Companion.days
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

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
    override val price = MutableValue("")
    override val error = MutableValue("")

    override fun onBackPressed() {
        onBack.invoke()
    }

    override fun dismissError() {
        error.value = ""
    }

    override fun setName(value: String) {
        name.value = value
        check()
    }

    override fun setPhotoPath(value: String) {
        photoPath.value = value
        check()
    }

    override fun setPrice(value: String) {
        price.value = value
        check()
    }

    override fun setExpiryDate(value: Instant) {
        expiryDate.value = value
        check()
    }

    private fun check() {
        canContinue.value = name.value.isNotEmpty() &&
                (price.value.toDoubleOrNull() != null && price.value.toDoubleOrNull()!! >= 0.0)
                && expiryDate.value > (Clock.System.now() - 1.days)
    }

    private val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'").also {
        it.timeZone = TimeZone.getTimeZone("UTC")
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun dispatchCreation() {
        scope.launch {
            isLoading.value = true

            // 1. Upload picture to CDN
            val photoUrl = withContext(Dispatchers.IO) {
                val id = Uuid.random().toHexString()

                val cdMap = MediaManager.get().cloudinary.uploader().upload(photoPath.value, mapOf(
                    "public_id" to id
                ))

                cdMap["url"]?.toString()
            }

            // 2. Add to database
            runCatching {
                apiController.prodCreate(ApiProductModel(
                    name = name.value,
                    photoUrl = photoUrl.orEmpty(),
                    expiry = sdf.format(expiryDate.value.toEpochMilliseconds()),
                    price = price.value.toDouble(),
                    storeId = appSettings.id
                ))
            }.onFailure {
                error.value = it.message.orEmpty()
            }.onSuccess {
                onAdded()
            }

            isLoading.value = false
        }
    }
}