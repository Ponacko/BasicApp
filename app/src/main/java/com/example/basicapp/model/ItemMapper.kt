package com.example.basicapp.model

import com.example.basicapp.data.ItemEntity

// Define a mapper object
object ItemMapper {

    private fun mapToEntity(item: Item): ItemEntity {
        return ItemEntity(
            id = item.id,
            name = item.name
        )
    }

    private fun mapToModel(itemEntity: ItemEntity): Item {
        return Item(
            id = itemEntity.id,
            name = itemEntity.name
        )
    }

    fun mapToEntityList(items: List<Item>): List<ItemEntity> {
        return items.map { mapToEntity(it) }
    }

    fun mapToModelList(itemEntities: List<ItemEntity>): List<Item> {
        return itemEntities.map { mapToModel(it) }
    }
}