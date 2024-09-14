package com.example.basicapp.repository

import com.example.basicapp.data.ItemDao
import com.example.basicapp.model.Item
import com.example.basicapp.model.ItemMapper
import com.example.basicapp.network.ItemNetworkSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ItemRepository(
    private val itemNetworkSource: ItemNetworkSource,
    private val itemDao: ItemDao
) {
    suspend fun getItemsFromDatabase(): List<Item> = withContext(Dispatchers.IO) {
        val itemEntities = itemDao.getAll()
        val items = ItemMapper.mapToModelList(itemEntities)
        return@withContext items
    }

    suspend fun getItemsFromApi(): List<Item> = withContext(Dispatchers.IO) {
        val items = itemNetworkSource.fetchItems()
        if (items.isNotEmpty()) {
            itemDao.deleteAll()
            val itemEntities = ItemMapper.mapToEntityList(items)
            itemDao.insertAll(itemEntities)
        }
        return@withContext items
    }
}