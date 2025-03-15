package com.example.urhacks25.components.store_flow.product_list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.example.urhacks25.core.ApiController
import com.example.urhacks25.core.AppSettings
import com.example.urhacks25.core.api_model.ApiProductModel
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DefaultStoreProductListComponent(
    private val onCreateClicked: () -> Unit,
    private val onProductClicked: (Long) -> Unit,
    componentContext: ComponentContext,
) : StoreProductListComponent, ComponentContext by componentContext, KoinComponent {
    private val scope = coroutineScope()
    private val appSettings by inject<AppSettings>()
    private val apiController by inject<ApiController>()

    override val isLoading = MutableValue(false)
    override val isRefreshing = MutableValue(false)
    override val items = MutableValue<List<ApiProductModel>>(emptyList())

    init {
        scope.launch {
            load(false)
        }
    }

    override fun refresh() {
        scope.launch {
            load(true)
        }
    }

    override fun onCreateClicked() {
        onCreateClicked.invoke()
    }

    override fun onMarkProductAsDone(productId: Long) {
        TODO("Not yet implemented")
    }

    override fun onProductClicked(productId: Long) {
        onProductClicked.invoke(productId)
    }

    private suspend fun load(refresh: Boolean) {
        if (refresh) {
            isRefreshing.value = true
        } else {
            isLoading.value = true
        }

        apiController.getProdByStore(storeId = appSettings.id).onSuccess {
            items.value = it
        }.onFailure {
            it.printStackTrace()
        }

        isRefreshing.value = false
        isLoading.value = false
    }
}