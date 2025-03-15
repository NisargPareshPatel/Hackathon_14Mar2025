package com.example.urhacks25.core.api_model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class ApiStoreModel @OptIn(ExperimentalSerializationApi::class) constructor(
    @SerialName("name") val name: String,
    @SerialName("email") val email: String,
    @SerialName("lat") val lat: String,
    @SerialName("long") val long: String,
    //
    @SerialName("id") @JsonNames("_id") val id: String? = null,
    @SerialName("password") val password: String? = null,
    @SerialName("token") val token: String? = null,
)