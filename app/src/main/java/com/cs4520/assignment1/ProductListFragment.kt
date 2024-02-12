package com.cs4520.assignment1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProductListFragment: Fragment() {
    lateinit var recyclerView: RecyclerView
    companion object {
        fun newInstance(): ProductListFragment{
            return ProductListFragment();
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.product_list, container, false)
        recyclerView = view.findViewById(R.id.productList)
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataset = arrayOf("January", "February", "March")
        val productListAdapter = ProductListAdapter(dataset);
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = productListAdapter
    }
}