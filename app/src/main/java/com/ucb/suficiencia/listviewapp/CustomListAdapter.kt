package com.ucb.suficiencia.listviewapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView

// Data class to hold each list item's details
data class ListItem(var text: String, var image: Bitmap?, var isChecked: Boolean)

class CustomListAdapter(context: Context, private val items: MutableList<ListItem>) :
    ArrayAdapter<ListItem>(context, R.layout.list_item, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
        val itemText = view.findViewById<TextView>(R.id.itemText)
        val itemImage = view.findViewById<ImageView>(R.id.itemImage)

        val item = items[position]

        // Update the item's views
        checkBox.isChecked = item.isChecked
        itemText.text = item.text

        // Set the image or default image if null
        if (item.image != null) {
            itemImage.setImageBitmap(item.image)
        } else {
            // Ensure the correct default image is used
            itemImage.setImageBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.defaultimage))
        }

        // Handle checkbox changes
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            item.isChecked = isChecked
        }

        return view
    }
}
