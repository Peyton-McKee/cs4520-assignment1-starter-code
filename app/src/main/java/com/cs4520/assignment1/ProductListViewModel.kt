package com.cs4520.assignment1

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class ProductListViewModel: ViewModel() {
    val productsLiveData: MutableLiveData<List<ProductItem>> by lazy {
        MutableLiveData<List<ProductItem>>()
    }

    val errorLiveData: MutableLiveData<Exception?> by lazy {
        MutableLiveData<Exception?>()
    }
    val loadingLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun loadProducts() {
        loadingLiveData.postValue(true)

        GlobalScope.launch {
            try {
                val service = API.getInstance().create(ProductService::class.java)
                val result = service.listProducts(null).execute().body()

                Log.d("ProductListViewModel", "result: $result")

                if (result != null) {
                    val products = mutableListOf<ProductItem>()
                    result.forEach {
                        val productItem = it.toProductItem()
                        if (productItem != null) {
                            products.add(productItem)
                        }
                    }

                    Log.d("ProductListViewModel", "result: $products")

                    productsLiveData.postValue(products)
                } else {
                    errorLiveData.postValue(Exception("Failed to load products"))
                }
            } catch (e: Exception) {
                errorLiveData.postValue(e)
            } finally {
                loadingLiveData.postValue(false)
            }
        }
    }
}