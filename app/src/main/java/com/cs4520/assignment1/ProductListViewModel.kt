package com.cs4520.assignment1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import java.lang.Exception

class ProductListViewModel(private val database: Database) : ViewModel() {
    private val _productsLiveData: MutableStateFlow<List<ProductItem>> = MutableStateFlow(emptyList())
    val productsLiveData: StateFlow<List<ProductItem>> = _productsLiveData

    private val _errorLiveData: MutableStateFlow<Exception?> = MutableStateFlow(null)
    val errorLiveData: StateFlow<Exception?> = _errorLiveData

    private val _loadingLiveData: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val loadingLiveData: StateFlow<Boolean> = _loadingLiveData
    init {
        loadProducts()

        Repository.productsLiveData.observeForever {
            _productsLiveData.value = it
        }
    }

    private fun loadProducts() {
        _loadingLiveData.value = true

        viewModelScope.launch(Dispatchers.IO) {
            try {
                APIWorkManager.clearAllJobs()
                Repository.fetchProducts()
                APIWorkManager.createJob()
            } catch (e: Exception) {
                _errorLiveData.value = e

                if (productsLiveData.value.isEmpty()) {
                    _productsLiveData.value = database.productItemDao()
                        .getAllProductItems()
                        .mapNotNull { it.toProductItem() }
                }
            } finally {
                _loadingLiveData.value = false
            }
        }
    }
}