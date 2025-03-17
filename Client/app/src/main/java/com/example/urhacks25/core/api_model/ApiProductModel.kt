package com.example.urhacks25.core.api_model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiProductModel (
    @SerialName("name") val name: String,
    @SerialName("photo") val photoUrl: String,
    @SerialName("expiry") val expiry: String,
    @SerialName("price") val price: String,
    @SerialName("store_id") val storeId: String,
)