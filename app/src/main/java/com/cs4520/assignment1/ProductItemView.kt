package com.cs4520.assignment1

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun ProductItemView(item: ProductItem, painter: Painter, backgroundColor: Color = Color.White) {
    Row(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(16.dp)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painter,
            contentDescription = "Content Image",
            Modifier
                .height(80.dp)
                .width(80.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(text = item.name)
            Text(text = item.price.toString())
            Text(text = item.expirationDate)
        }
    }

}