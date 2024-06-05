package com.example.videoplayer

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

// The GridAdapter class is a custom adapter for a RecyclerView that displays a grid of items
class GridAdapter(
    private val context: Context, // Context for accessing resources and inflating layouts
    private val items: List<GridItem>, // List of items to be displayed in the grid
    private val clickListener: (GridItem) -> Unit // Lambda function to handle item clicks
) : RecyclerView.Adapter<GridAdapter.GridViewHolder>() {

    // ViewHolder class to hold references to the views for each grid item
    class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView) // TextView for the item title
        val thumbnail: ImageView = itemView.findViewById(R.id.thumbnail) // ImageView for the video thumbnail
    }

    // Inflates the layout for each grid item and creates a ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false)
        return GridViewHolder(view)
    }

    // Binds data to the views in the ViewHolder for the specified position
    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val item = items[position] // Get the item at the specified position
        holder.titleTextView.text = item.title // Set the item title
        holder.itemView.setOnClickListener { clickListener(item) } // Set the click listener for the item

        // Load the video thumbnail using Glide
        Glide.with(context)
            .asBitmap() // Request a bitmap to be loaded
            .load(item.videoUri) // Load the video URI
            .apply(RequestOptions().frame(1000000)) // Get a frame at 1 second (1,000,000 microseconds)
            .into(holder.thumbnail) // Load the bitmap into the ImageView
    }

    // Returns the total number of items in the adapter
    override fun getItemCount() = items.size
}

// Data class representing a grid item with a title and video URI
data class GridItem(val title: String, val videoUri: Uri)


