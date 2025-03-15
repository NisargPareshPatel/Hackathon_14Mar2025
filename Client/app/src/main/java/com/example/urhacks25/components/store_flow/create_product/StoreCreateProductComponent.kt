package com.example.urhacks25.components.store_flow.create_product

import com.arkivanov.decompose.value.Value
import kotlinx.datetime.Instant

interface StoreCreateProductComponent {
    val isLoading: Value<Boolean>
    fun onBackPressed()

    val error: Value<String>
    fun dismissError()

    val canContinue: Value<Boolean>
    fun dispatchCreation()

    val name: Value<String>
    fun setName(value: String)

    val photoPath: Value<String>
    fun setPhotoPath(value: String)

    val price: Value<String>
    fun setPrice(value: String)

    val expiryDate: Value<Instant>
    fun setExpiryDate(value: Instant)
}