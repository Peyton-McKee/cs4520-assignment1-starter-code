package com.cs4520.assignment1

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun ProductItemView(item: ProductItem, painter: Painter) {
    Row {
        Image(painter = painter, contentDescription = "Content Image")

        Column {
            Text(text = item.name)
            Text(text = item.price.toString())
            Text(text = item.expirationDate)
        }
    }
}