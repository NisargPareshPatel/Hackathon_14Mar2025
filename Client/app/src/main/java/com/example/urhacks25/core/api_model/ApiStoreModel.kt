package com.example.urhacks25.core.api_model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiStoreModel (
    @SerialName("name") val name: String,
    @SerialName("email") val email: String,
    @SerialName("password") val password: String? = null,
    @SerialName("lat") val lat: String,
    @SerialName("long") val long: String,
)