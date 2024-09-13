package com.ucb.suficiencia.listviewapp

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.os.SystemClock
import android.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var editText: EditText
    private lateinit var addButton: Button
    private val items = mutableListOf<ListItem>()
    private lateinit var adapter: CustomListAdapter

    // Double-click tracking
    private var lastClickTime: Long = 0
    private val DOUBLE_CLICK_TIME_DELTA: Long = 300

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
        editText = findViewById(R.id.editText)
        addButton = findViewById(R.id.addButton)

        adapter = CustomListAdapter(this, items)
        listView.adapter = adapter

        // Add new item to list
        addButton.setOnClickListener {
            val newItemText = editText.text.toString()
            if (newItemText.isNotEmpty()) {
                // Add new item with default image
                val newItem = ListItem(newItemText, BitmapFactory.decodeResource(resources, R.drawable.defaultimage), false)
                items.add(newItem)
                adapter.notifyDataSetChanged()
                editText.text.clear() // Clear text
            }
        }

        // Handle double-click on list item
        listView.setOnItemClickListener { _, _, position, _ ->
            val clickTime = SystemClock.elapsedRealtime()

            if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                // Double click detected
                showEditDeleteDialog(position)
            }
            lastClickTime = clickTime
        }
    }

    // Dialog to edit or delete the item
    private fun showEditDeleteDialog(position: Int) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_delete, null)
        val dialogEditText = dialogView.findViewById<EditText>(R.id.dialogEditText)
        dialogEditText.setText(items[position].text)

        AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("Edit") { dialog, _ ->
                items[position].text = dialogEditText.text.toString()
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            .setNegativeButton("Delete") { dialog, _ ->
                items.removeAt(position)
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            .create()
            .show()
    }
}
