package com.example.urhacks25.components.user_flow.product_page

import com.arkivanov.decompose.value.Value
import com.example.urhacks25.core.api_model.ApiProductModel
import java.util.Optional

interface UserProductPageComponent {
    val isLoading: Value<Boolean>
    val item: Value<Optional<ApiProductModel>>

    fun onBackPressed()
    fun requestBooking()
}