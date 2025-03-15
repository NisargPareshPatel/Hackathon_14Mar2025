package com.example.urhacks25.core.api_model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiUserModel (
    @SerialName("email") val email: String,
    @SerialName("first") val firstName: String,
    @SerialName("last") val lastName: String,
    //
    @SerialName("id") val id: String? = null,
    @SerialName("password") val password: String? = null,
    @SerialName("phone") val phone: String? = null,
    @SerialName("token") val token: String? = null,
)