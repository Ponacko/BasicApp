package com.example.basicapp.repository


import com.example.basicapp.data.User
import com.example.basicapp.data.UserDao
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
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

@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryUnitTest {

    private val userDao: UserDao = mockk()
    private lateinit var userRepository: UserRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        userRepository = UserRepository(userDao)

        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test getUserFromDatabase returns expected user`() = runTest(testDispatcher) {
        val mockUser = User(firstName = "John", lastName = "Doe").apply { id = 1 }
        coEvery { userDao.getById(1) } returns mockUser

        val result = userRepository.getUserFromDatabase(1)

        assertNotNull(result)
        assertEquals("John", result.firstName)
        assertEquals("Doe", result.lastName)
        assertEquals(1, result.id)

        coVerify(exactly = 1) { userDao.getById(1) }
    }

    @Test
    fun `test saveUserToDatabase inserts user correctly`() = runTest(testDispatcher) {
        val mockUser = User(firstName = "Jane", lastName = "Doe").apply { id = 2 }
        val userSlot = slot<User>()
        coEvery { userDao.insert(capture(userSlot)) } returns Unit

        userRepository.saveUserToDatabase(mockUser)

        coVerify(exactly = 1) { userDao.insert(mockUser) }
        assertEquals(mockUser, userSlot.captured)
    }
}