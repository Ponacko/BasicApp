package com.example.basicapp.network

import com.example.basicapp.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.random.Random

class FakeItemNetworkSource: ItemNetworkSource {

    // Simulate a network delay
    private val delayTimeMillis = 2000L // 2 seconds

    // returns random number of fake items
    override suspend fun fetchItems(): List<Item> = withContext(Dispatchers.IO) {
        // Simulate network delay
        delay(delayTimeMillis)

        // Generate fake items
        val randomCount = Random.nextInt(1, 20)
        val items = List(randomCount) { i ->
            Item(i, "Fake item $i")
        }
        items
    }
}