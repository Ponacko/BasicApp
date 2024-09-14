package com.example.basicapp.network

import com.example.basicapp.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.IOException
import kotlin.random.Random

class FakeItemNetworkSource: ItemNetworkSource {

    private val delayTimeMillis = 2000L // 2 seconds

    override suspend fun fetchItems(): List<Item> = withContext(Dispatchers.IO) {
        // Simulate network delay
        delay(delayTimeMillis)

        return@withContext generateRandomAmountOfItems()
    }

    private fun generateRandomAmountOfItems(): List<Item> {
        val randomCount = Random.nextInt(1, 20)
        val items = List(randomCount) { i ->
            Item(i, "Fake item $i")
        }
        return items
    }
}