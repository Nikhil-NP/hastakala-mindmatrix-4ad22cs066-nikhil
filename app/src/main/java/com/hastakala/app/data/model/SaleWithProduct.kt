package com.hastakala.app.data.model

data class SaleWithProduct(
    val id: Int,
    val productId: Int,
    val productName: String,
    val color: String,
    val quantity: Int,
    val pricePerUnit: Double,
    val totalAmount: Double,
    val saleDate: String,
    val timestamp: Long
)
