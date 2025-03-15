package com.example.urhacks25.core.model

import kotlinx.datetime.Instant

data class ProductModel (
    val name: String,
    val price: Double,
    val expiryDate: Instant
)