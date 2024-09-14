package com.example.basicapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basicapp.data.User
import com.example.basicapp.model.Item
import com.example.basicapp.repository.ItemRepository
import com.example.basicapp.repository.UserRepository
import kotlinx.coroutines.launch
import org.json.JSONException
import java.io.IOException

class MainViewModel(
    private val itemRepository: ItemRepository,
    private val userRepository: UserRepository
): ViewModel() {

    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>> get() = _items
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error
    private val _savedUser = MutableLiveData<User?>()
    val savedUser: LiveData<User?> get() = _savedUser
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()

    init {
        loadUser(0)
        viewModelScope.launch {
            val itemsFromDatabase = itemRepository.getItemsFromDatabase()
            if (itemsFromDatabase.isNotEmpty()) {
                _items.value = itemsFromDatabase
            } else {
                fetchItems()
            }
        }
    }

    fun fetchItems() {
        _isLoading.value = true
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
            _isLoading.value = false
        }
    }

    fun saveUser() {
        val user = User(firstName.value ?: "", lastName.value ?: "")
        viewModelScope.launch {
            userRepository.saveUserToDatabase(user)
        }
        _savedUser.value = user
    }

    fun loadUser(id: Int) {
        viewModelScope.launch {
            _savedUser.value = userRepository.getUserFromDatabase(id)
        }
    }

    fun deleteUser(id: Int) {
        viewModelScope.launch {
            try {
                userRepository.deleteUserFromDatabase(id)
                _savedUser.value = null
            } catch (e: Exception) {
            _error.value = "Failed to delete user: ${e.message}"
        }
        }
    }
}