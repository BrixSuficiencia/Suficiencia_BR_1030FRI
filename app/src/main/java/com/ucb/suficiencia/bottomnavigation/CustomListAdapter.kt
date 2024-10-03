package com.ucb.suficiencia.bottomnavigation

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView

// Import the ListItem from ListViewModel
import com.ucb.suficiencia.bottomnavigation.ListItem

class CustomListAdapter(context: Context, private val items: MutableList<ListItem>) :
    ArrayAdapter<ListItem>(context, R.layout.list_item, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
        val itemText = view.findViewById<TextView>(R.id.itemText)
        val itemImage = view.findViewById<ImageView>(R.id.itemImage)

        val item = items[position]

        checkBox.isChecked = item.isChecked
        itemText.text = item.text

        if (item.image != null) {
            itemImage.setImageBitmap(item.image)
        } else {
            itemImage.setImageBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.listimage))
        }

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            item.isChecked = isChecked
        }

        return view
    }
}
