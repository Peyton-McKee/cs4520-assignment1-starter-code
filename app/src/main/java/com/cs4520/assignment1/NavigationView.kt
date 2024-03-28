package com.cs4520.assignment1

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationView(database: Database) {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginView(viewModel = LoginViewModel(navController))
        }
        composable("productList") {
            val viewModel = ProductListViewModel(database)

            LaunchedEffect(viewModel) {
                viewModel.loadProducts()
            }

            ProductList(viewModel = viewModel)
        }
    }
}