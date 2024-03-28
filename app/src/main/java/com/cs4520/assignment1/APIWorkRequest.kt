package com.cs4520.assignment1

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception

class APIWorkRequest(appContext: Context, workerParams: WorkerParameters, private val database: Database) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val service = API.getInstance().create(ProductService::class.java)
            try {
                val result = service.listProducts(0).execute().body()
                if (result != null) {
                    val products = mutableListOf<ProductItem>()
                    result.forEach {
                        val productItem = it.toProductItem()
                        if (productItem != null && products.contains(productItem).not()) {
                            products.add(productItem)
                        }
                    }

                    val productItemDao = database.productItemDao()

                    products.forEach {
                        productItemDao.insertProductItem(it.toProductItemRoom())
                    }
                } else {
                    throw Exception("Failed to load products")
                }
            } catch (e: Exception) {
                Log.d("APIWorkManager", "Failed to load products", e)
                return Result.failure()
        }
        // Indicate whether the work finished successfully with the Result
        return Result.success()
    }
}
