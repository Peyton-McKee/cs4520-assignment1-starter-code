package com.cs4520.assignment1

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProductItemRoom::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract fun productItemDao(): ProductItemDao
}