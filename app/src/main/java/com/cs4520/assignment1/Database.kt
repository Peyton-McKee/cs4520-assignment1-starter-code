package com.cs4520.assignment1

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProductItemRoom::class], version = 1)
abstract class Database: RoomDatabase() {
    abstract fun productItemDao(): ProductItemDao

    companion object {
        private var instance: com.cs4520.assignment1.Database? = null

        fun setInstance(context: Context) {
            val db = Room.databaseBuilder(
                context,
                com.cs4520.assignment1.Database::class.java, "my-database"
            ).build()
            instance = db
        }

        fun getInstance(): com.cs4520.assignment1.Database {
            if (instance == null) {
                throw Exception("Database instance not set")
            }
            return instance!!
        }
    }
}