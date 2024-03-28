package com.cs4520.assignment1

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource


@Composable
fun ProductList(viewModel: ProductListViewModel) {
    val products = viewModel.productsLiveData.collectAsState()
    val loading = viewModel.loadingLiveData.collectAsState()
    val error = viewModel.errorLiveData.collectAsState()

    val context = LocalContext.current

    if (loading.value) {
        CircularProgressIndicator()
    } else {
        if (products.value.isEmpty()) {
            Text(text = "No products found")
        } else {
            LazyColumn {
                items(products.value) { item ->
                    when (item) {
                        is ProductItem.Food -> ProductItemView(
                            item = item,
                            painter = painterResource(id = R.drawable.food),
                            backgroundColor = Color.Yellow
                        )

                        is ProductItem.Equipment -> ProductItemView(
                            item = item,
                            painter = painterResource(id = R.drawable.equipment),
                            backgroundColor = Color.Green
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(error.value) {
        error.value?.let {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
    }
}