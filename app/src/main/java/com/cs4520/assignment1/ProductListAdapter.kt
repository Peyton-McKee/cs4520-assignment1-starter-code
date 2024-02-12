package com.cs4520.assignment1

import android.graphics.Color
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductListAdapter(private val dataSet: Array<ProductItem>) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView
        val imageView: ImageView
        val layout: LinearLayout
        val dateTextView: TextView
        val priceTextView: TextView

        init {
            nameTextView = view.findViewById(R.id.productTextView)
            imageView = view.findViewById(R.id.productImageView)
            layout = view.findViewById(R.id.productLayout)
            dateTextView = view.findViewById(R.id.productDateTextView)
            priceTextView = view.findViewById(R.id.productPriceTextView)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.product, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.nameTextView.text = dataSet[position].name
        viewHolder.priceTextView.text = ("$ " + dataSet[position].price.toString())
        viewHolder.dateTextView.text = dataSet[position].expirationDate
        if (dataSet[position].expirationDate == "") {
            viewHolder.dateTextView.visibility = View.GONE
        }
        when (dataSet[position]) {
            is ProductItem.Equipment -> {
                viewHolder.layout.setBackgroundColor(Color.parseColor("#E06666"))
                viewHolder.imageView.setImageResource(R.drawable.equipment)
            }
            is ProductItem.Food -> {
                viewHolder.layout.setBackgroundColor(Color.parseColor("#FFD965"))
                viewHolder.imageView.setImageResource(R.drawable.food)
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
