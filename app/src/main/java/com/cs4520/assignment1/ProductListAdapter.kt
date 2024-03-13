package com.cs4520.assignment1

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView


class ProductListAdapter(private var dataSet: MutableList<ProductItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM = 0
    private val LOADING = 1
    private var isLoadingAdded = false

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView
        val imageView: ImageView
        val layout: ConstraintLayout
        val dateTextView: TextView
        val priceTextView: TextView

        init {
            nameTextView = view.findViewById(R.id.productTextView)
            imageView = view.findViewById(R.id.productImageView)
            layout = view.findViewById(R.id.productItemConstraintLayout)
            dateTextView = view.findViewById(R.id.productDateTextView)
            priceTextView = view.findViewById(R.id.productPriceTextView)
        }
    }

    class LoadingVH(itemView: View) : RecyclerView.ViewHolder(itemView) {}


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // Create a new view, which defines the UI of the list item
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(viewGroup.context)

        when (viewType) {
            ITEM -> viewHolder = ViewHolder(LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.product, viewGroup, false))
            LOADING -> {
                val v2: View = inflater.inflate(R.layout.product_progress, viewGroup, false)
                viewHolder = LoadingVH(v2)
            }
        }

        if (viewHolder == null) {
            viewHolder = LoadingVH(LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.product_progress, viewGroup, false))
        }

        return viewHolder

    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        when (viewHolder) {
            is ViewHolder -> {
                // Get element from your dataset at this position and replace the
                // contents of the view with that element
                viewHolder.nameTextView.text = dataSet[position].name
                viewHolder.priceTextView.text = ("""$ ${dataSet[position].price}""")
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
            is LoadingVH -> {
                // Do nothing
            }
        }
    }

    override fun getItemCount() = dataSet.size

    override fun getItemViewType(position: Int): Int {
        return if (position == dataSet.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    fun updateData(newData: List<ProductItem>) {
        dataSet.addAll(newData)
    }
}
