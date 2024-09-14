package com.example.basicapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basicapp.model.Item
import com.example.basicapp.repository.ItemRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val itemRepository: ItemRepository
): ViewModel() {

    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>>
        get() = _items

    fun fetchItems() {
        viewModelScope.launch {
            _items.value = itemRepository.getItemsFromApi()
        }
    }
}