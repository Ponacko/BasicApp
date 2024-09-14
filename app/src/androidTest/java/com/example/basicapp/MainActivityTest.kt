package com.example.basicapp

import android.os.SystemClock
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withSubstring
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.basicapp.ui.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testFetchItemsButton() {
        onView(withId(R.id.fetchButton)).perform(click())

        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
        SystemClock.sleep(2000)
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun testSaveAndLoadUser() {
        onView(withId(R.id.firstName)).perform(typeText("John"))
        onView(withId(R.id.lastName)).perform(typeText("Doe"))

        onView(withId(R.id.lastName)).perform(closeSoftKeyboard())

        onView(withId(R.id.saveButton)).perform(click())

        onView(withId(R.id.loadedUserText)).check(matches(withSubstring("John Doe")))
    }
}