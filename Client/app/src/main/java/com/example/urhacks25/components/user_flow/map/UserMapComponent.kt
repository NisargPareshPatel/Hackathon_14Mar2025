package com.example.urhacks25.components.user_flow.map

import com.arkivanov.decompose.value.Value
import com.google.android.gms.maps.model.LatLng

interface UserMapComponent {
    val clusters: Value<List<MapCluster>>

    val inDirectoryMode: Value<Boolean>
    fun setInDirectoryMode(value: Boolean)

    fun onLogoutClicked()
    fun onStoreClicked(storeId: String, storeName: String)

    data class MapCluster (
        val id: String,
        val name: String,
        val coordinates: LatLng
    )
}