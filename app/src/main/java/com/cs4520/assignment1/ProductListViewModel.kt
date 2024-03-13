package com.cs4520.assignment1

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class ProductListViewModel: ViewModel() {
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

    suspend fun loadProducts(context: Context) {
        loadingLiveData.postValue(true)

        withContext(Dispatchers.IO) {
            val db = Room.databaseBuilder(
                context,
                Database::class.java, "my-database"
            ).build()

            try {
                val service = API.getInstance().create(ProductService::class.java)
                val result = service.listProducts(pagesLoaded).execute().body()
                pagesLoaded += 1

                if (result != null) {
                    val products = mutableListOf<ProductItem>()
                    result.forEach {
                        val productItem = it.toProductItem()
                        if (productItem != null && products.contains(productItem).not()) {
                            products.add(productItem)
                        }
                    }

                    productsLiveData.postValue(products)

                    val productItemDao = db.productItemDao()

                    products.forEach {
                        productItemDao.insertProductItem(it.toProductItemRoom())
                    }
                } else {
                    throw Exception("Failed to load products")
                }
            } catch (e: Exception) {
                errorLiveData.postValue(e)

                if (productsLiveData.value.isNullOrEmpty()) {
                    val productItemDao = db.productItemDao()
                    val products = productItemDao.getAllProductItems().mapNotNull { it.toProductItem() }
                    productsLiveData.postValue(products)
                }
            } finally {
                loadingLiveData.postValue(false)
            }
        }
    }
}