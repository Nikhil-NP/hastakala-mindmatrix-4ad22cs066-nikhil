package com.hastakala.app.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hastakala.app.data.entity.Sale
import com.hastakala.app.data.model.DailyRevenue
import com.hastakala.app.data.model.SaleWithProduct
import com.hastakala.app.data.model.SalesSummary

@Dao
interface SaleDao {
    @Insert
    suspend fun insertSale(sale: Sale): Long

    @Delete
    suspend fun deleteSale(sale: Sale)

    @Query("SELECT * FROM sales WHERE id = :id LIMIT 1")
    suspend fun getSaleById(id: Int): Sale?

    @Query(
        """
        SELECT sales.id, sales.productId, products.name AS productName, products.color,
               sales.quantity, sales.pricePerUnit, sales.totalAmount, sales.saleDate, sales.timestamp
        FROM sales
        INNER JOIN products ON products.id = sales.productId
        ORDER BY sales.timestamp DESC
        """
    )
    fun getAllSales(): LiveData<List<SaleWithProduct>>

    @Query(
        """
        SELECT sales.id, sales.productId, products.name AS productName, products.color,
               sales.quantity, sales.pricePerUnit, sales.totalAmount, sales.saleDate, sales.timestamp
        FROM sales
        INNER JOIN products ON products.id = sales.productId
        WHERE sales.timestamp BETWEEN :start AND :end
        ORDER BY sales.timestamp DESC
        """
    )
    fun getSalesInRange(start: Long, end: Long): LiveData<List<SaleWithProduct>>

    @Query(
        """
        SELECT products.name || ' (' || products.color || ')' AS label,
               SUM(sales.quantity) AS quantitySold,
               SUM(sales.totalAmount) AS revenue
        FROM sales
        INNER JOIN products ON products.id = sales.productId
        WHERE sales.timestamp BETWEEN :start AND :end
        GROUP BY products.id
        ORDER BY quantitySold DESC
        """
    )
    fun getSalesSummary(start: Long, end: Long): LiveData<List<SalesSummary>>

    @Query(
        """
        SELECT saleDate, SUM(totalAmount) AS revenue
        FROM sales
        WHERE timestamp BETWEEN :start AND :end
        GROUP BY saleDate
        ORDER BY timestamp ASC
        """
    )
    fun getDailyRevenue(start: Long, end: Long): LiveData<List<DailyRevenue>>

    @Query("SELECT COALESCE(SUM(totalAmount), 0) FROM sales WHERE timestamp BETWEEN :start AND :end")
    fun getIncomeInRange(start: Long, end: Long): LiveData<Double>

    @Query("SELECT COALESCE(SUM(quantity), 0) FROM sales WHERE timestamp BETWEEN :start AND :end")
    fun getItemsSoldInRange(start: Long, end: Long): LiveData<Int>
}
