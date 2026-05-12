package com.hastakala.app.ui.income

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.hastakala.app.R
import com.hastakala.app.adapter.IncomeAdapter
import com.hastakala.app.utils.DateFilter
import com.hastakala.app.viewmodel.IncomeViewModel

class IncomeFragment : Fragment(R.layout.fragment_income) {
    private val viewModel: IncomeViewModel by viewModels()
    private lateinit var adapter: IncomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val incomeText: TextView = view.findViewById(R.id.incomeTotalText)
        adapter = IncomeAdapter()

        val recyclerView = view.findViewById<RecyclerView>(R.id.incomeRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        view.findViewById<MaterialButton>(R.id.incomeWeekButton).setOnClickListener {
            viewModel.filter.value = DateFilter.WEEK
        }
        view.findViewById<MaterialButton>(R.id.incomeMonthButton).setOnClickListener {
            viewModel.filter.value = DateFilter.MONTH
        }
        view.findViewById<MaterialButton>(R.id.incomeAllButton).setOnClickListener {
            viewModel.filter.value = DateFilter.ALL
        }

        viewModel.income.observe(viewLifecycleOwner) {
            incomeText.text = "Rs ${it.toInt()}"
        }
        viewModel.sales.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = false
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteSale(adapter.getItem(viewHolder.bindingAdapterPosition).id)
            }
        }).attachToRecyclerView(recyclerView)
    }
}
