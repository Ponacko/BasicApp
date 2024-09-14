package com.example.basicapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.basicapp.data.User
import com.example.basicapp.model.Item
import com.example.basicapp.repository.ItemRepository
import com.example.basicapp.repository.UserRepository
import com.example.basicapp.ui.MainViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    private val itemRepository: ItemRepository = mockk()
    private val userRepository: UserRepository = mockk()

    private lateinit var mainViewModel: MainViewModel

    private val testDispatcher = StandardTestDispatcher()

    private val itemsObserver: Observer<List<Item>> = mockk(relaxed = true)
    private val errorObserver: Observer<String> = mockk(relaxed = true)
    private val userObserver: Observer<User?> = mockk(relaxed = true)
    private val isLoadingObserver: Observer<Boolean> = mockk(relaxed = true)

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        mainViewModel = MainViewModel(itemRepository, userRepository)

        Dispatchers.setMain(testDispatcher)

        mainViewModel.items.observeForever(itemsObserver)
        mainViewModel.error.observeForever(errorObserver)
        mainViewModel.savedUser.observeForever(userObserver)
        mainViewModel.isLoading.observeForever(isLoadingObserver)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()

        mainViewModel.items.removeObserver(itemsObserver)
        mainViewModel.error.removeObserver(errorObserver)
        mainViewModel.savedUser.removeObserver(userObserver)
        mainViewModel.isLoading.removeObserver(isLoadingObserver)
    }

    @Test
    fun `test fetchItems success`() = runTest(testDispatcher) {
        val mockItems = listOf(Item(1, "Item1"), Item(2,"Item2"))
        coEvery { itemRepository.getItemsFromApi() } returns mockItems

        mainViewModel.fetchItems()

        advanceUntilIdle()

        coVerify { itemRepository.getItemsFromApi() }
        coVerify { isLoadingObserver.onChanged(true) }
        coVerify { itemsObserver.onChanged(mockItems) }
        coVerify { isLoadingObserver.onChanged(false) }
        assertNull(mainViewModel.error.value)
    }

    @Test
    fun `test fetchItems network error`() = runTest(testDispatcher) {
        val errorMessage = "Network error"
        coEvery { itemRepository.getItemsFromApi() } throws IOException(errorMessage)

        mainViewModel.fetchItems()

        advanceUntilIdle()

        coVerify { isLoadingObserver.onChanged(true) }
        coVerify { errorObserver.onChanged("Network error. Please check your internet connection. $errorMessage") }
        coVerify { isLoadingObserver.onChanged(false) }
        assertNull(mainViewModel.items.value)
    }

    @Test
    fun `test saveUser`() = runTest(testDispatcher) {
        val expectedFirstName = "John"
        val expectedLastName = "Doe"
        mainViewModel.firstName.value = expectedFirstName
        mainViewModel.lastName.value = expectedLastName

        val userSlot = slot<User>()
        coEvery { userRepository.saveUserToDatabase(capture(userSlot)) } returns Unit

        mainViewModel.saveUser()

        advanceUntilIdle()

        assertEquals(expectedFirstName, userSlot.captured.firstName)
        assertEquals(expectedLastName, userSlot.captured.lastName)
        coVerify { userRepository.saveUserToDatabase(userSlot.captured) }
    }

    @Test
    fun `test loadUser success`() = runTest(testDispatcher) {
        val mockUser = User("Jane", "Doe").apply { id = 1 }
        coEvery { userRepository.getUserFromDatabase(1) } returns mockUser

        mainViewModel.loadUser(1)

        advanceUntilIdle()

        coVerify { userRepository.getUserFromDatabase(1) }
        coVerify { userObserver.onChanged(mockUser) }
        assertEquals(mockUser, mainViewModel.savedUser.value)
    }
}