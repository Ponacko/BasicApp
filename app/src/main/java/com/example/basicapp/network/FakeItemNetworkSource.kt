package com.example.basicapp.network

import com.example.basicapp.model.Item
import kotlin.random.Random

class FakeItemNetworkSource: ItemNetworkSource {

    // returns random number of fake items
    override fun fetchItems(): List<Item> {
        val randomCount = Random.nextInt(1, 20)
        val items = List(randomCount) { i ->
            Item(i, "Fake item $i")
        }
        return items
    }
}