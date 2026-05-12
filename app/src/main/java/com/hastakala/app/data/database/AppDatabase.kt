package com.hastakala.app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hastakala.app.data.dao.ProductDao
import com.hastakala.app.data.dao.SaleDao
import com.hastakala.app.data.entity.Product
import com.hastakala.app.data.entity.Sale

@Database(entities = [Product::class, Sale::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun saleDao(): SaleDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "hasta_kala_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
