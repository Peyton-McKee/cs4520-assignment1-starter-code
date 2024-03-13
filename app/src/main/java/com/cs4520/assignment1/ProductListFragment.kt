package com.cs4520.assignment1

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cs4520.assignment1.databinding.ProductListBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProductListFragment : Fragment() {
    private lateinit var binding: ProductListBinding
    private lateinit var viewModel: ProductListViewModel
    private lateinit var scrollListener : PaginationScrollListener
    private lateinit var adapter : ProductListAdapter

    companion object {
        fun newInstance(): ProductListFragment {
            return ProductListFragment();
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProductListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ProductListViewModel::class.java]

        binding.apply {
            addViewModelObservers()
            adapter = ProductListAdapter(mutableListOf(), productList)
            scrollListener = object : PaginationScrollListener(productList.layoutManager as LinearLayoutManager) {
                override fun loadMoreItems() {
                    GlobalScope.launch(Dispatchers.IO) {
                        viewModel.loadProducts(requireContext())
                    }
                }

                override val totalPageCount: Int
                    get() = adapter.dataSet.size
                override val isLoading: Boolean
                    get() = viewModel.loadingLiveData.value ?: false
            }
            productList.adapter = adapter
            productList.addOnScrollListener(scrollListener)
        }
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch {
            if (context != null) {
                viewModel.loadProducts(requireContext())
            }
        }
    }

    private fun ProductListBinding.addViewModelObservers() {
        viewModel.productsLiveData.observe(viewLifecycleOwner) {
            binding.productListProgressBar.visibility = ProgressBar.GONE
            if (it.isEmpty() && adapter.dataSet.isEmpty()) {
                binding.productListEmpty.visibility = View.VISIBLE
            } else {
                binding.productListEmpty.visibility = View.GONE
                adapter.updateData(it)
                binding.productList.adapter = adapter
            }
        }

        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            if (it) {
                binding.productListProgressBar.visibility = ProgressBar.VISIBLE
            } else {
                binding.productListProgressBar.visibility = ProgressBar.GONE
            }
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(context, "Error, ${it.toString()}", Toast.LENGTH_SHORT).show()
            binding.productListEmpty.visibility = View.VISIBLE
        }
    }
}