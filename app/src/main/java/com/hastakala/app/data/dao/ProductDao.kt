package com.hastakala.app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hastakala.app.data.entity.Product

@Dao
interface ProductDao {
    @Insert
    suspend fun insertProduct(product: Product): Long

    @Update
    suspend fun updateProduct(product: Product)

    @Query("UPDATE products SET currentStock = :newStock WHERE id = :productId")
    suspend fun updateStock(productId: Int, newStock: Int)

    @Query("UPDATE products SET currentStock = currentStock + :change WHERE id = :productId")
    suspend fun changeStock(productId: Int, change: Int)

    @Query("SELECT * FROM products ORDER BY name, color")
    fun getAllProducts(): LiveData<List<Product>>

    @Query("SELECT * FROM products ORDER BY name, color")
    suspend fun getAllProductsNow(): List<Product>

    @Query("SELECT * FROM products WHERE id = :id LIMIT 1")
    suspend fun getProductById(id: Int): Product?

    @Query("SELECT * FROM products WHERE currentStock <= lowStockThreshold ORDER BY currentStock ASC")
    fun getLowStockProducts(): LiveData<List<Product>>

    @Query("SELECT COUNT(*) FROM products")
    suspend fun countProducts(): Int
}
