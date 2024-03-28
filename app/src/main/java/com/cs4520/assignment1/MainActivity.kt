package com.cs4520.assignment1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = applicationContext
        Database.setInstance(context)
        val db = Database.getInstance()

        setContent {
            Surface {
                NavigationView(db)
            }
        }
    }

}
