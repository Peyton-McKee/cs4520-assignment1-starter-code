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
        }
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadProducts()
    }

    private fun ProductListBinding.addViewModelObservers() {
        viewModel.productsLiveData.observe(viewLifecycleOwner) {
            binding.productListProgressBar.visibility = ProgressBar.GONE
            if (it.isEmpty()) {
                binding.productListEmpty.visibility = View.VISIBLE
            } else {
                val adapter = ProductListAdapter(it.toTypedArray())
                binding.productList.adapter = adapter
                Log.d("ProductListFragment", "Product list: $it")
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
            Log.d("ProductListFragment", "Error, ${it.toString()}")
            Toast.makeText(context, "Error, ${it.toString()}", Toast.LENGTH_SHORT).show()
            binding.productListEmpty.visibility = View.VISIBLE
        }
    }
}