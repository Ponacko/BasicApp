package com.example.basicapp.repository

import com.example.basicapp.data.User
import com.example.basicapp.data.UserDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(
    private val userDao: UserDao
) {
    suspend fun getUserFromDatabase(userId: Int): User {
        return userDao.getById(userId)
    }

    suspend fun saveUserToDatabase(user: User) {
        withContext(Dispatchers.IO) {
            userDao.insert(user)
        }
    }

    fun getUserFromApi() {
        // TODO implement
    }
}