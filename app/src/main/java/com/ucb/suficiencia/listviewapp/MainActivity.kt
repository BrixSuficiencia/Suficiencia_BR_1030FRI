package com.ucb.suficiencia.listviewapp

import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.os.SystemClock
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var editText: EditText
    private lateinit var addButton: Button
    private lateinit var uploadImageButton: Button
    private var selectedImage: Bitmap? = null
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
        uploadImageButton = findViewById(R.id.uploadImageButton)

        adapter = CustomListAdapter(this, items)
        listView.adapter = adapter

        // Image picker for image upload
        val imagePicker = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                val inputStream = contentResolver.openInputStream(uri)
                selectedImage = BitmapFactory.decodeStream(inputStream)
            }
        }

        // Button to upload image
        uploadImageButton.setOnClickListener {
            imagePicker.launch("image/*")
        }

        // Add new item to list
        addButton.setOnClickListener {
            val newItemText = editText.text.toString()
            if (newItemText.isNotEmpty()) {
                val newItem = ListItem(newItemText, selectedImage, false)
                items.add(newItem)
                adapter.notifyDataSetChanged()
                selectedImage = null // Reset the image after adding the item
                editText.text.clear() // Clear text
            }
        }

        // Handle double-click on list item
        listView.setOnItemClickListener { _, _, position, _ ->
            val clickTime = SystemClock.elapsedRealtime()
            Toast.makeText(this, "Item clicked", Toast.LENGTH_SHORT).show() // Debugging toast

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
