package com.example.urhacks25.components.user_flow.store

import com.arkivanov.decompose.value.Value
import com.example.urhacks25.core.api_model.ApiProductModel

interface UserStoreComponent {
    val storeName: String
    val isLoading: Value<Boolean>

    val items: Value<List<ApiProductModel>>
    val isRefreshing: Value<Boolean>
    fun refresh()

    fun onBackPressed()
    fun onProductClicked(productId: String)
}