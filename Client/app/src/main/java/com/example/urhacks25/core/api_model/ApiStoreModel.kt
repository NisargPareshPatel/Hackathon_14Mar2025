package com.example.urhacks25.core.api_model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiStoreModel (
    @SerialName("name") val name: String,
    @SerialName("email") val email: String,
    @SerialName("lat") val lat: String,
    @SerialName("long") val long: String,
    //
    @SerialName("id") val id: String? = null,
    @SerialName("password") val password: String? = null,
    @SerialName("token") val token: String? = null,
)