package com.cs4520.assignment1

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ProductItemDao {
    @Upsert
    fun insertProductItem(productItem: ProductItemRoom)

    @Query("SELECT * FROM product_item")
    fun getAllProductItems(): List<ProductItemRoom>

    @Query("DELETE FROM product_item")
    fun deleteAllProductItems()
}