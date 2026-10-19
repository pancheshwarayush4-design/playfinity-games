package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.data.GameCategory
import com.example.ui.screens.AboutAndContactScreen
import com.example.ui.screens.CategoriesScreen
import com.example.ui.screens.DownloadGamesScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LiveWebPortalOverlay
import com.example.ui.screens.PlayOnlineScreen
import com.example.ui.theme.BorderNeonSubtle
import com.example.ui.theme.CardSurfaceDark
import com.example.ui.theme.DeepSpaceDark
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.PlayfinityGamesTheme
import com.example.ui.theme.TextPrimaryWhite
import com.example.ui.theme.TextSecondaryMuted
import com.example.ui.theme.WarningAmber
import com.example.util.rememberIsOnline

enum class PlayfinityDestination(
    val label: String,
    val icon: ImageVector,
    val testTag: String
) {
    HOME("Home", Icons.Default.Home, "nav_home"),
    DOWNLOADS("Download", Icons.Default.Download, "nav_downloads"),
    PLAY_ONLINE("Play Online", Icons.Default.SportsEsports, "nav_play_online"),
    CATEGORIES("Categories", Icons.Default.Category, "nav_categories"),
    ABOUT_CONTACT("About & Code", Icons.Default.Info, "nav_about_contact")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlayfinityGamesTheme {
                PlayfinityGamesApp()
            }
        }
    }
}

@Composable
fun PlayfinityGamesApp() {
    var currentDestination by rememberSaveable { mutableStateOf(PlayfinityDestination.HOME) }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var selectedCategory by rememberSaveable { mutableStateOf<GameCategory?>(null) }
    var activeOnlineGameId by rememberSaveable { mutableStateOf("cyber-drift") }
    var isArcadeFullScreen by rememberSaveable { mutableStateOf(false) }
    var showLiveWebPortal by rememberSaveable { mutableStateOf(false) }
    val isOnline by rememberIsOnline()

    // BackHandler for secondary screens returning to Home
    if (currentDestination != PlayfinityDestination.HOME && !showLiveWebPortal && !isArcadeFullScreen) {
        BackHandler {
            currentDestination = PlayfinityDestination.HOME
        }
    }

    if (showLiveWebPortal) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(ObsidianBlack)
                .statusBarsPadding(),
            color = ObsidianBlack
        ) {
            LiveWebPortalOverlay(
                onClose = { showLiveWebPortal = false }
            )
        }
        return
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = ObsidianBlack,
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            if (!isArcadeFullScreen) {
                PlayfinityTopHeader(
                    isOnline = isOnline,
                    onLogoClick = { currentDestination = PlayfinityDestination.HOME },
                    onLaunchWebPortal = { showLiveWebPortal = true }
                )
            }
        },
        bottomBar = {
            if (!isArcadeFullScreen) {
                NavigationBar(
                    containerColor = DeepSpaceDark,
                    tonalElevation = 8.dp
                ) {
                    PlayfinityDestination.entries.forEach { dest ->
                        val selected = currentDestination == dest
                        NavigationBarItem(
                            selected = selected,
                            onClick = { currentDestination = dest },
                            icon = {
                                Icon(
                                    imageVector = dest.icon,
                                    contentDescription = dest.label
                                )
                            },
                            label = {
                                Text(
                                    text = dest.label,
                                    maxLines = 1
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = ObsidianBlack,
                                selectedTextColor = ElectricBlue,
                                indicatorColor = ElectricBlue,
                                unselectedIconColor = TextSecondaryMuted,
                                unselectedTextColor = TextSecondaryMuted
                            ),
                            modifier = Modifier.testTag(dest.testTag)
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentDestination) {
                PlayfinityDestination.HOME -> {
                    HomeScreen(
                        searchQuery = searchQuery,
                        onSearchQueryChange = { searchQuery = it },
                        onNavigateToDownloads = { category ->
                            selectedCategory = category
                            currentDestination = PlayfinityDestination.DOWNLOADS
                        },
                        onNavigateToPlayOnline = { gameId ->
                            activeOnlineGameId = gameId
                            currentDestination = PlayfinityDestination.PLAY_ONLINE
                        },
                        onNavigateToCategories = {
                            currentDestination = PlayfinityDestination.CATEGORIES
                        },
                        onOpenLiveWebPortal = {
                            showLiveWebPortal = true
                        }
                    )
                }

                PlayfinityDestination.DOWNLOADS -> {
                    DownloadGamesScreen(
                        selectedCategory = selectedCategory,
                        onSelectCategory = { selectedCategory = it },
                        searchQuery = searchQuery,
                        onSearchQueryChange = { searchQuery = it }
                    )
                }

                PlayfinityDestination.PLAY_ONLINE -> {
                    PlayOnlineScreen(
                        activeGameId = activeOnlineGameId,
                        onSelectGame = { activeOnlineGameId = it },
                        isFullScreen = isArcadeFullScreen,
                        onToggleFullScreen = { isArcadeFullScreen = it },
                        onOpenLiveWebPortal = { showLiveWebPortal = true }
                    )
                }

                PlayfinityDestination.CATEGORIES -> {
                    CategoriesScreen(
                        onSelectCategoryForDownloads = { category ->
                            selectedCategory = category
                            currentDestination = PlayfinityDestination.DOWNLOADS
                        }
                    )
                }

                PlayfinityDestination.ABOUT_CONTACT -> {
                    AboutAndContactScreen(
                        onOpenLiveWebPortal = { showLiveWebPortal = true }
                    )
                }
            }
        }
    }
}

@Composable
private fun PlayfinityTopHeader(
    isOnline: Boolean,
    onLogoClick: () -> Unit,
    onLaunchWebPortal: () -> Unit
) {
    Surface(
        color = DeepSpaceDark,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    onClick = onLogoClick,
                    color = DeepSpaceDark,
                    modifier = Modifier.testTag("brand_logo_header")
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.FlashOn,
                            contentDescription = null,
                            tint = ElectricBlue,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = buildAnnotatedString {
                                withStyle(SpanStyle(color = TextPrimaryWhite, fontWeight = FontWeight.Bold)) {
                                    append("PLAYFINITY ")
                                }
                                withStyle(SpanStyle(color = ElectricBlue, fontWeight = FontWeight.Bold)) {
                                    append("GAMES")
                                }
                            },
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                }

                Surface(
                    onClick = onLaunchWebPortal,
                    color = CardSurfaceDark,
                    shape = RoundedCornerShape(50),
                    border = BorderStroke(1.dp, ElectricBlue),
                    modifier = Modifier.testTag("top_web_mode_button")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = "Open HTML5 Web Mode",
                            tint = ElectricBlue,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Web Portal",
                            style = MaterialTheme.typography.labelLarge,
                            color = ElectricBlue
                        )
                    }
                }
            }

            if (!isOnline) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(WarningAmber.copy(alpha = 0.16f))
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .testTag("offline_connectivity_banner"),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.WifiOff,
                        contentDescription = null,
                        tint = WarningAmber,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Offline Mode: Local HTML5 games work offline; store downloads require internet.",
                        style = MaterialTheme.typography.labelMedium,
                        color = WarningAmber
                    )
                }
            }

            HorizontalDivider(color = BorderNeonSubtle, thickness = 1.dp)
        }
    }
}
