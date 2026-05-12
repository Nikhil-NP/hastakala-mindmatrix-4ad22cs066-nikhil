package com.hastakala.app.utils

import android.content.Context
import com.hastakala.app.data.database.AppDatabase
import com.hastakala.app.repository.ShopRepository

object RepositoryProvider {
    @Volatile private var repository: ShopRepository? = null

    fun getRepository(context: Context): ShopRepository {
        return repository ?: synchronized(this) {
            val db = AppDatabase.getDatabase(context)
            ShopRepository(db, db.productDao(), db.saleDao()).also { repository = it }
        }
    }
}
