package com.example.urhacks25.components.user_flow.map

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.example.urhacks25.core.ApiController
import com.example.urhacks25.core.AppSettings
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class DefaultUserMapComponent(
    private val onStoreClicked: (String, String) -> Unit,
    private val onLogoutClicked: () -> Unit,
    componentContext: ComponentContext
): UserMapComponent, ComponentContext by componentContext, KoinComponent {
    private val scope = coroutineScope()
    private val appSettings by inject<AppSettings>()
    private val apiController by inject<ApiController>()

    init {
        doOnCreate {
            loadStores()
        }
    }

    override val clusters = MutableValue(emptyList<UserMapComponent.MapCluster>())
    override val inDirectoryMode = MutableValue(false)

    override fun setInDirectoryMode(value: Boolean) {
        inDirectoryMode.value = value
    }

    override fun onLogoutClicked() {
        onLogoutClicked.invoke()
    }

    override fun onStoreClicked(storeId: String, storeName: String) {
        onStoreClicked.invoke(storeId, storeName)
    }

    private fun loadStores() {
        scope.launch {
            apiController.getStores().onSuccess { stores ->
                clusters.value = stores.filter {
                    s -> s.id != null && s.lat.toDoubleOrNull() != null && s.long.toDoubleOrNull() != null
                }.map { s ->
                    UserMapComponent.MapCluster(
                        id = s.id!!,
                        name = s.name,
                        coordinates = LatLng(s.lat.toDouble(), s.long.toDouble())
                    )
                }
            }.onFailure {
                it.printStackTrace()
            }
        }
    }
}