package com.ucb.suficiencia.bottomnavigation

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import android.os.SystemClock
import android.app.AlertDialog

class ListFragment : Fragment(R.layout.fragment_list) {

    private lateinit var listView: ListView
    private lateinit var editText: EditText
    private lateinit var addButton: Button
    private lateinit var adapter: CustomListAdapter

    private var lastClickTime: Long = 0
    private val DOUBLE_CLICK_TIME_DELTA: Long = 300

    // Using the ViewModel for list data persistence
    private val listViewModel: ListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        listView = view.findViewById(R.id.listView)
        editText = view.findViewById(R.id.editText)
        addButton = view.findViewById(R.id.addButton)

        // Observe the LiveData from the ViewModel
        listViewModel.items.observe(viewLifecycleOwner) { items ->
            adapter = CustomListAdapter(requireContext(), items)
            listView.adapter = adapter
        }

        // Adding a new item
        addButton.setOnClickListener {
            val newItemText = editText.text.toString()
            if (newItemText.isNotEmpty()) {
                val newItem = ListItem(newItemText, BitmapFactory.decodeResource(resources, R.drawable.listimage), false)
                listViewModel.addItem(newItem)
                editText.text.clear()
            }
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val clickTime = SystemClock.elapsedRealtime()
            if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                showEditDeleteDialog(position)
            }
            lastClickTime = clickTime
        }

        return view
    }

    private fun showEditDeleteDialog(position: Int) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit_delete, null)
        val dialogEditText = dialogView.findViewById<EditText>(R.id.dialogEditText)
        dialogEditText.setText(listViewModel.items.value?.get(position)?.text)

        AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton("Edit") { dialog, _ ->
                listViewModel.updateItem(position, dialogEditText.text.toString())
                dialog.dismiss()
            }
            .setNegativeButton("Delete") { dialog, _ ->
                listViewModel.removeItem(position)
                dialog.dismiss()
            }
            .create()
            .show()
    }
}
