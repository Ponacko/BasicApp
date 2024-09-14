package com.example.basicapp.network

import com.example.basicapp.model.Item

interface ItemNetworkSource {
    suspend fun fetchItems(): List<Item>
}