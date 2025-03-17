package com.example.urhacks25.components.store_flow.product_list

import com.arkivanov.decompose.value.Value
import com.example.urhacks25.core.model.ProductModel

interface StoreProductListComponent {
    val isLoading: Value<Boolean>
    fun onBackPressed()

    val items: Value<List<ProductModel>>
    val isRefreshing: Value<Boolean>
    fun refresh()

    fun onMarkProductAsDone(productId: Long)
    fun onProductClicked(productId: Long)
}