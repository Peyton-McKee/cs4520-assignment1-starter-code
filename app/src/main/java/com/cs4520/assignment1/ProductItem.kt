package com.cs4520.assignment1

sealed class ProductItem {
    abstract val name: String
    abstract val expirationDate: String
    abstract val price: Float
    data class Food(override val name: String, override val expirationDate: String,
                    override val price: Float): ProductItem()
    data class Equipment(override val name: String, override val expirationDate: String,
                         override val price: Float): ProductItem()
}