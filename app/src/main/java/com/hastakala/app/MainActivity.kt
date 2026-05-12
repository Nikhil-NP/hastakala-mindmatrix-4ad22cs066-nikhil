package com.hastakala.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hastakala.app.ui.billing.QuickBillFragment
import com.hastakala.app.ui.dashboard.DashboardFragment
import com.hastakala.app.ui.income.IncomeFragment
import com.hastakala.app.ui.stock.StockFragment
import com.hastakala.app.utils.RepositoryProvider
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            RepositoryProvider.getRepository(applicationContext).seedSampleProducts()
        }

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_stock -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, StockFragment()).commit()
                R.id.nav_billing -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, QuickBillFragment()).commit()
                R.id.nav_dashboard -> showDashboard()
                R.id.nav_income -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, IncomeFragment()).commit()
            }
            true
        }

        if (savedInstanceState == null) {
            bottomNavigation.selectedItemId = R.id.nav_dashboard
        }
    }

    fun openBilling() {
        findViewById<BottomNavigationView>(R.id.bottomNavigation).selectedItemId = R.id.nav_billing
    }

    private fun showDashboard() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, DashboardFragment())
            .commit()
    }
}
