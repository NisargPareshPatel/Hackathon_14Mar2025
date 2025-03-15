package com.example.urhacks25.components.user_flow.store

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.example.urhacks25.core.ApiController
import com.example.urhacks25.core.api_model.ApiProductModel
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DefaultUserStoreComponent(
    override val storeName: String,
    private val storeId: String,
    private val onBackClicked: () -> Unit,
    private val onProductClicked: (String) -> Unit,
    componentContext: ComponentContext,
) : UserStoreComponent, ComponentContext by componentContext, KoinComponent {
    private val scope = coroutineScope()
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

    override fun onBackPressed() {
        onBackClicked.invoke()
    }

    override fun onProductClicked(productId: String) {
        onProductClicked.invoke(productId)
    }

    private suspend fun load(refresh: Boolean) {
        if (refresh) {
            isRefreshing.value = true
        } else {
            isLoading.value = true
        }

        apiController.getProdByStore(storeId = storeId).onSuccess {
            items.value = it
        }.onFailure {
            it.printStackTrace()
        }

        isRefreshing.value = false
        isLoading.value = false
    }
}