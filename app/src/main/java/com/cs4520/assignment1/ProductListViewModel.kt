package com.cs4520.assignment1

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    suspend fun loadProducts(context: Context) {
        loadingLiveData.postValue(true)

        withContext(Dispatchers.IO) {
            val db = Room.databaseBuilder(
                context,
                Database::class.java, "my-database"
            ).build()

            try {
                val service = API.getInstance().create(ProductService::class.java)
                val result = service.listProducts(null).execute().body()

                Log.d("ProductListViewModel", "result: $result")

                if (result != null) {
                    val products = mutableListOf<ProductItem>()
                    result.forEach {
                        val productItem = it.toProductItem()
                        if (productItem != null && products.contains(productItem).not()) {
                            products.add(productItem)
                        }
                    }

                    Log.d("ProductListViewModel", "result: $products")

                    productsLiveData.postValue(products)

                    val productItemDao = db.productItemDao()

                    productItemDao.deleteAllProductItems()

                    products.forEach {
                        productItemDao.insertProductItem(it.toProductItemRoom())
                    }
                } else {
                    throw Exception("Failed to load products")
                }
            } catch (e: Exception) {
                val productItemDao = db.productItemDao()

                val products = productItemDao.getAllProductItems().mapNotNull { it.toProductItem() }

                errorLiveData.postValue(e)

                productsLiveData.postValue(products)
            } finally {
                loadingLiveData.postValue(false)
            }
        }
    }
}