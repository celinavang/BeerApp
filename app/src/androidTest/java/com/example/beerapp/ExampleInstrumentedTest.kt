package com.example.beerapp

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.beerapp.screens.Authentication
import com.example.beerapp.ui.theme.AppTheme

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MyComposeTest {

    @get:Rule val composeTestRule = createComposeRule()
    // use createAndroidComposeRule<YourActivity>() if you need access to
    // an activity

    @Test
    fun myTest() {
        // Start the app
        composeTestRule.setContent {
            MainScreen()
        }

        composeTestRule.onNodeWithTag("email").isDisplayed()
        composeTestRule.onNodeWithTag("email").performTextInput("fakemail@civah.dk")
        composeTestRule.onNodeWithTag("password").performTextInput("pass123")
        composeTestRule.onNodeWithText("SIGN IN").performClick()
        composeTestRule.onNodeWithTag("email").isNotDisplayed()

    }
}

class MyComposeTest2 {

    @get:Rule val composeTestRule = createComposeRule()
    // use createAndroidComposeRule<YourActivity>() if you need access to
    // an activity

    @Test
    fun myTest() {
        // Start the app
        composeTestRule.setContent {
            MainScreen()
        }

        composeTestRule.onNodeWithTag("email").isDisplayed()
        composeTestRule.onNodeWithTag("email").performTextInput("fakemail@civah.dk")
        composeTestRule.onNodeWithTag("password").performTextInput("pass")
        composeTestRule.onNodeWithTag("email").isDisplayed()
    }
}