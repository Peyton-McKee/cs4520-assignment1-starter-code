package com.cs4520.assignment1

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductItemDao {
    @Insert
    fun insertProductItem(productItem: ProductItemRoom)

    @Query("SELECT * FROM product_item")
    fun getAllProductItems(): List<ProductItemRoom>

    @Query("DELETE FROM product_item")
    fun deleteAllProductItems()
}