package com.example.basicapp.repository

import com.example.basicapp.data.ItemDao
import com.example.basicapp.model.Item
import com.example.basicapp.model.ItemMapper
import com.example.basicapp.network.ItemNetworkSource

class ItemRepository(
    private val itemNetworkSource: ItemNetworkSource,
    private val itemDao: ItemDao
) {
    suspend fun getItemsFromDatabase(): List<Item> {
        val itemEntities = itemDao.getAll()
        val items = ItemMapper.mapToModelList(itemEntities)
        return items
    }

    suspend fun getItemsFromApi(): List<Item> {
        val items = itemNetworkSource.fetchItems()
        if (items.isNotEmpty()) {
            itemDao.deleteAll()
            val itemEntities = ItemMapper.mapToEntityList(items)
            itemDao.insertAll(itemEntities)
        }
        return items
    }
}