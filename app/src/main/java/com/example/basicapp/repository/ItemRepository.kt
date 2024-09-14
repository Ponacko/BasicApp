package com.example.basicapp.repository

import com.example.basicapp.network.ItemNetworkSource

class ItemRepository(
    private val itemNetworkSource: ItemNetworkSource
) {
    fun getItemsFromDatabase() {
        // TODO implement
    }

    fun getItemsFromApi() = itemNetworkSource.fetchItems()
}