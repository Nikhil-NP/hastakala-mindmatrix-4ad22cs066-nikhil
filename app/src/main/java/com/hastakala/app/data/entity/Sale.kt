package com.hastakala.app.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "sales",
    foreignKeys = [
        ForeignKey(
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["productId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("productId")]
)
data class Sale(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productId: Int,
    val quantity: Int,
    val pricePerUnit: Double,
    val totalAmount: Double,
    val saleDate: String,
    val timestamp: Long
)
