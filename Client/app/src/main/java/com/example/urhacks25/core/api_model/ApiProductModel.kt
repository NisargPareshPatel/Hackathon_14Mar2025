package com.example.urhacks25.core.api_model

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiProductModel (
    @SerialName("name") val name: String,
    @SerialName("photo") val photoUrl: String,
    @SerialName("expiry") val expiry: Instant,
    @SerialName("price") val price: Double,
    @SerialName("store_id") val storeId: String,

    @SerialName("_id") val id: String? = null,
    @SerialName("booked") val booked: Boolean = false,
    @SerialName("booker_id") val bookerId: String? = null,
)