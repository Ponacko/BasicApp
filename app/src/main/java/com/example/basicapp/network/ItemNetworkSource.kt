package com.example.basicapp.network

import com.example.basicapp.model.Item

interface ItemNetworkSource {
    fun fetchItems(): List<Item>
}