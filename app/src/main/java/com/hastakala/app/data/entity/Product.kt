package com.hastakala.app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val color: String,
    val iconRes: Int,
    val currentStock: Int,
    val lowStockThreshold: Int
)
