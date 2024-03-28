package com.cs4520.assignment1

import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.cs4520.assignment5.R


@Composable
fun ProductList(viewModel: ProductListViewModel) {
    val products = viewModel.productsLiveData.observeAsState()
    val loading = viewModel.loadingLiveData.observeAsState()
    val error = viewModel.errorLiveData.observeAsState()

    val context = LocalContext.current

    if (loading.value == true) {
        return CircularProgressIndicator()
    }

    LazyColumn {
        items(products.value ?: emptyList()) { item ->
            when (item) {
                is ProductItem.Food -> ProductItemView(
                    item = item,
                    painter = painterResource(id = R.drawable.food)
                )

                is ProductItem.Equipment -> ProductItemView(
                    item = item,
                    painter = painterResource(id = R.drawable.equipment)
                )
            }
        }
    }

    LaunchedEffect(error.value) {
        error.value?.let {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
    }
}