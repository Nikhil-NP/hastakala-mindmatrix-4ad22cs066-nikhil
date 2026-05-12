package com.hastakala.app.repository

import androidx.lifecycle.LiveData
import androidx.room.withTransaction
import com.hastakala.app.R
import com.hastakala.app.data.dao.ProductDao
import com.hastakala.app.data.dao.SaleDao
import com.hastakala.app.data.database.AppDatabase
import com.hastakala.app.data.entity.Product
import com.hastakala.app.data.entity.Sale
import com.hastakala.app.data.model.DailyRevenue
import com.hastakala.app.data.model.SaleWithProduct
import com.hastakala.app.data.model.SalesSummary
import com.hastakala.app.utils.DateFilter
import com.hastakala.app.utils.DateUtils

class ShopRepository(
    private val database: AppDatabase,
    private val productDao: ProductDao,
    private val saleDao: SaleDao
) {
    val products: LiveData<List<Product>> = productDao.getAllProducts()
    val lowStockProducts: LiveData<List<Product>> = productDao.getLowStockProducts()
    val allSales: LiveData<List<SaleWithProduct>> = saleDao.getAllSales()

    suspend fun seedSampleProducts() {
        if (productDao.countProducts() > 0) return
        productDao.insertProduct(Product(name = "Banana Fiber Bag", color = "Natural", iconRes = R.drawable.ic_bag, currentStock = 25, lowStockThreshold = 5))
        productDao.insertProduct(Product(name = "Artisan Keychain", color = "Red", iconRes = R.drawable.ic_keychain, currentStock = 45, lowStockThreshold = 10))
        productDao.insertProduct(Product(name = "Woven Coaster Set", color = "Multi", iconRes = R.drawable.ic_coaster, currentStock = 12, lowStockThreshold = 10))
        productDao.insertProduct(Product(name = "Coiled Basket", color = "Brown", iconRes = R.drawable.ic_bag, currentStock = 16, lowStockThreshold = 6))
    }

    suspend fun addProduct(name: String, color: String, stock: Int, threshold: Int) {
        productDao.insertProduct(
            Product(
                name = name.trim(),
                color = color.trim(),
                iconRes = R.drawable.ic_bag,
                currentStock = stock,
                lowStockThreshold = threshold
            )
        )
    }

    suspend fun updateProduct(product: Product) {
        productDao.updateProduct(product)
    }

    suspend fun saveSale(productId: Int, quantity: Int, pricePerUnit: Double): Result<Unit> {
        return database.withTransaction {
            val product = productDao.getProductById(productId)
                ?: return@withTransaction Result.failure(IllegalArgumentException("Product not found"))
            if (quantity <= 0) {
                return@withTransaction Result.failure(IllegalArgumentException("Enter a valid quantity"))
            }
            if (pricePerUnit <= 0.0) {
                return@withTransaction Result.failure(IllegalArgumentException("Enter a valid price"))
            }
            if (product.currentStock < quantity) {
                return@withTransaction Result.failure(IllegalArgumentException("Only ${product.currentStock} items in stock"))
            }

            val now = System.currentTimeMillis()
            saleDao.insertSale(
                Sale(
                    productId = productId,
                    quantity = quantity,
                    pricePerUnit = pricePerUnit,
                    totalAmount = quantity * pricePerUnit,
                    saleDate = DateUtils.formatDate(now),
                    timestamp = now
                )
            )
            productDao.changeStock(productId, -quantity)
            Result.success(Unit)
        }
    }

    suspend fun deleteSaleAndRestoreStock(saleId: Int) {
        database.withTransaction {
            val sale = saleDao.getSaleById(saleId) ?: return@withTransaction
            saleDao.deleteSale(sale)
            productDao.changeStock(sale.productId, sale.quantity)
        }
    }

    fun getSalesSummary(filter: DateFilter): LiveData<List<SalesSummary>> {
        val (start, end) = DateUtils.rangeFor(filter)
        return saleDao.getSalesSummary(start, end)
    }

    fun getDailyRevenue(filter: DateFilter): LiveData<List<DailyRevenue>> {
        val (start, end) = DateUtils.rangeFor(filter)
        return saleDao.getDailyRevenue(start, end)
    }

    fun getIncome(filter: DateFilter): LiveData<Double> {
        val (start, end) = DateUtils.rangeFor(filter)
        return saleDao.getIncomeInRange(start, end)
    }

    fun getItemsSold(filter: DateFilter): LiveData<Int> {
        val (start, end) = DateUtils.rangeFor(filter)
        return saleDao.getItemsSoldInRange(start, end)
    }

    fun getSales(filter: DateFilter): LiveData<List<SaleWithProduct>> {
        if (filter == DateFilter.ALL) return allSales
        val (start, end) = DateUtils.rangeFor(filter)
        return saleDao.getSalesInRange(start, end)
    }
}
