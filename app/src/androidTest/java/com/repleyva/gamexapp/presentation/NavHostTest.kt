package com.repleyva.gamexapp.presentation

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.platform.app.InstrumentationRegistry
import com.repleyva.gamexapp.R
import com.repleyva.gamexapp.presentation.screens.main.MainActivity
import com.repleyva.gamexapp.presentation.screens.main.MainScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavHostTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: TestNavHostController

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setupNewsNavHost() {
        hiltRule.inject()
        composeTestRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            MainScreen()
        }
    }

    @Test
    fun verifyNavigatorDisplayedAtStart() {
        composeTestRule.onNodeWithTag("navigatorTag").assertExists()
    }

    @Test
    fun verifyBottomNavigatorDisplayedAtStart() {
        composeTestRule.onNodeWithTag("bottomNavigatorTag").assertExists()
    }

    @Test
    fun verifyHomeScreenIsDisplayed() {
        composeTestRule.onNodeWithText(context.getString(R.string.title_home)).performClick()
        composeTestRule.onNodeWithTag("homeScreen").assertExists()
    }

    @Test
    fun verifySearchScreenIsDisplayed() {
        composeTestRule.onNodeWithText(context.getString(R.string.title_search)).performClick()
        composeTestRule.onNodeWithTag("searchScreen").assertExists()
    }

    @Test
    fun verifyBookmarksScreenIsDisplayed() {
        composeTestRule.onNodeWithText(context.getString(R.string.title_bookmark)).performClick()
        composeTestRule.onNodeWithTag("bookmarkScreen").assertExists()
    }

}
