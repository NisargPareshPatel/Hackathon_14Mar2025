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
    private val onLogoutClicked: () -> Unit,
    componentContext: ComponentContext,
) : StoreProductListComponent, ComponentContext by componentContext, KoinComponent {
    private val scope = coroutineScope()
    private val appSettings by inject<AppSettings>()
    private val apiController by inject<ApiController>()

    override val isLoading = MutableValue(false)
    override val isRefreshing = MutableValue(false)
    override val items = MutableValue<List<ApiProductModel>>(emptyList())
    override val itemsBooked =
        MutableValue<List<StoreProductListComponent.BookedModel>>(emptyList())

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

    }

    override fun onProductClicked(productId: Long) {
        onProductClicked.invoke(productId)
    }

    override fun onLogoutClicked() {
        onLogoutClicked.invoke()
    }

    private suspend fun load(refresh: Boolean) {
        if (refresh) {
            isRefreshing.value = true
        } else {
            isLoading.value = true
        }

        apiController.getProdByStore(storeId = appSettings.id).onSuccess { list ->
            val (booked, other) = list.partition(ApiProductModel::booked)

            runCatching {
                itemsBooked.value = booked.map {
                    StoreProductListComponent.BookedModel(
                        item = it,
                        user = apiController.getUserById(it.bookerId!!).getOrThrow()
                    )
                }
            }.onFailure {
                it.printStackTrace()
            }

            items.value = other
        }.onFailure {
            it.printStackTrace()
        }

        isRefreshing.value = false
        isLoading.value = false
    }
}