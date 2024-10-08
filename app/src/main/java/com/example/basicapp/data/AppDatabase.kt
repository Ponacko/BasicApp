package com.example.basicapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class, ItemEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun itemDao(): ItemDao
}