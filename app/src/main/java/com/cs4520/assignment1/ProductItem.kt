package com.cs4520.assignment1

sealed class ProductItem {
    abstract val name: String
    abstract val expirationDate: String
    abstract val price: Int
    data class Food(override val name: String, override val expirationDate: String,
                    override val price: Int): ProductItem()
    data class Equipment(override val name: String, override val expirationDate: String,
                         override val price: Int): ProductItem()
}