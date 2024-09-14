package com.example.basicapp.repository

import com.example.basicapp.network.ItemNetworkSource

class ItemRepository(
    private val itemNetworkSource: ItemNetworkSource
) {
    fun getItemsFromDatabase() {
        // TODO implement
    }

    suspend fun getItemsFromApi() = itemNetworkSource.fetchItems()
}