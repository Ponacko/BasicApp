package com.example.basicapp

import android.net.http.HttpException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basicapp.model.Item
import com.example.basicapp.repository.ItemRepository
import kotlinx.coroutines.launch
import org.json.JSONException
import java.io.IOException

class MainViewModel(
    private val itemRepository: ItemRepository
): ViewModel() {

    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>>
        get() = _items
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchItems() {
        viewModelScope.launch {
            try {
                val itemsFromApi = itemRepository.getItemsFromApi()
                _items.value = itemsFromApi
            } catch (e: IOException) {
                _error.value = "Network error. Please check your internet connection. ${e.message}"
            } catch (e: JSONException) {
                _error.value = "Data error. Unable to process the response. ${e.message}"
            } catch (e: Exception) {
                _error.value = "An unexpected error occurred: ${e.message}"
            }
        }
    }
}