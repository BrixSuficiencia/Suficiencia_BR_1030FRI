package com.ucb.suficiencia.bottomnavigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.graphics.Bitmap

data class ListItem(var text: String, var image: Bitmap?, var isChecked: Boolean)

class ListViewModel : ViewModel() {
    private val _items = MutableLiveData<MutableList<ListItem>>()

    init {
        // Initialize with an empty list
        _items.value = mutableListOf()
    }

    val items: LiveData<MutableList<ListItem>>
        get() = _items

    // Method to add an item
    fun addItem(item: ListItem) {
        _items.value?.add(item)
        _items.value = _items.value // Trigger live data update
    }

    // Method to remove an item
    fun removeItem(position: Int) {
        _items.value?.removeAt(position)
        _items.value = _items.value // Trigger live data update
    }

    // Method to update an item
    fun updateItem(position: Int, newText: String) {
        _items.value?.get(position)?.text = newText
        _items.value = _items.value // Trigger live data update
    }
}
