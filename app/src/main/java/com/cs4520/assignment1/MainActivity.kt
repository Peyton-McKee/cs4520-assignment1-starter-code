package com.cs4520.assignment1

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Surface
import androidx.room.Room
import com.cs4520.assignment5.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = applicationContext
        val db = Room.databaseBuilder(
            context,
            Database::class.java, "my-database"
        ).build()

        setContent {
            Surface {
                NavigationView(db)
            }
        }
    }

}
