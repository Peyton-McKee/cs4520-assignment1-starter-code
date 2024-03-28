package com.cs4520.assignment1

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class ProductListViewModel(private val database: Database) : ViewModel() {
    private var pagesLoaded = 0

    val productsLiveData: MutableLiveData<List<ProductItem>> by lazy {
        MutableLiveData<List<ProductItem>>()
    }

    val errorLiveData: MutableLiveData<Exception?> by lazy {
        MutableLiveData<Exception?>()
    }
    val loadingLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    suspend fun loadProducts() {
        Log.d("ProductListViewModel", "Loading products")
        loadingLiveData.postValue(true)

        Log.d("ProductListViewModel", "Loading products")

        val service = API.getInstance().create(ProductService::class.java)
        withContext(Dispatchers.IO) {
            Log.d("ProductListViewModel", "Entering Context")
            try {
                val result = service.listProducts(pagesLoaded).execute().body()
                Log.d("ProductListViewModel", "Loaded products")
                if (result != null) {
                    pagesLoaded += 1
                    val products = mutableListOf<ProductItem>()
                    result.forEach {
                        val productItem = it.toProductItem()
                        if (productItem != null && products.contains(productItem).not()) {
                            products.add(productItem)
                        }
                    }

                    productsLiveData.postValue(products)

                    val productItemDao = database.productItemDao()

                    products.forEach {
                        productItemDao.insertProductItem(it.toProductItemRoom())
                    }
                } else {
                    throw Exception("Failed to load products")
                }
            } catch (e: Exception) {
                Log.d("ProductListViewModel", "Failed to load products", e)
                errorLiveData.postValue(e)

                if (productsLiveData.value.isNullOrEmpty()) {
                    val productItemDao = database.productItemDao()
                    val products =
                        productItemDao.getAllProductItems().mapNotNull { it.toProductItem() }
                    productsLiveData.postValue(products)
                }
            } finally {
                loadingLiveData.postValue(false)
            }
        }
    }
}