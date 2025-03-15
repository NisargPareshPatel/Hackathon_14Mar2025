package com.example.urhacks25.core.api_model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiUserModel (
    @SerialName("email") val email: String,
    @SerialName("password") val password: String? = null,
    @SerialName("first") val firstName: String,
    @SerialName("last") val lastName: String,
    @SerialName("phone") val phone: String,
)