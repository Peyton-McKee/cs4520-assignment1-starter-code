package com.cs4520.assignment1

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "product_item")
data class ProductItemRoom (
    @PrimaryKey val name: String,
    @ColumnInfo(name = "expiry_date") val expiryDate: String?,
    @ColumnInfo(name = "price") val price: Float,
    @ColumnInfo(name = "type") val type: String
) {
    fun toProductItem(): ProductItem? {
        return when (type) {
            "Food" -> ProductItem.Food(name, expiryDate ?: "", price)
            "Equipment" -> ProductItem.Equipment(name, expiryDate ?: "", price)
            else -> null
        }
    }
}