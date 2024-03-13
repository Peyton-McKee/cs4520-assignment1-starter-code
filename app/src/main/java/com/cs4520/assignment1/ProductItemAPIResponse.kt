package com.cs4520.assignment1

import android.util.Log

data class ProductItemAPIResponse (
    val name: String?,
    val expiryDate: String?,
    val price: Float?,
    val type: String?
) {
    fun toProductItem(): ProductItem? {
        if (name == null || price == null) {
            return null
        }

        return when (type) {
            "Food" -> ProductItem.Food(name, expiryDate ?: "", price)
            "Equipment" -> ProductItem.Equipment(name, expiryDate ?: "", price)
            else -> null
        }
    }
}
