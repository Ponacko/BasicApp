package com.example.basicapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.basicapp.repository.ItemRepository

class MainViewModel(
    private val itemRepository: ItemRepository
): ViewModel() {

    private val _items = MutableLiveData<Int>()
    val items: LiveData<Int>
        get() = _items

    fun fetchItems() = itemRepository.getItemsFromApi()
}