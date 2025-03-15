package com.example.urhacks25.components.store_flow.product_list

import com.arkivanov.decompose.value.Value
import com.example.urhacks25.core.api_model.ApiProductModel
import com.example.urhacks25.core.api_model.ApiUserModel
import com.example.urhacks25.core.model.ProductModel

interface StoreProductListComponent {
    val isLoading: Value<Boolean>

    val itemsBooked: Value<List<BookedModel>>
    val items: Value<List<ApiProductModel>>

    val isRefreshing: Value<Boolean>
    fun refresh()

    fun onLogoutClicked()
    fun onCreateClicked()
    fun onMarkProductAsDone(productId: Long)
    fun onProductClicked(productId: Long)

    data class BookedModel (
        val item: ApiProductModel,
        val user: ApiUserModel
    )
}