package com.example.urhacks25.core.api_model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiAuthModel (
    @SerialName("email") val email: String,
    @SerialName("password") val password: String
)