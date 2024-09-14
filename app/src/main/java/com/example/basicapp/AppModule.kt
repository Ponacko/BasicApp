package com.example.basicapp

import com.example.basicapp.network.FakeItemNetworkSource
import com.example.basicapp.network.ItemNetworkSource
import com.example.basicapp.repository.ItemRepository
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single<ItemNetworkSource> { FakeItemNetworkSource() }
    single { ItemRepository(get()) }

    viewModelOf(::MainViewModel)
}