package com.cs4520.assignment1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object Repository {
    private val _productsLiveData: MutableLiveData<List<ProductItem>> = MutableLiveData(emptyList())
    val productsLiveData: LiveData<List<ProductItem>> = _productsLiveData

    fun fetchProducts(random: Boolean = false) {
        val service = API.getInstance().create(ProductService::class.java)
        val result = if (random) {
            service.randomProduct().execute().body()
        } else {
            service.listProducts(0).execute().body()
        }
        if (result != null) {
            val products = mutableListOf<ProductItem>()
            result.forEach {
                val productItem = it.toProductItem()
                if (productItem != null && products.contains(productItem).not()) {
                    products.add(productItem)
                }
            }
            _productsLiveData.value = products
            addProductsToDatabase(products)
        } else {
            throw Exception("Failed to load products")
        }
    }

    private fun addProductsToDatabase(products: List<ProductItem>) {
        val productItemDao = Database.getInstance().productItemDao()
        products.forEach {
            productItemDao.insertProductItem(it.toProductItemRoom())
        }
    }
}