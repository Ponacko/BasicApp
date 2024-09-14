package com.example.basicapp.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun initDb() {
        // Create an in-memory database that will be destroyed after the test runs
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()

        userDao = database.userDao()
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun closeDb() {
        database.close()
        Dispatchers.resetMain()
    }

    @Test
    fun testInsertAndGetUser() = runTest(testDispatcher) {
        val user = User(firstName = "John", lastName = "Doe")

        userDao.insert(user)

        val insertedUser = userDao.getById(1)

        assertNotNull(insertedUser)
        assertEquals("John", insertedUser.firstName)
        assertEquals("Doe", insertedUser.lastName)
    }

    @Test
    fun testDeleteUser() = runTest(testDispatcher) {
        val user = User(firstName = "Jane", lastName = "Smith")
        userDao.insert(user)

        val userToDelete = userDao.getById(1)
        userDao.delete(userToDelete)

        val deletedUser = try {
            userDao.getById(1)
        } catch (e: Exception) {
            null
        }

        assertEquals(null, deletedUser)
    }
}