package com.example

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import com.example.data.GameCategory
import com.example.data.PlayfinityRepository
import com.example.ui.theme.PlayfinityGamesTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36], qualifiers = "w411dp-h891dp")
class ExampleRobolectricTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `verify app name and branding resources`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Playfinity Games", appName)
    }

    @Test
    fun `verify website assets exist and contain required gaming sections and playable games`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val indexHtml = context.assets.open("web/index.html").bufferedReader().use { it.readText() }
        val styleCss = context.assets.open("web/style.css").bufferedReader().use { it.readText() }
        val scriptJs = context.assets.open("web/script.js").bufferedReader().use { it.readText() }

        assertTrue(indexHtml.contains("PLAYFINITY"))
        assertTrue(indexHtml.contains("ENTER THE WORLD OF GAMING"))
        assertTrue(indexHtml.contains("id=\"download-games\""))
        assertTrue(indexHtml.contains("id=\"play-online\""))
        assertTrue(indexHtml.contains("id=\"categories\""))
        assertTrue(indexHtml.contains("id=\"about\""))
        assertTrue(styleCss.contains("--electric-blue"))
        assertTrue(scriptJs.contains("https://play.google.com/store/apps/details"))
        assertTrue(scriptJs.contains("https://f-droid.org/packages/"))
        assertTrue(scriptJs.contains("updateCyberDrift"))
    }

    @Test
    fun `verify repository has all 6 categories and authorized official download links`() {
        val games = PlayfinityRepository.downloadableGames
        assertEquals(12, games.size)

        val coveredCategories = games.map { it.category }.toSet()
        assertEquals(GameCategory.entries.toSet(), coveredCategories)

        games.forEach { game ->
            val isAuthorizedStore = game.officialUrl.startsWith("https://play.google.com/") ||
                game.officialUrl.startsWith("https://f-droid.org/")
            assertTrue("Link must be official store: ${game.officialUrl}", isAuthorizedStore)
        }

        assertTrue(PlayfinityRepository.onlineBrowserGames.size >= 3)
    }

    @Test
    fun `verify compose UI navigation across home, downloads, categories, and about`() {
        composeTestRule.setContent {
            PlayfinityGamesTheme {
                PlayfinityGamesApp()
            }
        }
        composeTestRule.waitForIdle()

        // Home Hero check
        composeTestRule.onNodeWithTag("hero_heading_text").assertIsDisplayed()
        composeTestRule.onNodeWithText("ENTER THE WORLD OF GAMING").assertIsDisplayed()

        // Navigate to Categories tab
        composeTestRule.onNodeWithTag("nav_categories").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("GAME CATEGORIES").assertIsDisplayed()

        // Navigate to Downloads tab and test search filtering
        composeTestRule.onNodeWithTag("nav_downloads").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("DOWNLOAD GAMES").assertIsDisplayed()
        composeTestRule.onNodeWithTag("download_search_input").performTextInput("Shadow Fight")
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("game_card_shadow_fight_4").assertIsDisplayed()

        // Navigate to About & Code tab
        composeTestRule.onNodeWithTag("nav_about_contact").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("ABOUT US").assertIsDisplayed()
    }
}
