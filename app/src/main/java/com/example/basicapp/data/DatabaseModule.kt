package com.example.basicapp.data

import androidx.room.Room
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    single { get<AppDatabase>().userDao() }
    single { get<AppDatabase>().itemDao() }
}